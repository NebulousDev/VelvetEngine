#version 330

in vec2 vTexCoord;
out vec4 outColor;

uniform sampler2D texture0;

void main()
{
	outColor = texture(texture0, vTexCoord);
}