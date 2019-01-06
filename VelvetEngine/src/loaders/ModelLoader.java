package loaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import graphics.Graphics;
import graphics.Graphics.BufferType;
import graphics.Material;
import graphics.Mesh;
import graphics.Model;
import graphics.Texture;
import math.Vector2f;
import math.Vector3f;
import resource.ResourceManager;
import utils.FileReader;
import utils.FileReader.FileType;
import utils.FileUtils;

public class ModelLoader
{
	private ModelLoader() {}
	
	static class Vertex
	{
		Vector3f position;
		Vector2f texCoord;
		Vector3f normal;
		Vector3f tangent;
	}
	
	static class Index
	{
		int position 	= 0;
		int texCoord 	= -1;
		int normal 		= -1;
		
		int smoothGroup;
	}
	
	static class SubMesh
	{
		String name;
		Material material;
		int indexBegin;
		int indexEnd;
	}
	
	static class MaterialData
	{
		public String name;
		public String diffuse;
		public String normal;
		public String specular;
		
		public float exponent;
		public float intensity;
		public Vector3f specularColor;
	}
	
	static class MultiMesh
	{
		ArrayList<Vector3f>	positions;
		ArrayList<Vector2f> texCoords;
		ArrayList<Vector3f> normals;
		
		ArrayList<SubMesh> subMeshes;
		
		ArrayList<ArrayList<Index>> faces;
	}
	
	public static Model load(Graphics gfx, ResourceManager resourceManager, String filepath, boolean generateNormals, boolean smooth, boolean generateTangents, boolean generateUVs)
	{
		Model model = new Model();
		return load(model, resourceManager, gfx, filepath, generateNormals, smooth, generateTangents, generateUVs);
	}
	
