#version 120

uniform samplerCube tex;

varying vec3 dir;

void main()
{
    vec3 d = normalize(dir);

    gl_FragColor = textureCube(tex, d);
}

