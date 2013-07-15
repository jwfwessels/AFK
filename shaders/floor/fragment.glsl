#version 120

uniform sampler2D tex;
uniform sampler2D shadowmap;

varying vec4 lposition;

bool shadowed(vec2 v, float dist)
{
    return texture2D(shadowmap, v).z < dist;
}

void main()
{
    // perspective division for the light position
    vec4 ssLightPos = lposition / lposition.w;

    // making the checkerboard!
    int u = int(gl_TexCoord[0].x);
    int v = int(gl_TexCoord[0].y);

    float colour;
    if (mod(u,2) == mod(v,2)) colour = 0.2;
    else colour = 0.7;

    if (shadowed(ssLightPos.xy, ssLightPos.z))
    {
        gl_FragColor = vec4(1,0,1,1);
    }
    else
    {
        //gl_FragColor = texture2D(tex, gl_TexCoord[0].xy);
        gl_FragColor = vec4(vec3(colour),1.0);
    }
}

