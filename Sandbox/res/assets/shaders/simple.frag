#version 330

#define MAX_DIRECTIONAL_LIGHTS 4
#define MAX_POINT_LIGHTS 16

struct DirectionLight
{
	vec3 direction;
	vec3 color;
	float intensity;
};

struct PointLight
{
	vec3 position;
	vec3 color;
	vec3 attenuation;
	float intensity;
};

uniform DirectionLight dirLights[MAX_DIRECTIONAL_LIGHTS];
uniform int dirLightCount;

uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform int pointLightCount;

uniform sampler2D texture0;

in vec3 vFragPos;
in vec3 vNormal;
in vec2 vTexCoord;

out vec4 outColor;

vec3 calcDirectionalLight(DirectionLight dirLight, vec3 normal)
{
	vec3 lightDir = normalize(-dirLight.direction);
	float difference = max(dot(normal, lightDir), 0.0);
	return difference * dirLight.color * dirLight.intensity;
}

vec3 calcPointLight(PointLight pointLight, vec3 normal)
{
	vec3 lightDir = normalize(pointLight.position - vFragPos);
    float difference = max(dot(normal, lightDir), 0.0);
    
    float constant = pointLight.attenuation.x;
    float linear = pointLight.attenuation.y;
    float quadratic = pointLight.attenuation.z;
    
    float distance = length(pointLight.position - vFragPos);
    float attenuation = 1.0 / (constant + linear * distance + quadratic * (distance * distance));
  	
    return (difference * pointLight.color) * attenuation * pointLight.intensity;
}

void main()
{
	vec3 diffuse;

	vec3 norm = normalize(vNormal);
	
	// Directional Lights
	for(int i = 0; i < min(dirLightCount, MAX_DIRECTIONAL_LIGHTS); i++)
		diffuse += calcDirectionalLight(dirLights[i], norm);
	
	// Point Lights
	for(int i = 0; i < min(pointLightCount, MAX_POINT_LIGHTS); i++)
		diffuse += calcPointLight(pointLights[i], norm);
	
	// Ambient Color
	float ambientStrength = 0.1;
	vec3 ambientColor = vec3(1.0, 1.0, 1.0) * ambientStrength;
	diffuse += ambientColor;
	
	vec3 result = diffuse * texture(texture0, vTexCoord).xyz;

	outColor = vec4(result, 1.0);
}