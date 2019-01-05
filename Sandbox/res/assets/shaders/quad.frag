#version 330

in vec2 vTexCoord;

out vec4 outColor;

uniform sampler2D diffuse;

void main()
{
	const float hdrEffect = 1.0;

	const float gamma = 2.2;
    vec3 color = texture(diffuse, vTexCoord).rgb;
  
    // Exposure tone mapping
    vec3 mapped = vec3(1.0) - exp(-color * 0.5);
    
    // Gamma correction 
    mapped = pow(mapped, vec3(1.0 / gamma));
  
	outColor = mix(vec4(color, 1.0), vec4(mapped, 1.0), hdrEffect);
}