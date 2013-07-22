#version 120

uniform sampler2D tex;

uniform vec3 colour;
uniform float opacity;

void main()
{

    float ip = texture2D(tex,gl_TexCoord[0].xy).r;

    gl_FragColor = vec4(colour,opacity*ip);
}

