import org.lwjgl.opengl.GL11;

import core.Application;
import core.Game;
import core.Window;
import entity.Entity;
import entity.camera.CameraComponent;
import entity.camera.PerspectiveCameraComponent;
import entity.component.TransformComponent;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.Mesh;
import graphics.ShaderProgram;
import graphics.Texture;
import graphics.component.MeshComponent;
import graphics.component.PhongMaterialComponent;
import graphics.component.PhongRenderComponent;
import graphics.renderers.PhongRenderer;
import input.Buttons;
import input.Input;
import input.Keys;
import math.Axis;
import math.Matrix4f;
import math.Quaternion;
import math.Vector3f;
import resource.Asset;
import resource.AssetManager;

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
		
		/* Initialize Resources */
		
		AssetManager assetManager = app.getGame().getResourceManager();
		assetManager.registerAssetType(new Mesh(), "Mesh");
		assetManager.registerAssetType(new Texture(), "Texture");
		assetManager.registerAssetType(new ShaderProgram(), "ShaderProgram");
		
		assetManager.registerAsset(Mesh.TYPE, "mesh_testscene", "/models/testscene.obj", 0, AssetManager.PRELOAD);
		assetManager.registerAsset(Mesh.TYPE, "mesh_bunny", "/models/bunny.obj", 0, AssetManager.PRELOAD);
		assetManager.registerAsset(Texture.TYPE, "texture_default", "/textures/default.png", 0, AssetManager.PRELOAD);
		assetManager.registerAsset(Texture.TYPE, "texture_default2", "/textures/default2.png", 0, AssetManager.PRELOAD);
		assetManager.registerAsset(ShaderProgram.TYPE, "shader_default", "/shaders/test", 0, AssetManager.PRELOAD);
		
		/* Load Resources */
		
		Asset<Mesh> 			scene 		= assetManager.getAsset("mesh_testscene");
		Asset<Mesh> 			bunny 		= assetManager.getAsset("mesh_bunny");
		Asset<Texture> 			texture 	= assetManager.getAsset("texture_default2");
		Asset<ShaderProgram> 	program 	= assetManager.getAsset("shader_default");
		
		gfx.setActiveTextureSlot(0);
		gfx.bindTexture(texture.getResource());
		
		/* Setup Entities */
		
		TransformComponent 		groundTransform 	= new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());
		MeshComponent 			groundMesh 			= new MeshComponent(scene.getResource());
		PhongMaterialComponent 	groundMaterial		= new PhongMaterialComponent();
		PhongRenderComponent	groundRender		= new PhongRenderComponent(groundMesh, groundMaterial);
		Entity 					groundEntity 		= game.getEntityManager().createEntity("entity_ground", groundTransform, groundMesh, groundMaterial, groundRender);
		
		TransformComponent 		bunnyTransform 		= new TransformComponent(new Vector3f(0.0f, 0.0f, -10.0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());
		MeshComponent 			bunnyMesh 			= new MeshComponent(bunny.getResource());
		PhongRenderComponent	bunnyRender			= new PhongRenderComponent(bunnyMesh, groundMaterial);
		Entity 					bunnyEntity 		= game.getEntityManager().createEntity("entity_bunny", bunnyTransform, bunnyMesh, groundMaterial, bunnyRender);
		
		/* Setup Camera */
		
		TransformComponent 	cameraTransform 	= new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());
		CameraComponent 	cameraComponent 	= new PerspectiveCameraComponent(60.0f, window.getAspect(), 0.0001f, 1000f);
		//CameraComponent 	cameraComponent 	= new OrthoCameraComponent(20f, window.getAspect(), -100f, 100f);
		Entity				cameraEntity		= game.getEntityManager().createEntity("camera", cameraTransform, cameraComponent);
		
		/* Setup Renderers */
		
		PhongRenderer		phongRenderer		= new PhongRenderer(assetManager);
		
		System.out.println("ground: " + game.getEntityManager().getAllComponents(groundEntity.getEntityID()));
		System.out.println("bunny: " + game.getEntityManager().getAllComponents(bunnyEntity.getEntityID()));
		System.out.println("camera: " + game.getEntityManager().getAllComponents(cameraEntity.getEntityID()));
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		int error = 0;
		if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
			System.out.println("GLERROR: " + error);
		
		float sensitivity = 0.04f;
		float speed = 0.001f;
		
		Matrix4f mvpMatrix = null;
		
		while(!app.shouldClose())
		{
			gfx.clearBuffers();
			Input.getInstance().update();
			app.pollEvents();
			
			if(Input.keyHeld(Keys.KEY_W)) cameraTransform.position.add(cameraTransform.getForward().mul(-speed));
			if(Input.keyHeld(Keys.KEY_S)) cameraTransform.position.add(cameraTransform.getBack().mul(-speed));
			if(Input.keyHeld(Keys.KEY_A)) cameraTransform.position.add(cameraTransform.getLeft().mul(-speed));
			if(Input.keyHeld(Keys.KEY_D)) cameraTransform.position.add(cameraTransform.getRight().mul(-speed));
			
			if(Input.keyPressed(Keys.KEY_UP)) 	speed *= 2.0;
			if(Input.keyPressed(Keys.KEY_DOWN)) speed /= 2.0;
			
			if(Input.isMouseCaptured())
			{
				cameraTransform.rotate(Axis.UP, Input.getMouseRelative().x * -sensitivity);
				cameraTransform.rotate(cameraTransform.getRight(), Input.getMouseRelative().y * -sensitivity);
			}
			
			if(Input.buttonPressed(Buttons.BUTTON_LEFT)) Input.captureMouse(false);
			if(Input.keyPressed(Keys.KEY_ESCAPE)) Input.releaseMouse();
		
			phongRenderer.begin(gfx, game.getEntityManager());
			phongRenderer.render(cameraEntity, gfx, game.getEntityManager());
			phongRenderer.end(gfx, game.getEntityManager());
			
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
