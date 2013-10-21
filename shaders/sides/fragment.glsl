#version 120

uniform sampler2D tex;

varying float ip;

const float dark = 0.4;
const float light = 1.3;

void main()
{

    float colour = mix(dark,light,gl_TexCoord[0].y);

    gl_FragColor = texture2D(tex, gl_TexCoord[0].xy)*colour*ip;
}

