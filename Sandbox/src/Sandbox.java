import org.lwjgl.opengl.GL11;

import core.Application;
import core.Window;
import entity.Camera;
import entity.Entity;
import entity.components.MeshComponent;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsMesh;
import graphics.GraphicsProgram;
import graphics.GraphicsShader;
import graphics.ShaderType;
import graphics.renderers.ModelRenderer;
import graphics.GraphicsTexture;
import graphics.GraphicsUniform;
import input.Buttons;
import input.Input;
import input.Keys;
import loaders.ModelLoader;
import loaders.TextureLoader;
import math.Axis;
import math.Matrix4f;
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
		
		Camera camera = Camera.createCamera(Matrix4f.Perspective(60.0f, window.getAspect(), 0.01f, 1000f));
		camera.getPosition().set(0, 0, 0.0f);
		
		String vert = FileUtils.readFileAsString("shaders/test.vert");
		String frag = FileUtils.readFileAsString("shaders/test.frag");
		
		GraphicsProgram program 	= gfx.createProgram("testShaderProgram");
		GraphicsShader 	vertex 		= gfx.createShader("testShaderVert", ShaderType.SHADER_TYPE_VERTEX, vert);
		GraphicsShader 	fragment 	= gfx.createShader("testShaderFrag", ShaderType.SHADER_TYPE_FRAGMENT, frag);
		
		gfx.attachShader(program, vertex);
		gfx.attachShader(program, fragment);
		
		gfx.finalizeProgram(program);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		GraphicsMesh model = ModelLoader.loadFromFile(gfx, "models/testscene.obj");
		GraphicsTexture texture = TextureLoader.loadFromFile(gfx, "textures/default2.png");
		
		gfx.setActiveTextureSlot(0);
		gfx.bindTexture(texture);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Matrix4f mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(Matrix4f.Translation(Axis.FORWARD)).mul(camera.getView()).mul(Matrix4f.Translation(camera.getPosition()));
		GraphicsUniform mvp = gfx.getUniform(program, "mvp");
		GraphicsUniform view = gfx.getUniform(program, "view");
		
		int error = 0;
		if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
			System.out.println("GLERROR: " + error);
		
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
			gfx.setUniform(view, camera.getView().mul(Matrix4f.Translation(camera.getPosition())));
			
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
