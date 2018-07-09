import org.lwjgl.opengl.GL11;

import core.Application;
import core.Window;
import entity.Camera;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.Model;
import graphics.ModelRenderer;
import graphics.Program;
import graphics.Shader;
import graphics.ShaderType;
import graphics.Uniform;
import input.Buttons;
import input.Input;
import input.Keys;
import math.Axis;
import math.Matrix4f;
import resource.AssetManager;
import resource.Texture;
import utils.FileUtils;

public class Sandbox
{
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Application app = Application.createApplication("VelvetEngine", GraphicsAPI.GRAPHICS_OPENGL);
		app.createWindow("VelvetEngine", 1280, 720, 0, 0, Window.CENTERED);
		
		Window window = app.getWindow();
		
		Graphics gfx = app.getGraphics();
		gfx.setClearColor(0.0f, 0.06f, 0.08f, 1.0f);
		//gfx.setClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		Camera camera = Camera.createCamera(Matrix4f.Perspective(60.0f, window.getAspect(), 0.01f, 1000f));
		camera.getPosition().set(0, 0, 0.0f);
		
		String vert = FileUtils.readFileAsString("/shaders/test.vert");
		String frag = FileUtils.readFileAsString("/shaders/test.frag");
		
		Program program 	= gfx.createProgram("testShaderProgram");
		Shader 	vertex 		= gfx.createShader("testShaderVert", ShaderType.SHADER_TYPE_VERTEX, vert);
		Shader 	fragment 	= gfx.createShader("testShaderFrag", ShaderType.SHADER_TYPE_FRAGMENT, frag);
		
		gfx.attachShader(program, vertex);
		gfx.attachShader(program, fragment);
		
		gfx.finalizeProgram(program);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Model model = Model.loadFromFile(gfx, "/models/testScene.obj");
		
		Texture texture = (Texture) AssetManager.getInstance().addAndLoadAsset("/textures/test.png");
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Matrix4f mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(Matrix4f.Translation(Axis.FORWARD)).mul(camera.getView()).mul(Matrix4f.Translation(camera.getPosition()));
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
		
		float[] cubeVerts = 
		{
			// POSITION			// COLOR
			-1.0f,-1.0f,-1.0f,	0.583f, 0.771f, 0.014f,
			-1.0f,-1.0f, 1.0f,	0.609f, 0.115f, 0.436f,
			-1.0f, 1.0f, 1.0f,	0.327f, 0.483f, 0.844f,
			 1.0f, 1.0f,-1.0f,	0.822f, 0.569f, 0.201f,
			-1.0f,-1.0f,-1.0f,	0.435f, 0.602f, 0.223f,
			-1.0f, 1.0f,-1.0f,	0.310f, 0.747f, 0.185f,
			 1.0f,-1.0f, 1.0f,	0.597f, 0.770f, 0.761f,
			-1.0f,-1.0f,-1.0f,	0.559f, 0.436f, 0.730f,
			 1.0f,-1.0f,-1.0f,	0.359f, 0.583f, 0.152f,
			 1.0f, 1.0f,-1.0f,	0.483f, 0.596f, 0.789f,
			 1.0f,-1.0f,-1.0f,	0.559f, 0.861f, 0.639f,
			-1.0f,-1.0f,-1.0f,	0.195f, 0.548f, 0.859f,
			-1.0f,-1.0f,-1.0f,	0.014f, 0.184f, 0.576f,
			-1.0f, 1.0f, 1.0f,	0.771f, 0.328f, 0.970f,
			-1.0f, 1.0f,-1.0f,	0.406f, 0.615f, 0.116f,
			 1.0f,-1.0f, 1.0f,	0.676f, 0.977f, 0.133f,
			-1.0f,-1.0f, 1.0f,	0.971f, 0.572f, 0.833f,
			-1.0f,-1.0f,-1.0f,	0.140f, 0.616f, 0.489f,
			-1.0f, 1.0f, 1.0f,	0.997f, 0.513f, 0.064f,
			-1.0f,-1.0f, 1.0f,	0.945f, 0.719f, 0.592f,
			 1.0f,-1.0f, 1.0f,	0.543f, 0.021f, 0.978f,
			 1.0f, 1.0f, 1.0f,	0.279f, 0.317f, 0.505f,
			 1.0f,-1.0f,-1.0f,	0.167f, 0.620f, 0.077f,
			 1.0f, 1.0f,-1.0f,	0.347f, 0.857f, 0.137f,
			 1.0f,-1.0f,-1.0f,	0.055f, 0.953f, 0.042f,
			 1.0f, 1.0f, 1.0f,	0.714f, 0.505f, 0.345f,
			 1.0f,-1.0f, 1.0f,	0.783f, 0.290f, 0.734f,
			 1.0f, 1.0f, 1.0f,	0.722f, 0.645f, 0.174f,
			 1.0f, 1.0f,-1.0f,	0.302f, 0.455f, 0.848f,
			-1.0f, 1.0f,-1.0f,	0.225f, 0.587f, 0.040f,
			 1.0f, 1.0f, 1.0f,	0.517f, 0.713f, 0.338f,
			-1.0f, 1.0f,-1.0f,	0.053f, 0.959f, 0.120f,
			-1.0f, 1.0f, 1.0f,	0.393f, 0.621f, 0.362f,
			 1.0f, 1.0f, 1.0f,	0.673f, 0.211f, 0.457f,
			-1.0f, 1.0f, 1.0f,	0.820f, 0.883f, 0.371f,
			 1.0f,-1.0f, 1.0f,	0.982f, 0.099f, 0.879f
		};
		
		//TODO: MOVE TO GRAPHICS 
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		//GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		float sensitivity = 0.04f;
		float speed = 0.001f;

		while(!app.shouldClose())
		{
			gfx.clearBuffers();
			Input.getInstance().update();
			app.pollEvents();
			
			if(Input.keyHeld(Keys.KEY_W)) camera.getPosition().add(camera.getForward().copy().mul(-speed));
			if(Input.keyHeld(Keys.KEY_S)) camera.getPosition().add(camera.getBack().copy().mul(-speed));
			if(Input.keyHeld(Keys.KEY_A)) camera.getPosition().add(camera.getLeft().copy().mul(speed));
			if(Input.keyHeld(Keys.KEY_D)) camera.getPosition().add(camera.getRight().copy().mul(speed));
			
			if(Input.keyPressed(Keys.KEY_UP)) 	speed *= 2.0;
			if(Input.keyPressed(Keys.KEY_DOWN)) speed /= 2.0;
			
			if(Input.isMouseCaptured())
			{
				camera.rotate(Axis.UP, Input.getMouseRelative().x * sensitivity);
				camera.rotate(camera.getRight(), Input.getMouseRelative().y * -sensitivity);
				mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(Matrix4f.Translation(Axis.FORWARD)).mul(camera.getView()).mul(Matrix4f.Translation(camera.getPosition()));
			}
			
			if(Input.buttonPressed(Buttons.BUTTON_LEFT)) Input.captureMouse(false);
			if(Input.keyPressed(Keys.KEY_ESCAPE)) Input.releaseMouse();
		
			gfx.bindProgram(program);
			
			gfx.setUniform(mvp, mvpMatrix);
			
			///////////////////////////////
			
			ModelRenderer.render(gfx, program, model);
			
			///////////////////////////////
			
			//int error = 0;
			if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
				System.out.println("GLERROR: " + error);

			gfx.unbindProgram();
			
			app.swapBuffers();
		}
	}

}
