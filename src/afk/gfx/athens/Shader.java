/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.gfx.athens;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class Shader extends AthensResource
{

    private int program;
    private int vertexShader;
    private int fragmentShader;
    private Map<String, Integer> uniforms = new HashMap<String, Integer>();

    public Shader(String name)
    {
        super(SHADER, name);
    }

    @Override
    public void load(GL2 gl) throws IOException
    {
        vertexShader = AthensUtils.loadShaderProgram(gl, "shaders/" + name + "/vertex.glsl", GL2.GL_VERTEX_SHADER);
        fragmentShader = AthensUtils.loadShaderProgram(gl, "shaders/" + name + "/fragment.glsl", GL2.GL_FRAGMENT_SHADER);

        /* Start by defining a shader program which acts as a container. */
        program = gl.glCreateProgram();

        /* Next, shaders are added to the shader program. */
        if (vertexShader != -1)
        {
            gl.glAttachShader(program, vertexShader);
        }
        if (fragmentShader != -1)
        {
            gl.glAttachShader(program, fragmentShader);
        }

        /* Finally, the program must be linked. */
        gl.glLinkProgram(program);

        /* Check if it linked  properly. */
        AthensUtils.checkProgramLogInfo(gl, program);

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

    public int findUniform(GL2 gl, String name)
    {
        Integer i = uniforms.get(name);
        if (i == null)
        {
            i = gl.glGetUniformLocation(program, name);
            uniforms.put(name, i);
        }
        return i;
    }

    public void updateUniform(GL2 gl, String name, Mat4 value)
    {
        int uniform = findUniform(gl, name);
        if (uniform == -1)
        {
            return;
        }
        gl.glUniformMatrix4fv(uniform, 1, false, value.getBuffer());
    }

    public void updateUniform(GL2 gl, String name, float value)
    {
        int uniform = findUniform(gl, name);
        if (uniform == -1)
        {
            return;
        }
        gl.glUniform1f(uniform, value);
    }

    public void updateUniform(GL2 gl, String name, Vec3 value)
    {
        int uniform = findUniform(gl, name);
        if (uniform == -1)
        {
            return;
        }
        gl.glUniform3fv(uniform, 1, value.getBuffer());
    }

    public void updateUniform(GL2 gl, String name, Vec4 value)
    {
        int uniform = findUniform(gl, name);
        if (uniform == -1)
        {
            return;
        }
        gl.glUniform4fv(uniform, 1, value.getBuffer());
    }

    public void updateUniform(GL2 gl, String name, int value)
    {
        int uniform = findUniform(gl, name);
        if (uniform == -1)
        {
            return;
        }
        gl.glUniform1i(uniform, value);
    }
}
