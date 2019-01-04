#version 330

in vec2 vTexCoord;

out vec4 outColor;

uniform sampler2D diffuse;

void main()
{
	const float gamma = 2.2;
    vec3 hdrColor = texture(diffuse, vTexCoord).rgb;
  
    // Exposure tone mapping
    vec3 mapped = vec3(1.0) - exp(-hdrColor * 1.0);
    
    // Gamma correction 
    mapped = pow(mapped, vec3(1.0 / gamma));
  
	outColor = vec4(mapped, 1.0);
}