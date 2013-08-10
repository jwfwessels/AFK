#version 120

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

uniform vec3 sun;
uniform vec3 eye;

varying float ip;

void main()
{
    gl_TexCoord[0] = gl_MultiTexCoord0;

    vec3 g_normal = normalize((world * vec4(gl_Normal,0.0f)).xyz);

    vec3 g_colour = gl_Vertex.xyz;
    vec3 g_position = (world * gl_Vertex).xyz;

    float ia = 0.7f;
    float id = 1.0f;
    //float is = 0.3f;
    //float s = 50.0f;

    vec3 v = normalize(eye-g_position);
    vec3 l = normalize(sun);

    ip = ia + max(dot(l,g_normal),0)*id; // + pow(max(dot(r,v),0),s)*is;

    gl_Position = projection * view * world * gl_Vertex;
}