	public static Model load(Model model, ResourceManager resourceManager, Graphics gfx, String filepath, boolean generateNormals, boolean smooth, boolean generateTangents, boolean generateUVs)
	{
		// TODO: parse file type
		
		MultiMesh multiMesh = loadMultiMeshOBJ(gfx, resourceManager, filepath, generateNormals, smooth, generateTangents, generateUVs);
		model.meshes = new ArrayList<>();
		
		for(SubMesh subMesh : multiMesh.subMeshes)
		{
			Mesh mesh = new Mesh();
			mesh.name = subMesh.name;
			
			ArrayList<Vertex> vertices = new ArrayList<>();
			ArrayList<Integer> indices = new ArrayList<>();
			HashMap<Vertex, Integer> indexMap = new HashMap<>();
			
			Vector2f minPosition = new Vector2f(0.0f);
			Vector2f maxPosition = new Vector2f(1.0f);
			
			float invX = 1.0f / (maxPosition.x - minPosition.x);
			float invY = 1.0f / (maxPosition.y - minPosition.y);
			
			int currentIndex = 0;
			
			for(int i = subMesh.indexBegin; i < subMesh.indexEnd; i++)
			{
				ArrayList<Index> face = multiMesh.faces.get(i);
				
				// Triangulate Faces
				for(int j = 0; j < face.size() - 2; j++)
				{
					Vertex vertex1 = new Vertex();
					Vertex vertex2 = new Vertex();
					Vertex vertex3 = new Vertex();
					
					Index index1 = face.get(0 + 0);
					Index index2 = face.get(j + 1);
					Index index3 = face.get(j + 2);
					
					int pIndex1 = index1.position - 1;
					int pIndex2 = index2.position - 1;
					int pIndex3 = index3.position - 1;
					
					int tIndex1 = index1.texCoord - 1;
					int tIndex2 = index2.texCoord - 1;
					int tIndex3 = index3.texCoord - 1;
					
					int nIndex1 = index1.normal - 1;
					int nIndex2 = index2.normal - 1;
					int nIndex3 = index3.normal - 1;
					
					vertex1.position = multiMesh.positions.get(pIndex1);
					vertex2.position = multiMesh.positions.get(pIndex2);
					vertex3.position = multiMesh.positions.get(pIndex3);
					
					if(index1.texCoord != -1 && !generateUVs)
					{
						vertex1.texCoord = multiMesh.texCoords.get(tIndex1);
						vertex2.texCoord = multiMesh.texCoords.get(tIndex2);
						vertex3.texCoord = multiMesh.texCoords.get(tIndex3);
					}
					else
					{
						vertex1.texCoord = new Vector2f();
						vertex2.texCoord = new Vector2f();
						vertex3.texCoord = new Vector2f();
						
						//GENERATE UVS

						vertex1.texCoord.x = (vertex1.position.x - minPosition.x) * invX;
						vertex1.texCoord.y = (vertex1.position.y - minPosition.y) * invY;
						
						vertex2.texCoord.x = (vertex2.position.x - minPosition.x) * invX;
						vertex2.texCoord.y = (vertex2.position.y - minPosition.y) * invY;
						
						vertex3.texCoord.x = (vertex3.position.x - minPosition.x) * invX;
						vertex3.texCoord.y = (vertex3.position.y - minPosition.y) * invY;
					}
					
					if(index1.normal != -1 && !generateNormals)
					{
						vertex1.normal = multiMesh.normals.get(nIndex1);
						vertex2.normal = multiMesh.normals.get(nIndex2);
						vertex3.normal = multiMesh.normals.get(nIndex3);
					}
					else
					{
						vertex1.normal = new Vector3f();
						vertex2.normal = new Vector3f();
						vertex3.normal = new Vector3f();
						
						//GENERATE NORMALS

						vertex1.normal = calcNormal(vertex1.position, vertex2.position, vertex3.position, true);
						vertex2.normal = calcNormal(vertex2.position, vertex3.position, vertex1.position, true);
						vertex3.normal = calcNormal(vertex3.position, vertex1.position, vertex2.position, true);
					}
					
					Integer vIndex1 = indexMap.get(vertex1);
					Integer vIndex2 = indexMap.get(vertex2);
					Integer vIndex3 = indexMap.get(vertex3);
					
					if(vIndex1 != null)
						indices.add(vIndex1);
					else
					{
						vertices.add(vertex1);
						indices.add(currentIndex);
						indexMap.put(vertex1, vIndex1);
						++currentIndex;
					}
					
					if(vIndex2 != null)
						indices.add(vIndex2);
					else
					{
						vertices.add(vertex2);
						indices.add(currentIndex);
						indexMap.put(vertex2, vIndex2);
						++currentIndex;
					}
					
					if(vIndex3 != null)
						indices.add(vIndex3);
					else
					{
						vertices.add(vertex3);
						indices.add(currentIndex);
						indexMap.put(vertex3, vIndex3);
						++currentIndex;
					}
					
				}
				
			}
			
			FloatBuffer vertexBuffer = FloatBuffer.allocate(vertices.size() * 8);	//12 for tangent
			IntBuffer indexBuffer = IntBuffer.allocate(indices.size());
			
			for(Vertex vert : vertices)
			{
				vertexBuffer.put(vert.position.x);
				vertexBuffer.put(vert.position.y);
				vertexBuffer.put(vert.position.z);
				
				vertexBuffer.put(vert.texCoord.x);
				vertexBuffer.put(vert.texCoord.y);
				
				vertexBuffer.put(vert.normal.x);
				vertexBuffer.put(vert.normal.y);
				vertexBuffer.put(vert.normal.z);
			}
			
			for(Integer i : indices)
			{
				indexBuffer.put(i);
			}

			mesh.vertexBuffer = gfx.createBuffer();
			mesh.indexBuffer = gfx.createBuffer();
			mesh.vertexSize = vertices.size();
			mesh.indexSize = indices.size();
			mesh.material = subMesh.material;
			
			gfx.setBufferData(mesh.vertexBuffer, BufferType.VERTEX, vertexBuffer.array());
			gfx.setBufferData(mesh.indexBuffer, BufferType.ELEMENT, indexBuffer.array());

			model.meshes.add(mesh);
		}
		
		return model;
	}
	
