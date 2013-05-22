
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javax.media.opengl.GL2;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */
public class Shader
{
    public static final int POSITION_LOC = 1, NORMAL_LOC = 2;
    
    protected int program;
    private int vertexShader;
    private int fragmentShader;
    
    public Shader(GL2 gl, String name)
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
