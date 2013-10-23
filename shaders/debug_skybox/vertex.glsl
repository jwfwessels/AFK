#version 120

uniform mat4 projection;
uniform mat4 view;
uniform mat4 world;

varying vec3 dir;

void main()
{
    dir = (gl_Vertex).xyz;

    gl_Position = projection * view * world * gl_Vertex;
}

