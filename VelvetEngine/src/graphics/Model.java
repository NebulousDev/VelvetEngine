package graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import loaders.OBJLoader.OBJMeshFace;
import loaders.OBJLoader.OBJMeshVertex;
import loaders.OBJLoader.OBJModel;
import loaders.OBJLoader.OBJObject;
import math.Vector3f;

public class Model
{
	public static class Material
	{
		public Vector3f color;
	}
	
	public static class Vertex
	{
		public static final int SIZE = 3;
		public Vector3f position;
	}
	
	public static class Mesh
	{
		public Material 			material;
		public int 					iboOffset;
		public int 					iboCount;
	}
	
	public GraphicsBuffer 	vbo;
	public GraphicsBuffer 	ibo;
	public Uniform 			uniformColor;

	ArrayList<Mesh> meshes;
	
	private static void genMesh(OBJObject object, Model model, ArrayList<Vertex> allVerts, ArrayList<Integer> allIdxs, int idxCount)
	{
		Mesh mesh = new Mesh();
		mesh.iboOffset = idxCount;
		mesh.iboCount = object.mesh.faces.size() 
				* object.mesh.faces.get(0).numVertices;
		idxCount += mesh.iboCount;
		
		Material material = new Material();
		if(object.material != null)
			material.color = object.material.diffuse;
		else material.color = new Vector3f(1);
		mesh.material = material;
		
		for(Vector3f pos : object.mesh.vertices)
		{
			Vertex vert = new Vertex();
			vert.position = pos;
			allVerts.add(vert);
		}
		
		for(OBJMeshFace face : object.mesh.faces)
		{
			for(OBJMeshVertex vert : face.vertices)
			{
				allIdxs.add(vert.vertIdx - 1);
			}
		}
		
		model.meshes.add(mesh);
	}
	
	public static Model loadModelFromOBJ(Graphics gfx, OBJModel obj)
	{
		Model model = new Model();
		model.vbo = gfx.createBuffer();
		model.ibo = gfx.createBuffer();
		model.meshes = new ArrayList<>();
		
		int idxCount = 0;
		
		ArrayList<Vertex> allVerts = new ArrayList<>();
		ArrayList<Integer> allIdxs = new ArrayList<>();
		
		//TODO: do this:
		if(!obj.useObjectList)
		{
			genMesh(obj.object, model, allVerts, allIdxs, idxCount);
		}
		
		else
		{
			for(OBJObject object : obj.objects)
			{
				genMesh(object, model, allVerts, allIdxs, idxCount);
			}
		}
		
		FloatBuffer vboBuffer = FloatBuffer.allocate(allVerts.size() * Vertex.SIZE);
		IntBuffer iboBuffer = IntBuffer.allocate(allIdxs.size());	
		
		for(Vertex vert : allVerts)
		{
			vboBuffer.put(vert.position.x);
			vboBuffer.put(vert.position.y);
			vboBuffer.put(vert.position.z);
		}
		
		for(int idx : allIdxs)
		{
			iboBuffer.put(idx);
		}
		
		vboBuffer.flip();
		iboBuffer.flip();
		
		gfx.setBufferData(model.vbo, BufferType.GRAPHICS_BUFFER_VERTEX, vboBuffer.array());
		gfx.setBufferData(model.ibo, BufferType.GRAPHICS_BUFFER_INDEX, iboBuffer.array());
		
		gfx.bindBuffer(model.vbo);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 3 * Float.BYTES, 0);
		
		return model;
	}
	
	public void draw(Graphics gfx)
	{
		gfx.bindBuffer(vbo);
		gfx.bindBuffer(ibo);
		
		for(Mesh mesh : meshes)
		{
			//gfx.setUniform(uniformColor, mesh.material.color);
			gfx.drawElementsRange(mesh.iboOffset, mesh.iboCount);
		}
	}
}
