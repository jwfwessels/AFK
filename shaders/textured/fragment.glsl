#version 120

uniform sampler2D tex;

varying float ip;

uniform vec3 colour;
uniform float opacity;

void main()
{
    float u = gl_TexCoord[0].x;
    float v = 1.0 - gl_TexCoord[0].y;

    gl_FragColor = vec4(ip * texture2D(tex, vec2(u,v)).xyz,opacity);
}

