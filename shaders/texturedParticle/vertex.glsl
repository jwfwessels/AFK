#version 120

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

uniform vec3 sun;
uniform vec3 eye;

void main()
{
    gl_TexCoord[0] = gl_MultiTexCoord0;

    gl_Position = projection * view * world * gl_Vertex;
}

