#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec3 normal;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;

out vec2 vTexCoord;

void main()
{
	mat4 mvp = perspective * view * model;
	gl_Position = mvp * vec4(position, 1.0);
	vTexCoord = texCoord;
}