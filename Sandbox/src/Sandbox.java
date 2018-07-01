import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.Application;
import core.Buttons;
import core.Input;
import core.Keys;
import core.Window;
import entity.Camera;
import graphics.BufferType;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsBuffer;
import graphics.Program;
import graphics.Shader;
import graphics.ShaderType;
import graphics.Uniform;
import loaders.FileLoader;
import loaders.OBJLoader;
import loaders.OBJLoader.OBJModel;
import math.Axis;
import math.Matrix4f;

public class Sandbox
{
	public static void main(String[] args)
	{
		new Axis();
		
		Application app = Application.createApplication("VelvetEngine", GraphicsAPI.GRAPHICS_OPENGL);
		app.createWindow("VelvetEngine", 1280, 720, 0, 0, Window.CENTERED);
		
		Window window = app.getWindow();
		Input input = app.getInput();
		
		Graphics gfx = app.getGraphics();
		gfx.setClearColor(0.0f, 0.08f, 0.1f, 1.0f);
		
		Camera camera = Camera.createCamera(Matrix4f.Perspective(90.0f, window.getAspect(), 0.01f, 1000f));
		camera.getPosition().set(0, 0, 1.0f);
		
		String vert = FileLoader.readFileAsString("/shaders/test.vert");
		String frag = FileLoader.readFileAsString("/shaders/test.frag");
		
		Program program 	= gfx.createProgram("testShaderProgram");
		Shader 	vertex 		= gfx.createShader("testShaderVert", ShaderType.SHADER_TYPE_VERTEX, vert);
		Shader 	fragment 	= gfx.createShader("testShaderFrag", ShaderType.SHADER_TYPE_FRAGMENT, frag);
		
		gfx.attachShader(program, vertex);
		gfx.attachShader(program, fragment);
		
		gfx.finalizeProgram(program);
		
		OBJModel model = OBJLoader.parseOBJModel("/models", "standard");
		
		Matrix4f mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(camera.getView()).mul(Matrix4f.Translation(camera.getPosition()));
		Uniform mvp = gfx.getUniform(program, "mvp");
		
		int error = 0;
		if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
			System.out.println("GLERROR: " + error);
		
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
		
		float sensitivity = 0.04f;
		float speed = 0.001f;
		
		while(!app.shouldClose())
		{
			gfx.clearBuffers();
			input.update();
			app.pollEvents();
			
			if(input.keyHeld(Keys.KEY_W)) camera.getPosition().add(camera.getForward().copy().mul(-speed));
			if(input.keyHeld(Keys.KEY_S)) camera.getPosition().add(camera.getBack().copy().mul(-speed));
			if(input.keyHeld(Keys.KEY_A)) camera.getPosition().add(camera.getLeft().copy().mul(-speed));
			if(input.keyHeld(Keys.KEY_D)) camera.getPosition().add(camera.getRight().copy().mul(-speed));
			
			System.out.println(camera.getForward());
			
			if(input.isMouseCaptured())
			{
				camera.rotate(Axis.UP, input.getMouseRelative().x * -sensitivity);
				camera.rotate(camera.getRight(), input.getMouseRelative().y * -sensitivity);
				mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(camera.getView()).mul(Matrix4f.Translation(camera.getPosition()));
			}
			
			if(input.buttonPressed(Buttons.BUTTON_LEFT)) input.captureMouse(false);
			if(input.keyPressed(Keys.KEY_ESCAPE)) input.releaseMouse();
		
			gfx.bindProgram(program);
			gfx.bindBuffer(vbo);
			gfx.bindBuffer(ibo);
			
			gfx.setUniform(mvp, mvpMatrix);
			
			GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, plainIdxs.length, GL11.GL_UNSIGNED_INT, 0);
			
			//int error = 0;
			if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
				System.out.println("GLERROR: " + error);
			
			gfx.unbindBuffer(ibo);
			gfx.unbindBuffer(vbo);
			gfx.unbindProgram();
			
			app.swapBuffers();
		}
	}

}