	public static MultiMesh loadMultiMeshOBJ(Graphics gfx, ResourceManager resourceManager, String filepath, boolean generateNormals, boolean smooth, boolean generateTangents, boolean generateUVs)
	{
		if(!FileUtils.fileExists(filepath))
		{
			System.out.println("Error loading OBJ file : File not found - '" + filepath + "'.");
			return null;
		}
		
		FileReader 		fileReader 		= FileReader.getFileReader(FileType.TEXT, filepath);
		SubMesh 		activeMesh 		= null;
		MaterialData 	activeMaterial 	= null;

		ArrayList<SubMesh> subMeshes	= new ArrayList<>();
		
		ArrayList<Vector3f>	positions 	= new ArrayList<>();
		ArrayList<Vector2f> texCoords 	= new ArrayList<>();
		ArrayList<Vector3f> normals		= new ArrayList<>();
		
		ArrayList<ArrayList<Index>> faces = new ArrayList<>();
		
		HashMap<String, MaterialData> materialMap = new HashMap<>();
		
		int activeSmoothGroup = -1;
		
		int currentIndex = 0;
		
		while(fileReader.hasLine())
		{
			String line = fileReader.readLine();
			
			if(line.startsWith("#"))
				continue;
			
			String[] tokens = line.trim().split("\\s+");
			
			
			/* Parse Object/Group */
			
			if(tokens[0].equals("o") || tokens[0].equals("g"))
			{
				if(activeMesh == null && !positions.isEmpty())
				{
					// Default group
					activeMesh = new SubMesh();
					activeMesh.name = "default";
					activeMesh.indexEnd = currentIndex;
					
					if(activeMaterial != null)
					{
						Material material = new Material();
						material.name = activeMaterial.name;
						material.diffuse = activeMaterial.diffuse == null ? null : resourceManager.getResource(Texture.class, activeMaterial.diffuse);
						material.normal = activeMaterial.normal == null ? null : resourceManager.getResource(Texture.class, activeMaterial.normal);
//						material.specular = activeMaterial.specular == null ? null : resourceManager.getResource(Texture.class, activeMaterial.specular);
						material.exponent = activeMaterial.exponent;
						material.intensity = activeMaterial.intensity;
						activeMesh.material = material;
					}
					
					subMeshes.add(activeMesh);
					
					continue;
				}
				else
				{
					if(activeMesh != null)
					{
						activeMesh.indexEnd = currentIndex;

						if(activeMaterial != null)
						{
							Material material = new Material();
							material.name = activeMaterial.name;
							material.diffuse = activeMaterial.diffuse == null ? null : resourceManager.getResource(Texture.class, activeMaterial.diffuse);
							material.normal = activeMaterial.normal == null ? null : resourceManager.getResource(Texture.class, activeMaterial.normal);
//							material.specular = activeMaterial.specular == null ? null : resourceManager.getResource(Texture.class, activeMaterial.specular);
							material.exponent = activeMaterial.exponent;
							material.intensity = activeMaterial.intensity;
							activeMesh.material = material;
						}
						
						subMeshes.add(activeMesh);
					}
					
					activeMesh = new SubMesh();
					activeMesh.indexBegin = currentIndex;
					activeMesh.name = tokens[1];
					
					continue;
				}
			}
			
			
			/* Use Material */
			
			if(tokens[0].equals("mtllib"))
			{
				String name = FileUtils.getFilename(tokens[1]);
				String mtlPath = FileUtils.getContainingFolder(filepath) + "\\" + name + ".mtl";

				HashMap<String, MaterialData> materialLib = loadMTL(mtlPath);
				
				materialMap.putAll(materialLib);
			}
			
			
			/* Use Material */
			
			if(tokens[0].equals("usemtl"))
			{
				MaterialData material = materialMap.get(tokens[1]);
				activeMaterial = material;
			}
			
			
			/* Parse Vertex */
			
			if(tokens[0].equals("v"))
			{
				Vector3f vertex = new Vector3f();
				vertex.x = Float.parseFloat(tokens[1]);
				vertex.y = Float.parseFloat(tokens[2]);
				vertex.z = Float.parseFloat(tokens[3]);
				positions.add(vertex);
				continue;
			}
			

			/* Parse Normal */
			
			if(tokens[0].equals("vn"))
			{
				if(!generateNormals)
				{
					Vector3f normal = new Vector3f();
					normal.x = Float.parseFloat(tokens[1]);
					normal.y = Float.parseFloat(tokens[2]);
					normal.z = Float.parseFloat(tokens[3]);
					normals.add(normal);
					continue;
				}
				
			}
			

			/* Parse Texture Coordinate */
			
			if(tokens[0].equals("vt"))
			{
				if(!generateUVs)
				{
					Vector2f texCoord = new Vector2f();
					texCoord.x = Float.parseFloat(tokens[1]);
					texCoord.y = Float.parseFloat(tokens[2]);
					texCoords.add(texCoord);
				}
				
				continue;
			}
			

			/* Parse Face */
			
			if(tokens[0].equals("f"))
			{
				ArrayList<Index> indices = new ArrayList<>();
				
				for(int i = 1; i < tokens.length; i++)
				{
					Index index = new Index();
					
					String subIndices[] = tokens[i].split("/");
					int len = subIndices.length;
					
					index.position = Integer.parseInt(subIndices[0]);
					
					if(len > 1 && !subIndices[1].equals(""))
						index.texCoord = Integer.parseInt(subIndices[1]);
					
					if(len > 2)
						index.normal = Integer.parseInt(subIndices[2]);
					
					index.smoothGroup = activeSmoothGroup;
					
					indices.add(index);
				}
				
				faces.add(indices);
				
				++currentIndex;
				
				continue;
			}

			
			/* Parse Smoothing Group */
			
			if(tokens[0].equals("s"))
			{
				if(tokens[1].equals("off"))
				{
					activeSmoothGroup = -1;
				}
				
				else if (tokens[1].equals("on"))
				{
					activeSmoothGroup = 1;
				}
				
				else
				{
					activeSmoothGroup = Integer.parseInt(tokens[1]);
				}
				
				continue;
			}
			
		}
		
		if(activeMesh == null && !positions.isEmpty())
		{
			// Default group
			activeMesh = new SubMesh();
			activeMesh.name = "default";
			activeMesh.indexEnd = currentIndex;
			
			if(activeMaterial != null)
			{
				Material material = new Material();
				material.name = activeMaterial.name;
				material.diffuse = activeMaterial.diffuse == null ? null : resourceManager.getResource(Texture.class, activeMaterial.diffuse);
				material.normal = activeMaterial.normal == null ? null : resourceManager.getResource(Texture.class, activeMaterial.normal);
//				material.specular = activeMaterial.specular == null ? null : resourceManager.getResource(Texture.class, activeMaterial.specular);
				material.exponent = activeMaterial.exponent;
				material.intensity = activeMaterial.intensity;
				activeMesh.material = material;
			}
			
			subMeshes.add(activeMesh);
		}
		else
		{
			if(activeMesh != null)
			{
				activeMesh.indexEnd = currentIndex;
				
				if(activeMaterial != null)
				{
					Material material = new Material();
					material.name = activeMaterial.name;
					material.diffuse = activeMaterial.diffuse == null ? null : resourceManager.getResource(Texture.class, activeMaterial.diffuse);
					material.normal = activeMaterial.normal == null ? null : resourceManager.getResource(Texture.class, activeMaterial.normal);
//					material.specular = activeMaterial.specular == null ? null : resourceManager.getResource(Texture.class, activeMaterial.specular);
					material.exponent = activeMaterial.exponent;
					material.intensity = activeMaterial.intensity;
					activeMesh.material = material;
				}
				
				subMeshes.add(activeMesh);
			}
		}
		
		fileReader.close();
		
		MultiMesh multi = new MultiMesh();
		multi.positions = positions;
		multi.texCoords = texCoords;
		multi.normals 	= normals;
		multi.subMeshes = subMeshes;
		multi.faces 	= faces;
		
		return multi;
	}
	
