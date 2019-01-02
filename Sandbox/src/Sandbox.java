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
import graphics.system.LineRenderer;
import graphics.system.PhongRenderer;
import input.Buttons;
import input.Input;
import input.Keys;
import math.Matrix4f;
import math.Quaternion;
import math.Vector3f;
import resource.ResourceManager;
import utils.Axis;

public class Sandbox extends Game
{
	TransformComponent 	cameraTransform;
	CameraComponent 	cameraComponent;
	Entity				cameraEntity;
	PhongRenderer 		phongRenderer;
	LineRenderer 		lineRenderer;
	
	Entity 				lineEntity;
	
	@Override
	@SuppressWarnings("unused")
	protected void onLoad()
	{
//		float[] data = 
//		{
//			0.9989685f, 0.0f, -0.04551963f, 0.0f,
//			0.0f, 1.000005f, 0.0f, 0.0f,
//			0.04551963f, 0.0f, 0.9989685f, 0.0f,
//			0.0f, 0.0f, 0.0f, 1.0f
//		};
//		
//		float[] data2 = 
//		{
//			1.0f, 2.0f, 3.0f, 0.0f,
//			4.0f, 5.0f, 6.0f, 0.0f,
//			7.0f, 8.0f, 9.0f, 0.0f,
//			0.0f, 0.0f, 0.0f, 1.0f
//		};
//		
//		Matrix4f id = new Matrix4f();
//		Matrix4f mat1 = new Matrix4f(data2);
//		
//		System.out.println("---\n" + id + "\n");
//		System.out.println(mat1 + "\n");
//		
//		Matrix4f result = new Matrix4f();
//		
//		id.mul(mat1);
//		
//		System.out.println(id + "\n");
		
		/* Initialize Resources */
		
		Graphics gfx = getGraphics();
		gfx.setClearColor(0.0f, 0.06f, 0.08f, 1.0f);
		
		ResourceManager resourceManager = getResourceManager();
		resourceManager.addPath("assets/models");
		resourceManager.addPath("assets/shaders");
		resourceManager.addPath("assets/textures");
		
		/* Load Resources */
		
		Mesh 			scene 		= resourceManager.getResource(Mesh.class, "testscene");
		Mesh  			standard 	= resourceManager.getResource(Mesh.class, "standard");
		Mesh  			bunny 		= resourceManager.getResource(Mesh.class, "bunny");
		Texture 		texture 	= resourceManager.getResource(Texture.class, "default2");
		ShaderProgram 	program 	= resourceManager.getResource(ShaderProgram.class, "simple");
		
		gfx.setActiveTextureSlot(0);
		gfx.bindTexture(texture);
		
		/* Setup Entities */
		
		class RotatingComponent implements UpdateComponent
		{
			Quaternion rot = new Quaternion().setAxisAngle(Axis.UP, 0.001f);

			@Override
			public void update(Game game, Entity entity, float delta)
			{
				TransformComponent transform = entity.getComponent(TransformComponent.class);
				if(transform != null)
					transform.orientation.rotate(rot);
			}
			
			@Override
			public Class<? extends Component> getCastType()
			{
				return UpdateComponent.class;
			}
		}
		
		RotatingComponent rotatingComponent = new RotatingComponent();
		
		UpdateComponent test = UpdateComponent.class.cast(rotatingComponent);
		
		TransformComponent 		groundTransform 	= new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Quaternion());
		MeshComponent 			groundMesh 			= new MeshComponent(scene);
		PhongMaterialComponent 	groundMaterial		= new PhongMaterialComponent();
		PhongRenderComponent	groundRender		= new PhongRenderComponent();
		Entity 					groundEntity 		= getEntityManager().createEntity("entity_ground", groundTransform, groundMesh, groundMaterial, groundRender);
		
		TransformComponent 		bunnyTransform 		= new TransformComponent(new Vector3f(0.0f, 0.0f, -10.0f), new Vector3f(2.0f, 2.0f, 2.0f), new Quaternion());
		MeshComponent 			bunnyMesh 			= new MeshComponent(bunny);
		PhongRenderComponent	bunnyRender			= new PhongRenderComponent();
		Entity 					bunnyEntity 		= getEntityManager().createEntity("entity_bunny", bunnyTransform, bunnyMesh, rotatingComponent, groundMaterial, bunnyRender, test);
		
		MeshComponent			standardMesh		= new MeshComponent(standard);
		PhongRenderComponent	standatdRender		= new PhongRenderComponent();
		
		/* Setup Camera */
		
