#version 120

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

uniform mat4 lprojection;
uniform mat4 lview;

varying vec4 lposition;

const mat4 bias = mat4 (.5f, .0f, .0f, .0f,
                        .0f, .5f, .0f, .0f,
                        .0f, .0f, .5f, .0f,
                        .5f, .5f, .5f, 1.f);

void main()
{
    gl_TexCoord[0] = gl_MultiTexCoord0;

    gl_Position = projection * view * world * gl_Vertex;
    lposition = bias * lprojection * lview * world * gl_Vertex;
}

