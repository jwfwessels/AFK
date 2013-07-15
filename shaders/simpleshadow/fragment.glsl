#version 120

uniform vec3 colour;
uniform float opacity;

void main()
{
    float u = gl_TexCoord[0].x-0.5;
    float v = 1.0 - gl_TexCoord[0].y-0.5;

    float ip;
    float len = u*u + v*v;
    if (len > 0.25) ip = 0.0;
    else ip = 1.0-len/0.25;

    gl_FragColor = vec4(colour,opacity*ip);
}

