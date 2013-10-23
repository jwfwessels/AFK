#version 120

uniform samplerCube tex;
uniform vec3 sun;

varying vec3 dir;

void main()
{
    float factor = 1.0f;

    vec3 d = normalize(dir);

    if (d.y < -0.5) factor = 0;
    else if (d.y < 0)
    {
        factor = 1.0 - -d.y/0.5;
    }

    gl_FragColor = textureCube(tex, d)*factor;

    if (dot(d,normalize(sun)) > 0.996)
        gl_FragColor = vec4(1,0,0,1);

    gl_FragColor = vec4(1,0,1,1);
}

