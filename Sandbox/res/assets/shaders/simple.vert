#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec3 normal;
layout(location = 3) in vec3 tangent;

uniform mat4 view;
uniform mat4 perspective;
uniform mat4 model;

uniform vec3 cameraPosition;

out vec3 vFragPos;
out vec2 vTexCoord;
out vec3 vNormal;
out mat3 vTBN;

void main()
{
	mat4 mvp = perspective * view * model;

	mat3 inverseModel = mat3(transpose(inverse(model)));

	vec3 n = normalize(inverseModel * normal);
	vec3 t = normalize(inverseModel * tangent);
	vec3 b = normalize(inverseModel * cross(normal, tangent));
	
	mat3 tbn = mat3(t, b, n);

	vFragPos = vec3(model * vec4(position, 1.0));
	vTexCoord = texCoord;
	vNormal = inverseModel * normal;
	vTBN = tbn;

	gl_Position = mvp * vec4(position, 1.0);
}