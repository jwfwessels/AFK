package afk.gfx.athens;


import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class Shader extends AthensResource
{
    public static final int POSITION_LOC = 1, NORMAL_LOC = 2;
    
    private int program;
    private int vertexShader;
    private int fragmentShader;

    public Shader(String name)
    {
        super(SHADER, name);
    }
    
    @Override
    public void load(GL2 gl) throws IOException
    {
        vertexShader = Utils.loadShaderProgram(gl, "shaders/"+name+"/vertex.glsl", GL2.GL_VERTEX_SHADER);
        fragmentShader = Utils.loadShaderProgram(gl, "shaders/"+name+"/fragment.glsl", GL2.GL_FRAGMENT_SHADER);

       /* Start by defining a shader program which acts as a container. */
       program = gl.glCreateProgram();

       /* Next, shaders are added to the shader program. */
       if (vertexShader != -1) gl.glAttachShader(program, vertexShader);
       if (fragmentShader != -1) gl.glAttachShader(program, fragmentShader);

       /* Finally, the program must be linked. */
       gl.glLinkProgram(program);

       /* Check if it linked  properly. */
       Utils.checkProgramLogInfo(gl, program);
       
       loaded.set(true);
    }

    @Override
    public void unload(GL2 gl)
    {
        loaded.set(false);
        gl.glDeleteShader(program);
    }
    
    public void use(GL2 gl)
    {
        gl.glUseProgram(program);
    }
    
    public int getProgram()
    {
        return program;
    }
    
    public void updateUniform(GL2 gl, String name, Mat4 value)
    {
        int uniform = Utils.findUniform(gl, name, program);
        if (uniform == -1) return;
        gl.glUniformMatrix4fv(uniform, 1, false, value.getBuffer());
    }
    
    public void updateUniform(GL2 gl, String name, float value)
    {
        int uniform = Utils.findUniform(gl, name, program);
        if (uniform == -1) return;
        gl.glUniform1f(uniform, value);
    }
    
    public void updateUniform(GL2 gl, String name, Vec3 value)
    {
        int uniform = Utils.findUniform(gl, name, program);
        if (uniform == -1) return;
        gl.glUniform3fv(uniform, 1, value.getBuffer());
    }
    
    public void updateUniform(GL2 gl, String name, Vec4 value)
    {
        int uniform = Utils.findUniform(gl, name, program);
        if (uniform == -1) return;
        gl.glUniform4fv(uniform, 1, value.getBuffer());
    }
    
    public void updateUniform(GL2 gl, String name, int value)
    {
        int uniform = Utils.findUniform(gl, name, program);
        if (uniform == -1) return;
        gl.glUniform1i(uniform, value);
    }
}
