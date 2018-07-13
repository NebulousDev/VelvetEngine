package loaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.BufferType;
import graphics.Graphics;
import graphics.Model;
import graphics.Model.SubMesh;
import math.Vector3f;
import utils.FileUtils;
import velvetobj.OBJBundle;
import velvetobj.OBJModel;
import velvetobj.OBJParser;
import velvetobj.OBJVertex;

public class ModelLoader
{
	private ModelLoader() {}
	
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
		OBJModel objModel = OBJParser.parseOBJ(FileUtils.getResourcePath() + filepath);
		
		model.vbo = gfx.createBuffer();
		model.ibo = gfx.createBuffer();
		
		model.meshes = new ArrayList<>();
		
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
		
		gfx.setBufferData(model.vbo, BufferType.GRAPHICS_BUFFER_VERTEX, vertexBuffer.array());
		gfx.setBufferData(model.ibo, BufferType.GRAPHICS_BUFFER_INDEX, indexBuffer.array());
		
		for(OBJBundle objBundle : objModel.materialBundles)
		{
			SubMesh mesh = new SubMesh();
			mesh.name = objBundle.name;
			mesh.offset = objBundle.index;
			mesh.count = objBundle.count;
			
			if(objBundle.material != null)
				mesh.color = new Vector3f(objBundle.material.diffuse);
			else mesh.color = new Vector3f(1.0f);
			
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
