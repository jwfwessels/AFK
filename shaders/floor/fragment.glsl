#version 120

//uniform sampler2D tex;
uniform float opacity;

void main()
{
    // making the checkerboard!
    int u = int(gl_TexCoord[0].x);
    int v = int(gl_TexCoord[0].y);

    float colour;
    if (mod(u,2) == mod(v,2)) colour = 0.5;
    else colour = 0.7;

    //gl_FragColor = texture2D(tex, texCoord);
    gl_FragColor = vec4(vec3(colour),opacity);
}

