#version 120

uniform mat4 projection;
uniform mat4 world;
uniform mat4 view;

varying vec3 v_colour;
varying vec3 v_normal;
varying vec3 v_ndc;
varying vec3 v_ndc_norm;

void main()
{
    v_normal = normalize((world * vec4(gl_Normal,0.0f)).xyz);

   vec4 v_position = projection * view * world * gl_Vertex;
   v_ndc_norm = normalize((projection * view * world * vec4(gl_Normal, 0.0f)).xyz);
   v_colour = gl_Vertex.rgb;
   v_ndc = v_position.xyz;
   gl_Position = v_position;
}

