#version 330

#define MAX_DIRECTIONAL_LIGHTS 4
#define MAX_SPOT_LIGHTS 16
#define MAX_POINT_LIGHTS 16

layout (location = 0) out vec4 fColorOut;

struct DirectionLight
{
	vec3 direction;
	vec3 color;
	float intensity;
};

struct SpotLight
{
	vec3 position;
	vec3 direction;
	vec3 attenuation;
	vec3 color;
	float radius;
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

uniform vec3 ambientColor;
uniform float ambientIntensity;

uniform DirectionLight dirLights[MAX_DIRECTIONAL_LIGHTS];
uniform int dirLightCount;

uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
uniform int spotLightCount;

uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform int pointLightCount;

uniform Material material;

in vec3 gFragPos;
in vec2 gTexCoord;
in mat3 gTBN;

vec3 calcDirectionalLight(DirectionLight dirLight, vec3 normal)
{
	vec3 lightDir = normalize(-dirLight.direction);
	vec3 viewDir = normalize(-cameraPosition - gFragPos);
	vec3 halfDir = normalize(lightDir + viewDir);
	
	float spec = pow(max(dot(normal, halfDir), 0.0), material.exponent);
	vec3 specular = material.intensity * spec * dirLight.color;  
	
	float diff = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = diff * dirLight.color * dirLight.intensity;
	
	return diffuse + specular;
}

vec3 calcSpotLight(SpotLight spotLight, vec3 normal)
{
	vec3 lightDir = normalize(spotLight.position - gFragPos);
	
	float theta = dot(lightDir, normalize(-spotLight.direction));
	
	if(theta > spotLight.radius)
	{
		vec3 viewDir = normalize(cameraPosition - gFragPos);
		vec3 halfDir = normalize(lightDir + viewDir);
	
		float constant = spotLight.attenuation.x;
	    float linear = spotLight.attenuation.y;
	    float quadratic = spotLight.attenuation.z;
	    
	    float distance = length(spotLight.position - gFragPos);
	    float attenuation = 1.0 / (constant + linear * distance + quadratic * (distance * distance));
	    
	    float spec = pow(max(dot(normal, halfDir), 0.0), material.exponent);
		vec3 specular = spec * spotLight.color * material.intensity * attenuation;  
	    
	    float difference = max(dot(normal, lightDir), 0.0);
		vec3 diffuse = difference * spotLight.color * spotLight.intensity * attenuation;
		
		return diffuse + specular;
	}
  	
    return vec3(0);
}

vec3 calcPointLight(PointLight pointLight, vec3 normal)
{
	vec3 lightDir = normalize(pointLight.position - gFragPos);
	vec3 viewDir = normalize(cameraPosition - gFragPos);
	vec3 halfDir = normalize(lightDir + viewDir);
	
    float constant = pointLight.attenuation.x;
    float linear = pointLight.attenuation.y;
    float quadratic = pointLight.attenuation.z;
    
    float distance = length(pointLight.position - gFragPos);
    float attenuation = 1.0 / (constant + linear * distance + quadratic * (distance * distance));
    
    float spec = pow(max(dot(normal, halfDir), 0.0), material.exponent);
	vec3 specular = spec * pointLight.color * material.intensity * attenuation;  
    
    float difference = max(dot(normal, lightDir), 0.0);
	vec3 diffuse = difference * pointLight.color * pointLight.intensity * attenuation;
  	
    return diffuse + specular;
}

vec3 calcNormal(sampler2D normTexture)
{
	const float normStrength = 1.0;

	vec3 texNormal = texture(normTexture, gTexCoord).rgb;
	texNormal = normalize(texNormal * 2.0 - 1.0);
	texNormal = texNormal * vec3(normStrength, normStrength, 1.0);

	return normalize(gTBN * texNormal);
}

void main()
{
	vec3 result = vec3(0);
	
	vec3 normal = calcNormal(material.normal);
	
	// Directional Lights
	for(int i = 0; i < min(dirLightCount, MAX_DIRECTIONAL_LIGHTS); i++)
		result += calcDirectionalLight(dirLights[i], normal);
		
	// Spotlight Lights
	for(int i = 0; i < min(spotLightCount, MAX_SPOT_LIGHTS); i++)
		result += calcSpotLight(spotLights[i], normal);
	
	// Point Lights
	for(int i = 0; i < min(pointLightCount, MAX_POINT_LIGHTS); i++)
		result += calcPointLight(pointLights[i], normal);
	
	// Ambient Color
	vec3 ambient = ambientColor * ambientIntensity;
	result += ambient;
	
	vec4 texture = texture(material.diffuse, gTexCoord);
	
	fColorOut = vec4(result, 1.0) * texture;
}
