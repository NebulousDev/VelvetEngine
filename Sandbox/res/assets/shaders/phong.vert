#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec3 normal;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;

uniform vec3 cameraPosition;

out vec3 vFragPos;
out vec2 vTexCoord;
out vec3 vNormal;
out mat4 vView;

void main()
{
	vFragPos = vec3(model * vec4(position, 1.0));
	vTexCoord = texCoord;
	vNormal = mat3(transpose(inverse(model))) * normal;
	vView = perspective * view;

	gl_Position = model * vec4(position, 1.0);
}