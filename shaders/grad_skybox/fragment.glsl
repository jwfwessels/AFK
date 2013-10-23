#version 120

varying vec3 dir;

void main()
{
    float factor = 1.0f;

    vec3 d = normalize(dir);
    vec4 purple = vec4(0.046875, 0, 0.1015625,1);
    vec4 black = vec4(0,0,0,1);

    if (d.y < -0.5) factor = 0;
    else if (d.y < 0)
    {
        factor = 1.0 - -d.y/0.5;
    }

    gl_FragColor = mix(black,purple,factor);
}

