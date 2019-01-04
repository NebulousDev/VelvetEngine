package loaders;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.Graphics;
import graphics.Graphics.BufferType;
import graphics.Mesh;
import graphics.Mesh.SubMesh;
import loaders.obj.OBJBundle;
import loaders.obj.OBJModel;
import loaders.obj.OBJParser;
import loaders.obj.OBJVertex;
import math.Vector3f;
import utils.FileUtils;

public class ModelLoader
{
	private ModelLoader() {}
	
	public static Mesh loadFromFile(Graphics gfx, String filepath)
	{
		String ext = FileUtils.getFileExtention(filepath);
		
		if(ext.equals(".obj"))
			return loadFromOBJ(gfx, filepath, true);
		
		else
		{
			System.out.println("Error loading Mesh. Unknown extenstion '" + ext + "'. File: " + filepath);
			return null;
		}
		
	}
	
	private static float[] calcTangent(OBJVertex[] vertices, int index)
	{
		float[] tangent = new float[3];
		
		float pos1X = vertices[index].position[0];
		float pos1Y = vertices[index].position[1];
		float pos1Z = vertices[index].position[2];
		
		float pos2X = vertices[index + 1].position[0];
		float pos2Y = vertices[index + 1].position[1];
		float pos2Z = vertices[index + 1].position[2];
		
		float pos3X = vertices[index + 2].position[0];
		float pos3Y = vertices[index + 2].position[1];
		float pos3Z = vertices[index + 2].position[2];
		
		float uv1X = vertices[index].texture[0];
		float uv1Y = vertices[index].texture[1];
		
		float uv2X = vertices[index + 1].texture[0];
		float uv2Y = vertices[index + 1].texture[1];
		
		float uv3X = vertices[index + 2].texture[0];
		float uv3Y = vertices[index + 2].texture[1];
		
		float edge1X = pos2X - pos1X;
		float edge1Y = pos2Y - pos1Y;
		float edge1Z = pos2Z - pos1Z;
		
		float edge2X = pos3X - pos2X;
		float edge2Y = pos3Y - pos2Y;
		float edge2Z = pos3Z - pos2Z;
		
		float duv1X = uv2X - uv1X;
		float duv1Y = uv2Y - uv1Y;
		
		float duv2X = uv3X - uv2X;
		float duv2Y = uv3Y - uv2Y;
		
		float f = 1.0f / (duv1X * duv2Y - duv2X * duv1Y);
		
		tangent[0] = f * (duv2Y * edge1X - duv1Y * edge2X);
		tangent[1] = f * (duv2Y * edge1Y - duv1Y * edge2Y);
		tangent[2] = f * (duv2Y * edge1Z - duv1Y * edge2Z);
		
		//TODO: normalize these!
		
		return tangent;
	}
	
	private static Mesh loadFromOBJ(Graphics gfx, String filepath, boolean calcTangent)
	{
		Mesh model = new Mesh();
		OBJModel objModel = OBJParser.parseOBJ(FileUtils.RESOURCE_PATH + filepath);
		
		model.vbo = gfx.createBuffer();
		model.ibo = gfx.createBuffer();
		
		model.subMeshes = new ArrayList<>();
		
		FloatBuffer vertexBuffer = FloatBuffer.allocate(objModel.vertices.length * 12);
		IntBuffer indexBuffer = IntBuffer.allocate(objModel.indices.length);
		
		for(int i = 0; i < objModel.vertices.length; i++)
		{
			OBJVertex vert = objModel.vertices[i];
			
			if(vert.position != null) vertexBuffer.put(vert.position);
			else vertexBuffer.put(new float[] {0,0,0});
			
			if(vert.texture != null) vertexBuffer.put(vert.texture);
			else vertexBuffer.put(new float[] {0,0});
			
			if(vert.normal != null) vertexBuffer.put(vert.normal);
			else vertexBuffer.put(new float[] {0,0,0});
			
			if(vert.normal != null && calcTangent)
				vertexBuffer.put(calcTangent(objModel.vertices, i));
		}
		
		indexBuffer.put(objModel.indices);
		
		gfx.setBufferData(model.vbo, BufferType.VERTEX, vertexBuffer.array());
		gfx.setBufferData(model.ibo, BufferType.ELEMENT, indexBuffer.array());
		
		for(OBJBundle objBundle : objModel.materialBundles)
		{
			SubMesh mesh = new SubMesh();
			mesh.name = objBundle.name;
			mesh.offset = objBundle.index;
			mesh.count = objBundle.count;
			
			if(objBundle.material != null)
				mesh.color = new Vector3f(objBundle.material.diffuse[0], 
						objBundle.material.diffuse[1], objBundle.material.diffuse[2]);
			else mesh.color = new Vector3f(1.0f);
			
			model.subMeshes.add(mesh);
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
