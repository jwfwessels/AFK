#version 120

//uniform sampler2D tex;

varying float ip;

void main()
{
    float u = gl_TexCoord[0].x-0.5;
    float v = 1.0 - gl_TexCoord[0].y-0.5;

    /*gl_FragColor = texture2D(tex, vec2(u,v))*ip;*/

    if (u*u + v*v > 0.25) discard;

    gl_FragColor = vec4(ip,0,ip,1.0);
}