	private static HashMap<String, MaterialData> loadMTL(String filepath)
	{
		if(!FileUtils.fileExists(filepath))
		{
			System.out.println("Error loading MTL file : File not found - '" + filepath + "'.");
			return null;
		}
		
		FileReader fileReader = FileReader.getFileReader(FileType.TEXT, filepath);

		MaterialData activeMaterial = null;
		HashMap<String, MaterialData> materials = new HashMap<>();
		
		while(fileReader.hasLine())
		{
			String line = fileReader.readLine();
			
			if(line.startsWith("#"))
				continue;
			
			String tokens[] = line.trim().split("\\s+");
			
			
			/* Material */
			
			if(tokens[0].equals("newmtl"))
			{
				if(activeMaterial != null)
					materials.put(activeMaterial.name, activeMaterial);
				
				activeMaterial = new MaterialData();
				activeMaterial.name = tokens[1];
				
				continue;
			}
			
			
			/* Diffuse Texture*/
			
			if(tokens[0].equals("map_Kd"))
			{
				if(activeMaterial != null)
					activeMaterial.diffuse = tokens[1];
				continue;
			}
			
			
			/* Normal Texture */
			
			if(tokens[0].equals("map_bump") || tokens[0].equals("bump"))
			{
				if(activeMaterial != null)
					activeMaterial.normal = tokens[1];
				continue;
			}
			
			/* Specular Color */
			
			if(tokens[0].equals("Ks"))
			{
				if(activeMaterial != null)
				{
					activeMaterial.specularColor = new Vector3f();
					activeMaterial.specularColor.x = Float.parseFloat(tokens[1]);
					activeMaterial.specularColor.y = Float.parseFloat(tokens[2]);
					activeMaterial.specularColor.z = Float.parseFloat(tokens[3]);
				}
				
				continue;
			}
			
			/* Specular Exponent */
			
			if(tokens[0].equals("Ns"))
			{
				if(activeMaterial != null)
					activeMaterial.exponent = Float.parseFloat(tokens[1]);
				continue;
			}
		}
		
		if(activeMaterial != null)
			materials.put(activeMaterial.name, activeMaterial);
		
		return materials;
	}
	
