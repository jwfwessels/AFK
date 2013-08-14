#version 120

//uniform sampler2D tex;

varying float ip;

uniform vec3 colour;
uniform float opacity;

void main()
{
    gl_FragColor = vec4(ip * colour,opacity);
}

