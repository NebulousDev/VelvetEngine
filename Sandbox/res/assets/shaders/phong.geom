#version 330 core

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in vec3 vFragPos[];
in vec2 vTexCoord[];
in vec3 vNormal[];
in mat4 vView[];

out vec3 gFragPos;
out vec2 gTexCoord;
out mat3 gTBN;

vec3 calcTangent()
{
	vec3 v0 = gl_in[0].gl_Position.xyz;
	vec3 v1 = gl_in[1].gl_Position.xyz;
	vec3 v2 = gl_in[2].gl_Position.xyz;

    vec3 e1 = v1 - v0;
    vec3 e2 = v2 - v0;

    float dU1 = vTexCoord[1].x - vTexCoord[0].x;
    float dV1 = vTexCoord[1].y - vTexCoord[0].y;
    float dU2 = vTexCoord[2].x - vTexCoord[0].x;
    float dV2 = vTexCoord[2].y - vTexCoord[0].y;

    float f = 1.0 / (dU1 * dV2 - dU2 * dV1);

    vec3 tangent;

    tangent.x = f * (dV2 * e1.x - dV1 * e2.x);
    tangent.y = f * (dV2 * e1.y - dV1 * e2.y);
    tangent.z = f * (dV2 * e1.z - dV1 * e2.z);
	
	return normalize(tangent);
}

void main()
{
	vec3 tangent = calcTangent();
	
	for(int i = 0; i < gl_in.length(); i++)
	{
		gFragPos = vFragPos[i];
		gTexCoord = vTexCoord[i];
		
		vec3 biTangent = normalize(cross(tangent, vNormal[i]));
		mat3 TBN = mat3(tangent, biTangent, vNormal[i]);
		
		gTBN = TBN;
		gl_Position = vView[i] * gl_in[i].gl_Position;
		
		EmitVertex();
	}

	EndPrimitive();
}
