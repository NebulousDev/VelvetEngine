package loaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.BufferType;
import graphics.Graphics;
import graphics.GraphicsBuffer;
import loaders.OBJLoader.OBJMaterial;
import loaders.OBJLoader.OBJMeshFace;
import loaders.OBJLoader.OBJMeshVertex;
import loaders.OBJLoader.OBJModel;
import loaders.OBJLoader.OBJObject;
import loaders.OBJLoader.OBJTexture;
import math.Vector2f;
import math.Vector3f;

public class ModelLoader
{
	public static class Vertex
	{
		public Vector3f pos;
		public Vector2f tex;
		public Vector3f norm;
	}
	
	public static class Material
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
	
	public static class Mesh
	{
		public String name;
		public Material material;
		public ArrayList<Vertex> vertices;
		public ArrayList<Integer> indices;
	}
	
	public static class Model
	{
		public String name;
		public ArrayList<Mesh> meshes;
		public int totalVertices;
		public int totalIndices;
	}
	
	public static class MeshBuffer
	{
		public int offset;
		public int count;
	}
	
	public static class ModelBuffer
	{
		public GraphicsBuffer vbo;
		public GraphicsBuffer ibo;
		
		public ArrayList<MeshBuffer> meshBuffers;
	}
	
	private static Material parseOBJMaterial(OBJMaterial objMaterial)
	{
		Material material = new Material();
		material.name = objMaterial.name;
		
		material.diffuse = objMaterial.diffuse;
		
		//TODO: DO
		
		return material;
		
	}
	
	private static String createVertexHashString(int vertIdx, int texIdx, int normIdx)
	{
		return "" + vertIdx + texIdx + normIdx;
	}
	
	private static Mesh parseOBJMesh(OBJObject objObject, ArrayList<Vector3f> allPos, ArrayList<Vector2f> allTex, ArrayList<Vector3f> allNorm)
	{
		Mesh mesh = new Mesh();
		mesh.name = objObject.name;
		mesh.vertices = new ArrayList<>();
		mesh.indices = new ArrayList<>();
		
		HashMap<String, Integer> vertexMap = new HashMap<>();
		
		mesh.material = parseOBJMaterial(objObject.material);
		
		int idxCount = 0;
		
		for(OBJMeshFace face : objObject.mesh.faces)
		{
			for(OBJMeshVertex vert : face.vertices)
			{
				String key = createVertexHashString(vert.vertIdx, vert.texIdx, vert.normIdx);
				
				if(vertexMap.containsKey(key))
					mesh.indices.add(vertexMap.get(key));
				
				else
				{
					Vertex vertex = new Vertex();
					vertex.pos = allPos.get(vert.vertIdx - 1);
					if(objObject.mesh.hasTexCoords)vertex.tex = allTex.get(vert.texIdx - 1);
					if(objObject.mesh.hasNormals)vertex.norm = allNorm.get(vert.normIdx - 1);
					mesh.vertices.add(vertex);
					mesh.indices.add(idxCount);
					vertexMap.put(key, idxCount++);
				}
			}
		}
		
		return mesh;
	}
	
	public static Model createFromOBJModel(OBJModel objModel)
	{
		Model model = new Model();
		model.name = objModel.name;
		model.meshes = new ArrayList<>();
		
		ArrayList<Vector3f> allPos = new ArrayList<>();
		ArrayList<Vector2f> alltex = new ArrayList<>();
		ArrayList<Vector3f> allNorm = new ArrayList<>();
		
		for(OBJObject object : objModel.objects)
		{
			allPos.addAll(object.mesh.vertices);
			alltex.addAll(object.mesh.texCoords);
			allNorm.addAll(object.mesh.normals);
		}
		
		for(OBJObject object : objModel.objects)
		{
			Mesh mesh = parseOBJMesh(object, allPos, alltex, allNorm);
			model.meshes.add(mesh);
			model.totalVertices += mesh.vertices.size();
			model.totalIndices += mesh.indices.size();
		}
		
		return model;
	}
	
	public static ModelBuffer loadToModelBuffer(Graphics gfx, Model model)
	{
		ModelBuffer modelBuffer = new ModelBuffer();
		modelBuffer.meshBuffers = new ArrayList<>();
		modelBuffer.vbo = gfx.createBuffer();
		modelBuffer.ibo = gfx.createBuffer();
		
		FloatBuffer vertexBuffer = FloatBuffer.allocate(model.totalVertices * 8);
		IntBuffer indexBuffer = IntBuffer.allocate(model.totalIndices);
		
		int currentOffset = 0;
		
		for(Mesh mesh : model.meshes)
		{
			MeshBuffer meshBuffer = new MeshBuffer();
			meshBuffer.offset = currentOffset;
			meshBuffer.count = mesh.vertices.size();
			currentOffset += mesh.vertices.size();
			
			for(Vertex vert : mesh.vertices)
			{
				if(vert.pos != null)
				{
					vertexBuffer.put(vert.pos.x);
					vertexBuffer.put(vert.pos.y);
					vertexBuffer.put(vert.pos.z);
				}
				
				if(vert.tex != null)
				{
					vertexBuffer.put(vert.tex.x);
					vertexBuffer.put(vert.tex.y);
				}
				
				if(vert.norm != null)
				{
					vertexBuffer.put(vert.norm.x);
					vertexBuffer.put(vert.norm.y);
					vertexBuffer.put(vert.norm.z);
				}
			}
			
			for(int i : mesh.indices)
				indexBuffer.put(i);
			
			modelBuffer.meshBuffers.add(meshBuffer);
		}
		
		//vertexBuffer.flip();
		//indexBuffer.flip();
		
		gfx.setBufferData(modelBuffer.vbo, BufferType.GRAPHICS_BUFFER_VERTEX, vertexBuffer.array());
		gfx.setBufferData(modelBuffer.ibo, BufferType.GRAPHICS_BUFFER_INDEX, indexBuffer.array());
		
		return modelBuffer;
	}

	public static void drawModel(Graphics gfx, ModelBuffer buffer)
	{
		gfx.bindBuffer(buffer.vbo);
		gfx.bindBuffer(buffer.ibo);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
		
		for(MeshBuffer mesh : buffer.meshBuffers)
		{
			gfx.drawElementsRange(mesh.offset, mesh.count);
		}
	}

}
