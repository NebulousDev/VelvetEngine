package graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import loaders.FileUtils;
import loaders.OBJFormat.OBJMesh;
import loaders.OBJFormat.OBJModel;
import loaders.OBJFormat.OBJVertex;
import loaders.OBJLoader;

public class Model
{
	public static class SubMesh
	{
		public String name;
		public int offset;
		public int count;
	}
	
	public GraphicsBuffer vbo;
	public GraphicsBuffer ibo;
	
	public ArrayList<SubMesh> meshes;
	
	public void dispose(Graphics gfx)
	{
		gfx.freeBuffer(vbo);
		gfx.freeBuffer(ibo);
	}
	
	public static Model loadFromFile(Graphics gfx, String filepath)
	{
		String ext = FileUtils.getFileExtention(filepath);
		
		if(ext.equals(".obj"))
			return loadFromOBJ(gfx, filepath);
		
		else
		{
			System.out.println("Error loading Mesh. Unknown extenstion '" + ext + "'. File: " + filepath);
			return null;
		}
		
	}
	
	private static Model loadFromOBJ(Graphics gfx, String filepath)
	{
		Model model = new Model();
		OBJModel objModel = OBJLoader.readOBJ(filepath);
		
		model.vbo = gfx.createBuffer();
		model.ibo = gfx.createBuffer();
		
		model.meshes = new ArrayList<>();
		
		FloatBuffer vertexBuffer = FloatBuffer.allocate(objModel.vertices.length * OBJVertex.SIZE);
		IntBuffer indexBuffer = IntBuffer.allocate(objModel.indices.length);
		
		for(OBJVertex vert : objModel.vertices)
		{
			vertexBuffer.put(vert.position.toArray());
			vertexBuffer.put(vert.texCoord.toArray());
			vertexBuffer.put(vert.normal.toArray());
		}
		
		indexBuffer.put(objModel.indices);
		
		gfx.setBufferData(model.vbo, BufferType.GRAPHICS_BUFFER_VERTEX, vertexBuffer.array());
		gfx.setBufferData(model.ibo, BufferType.GRAPHICS_BUFFER_INDEX, indexBuffer.array());
		
		for(OBJMesh objMesh : objModel.meshes)
		{
			SubMesh mesh = new SubMesh();
			mesh.name = objMesh.name;
			mesh.offset = objMesh.idxOffset;
			mesh.count = objMesh.idxCount;
			model.meshes.add(mesh);
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		gfx.bindBuffer(model.vbo);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
		gfx.unbindBuffer(model.vbo);
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		return model;
	}
}
