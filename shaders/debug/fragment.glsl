#version 120

uniform vec3 colour;
uniform float opacity;

void main()
{
    gl_FragColor = vec4(colour,opacity);
}

