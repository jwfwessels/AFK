#version 120

uniform sampler2D tex;

uniform vec3 colour;
uniform float opacity;

void main()
{
    float ip = texture2D(tex, gl_TexCoord[0].xy).r;

    if (ip < 0.5) discard;

    gl_FragColor = vec4(colour*ip,opacity);
}

