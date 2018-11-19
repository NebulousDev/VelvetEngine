package graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.Game;
import math.Vector3f;
import resource.ResourceType;
import utils.FileUtils;
import velvetobj.OBJBundle;
import velvetobj.OBJModel;
import velvetobj.OBJParser;
import velvetobj.OBJVertex;

public class Mesh extends ResourceType<Mesh>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Mesh Resource";
	
	public static class SubMesh
	{
		public String 	name;
		public int 		offset;
		public int 		count;
		public Vector3f color;
	}
	
	public GraphicsBuffer vbo;
	public GraphicsBuffer ibo;
	
	public ArrayList<SubMesh> subMeshes;

	@Override
	public boolean load(Game game, String localName, String filepath, int flags)
	{
		Graphics gfx = game.getGraphics();
		
		if(!FileUtils.fileExists(filepath))
		{
			System.out.println("Error! Failed to register resource '" + localName + "'. File not found at '" + filepath + "'.");
			return false;
		}
		
		OBJModel objModel = OBJParser.parseOBJ(filepath);
		
		vbo = gfx.createBuffer();
		ibo = gfx.createBuffer();
		
		subMeshes = new ArrayList<>();
	
		FloatBuffer vertexBuffer = FloatBuffer.allocate(objModel.vertices.length * 8);
		IntBuffer indexBuffer = IntBuffer.allocate(objModel.indices.length);
		
		for(OBJVertex vert : objModel.vertices)
		{
			if(vert.position != null) vertexBuffer.put(vert.position);
			else vertexBuffer.put(new float[] {0,0,0});
			
			if(vert.texture != null) vertexBuffer.put(vert.texture);
			else vertexBuffer.put(new float[] {0,0});
			
			if(vert.normal != null) vertexBuffer.put(vert.normal);
			else vertexBuffer.put(new float[] {0,0,0});
		}
		
		indexBuffer.put(objModel.indices);
		
		gfx.setBufferData(vbo, BufferType.GRAPHICS_BUFFER_VERTEX, vertexBuffer.array());
		gfx.setBufferData(ibo, BufferType.GRAPHICS_BUFFER_INDEX, indexBuffer.array());
		
		for(OBJBundle objBundle : objModel.materialBundles)
		{
			SubMesh mesh = new SubMesh();
			mesh.name = objBundle.name;
			mesh.offset = objBundle.index;
			mesh.count = objBundle.count;
			
			if(objBundle.material != null)
				mesh.color = new Vector3f(objBundle.material.diffuse);
			else mesh.color = new Vector3f(1.0f);
			
			subMeshes.add(mesh);
		}
		
		// TODO: Wrap with graphics calls
		// TODO: Change to VAOs
		///////////////////////////////////////////////////////////////////////////////////////////////
		gfx.bindBuffer(vbo);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
		gfx.unbindBuffer(vbo);
		///////////////////////////////////////////////////////////////////////////////////////////////
		
		return true;
	}

	public void bind(Graphics gfx)
	{
		gfx.bindBuffer(vbo);
		gfx.bindBuffer(ibo);
	}
	
	public void unbind(Graphics gfx)
	{
		gfx.unbindBuffer(vbo);
		gfx.unbindBuffer(ibo);
	}
	
	@Override
	public boolean unload(Game game)
	{
		dispose(game.getGraphics());
		return true;
	}
	
	private void dispose(Graphics gfx)
	{
		gfx.freeBuffer(vbo);
		gfx.freeBuffer(ibo);
	}

	@Override
	public Mesh create(Game game)
	{
		return new Mesh();
	}

	@Override
	public int getTypeID()
	{
		return TYPE;
	}

	@Override
	public String getTypeName()
	{
		return NAME;
	}
	
}
