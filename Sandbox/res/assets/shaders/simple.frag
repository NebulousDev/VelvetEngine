#version 330

#define MAX_DIRECTIONAL_LIGHTS 4
#define MAX_POINT_LIGHTS 16

layout (location = 0) out vec3 fColorOut;

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

struct Material
{
	sampler2D diffuse;
	sampler2D normal;
	float intensity;
	float exponent;
};

uniform vec3 cameraPosition;

uniform DirectionLight dirLights[MAX_DIRECTIONAL_LIGHTS];
uniform int dirLightCount;

uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform int pointLightCount;

uniform Material material;

in vec3 vFragPos;
in vec2 vTexCoord;
in vec3 vNormal;
in mat3 vTBN;

vec3 calcDirectionalLight(DirectionLight dirLight, vec3 normal)
{
	vec3 lightDir = normalize(-dirLight.direction);
	vec3 viewDir = normalize(-cameraPosition - vFragPos);
	vec3 halfDir = normalize(lightDir + viewDir);
	
	float spec = pow(max(dot(normal, halfDir), 0.0), material.exponent);
	vec3 specular = material.intensity * spec * dirLight.color;  
	
	float diff = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = diff * dirLight.color * dirLight.intensity;
	
	return diffuse + specular;
}

vec3 calcPointLight(PointLight pointLight, vec3 normal)
{
	vec3 lightDir = normalize(pointLight.position - vFragPos);
	vec3 viewDir = normalize(cameraPosition - vFragPos);
	vec3 halfDir = normalize(lightDir + viewDir);
	
    float constant = pointLight.attenuation.x;
    float linear = pointLight.attenuation.y;
    float quadratic = pointLight.attenuation.z;
    
    float distance = length(pointLight.position - vFragPos);
    float attenuation = 1.0 / (constant + linear * distance + quadratic * (distance * distance));
    
    float spec = pow(max(dot(normal, halfDir), 0.0), material.exponent);
	vec3 specular = spec * pointLight.color * material.intensity * attenuation;  
    
    float difference = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = difference * pointLight.color * pointLight.intensity * attenuation;
  	
    return diffuse + specular;
}

vec3 calcNormal(sampler2D normTexture, vec3 faceNormal)
{
	vec3 texNormal = vTBN * normalize(texture(normTexture, vTexCoord).rgb * 2.0);
	return texNormal;
	//return faceNormal;
}

void main()
{
	vec3 diffuse;

	vec3 norm = calcNormal(material.normal, normalize(vNormal));
	
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
	
	vec3 result = diffuse * texture(material.diffuse, vTexCoord).rgb;

	fColorOut = result;
}