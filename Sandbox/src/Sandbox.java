import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.Application;
import core.Window;
import graphics.BufferType;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsBuffer;
import graphics.Program;
import graphics.Shader;
import graphics.ShaderType;
import utils.FileReader;

public class Sandbox
{
	public static void main(String[] args)
	{
		Application app = Application.createApplication("VelvetEngine", GraphicsAPI.GRAPHICS_OPENGL);
		app.createWindow("VelvetEngine", 640, 480, 0, 0, Window.CENTERED);
		
		Graphics gfx = app.getGraphics();
		gfx.setClearColor(0.0f, 0.08f, 0.1f, 1.0f);
		
		String vert = FileReader.readFileAsString("/shaders/test.vert");
		String frag = FileReader.readFileAsString("/shaders/test.frag");
		
		Program program 	= gfx.createProgram("testShaderProgram");
		Shader 	vertex 		= gfx.createShader("testShaderVert", ShaderType.SHADER_TYPE_VERTEX, vert);
		Shader 	fragment 	= gfx.createShader("testShaderFrag", ShaderType.SHADER_TYPE_FRAGMENT, frag);
		
		gfx.attachShader(program, vertex);
		gfx.attachShader(program, fragment);
		
		gfx.finalizeProgram(program);
		
		float[] plainVerts = 
		{
			// POSITION				// COLOR
			-1.0f, -1.0f, 0.0f,		1.0f, 0.0f, 1.0f,
			-1.0f,  1.0f, 0.0f,		0.0f, 1.0f, 0.0f,
			 1.0f,  1.0f, 0.0f,		1.0f, 0.0f, 0.0f,
			 1.0f, -1.0f, 0.0f,		0.0f, 0.0f, 1.0f,
		};
		
		int[] plainIdxs = {0, 1, 2, 0, 2, 3};
		
		GraphicsBuffer vbo = gfx.createBuffer();
		GraphicsBuffer ibo = gfx.createBuffer();
		gfx.setBufferData(vbo, BufferType.GRAPHICS_BUFFER_VERTEX, plainVerts);
		gfx.setBufferData(ibo, BufferType.GRAPHICS_BUFFER_INDEX, plainIdxs);
		
		gfx.bindBuffer(vbo);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
		gfx.unbindBuffer(vbo);
		
		while(!app.shouldClose())
		{
			gfx.clearBuffers();
		
			gfx.bindProgram(program);
			gfx.bindBuffer(vbo);
			gfx.bindBuffer(ibo);
			
			GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, plainIdxs.length, GL11.GL_UNSIGNED_INT, 0);
			
			int error = 0;
			if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
				System.out.println("GLERROR: " + error);
			
			gfx.unbindBuffer(ibo);
			gfx.unbindBuffer(vbo);
			gfx.unbindProgram();
			
			app.update();
		}
	}

}
