#version 120

uniform vec3 colour;

void main()
{
    float ip = gl_FragCoord.z;
    gl_FragColor = vec4(ip,ip,ip,1);
}

