package afk.gfx.athens;

import afk.gfx.Camera;
import afk.gfx.GfxEntity;
import afk.gfx.Light;
import afk.gfx.Resource;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.Collection;
import javax.media.opengl.GL2;

/**
 *
 * @author daniel
 */
public class AthensEntity extends GfxEntity
{
    private Athens engine;
    
    public static final Vec3 X_AXIS = new Vec3(1,0,0);
    public static final Vec3 Y_AXIS = new Vec3(0,1,0);
    public static final Vec3 Z_AXIS = new Vec3(0,0,1);
    
    protected Mesh mesh = null;
    protected Texture texture = null;
    protected Object material = null; // TODO: placeholder for materials in future
    protected Shader shader = null;
    
    // collection of composite entities
    private Collection<AthensEntity> children;
    protected AthensEntity parent = null;
    
    protected AthensEntity(Athens engine)
    {
        this.engine = engine;
    }
    
    protected Mat4 createWorldMatrix()
    {
        Mat4 monkeyWorld = new Mat4(1f);

        monkeyWorld = Matrices.translate(monkeyWorld, getWorldPosition());

        Vec3 worldRot = getWorldRotation();
        
        monkeyWorld = Matrices.rotate(monkeyWorld, worldRot.getY(), Y_AXIS);
        
        monkeyWorld = Matrices.rotate(monkeyWorld, worldRot.getX(), X_AXIS);

        monkeyWorld = Matrices.rotate(monkeyWorld, worldRot.getZ(), Z_AXIS);

        monkeyWorld = Matrices.scale(monkeyWorld, getWorldScale());
        
        return monkeyWorld;
    }
    
    protected void update(float delta)
    {
        if (children != null)
            for (AthensEntity entity :children)
                entity.update(delta);
    }
    
    protected void draw(GL2 gl, Camera camera, Light sun)
    {
        draw(gl, camera, sun, null);
    }
    
    protected void draw(GL2 gl, Camera camera, Light sun, Shader overrideShader)
    {
        // by default, active sets visibility of entity
        if (!active) return;
        
        if (overrideShader != null)
        {
            // TODO: hacks!
            if (!castShadows) return;
            
            overrideShader.updateUniform(gl, "world", createWorldMatrix());
            if (texture != null)
            {
                texture.use(gl, GL2.GL_TEXTURE0);
                overrideShader.updateUniform(gl, "tex", 0);
            }
            if (colour != null)
                overrideShader.updateUniform(gl, "colour", colour);
        }
        else if (shader != null)
        {
            shader.use(gl);

            if (texture != null)
            {
                texture.use(gl, GL2.GL_TEXTURE0);
                shader.updateUniform(gl, "tex", 0);
            }
            engine.getShadowMap().use(gl, GL2.GL_TEXTURE1);
            shader.updateUniform(gl, "shadowmap", 1);

            shader.updateUniform(gl, "world", createWorldMatrix());
            shader.updateUniform(gl, "view", camera.view);
            shader.updateUniform(gl, "projection", camera.projection);
            
            shader.updateUniform(gl, "lview", sun.getCamera().view);
            shader.updateUniform(gl, "lprojection", sun.getCamera().projection);

            shader.updateUniform(gl, "sun", sun.getPoint());
            shader.updateUniform(gl, "eye", camera.eye);

            if (colour != null)
                shader.updateUniform(gl, "colour", colour);
        }
        
        if (mesh != null)
            mesh.draw(gl);
        
        if (children != null)
            for (AthensEntity entity :children)
            {
                if (overrideShader == null)
                    entity.draw(gl, camera, sun);
                else
                    entity.draw(gl, camera, sun, overrideShader);
            }
    }
    
    @Override
    public void attachResource(Resource resource)
    {
        switch (resource.getType())
        {
            case Resource.WAVEFRONT_MESH:
            case Resource.PRIMITIVE_MESH:
            case Resource.HEIGHTMAP_MESH:
                mesh = (Mesh)resource;
                break;
            case Resource.TEXTURE_2D:
            case Resource.TEXTURE_CUBE:
                texture = (Texture)resource;
                break;
            case Resource.SHADER:
                shader = (Shader)resource;
                break;
            case Resource.MATERIAL:
                material = resource; // TODO: change this when we create an actual Material class.
                break;
            default:
                // TODO: throw new ResourceNotCompatableException();
                break;
        }
    }
    
    @Override
    public void addChild(GfxEntity entity)
    {
        if (children == null)
            children = new ArrayList<AthensEntity>();
        AthensEntity athensEntity = (AthensEntity)entity;
        children.add(athensEntity);
        athensEntity.parent = this;
    }
    
    @Override
    public void removeChild(GfxEntity entity)
    {
        if (children == null) return;
        AthensEntity athensEntity = (AthensEntity)entity;
        if (children.remove(athensEntity))
            athensEntity.parent = null;
    }

    @Override
    public Collection<? extends GfxEntity> removeAllChildren()
    {
        if (children == null) return new ArrayList<AthensEntity>();
        for (AthensEntity entity :children)
        {
            entity.parent = null;
        }
        Collection<? extends GfxEntity> result = children;
        children = null;
        return result;
    }
    
    @Override
    public GfxEntity getParent()
    {
        return parent;
    }

    public Mesh getMesh()
    {
        if (mesh == null && parent != null)
            return parent.getMesh();
        return mesh;
    }

    public Shader getShader()
    {
        if (shader == null && parent != null)
            return parent.getShader();
        return shader;
    }

    public Object getMaterial()
    {
        if (material == null && parent != null)
            return parent.getMaterial();
        return material;
    }

    public Texture getTexture()
    {
        if (texture == null && parent != null)
            return parent.getTexture();
        return texture;
    }
    
}