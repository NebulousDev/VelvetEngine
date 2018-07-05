package loaders;

import java.util.ArrayList;
import java.util.HashMap;

import math.Vector2f;
import math.Vector3f;

public class OBJLoader
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
	
	public static class OBJMeshVertex
	{
		public int vertIdx;
		public int texIdx;
		public int normIdx;
	}
	
	public static class OBJMeshFace
	{
		public int 						numVertices;
		public ArrayList<OBJMeshVertex> vertices;
	}
	
	public static class OBJMesh
	{
		public ArrayList<Vector3f> 		vertices;
		public ArrayList<Vector2f>		texCoords;
		public ArrayList<Vector3f>		normals;
		public ArrayList<OBJMeshFace>	faces;
		boolean hasTexCoords;
		boolean hasNormals;
	}
	
	public static class OBJObject
	{
		public String		name;
		public boolean		isDefault;
		public OBJMaterial 	material;
		public OBJMesh 		mesh;
	}
	
	public static class OBJModel
	{
		public String				name;
		public OBJObject			object;
		public ArrayList<OBJObject> objects;
		public boolean				useObjectList;
	}
	
	private static OBJTexture parseOBJTexture(String[] tokens)
	{
		OBJTexture texture = new OBJTexture();
		
		int i = 1;
		boolean name = false;
		while(i != tokens.length || name)
		{
			switch(tokens[i])
			{
				case "-blendu":
				{
					texture.horizonalBlend = !tokens[++i].toLowerCase().equals("off");
					break;
				}
				
				case "-blendv":
				{
					texture.verticalBlend = !tokens[++i].toLowerCase().equals("off");
					break;
				}
				
				case "-o":
				{
					float u = Float.parseFloat(tokens[++i]);
					float v = Float.parseFloat(tokens[++i]);
					texture.offset = new Vector2f(u, v);
					break;
				}
				
				case "-s":
				{
					float u = Float.parseFloat(tokens[++i]);
					float v = Float.parseFloat(tokens[++i]);
					texture.scale = new Vector2f(u, v);
					break;
				}
				
				case "-clamp":
				{
					texture.clamp = tokens[++i].toLowerCase().equals("on");
					break;
				}
				
				default:
				{
					texture.filename = tokens[i];
					name = true;
					break;
				}
			}
		}
		
		return texture;
	}
	
	private static void parseOBJMaterials(HashMap<String, OBJMaterial> materialMap, String filepath, String filename)
	{
		String data = FileLoader.readFileAsString(filepath + "/" + filename);
		String lines[] = data.split("\n");
		
		OBJMaterial material = null;
		
		for(String line : lines)
		{
			String tokens[] = line.replace("\n", "").replace("\r", "").split(" ");
			switch(tokens[0].toLowerCase())
			{
				case "newmtl":
				{
					if(material != null)
						materialMap.put(material.name, material);
					material = new OBJMaterial();
					material.name = tokens[1];
					break;
				}
				
				case "ka":
				{
					float r = Float.parseFloat(tokens[1]);
					float g = Float.parseFloat(tokens[2]);
					float b = Float.parseFloat(tokens[3]);
					material.ambient = new Vector3f(r, g, b);
					break;
				}
				
				case "kd":
				{
					float r = Float.parseFloat(tokens[1]);
					float g = Float.parseFloat(tokens[2]);
					float b = Float.parseFloat(tokens[3]);
					material.diffuse = new Vector3f(r, g, b);
					break;
				}
				
				case "ks":
				{
					float r = Float.parseFloat(tokens[1]);
					float g = Float.parseFloat(tokens[2]);
					float b = Float.parseFloat(tokens[3]);
					material.specular = new Vector3f(r, g, b);
					break;
				}
				
				case "ns":
				{
					material.exponent = Float.parseFloat(tokens[1]);
					break;
				}
				
				case "d":
				{
					material.transparency = Float.parseFloat(tokens[1]);
					break;
				}
				
				case "tr":
				{
					material.transparency = Float.parseFloat(tokens[1]);
					material.reverseTransparency = true;
					break;
				}
				
				case "illum":
				{
					material.illum = Integer.parseInt(tokens[1]);
					break;
				}
				
				case "map_ka":
				{
					material.ambientTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "map_kd":
				{
					material.diffuseTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "map_ks":
				{
					material.specularColorTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "map_ns":
				{
					material.specularHighlightTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "map_d":
				{
					material.alphaTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "map_bump":
				{
					material.bumpTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "bump":
				{
					material.bumpTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "map_disp":
				{
					material.displacementTexture = parseOBJTexture(tokens);
					break;
				}
				
				case "disp":
				{
					material.displacementTexture = parseOBJTexture(tokens);
					break;
				}
				
				default:
				{
					continue;
				}
			}
		}
		
		if(material != null)
			materialMap.put(material.name, material);
	}
	
	public static OBJModel parseOBJModel(String filepath, String filename)
	{
		String data = FileLoader.readFileAsString(filepath + "/" + filename + ".obj");
		String lines[] = data.split("\n");
	
		OBJModel model = new OBJModel();
		model.objects = new ArrayList<>();
		model.name = filename;
		
		HashMap<String, OBJMaterial> materialMap = new HashMap<>();

		OBJObject object = new OBJObject();
		object.name = "default";
		object.isDefault = true;
		object.mesh = new OBJMesh();
		object.mesh.vertices = new ArrayList<>();
		object.mesh.texCoords = new ArrayList<>();
		object.mesh.normals = new ArrayList<>();
		object.mesh.faces = new ArrayList<>();

		for(String line : lines)
		{
			String tokens[] = line.replace("\n", "").replace("\r", "").replace("  ", " ").split("\\ |\\  ");
			switch(tokens[0])
			{
				case "mtllib":
				{
					parseOBJMaterials(materialMap, filepath, tokens[1]);
					break;
				}
				
				case "usemtl":
				{
					if(materialMap.size() > 0)
						object.material = materialMap.get(tokens[1]);
					break;
				}
				
				case "o":
				{
					if(!object.isDefault)
						model.objects.add(object);
					
					object = new OBJObject();
					object.name = tokens[1];
					object.mesh = new OBJMesh();
					object.mesh.vertices = new ArrayList<>();
					object.mesh.texCoords = new ArrayList<>();
					object.mesh.normals = new ArrayList<>();
					object.mesh.faces = new ArrayList<>();
					model.useObjectList = true;
					break;
				}
				
				case "v":
				{
					float x = Float.parseFloat(tokens[1]);
					float y = Float.parseFloat(tokens[2]);
					float z = Float.parseFloat(tokens[3]);
					
					object.mesh.vertices.add(new Vector3f(x, y, z));
					
					break;
				}
				
				case "vt":
				{
					float u = Float.parseFloat(tokens[1]);
					float v = Float.parseFloat(tokens[2]);
					
					object.mesh.texCoords.add(new Vector2f(u, v));
					
					break;
				}
				
				case "vn":
				{
					float x = Float.parseFloat(tokens[1]);
					float y = Float.parseFloat(tokens[2]);
					float z = Float.parseFloat(tokens[3]);
					
					object.mesh.normals.add(new Vector3f(x, y, z));
					
					break;
				}
				
				case "f":
				{
					int numVerts = tokens.length - 1;
					
					OBJMeshFace face = new OBJMeshFace();
					face.numVertices = numVerts;
					face.vertices = new ArrayList<>();
					
					for(int i = 1; i < tokens.length; i++)
					{
						String[] elements = tokens[i].split("/");
						if(elements.length == 1)
						{
							OBJMeshVertex vert = new OBJMeshVertex();
							vert.vertIdx = Integer.parseInt(elements[0]);
							face.vertices.add(vert);
						}
						
						if(elements.length == 2)
						{
							OBJMeshVertex vert = new OBJMeshVertex();
							vert.vertIdx = Integer.parseInt(elements[0]);
							vert.texIdx = Integer.parseInt(elements[1]);
							object.mesh.hasTexCoords = true;
							face.vertices.add(vert);
						}
						
						if(elements.length == 3)
						{
							OBJMeshVertex vert = new OBJMeshVertex();
							vert.vertIdx = Integer.parseInt(elements[0]);
							
							if(!elements[1].equals(""))
							{
								vert.texIdx = Integer.parseInt(elements[1]);
								object.mesh.hasTexCoords = true;
							}
							
							vert.normIdx = Integer.parseInt(elements[2]);
							object.mesh.hasNormals = true;
							face.vertices.add(vert);
						}
					}
					
					object.mesh.faces.add(face);
					
					break;
				}
			}
		}
		
		if(!object.isDefault)
			model.objects.add(object);
		
		if(!model.useObjectList)
			model.object = object;
		
		return model;
	}
}
