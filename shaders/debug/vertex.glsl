#version 120

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

void main()
{
    gl_Position = projection * view * world * gl_Vertex;
}