		cameraTransform 	= new TransformComponent(new Vector3f(0f, 0f, 0f), new Vector3f(1.0f, 1.0f, 1.0f), new Quaternion());
		cameraComponent 	= new PerspectiveCameraComponent(60.0f, getApplication().getWindow().getAspect(), 0.0001f, 1000f);
		cameraEntity		= getEntityManager().createEntity("camera", cameraTransform, cameraComponent);
		
		/* Setup Systems */
		
		phongRenderer 	= new PhongRenderer(this);
		lineRenderer	= new LineRenderer(this);

		//lineEntity = getEntityManager().createEntity(new TransformComponent(), new LineRenderComponent(cameraTransform.position, getMouseRay(cameraEntity), new Vector4f(0, 1, 1, 1)));
	
	}

	float sensitivity = 0.04f;
	float speed = 0.001f;
	
	Matrix4f mvpMatrix = null;
	
	/*
	public Vector3f getMouseRay(Entity camera)
	{
		TransformComponent transformComponent = camera.getComponent(TransformComponent.class);
		CameraComponent cameraComponent = camera.getComponent(PerspectiveCameraComponent.class);
		
		float width = getApplication().getWindow().getWidth();
		float height = getApplication().getWindow().getHeight();
		
		Vector2f mousePosition = Input.getMouseAbsolute();
		
		float mousePosNormX = (mousePosition.x * 2.0f) / width - 1.0f;
		float mousePosNormY = 1.0f - (mousePosition.y * 2.0f) / height;

		Vector3f rayNorm = new Vector3f(mousePosNormX, mousePosNormY, 1.0f);
		Vector4f rayClip = new Vector4f(rayNorm.x, rayNorm.y, -1.0f, 1.0f);
		Vector4f rayEye = rayClip.mul(cameraComponent.projection.inverse());
		rayEye = new Vector4f(rayEye.x, rayEye.y, -1.0f, 1.0f);
		
		Matrix4f view = transformComponent.orientation.toMatrix().mul(Matrix4f.Translation(transformComponent.position));
		
		Vector4f rayWorld = rayEye.mul(view.inverse()).normalize();

		return rayWorld.xyz();
	}
	*/
	
	Quaternion pitch = new Quaternion();
	Quaternion yaw = new Quaternion();
	
	@Override
	protected void onUpdate()
	{
		if(Input.keyHeld(Keys.KEY_W)) cameraTransform.position.add(cameraTransform.getForward().mul(speed));
		if(Input.keyHeld(Keys.KEY_S)) cameraTransform.position.add(cameraTransform.getBack().mul(speed));
		if(Input.keyHeld(Keys.KEY_A)) cameraTransform.position.add(cameraTransform.getLeft().mul(-speed));
		if(Input.keyHeld(Keys.KEY_D)) cameraTransform.position.add(cameraTransform.getRight().mul(-speed));
		
		if(Input.keyHeld(Keys.KEY_LEFT)) cameraTransform.orientation.rotate(Axis.FORWARD, 0.5f * sensitivity);
		if(Input.keyHeld(Keys.KEY_RIGHT)) cameraTransform.orientation.rotate(Axis.FORWARD, 0.5f * -sensitivity);
		
		if(Input.keyPressed(Keys.KEY_UP)) 	speed *= 2.0;
		if(Input.keyPressed(Keys.KEY_DOWN)) speed /= 2.0;
		
		if(Input.isMouseCaptured())
		{
			pitch.setAxisAngle(Axis.RIGHT, Input.getMouseRelative().y * sensitivity);
			yaw.setAxisAngle(Axis.UP, Input.getMouseRelative().x * sensitivity);
			
			cameraTransform.orientation.rotateLocal(pitch);
			cameraTransform.orientation.rotate(yaw);
		}
		
		if(Input.buttonPressed(Buttons.BUTTON_LEFT)) Input.captureMouse(false);
		if(Input.keyPressed(Keys.KEY_ESCAPE)) Input.releaseMouse();
	
		Graphics gfx = getGraphics();
		
		//lineEntity.getComponent(LineRenderComponent.class).p1 = cameraTransform.position;
		//lineEntity.getComponent(LineRenderComponent.class).p2 = getMouseRay(cameraEntity);
		
		phongRenderer.begin(gfx, getEntityManager());
		phongRenderer.render(cameraEntity, gfx,getEntityManager());
		phongRenderer.end(gfx, getEntityManager());
		
		//lineRenderer.begin(gfx, getEntityManager());
		//lineRenderer.render(cameraEntity, gfx,getEntityManager());
		//lineRenderer.end(gfx, getEntityManager());
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
