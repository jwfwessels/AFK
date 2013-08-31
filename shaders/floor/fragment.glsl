#version 120

uniform sampler2D tex;

void main()
{
    // making the checkerboard!
    int u = int(gl_TexCoord[0].x*10);
    int v = int(gl_TexCoord[0].y*10);

    float colour;
    if (mod(u,2) == mod(v,2)) colour = 0.8;
    else colour = 1.0;

    gl_FragColor = texture2D(tex, gl_TexCoord[0].xy)*colour;
}

