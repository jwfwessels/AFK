#version 120

//uniform sampler2D tex;

uniform vec3 colour;
uniform float opacity;

varying float ip;

uniform float life;
uniform int particleID;

void main()
{
    float u = gl_TexCoord[0].x-0.5;
    float v = 1.0 - gl_TexCoord[0].y-0.5;

    /*gl_FragColor = texture2D(tex, vec2(u,v))*ip;*/

    if (u*u + v*v > 0.25) discard;

    gl_FragColor = vec4(colour,opacity);
}

