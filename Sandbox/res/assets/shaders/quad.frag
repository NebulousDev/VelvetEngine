#version 330

in vec2 vTexCoord;

out vec4 outColor;

uniform sampler2D diffuse;

void main()
{
	outColor = texture(diffuse, vTexCoord);
}