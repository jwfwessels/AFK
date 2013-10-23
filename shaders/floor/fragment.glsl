#version 120

uniform sampler2D tex;

varying float ip;

const float darkCheck = 0.95;
const float lightCheck = 1.0;

void main()
{
    // making the checkerboard!
    int u = int(gl_TexCoord[0].x*10);
    int v = int(gl_TexCoord[0].y*10);

    float colour;
    if (mod(u,2) == mod(v,2)) colour = darkCheck;
    else colour = lightCheck;

    gl_FragColor = texture2D(tex, gl_TexCoord[0].xy)*colour*ip;
}

