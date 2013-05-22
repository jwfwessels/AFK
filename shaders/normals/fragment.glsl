#version 120

uniform vec3 eye;

varying vec3 v_colour;
varying vec3 v_ndc;
varying vec3 v_ndc_norm;
varying vec3 v_normal;

void main()
{
    float shade = 1.0f-(v_ndc.z+1.0f)/2.0f;
    vec3 col = v_normal;
    gl_FragColor = vec4(col, 1.0f);
}