	private static Vector3f calcNormal(Vector3f p1, Vector3f p2, Vector3f p3, boolean flip)
	{
		Vector3f edge1 	= new Vector3f();
		Vector3f edge2 	= new Vector3f();
		
		p1.sub(p2, edge1);
		p2.sub(p3, edge2);
		
		Vector3f normal = edge1.cross(edge2);
		if(flip) normal.mul(-1.0f);
		
		return normal.normalize();
	}
	
	@SuppressWarnings("unused")
	private static Vector3f smoothNormals(ArrayList<Vector3f> normals, boolean flip)
	{
		return null;
	}
	
	@SuppressWarnings("unused")
	private static Vector3f calcTangent(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f uv1, Vector2f uv2)
	{
		Vector3f deltaPos1 = new Vector3f();
		Vector3f deltaPos2 = new Vector3f();
				
		p2.sub(p1, deltaPos1);
		p3.sub(p1, deltaPos2);
		
		Vector2f deltaUV1 = new Vector2f();
		Vector2f deltaUV2 = new Vector2f();
		
		uv2.sub(uv1, deltaUV1);
		uv2.sub(uv1, deltaUV2);
		
		float f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);
		
		Vector3f tangent = new Vector3f();
		
		tangent.x = f * (deltaUV2.y * deltaPos1.x - deltaUV1.y * deltaPos2.x);
	    tangent.y = f * (deltaUV2.y * deltaPos1.y - deltaUV1.y * deltaPos2.y);
	    tangent.z = f * (deltaUV2.y * deltaPos1.z - deltaUV1.y * deltaPos2.z);
		
		return tangent;
	}
	
}
