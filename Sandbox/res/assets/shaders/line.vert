#version 330

layout(location = 0) in vec3 position;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;

void main()
{
	mat4 mvp = perspective * view * model;
	gl_Position = mvp * vec4(position, 1.0);
}