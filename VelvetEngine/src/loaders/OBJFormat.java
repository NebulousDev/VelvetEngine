package loaders;

import math.Vector2f;
import math.Vector3f;

public class OBJFormat
{
	public static class OBJTexture
	{
		public String	filename;
		public boolean	verticalBlend;
		public boolean	horizonalBlend;
		public Vector2f offset;
		public Vector2f scale;
		public boolean	clamp;
		public float	bumpMult;
	}
	
	public static class OBJMaterial
	{
		public String		name;
		public Vector3f 	ambient;
		public Vector3f 	diffuse;
		public Vector3f 	specular;
		public float		exponent;
		public float		transparency;
		public boolean		reverseTransparency;
		public int			illum;
		public OBJTexture	ambientTexture;
		public OBJTexture	diffuseTexture;
		public OBJTexture	specularColorTexture;
		public OBJTexture	specularHighlightTexture;
		public OBJTexture	alphaTexture;
		public OBJTexture	bumpTexture;
		public OBJTexture	displacementTexture;
	}
	
	public static class OBJVertex
	{
		public static final int SIZE = 8;
		
		public Vector3f position;
		public Vector2f texCoord;
		public Vector3f normal;
	}
	
	public static class OBJMesh
	{
		public String	name;
		public int 		idxOffset;
		public int 		idxCount;
		public boolean 	hasPositions;
		public boolean 	hasTexCoords;
		public boolean 	hasNormals;
	}
	
	public static class OBJModel
	{
		public String		name;
		public OBJMesh[]	meshes;
		public OBJVertex[]	vertices;
		public int[]		indices;
	}
	
}
