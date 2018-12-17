import core.Application;
import core.Game;
import entity.Component;
import entity.Entity;
import entity.camera.CameraComponent;
import entity.camera.PerspectiveCameraComponent;
import entity.component.TransformComponent;
import entity.component.UpdateComponent;
import graphics.Graphics;
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
	TransformComponent 	cameraTransform;
	CameraComponent 	cameraComponent;
	Entity				cameraEntity;
	PhongRenderer 		phongRenderer;
	
	@Override
	@SuppressWarnings("unused")
	protected void onLoad()
	{
		/* Initialize Resources */
		
		Graphics gfx = getGraphics();
		gfx.setClearColor(0.0f, 0.06f, 0.08f, 1.0f);
		
		AssetManager assetManager = getAssetManager();
		assetManager.registerAssetType(new Mesh(), "Mesh");
		assetManager.registerAssetType(new Texture(), "Texture");
		assetManager.registerAssetType(new ShaderProgram(), "ShaderProgram");
		
		assetManager.registerAsset(Mesh.TYPE, 				"mesh_testscene", 		"/models/testscene.obj");
		assetManager.registerAsset(Mesh.TYPE, 				"mesh_standard", 		"/models/standard.obj");
		assetManager.registerAsset(Mesh.TYPE, 				"mesh_bunny", 			"/models/bunny.obj");
		assetManager.registerAsset(Texture.TYPE, 			"texture_default", 		"/textures/default.png");
		assetManager.registerAsset(Texture.TYPE, 			"texture_default2", 	"/textures/default2.png");
		assetManager.registerAsset(ShaderProgram.TYPE, 		"shader_default", 		"/shaders/test");
		
		/* Load Resources */
		
		Asset<Mesh> 			scene 		= assetManager.getAsset("mesh_testscene");
		Asset<Mesh> 			standard 	= assetManager.getAsset("mesh_standard");
		Asset<Mesh> 			bunny 		= assetManager.getAsset("mesh_bunny");
		Asset<Texture> 			texture 	= assetManager.getAsset("texture_default2");
		Asset<ShaderProgram> 	program 	= assetManager.getAsset("shader_default");
		
		gfx.setActiveTextureSlot(0);
		gfx.bindTexture(texture.getResource());
		
		/* Setup Entities */
		
		class RotatingComponent implements UpdateComponent
		{
			@Override
			public void update(Game game, Entity entity, float delta)
			{
				TransformComponent transform = entity.getComponent(TransformComponent.class);
				if(transform != null)
					transform.rotate(Axis.UP, 0.5f);
			}
			
			@Override
			public Class<? extends Component> getCastType()
			{
				return UpdateComponent.class;
			}
		}
		
		RotatingComponent rotatingComponent = new RotatingComponent();
		
		UpdateComponent test = UpdateComponent.class.cast(rotatingComponent);
		
		System.out.println(rotatingComponent.getCastType());
		System.out.println(test.getCastType());
		
		TransformComponent 		groundTransform 	= new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());
		MeshComponent 			groundMesh 			= new MeshComponent(scene.getResource());
		PhongMaterialComponent 	groundMaterial		= new PhongMaterialComponent();
		PhongRenderComponent	groundRender		= new PhongRenderComponent();
		Entity 					groundEntity 		= getEntityManager().createEntity("entity_ground", groundTransform, groundMesh, groundMaterial, groundRender);
		
		TransformComponent 		bunnyTransform 		= new TransformComponent(new Vector3f(0.0f, 0.0f, 10.0f), new Vector3f(1.0f, 2.0f, 1.0f), Quaternion.Identity());
		MeshComponent 			bunnyMesh 			= new MeshComponent(bunny.getResource());
		PhongRenderComponent	bunnyRender			= new PhongRenderComponent();
		Entity 					bunnyEntity 		= getEntityManager().createEntity("entity_bunny", bunnyTransform, bunnyMesh, groundMaterial, bunnyRender, test);
		
		MeshComponent			standardMesh		= new MeshComponent(standard.getResource());
		PhongRenderComponent	standatdRender		= new PhongRenderComponent();
		
		
		System.out.println(bunnyEntity.getAllComponents());
		
		for(int y = 0; y < 10; y++)
			for(int x = 0; x < 10; x++)
				getEntityManager().createEntity( new TransformComponent(new Vector3f(20.0f + (y * 5f), 0.0f, (x * 5f))), standardMesh, standatdRender );
		
		/* Setup Camera */
		
		//TransformComponent 	cameraTransform 	= new TransformComponent(new Vector3f(2.0f, -2.0f, 15.0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());//Quaternion.Rotation(Axis.UP, 90.0f));
		cameraTransform 	= new TransformComponent(new Vector3f(0f, 0f, 0f), new Vector3f(1.0f, 1.0f, 1.0f), Quaternion.Identity());//Quaternion.Rotation(Axis.UP, 90.0f));
		cameraComponent 	= new PerspectiveCameraComponent(60.0f, getApplication().getWindow().getAspect(), 0.0001f, 1000f);
		//CameraComponent 	cameraComponent 	= new OrthoCameraComponent(20f, window.getAspect(), -100f, 100f);
		cameraEntity		= getEntityManager().createEntity("camera", cameraTransform, cameraComponent);
		
		/* Setup Systems */
		
		phongRenderer 	= new PhongRenderer(assetManager);
		
		/////////////////////////////////////////////////////////////////////////////////////////
		
		//int error = 0;
		//if((error = GL11.glGetError()) != GL11.GL_NO_ERROR)
		//	System.out.println("GLERROR: " + error);
		
	}

	float sensitivity = 0.04f;
	float speed = 0.001f;
	
	Matrix4f mvpMatrix = null;
	
	@Override
	protected void onUpdate()
	{
		//if(Input.keyHeld(Keys.KEY_LEFT_SHIFT)) bunnyTransform.position.z += 0.01f;
		//if(Input.keyHeld(Keys.KEY_LEFT_CONTROL)) bunnyTransform.position.z -= 0.01f;
		
		if(Input.keyHeld(Keys.KEY_W)) cameraTransform.position.add(cameraTransform.getForward().mul(speed));
		if(Input.keyHeld(Keys.KEY_S)) cameraTransform.position.add(cameraTransform.getBack().mul(speed));
		if(Input.keyHeld(Keys.KEY_A)) cameraTransform.position.add(cameraTransform.getLeft().mul(-speed));
		if(Input.keyHeld(Keys.KEY_D)) cameraTransform.position.add(cameraTransform.getRight().mul(-speed));
		
		if(Input.keyPressed(Keys.KEY_UP)) 	speed *= 2.0;
		if(Input.keyPressed(Keys.KEY_DOWN)) speed /= 2.0;
		
		if(Input.isMouseCaptured())
		{
			cameraTransform.rotate(Axis.UP, Input.getMouseRelative().x * sensitivity);
			cameraTransform.rotate(cameraTransform.getRight(), Input.getMouseRelative().y * sensitivity);
		}
		
		if(Input.buttonPressed(Buttons.BUTTON_LEFT)) Input.captureMouse(false);
		if(Input.keyPressed(Keys.KEY_ESCAPE)) Input.releaseMouse();
	
		Graphics gfx = getGraphics();
		
		phongRenderer.begin(gfx, getEntityManager());
		phongRenderer.render(cameraEntity, gfx,getEntityManager());
		phongRenderer.end(gfx, getEntityManager());
	}

	@Override
	protected void onUnload()
	{
		
	}

	public static void main(String[] args)
	{
		new Application(new Sandbox()).start();
	}
	
}
