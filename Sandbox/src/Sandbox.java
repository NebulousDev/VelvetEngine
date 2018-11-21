import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import core.Application;
import core.Game;
import core.Window;
import entity.Entity;
import entity.Require;
import entity.Require.Requirements;
import entity.camera.Camera;
import entity.camera.CameraComponent;
import entity.component.TransformComponent;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.GraphicsUniform;
import graphics.Mesh;
import graphics.ShaderProgram;
import graphics.Texture;
import graphics.component.MeshComponent;
import graphics.renderers.ModelRenderer;
import input.Buttons;
import input.Input;
import input.Keys;
import math.Axis;
import math.Matrix4f;
import math.Quaternion;
import math.Vector3f;
import resource.Asset;
import resource.AssetManager;

@SuppressWarnings("deprecation")
public class Sandbox extends Game
{

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		/* Initialize Application */
		
		Application app = new Application("VelvetEngine", new Sandbox(), GraphicsAPI.GRAPHICS_OPENGL);
		Window window = app.getWindow();
		Graphics gfx = app.getGraphics();
		Game game = app.getGame();

		gfx.setClearColor(0.0f, 0.06f, 0.08f, 1.0f);
		
		app.start();
		
		/* Initialize Camera */
		
		Camera camera = Camera.createCamera(Matrix4f.Perspective(60.0f, window.getAspect(), 0.01f, 1000f));
		camera.getPosition().set(0, 0, 0.0f);
		
		/* Initialize Resources */
		
		AssetManager assetManager = app.getGame().getResourceManager();
		assetManager.registerAssetType(new Mesh(), "Mesh");
		assetManager.registerAssetType(new Texture(), "Texture");
		assetManager.registerAssetType(new ShaderProgram(), "ShaderProgram");
		
		assetManager.registerAsset(Mesh.TYPE, "mesh_testscene", "/models/testScene.obj");
		assetManager.registerAsset(Texture.TYPE, "texture_default", "/textures/default.png", 0, AssetManager.PRELOAD);
		assetManager.registerAsset(Texture.TYPE, "texture_default2", "/textures/default2.png", 0, AssetManager.PRELOAD);
		assetManager.registerAsset(ShaderProgram.TYPE, "shader_default", "/shaders/test", 0, AssetManager.PRELOAD);
		
		/* Load Resources */
		
		Asset<Mesh> 			mesh 	= assetManager.getAsset("mesh_testscene");
		Asset<Texture> 			texture = assetManager.getAsset("texture_default2");
		Asset<ShaderProgram> 	program = assetManager.getAsset("shader_default");
		
		gfx.setActiveTextureSlot(0);
		gfx.bindTexture(texture.getResource());
		
		/* Setup Entities */
		
		TransformComponent 	groundTransform 	= new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());
		MeshComponent 		groundMesh 			= new MeshComponent(mesh.getResource());
		Entity 				groundEntity 		= game.getEntityManager().createEntity("entity_ground", groundTransform, groundMesh);
		
		
		
		CameraComponent cameraComponent = new CameraComponent();
		
		Require[] requirements = cameraComponent.getClass().getAnnotationsByType(Require.class);
		System.out.println("@Requires Annotation Present: " + cameraComponent.getClass().isAnnotationPresent(Requirements.class));
		System.out.println("@Requires Annotation Data: " + Arrays.toString(requirements));
		
		
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		Matrix4f mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(camera.getView());
		GraphicsUniform mvp = gfx.getUniform(program.getResource(), "mvp");
		GraphicsUniform view = gfx.getUniform(program.getResource(), "view");
		
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
			//groundTransform.position.z += 0.001f;
			//groundTransform.orientation.mul(Quaternion.Rotation(Axis.UP, Math.toRadians(0.01f)));
			
			gfx.clearBuffers();
			Input.getInstance().update();
			app.pollEvents();
			
			if(Input.keyHeld(Keys.KEY_W)) camera.getPosition().add(camera.getForward().copy().mul(-speed));
			if(Input.keyHeld(Keys.KEY_S)) camera.getPosition().add(camera.getBack().copy().mul(-speed));
			if(Input.keyHeld(Keys.KEY_A)) camera.getPosition().add(camera.getLeft().copy().mul(-speed));
			if(Input.keyHeld(Keys.KEY_D)) camera.getPosition().add(camera.getRight().copy().mul(-speed));
			
			if(Input.keyPressed(Keys.KEY_UP)) 	speed *= 2.0;
			if(Input.keyPressed(Keys.KEY_DOWN)) speed /= 2.0;
			
			if(Input.isMouseCaptured())
			{
				camera.rotate(Axis.UP, Input.getMouseRelative().x * -sensitivity);
				camera.rotate(camera.getRight(), Input.getMouseRelative().y * -sensitivity);
			}
			
			mvpMatrix = Matrix4f.Identity().mul(camera.getProjection()).mul(camera.getView());
			mvpMatrix.mul(groundTransform.calcModelMatrix());
			
			if(Input.buttonPressed(Buttons.BUTTON_LEFT)) Input.captureMouse(false);
			if(Input.keyPressed(Keys.KEY_ESCAPE)) Input.releaseMouse();
		
			gfx.bindProgram(program.getResource());
			
			gfx.setUniform(mvp, mvpMatrix);
			gfx.setUniform(view, camera.getView());
			
			///////////////////////////////
			
			ModelRenderer.render(gfx, program.getResource(), groundMesh.mesh);
			
			///////////////////////////////
			
			//int error = 0;
			if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
				System.out.println("GLERROR: " + error);

			gfx.unbindProgram();
			
			app.swapBuffers();
		}
	}

	@Override
	protected void load()
	{
		
	}

	@Override
	protected void unload()
	{
		
	}

}
