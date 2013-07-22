#version 120

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

uniform vec3 sun;
uniform vec3 eye;

uniform float life;

void main()
{
    gl_TexCoord[0] = gl_MultiTexCoord0;

    gl_Position = projection * view * world * vec4(gl_Vertex.xyz * (1.0-life), 1.0);
}

