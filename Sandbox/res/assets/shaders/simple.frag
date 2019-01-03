#version 330

struct DirectionLight
{
	vec3 direction;
	vec3 color;
	float intensity;
};

uniform DirectionLight dirLights[4];
uniform float dirLightCount;

uniform sampler2D texture0;

in vec3 vFragPos;
in vec3 vNormal;
in vec2 vTexCoord;

out vec4 outColor;

vec3 calcDirectionalLight(DirectionLight dirLight, vec3 normal)
{
	vec3 lightDir = normalize(dirLight.direction - vFragPos);
	float diff = max(dot(normal, dirLight.direction), 0.0);
	return diff * dirLight.color;
}

void main()
{
	float ambientStrength = 0.1;
	vec3 ambientColor = vec3(1.0, 1.0, 1.0) * ambientStrength;
	
	vec3 norm = normalize(vNormal);
	
	vec3 diffuse;
	
	for(int i = 0; i < 1; i++)
		diffuse = calcDirectionalLight(dirLights[i], norm);
	
	vec3 result = (ambientColor + diffuse) * texture(texture0, vTexCoord).xyz;

	outColor = vec4(result, 1.0);
}