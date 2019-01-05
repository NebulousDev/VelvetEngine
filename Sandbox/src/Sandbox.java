import core.Application;
import core.Game;
import entity.Component;
import entity.Entity;
import entity.camera.CameraComponent;
import entity.camera.PerspectiveCameraComponent;
import entity.component.TransformComponent;
import entity.component.UpdateComponent;
import graphics.Graphics;
import graphics.Model;
import graphics.Texture;
import graphics.component.DirectionalLightComponent;
import graphics.component.LineRenderComponent;
import graphics.component.ModelComponent;
import graphics.component.PhongMaterialComponent;
import graphics.component.PhongRenderComponent;
import graphics.component.PointLightComponent;
import graphics.component.SpotLightComponent;
import graphics.system.LineRenderer;
import graphics.system.PhongRenderer;
import input.Buttons;
import input.Input;
import input.Keys;
import math.Matrix4f;
import math.Quaternion;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;
import resource.ResourceManager;
import utils.Axis;

public class Sandbox extends Game
{
	PhongRenderer 		phongRenderer;
	LineRenderer 		lineRenderer;

	TransformComponent 	cameraTransform;
	CameraComponent 	cameraComponent;
	Entity				cameraEntity;
	
	Entity 				lineEntity;
	
	@Override
	@SuppressWarnings("unused")
	protected void onLoad()
	{
//		float[] data = 
//		{
//			1.0f, 2.0f, 3.0f, 0.0f,
//			4.0f, 5.0f, 6.0f, 0.0f,
//			7.0f, 8.0f, 9.0f, 0.0f,
//			0.0f, 0.0f, 0.0f, 1.0f
//		};
//				
//		Matrix4f id = new Matrix4f();
//		Matrix4f mat = new Matrix4f(data);
//		Matrix4f mat = new Matrix4f(id.elements);
//		
//		System.out.println(mat);
//		System.out.println(mat.invert());
		
		/* Initialize Resources */
		
		Graphics gfx = getGraphics();
		gfx.setClearColor(0.0f, 0.06f, 0.08f, 1.0f);
		
		ResourceManager resourceManager = getResourceManager();
		resourceManager.addPath("assets");
		
		/* Load Resources */
		
		Model 		testScene	= resourceManager.getResource(Model.class, "testscene");
		Model 		sponza 		= resourceManager.getResource(Model.class, "sponza");
		Model  		standard 	= resourceManager.getResource(Model.class, "standard");
		Model  		dragon 		= resourceManager.getResource(Model.class, "dragon");
		Model  		bunny 		= resourceManager.getResource(Model.class, "bunny");
		Texture 	default1 	= resourceManager.getResource(Texture.class, "default1");
		Texture 	default2 	= resourceManager.getResource(Texture.class, "default2");
		Texture 	marble 		= resourceManager.getResource(Texture.class, "marble");
		Texture 	brick 		= resourceManager.getResource(Texture.class, "brickwall");
		Texture 	brick_n 	= resourceManager.getResource(Texture.class, "brickwall_n");
		
		/* Setup Entities */
		
		class RotatingComponent implements UpdateComponent
		{
			Quaternion rot = new Quaternion().setAxisAngle(Axis.UP, 1f);

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

		PhongMaterialComponent 	defaultMaterial		= new PhongMaterialComponent(brick, brick_n, 1.0f, 1024.0f);
		
		TransformComponent 		groundTransform 	= new TransformComponent(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.05f, 0.05f, 0.05f), new Quaternion());
		ModelComponent 			groundMesh 			= new ModelComponent(sponza);
		PhongRenderComponent	groundRender		= new PhongRenderComponent();
		Entity 					groundEntity 		= getEntityManager().createEntity("entity_ground", groundTransform, groundMesh, defaultMaterial, groundRender);
		
		TransformComponent 		bunnyTransform 		= new TransformComponent(new Vector3f(0.0f, 2.0f, 0.0f), new Vector3f(2.0f, 2.0f, 2.0f), new Quaternion());
		ModelComponent 			bunnyMesh 			= new ModelComponent(bunny);
		PhongRenderComponent	bunnyRender			= new PhongRenderComponent();
		Entity 					bunnyEntity 		= getEntityManager().createEntity("entity_bunny", bunnyTransform, bunnyMesh, rotatingComponent, defaultMaterial, bunnyRender, test);
		
		ModelComponent			standardMesh		= new ModelComponent(standard);
		PhongRenderComponent	standatdRender		= new PhongRenderComponent();
		
		/* Setup Lights */
		
		// DIRECTIONAL LIGHTS
		
		Vector3f direction = new Vector3f(0.0f, -1.0f, 0.5f);
		Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
		float intensity = 0.2f;
		DirectionalLightComponent dirLightComponent1 = new DirectionalLightComponent(direction, color, intensity);
		
		Vector3f direction2 = new Vector3f(0.0f, -1.0f, -0.8f);
		Vector3f color2 = new Vector3f(0.1f, 0.1f, 1.0f);
		float intensity2 = 0.1f;
		DirectionalLightComponent dirLightComponent2 = new DirectionalLightComponent(direction2, color2, intensity2);
		
		Entity dirLight1Entity = getEntityManager().createEntity("dirLight1", dirLightComponent1);
		Entity dirLight2Entity = getEntityManager().createEntity("dirLight2", dirLightComponent2);
		
		// POINT LIGHTS
		
		Vector3f position3 = new Vector3f(-20.0f, 5.0f, 0.0f);
		Vector3f color3 = new Vector3f(1.0f, 0.0f, 0.0f);
		Vector3f attenuation3 = new Vector3f(50.0f, 0.01f, 5.0f);
		float intensity3 = 200.0f;
		PointLightComponent pointLightComponnet1 = new PointLightComponent(attenuation3, color3, intensity3);
		TransformComponent pointLightTransform1 = new TransformComponent(position3);
		
		Vector3f position4 = new Vector3f(0.0f, 5.0f, 0.0f);
		Vector3f color4 = new Vector3f(0.0f, 1.0f, 0.0f);
		Vector3f attenuation4 = new Vector3f(50.0f, 0.01f, 5.0f);
		float intensity4 = 200.0f;
		PointLightComponent pointLightComponnet2 = new PointLightComponent(attenuation4, color4, intensity4);
		TransformComponent pointLightTransform2 = new TransformComponent(position4);
		
		Vector3f position5 = new Vector3f(20.0f, 5.0f, 0.0f);
		Vector3f color5 = new Vector3f(0.0f, 0.0f, 1.0f);
		Vector3f attenuation5 = new Vector3f(50.0f, 0.01f, 5.0f);
		float intensity5 = 200.0f;
		PointLightComponent pointLightComponnet3 = new PointLightComponent(attenuation5, color5, intensity5);
		TransformComponent pointLightTransform3 = new TransformComponent(position5);
		
		Entity pointLight1Entity = getEntityManager().createEntity("pointLight1", pointLightComponnet1, pointLightTransform1);
		Entity pointLight2Entity = getEntityManager().createEntity("pointLight2", pointLightComponnet2, pointLightTransform2);
		Entity pointLight3Entity = getEntityManager().createEntity("pointLight3", pointLightComponnet3, pointLightTransform3);
		
		// SPOT LIGHTS
		
		Vector3f position6 = new Vector3f(40.0f, 10.0f, 0.0f);
		Vector3f direction6 = new Vector3f(0.0f, -1.0f, 0.0f);
		Vector3f color6 = new Vector3f(1.0f, 1.0f, 1.0f);
		Vector3f attenuation6 = new Vector3f(50.0f, 0.01f, 5.0f);
		float radius = 0.8f;
		float intensity6 = 300.0f;
		SpotLightComponent spotLightComponnet1 = new SpotLightComponent(direction6, attenuation6, color6, radius, intensity6);
		TransformComponent spotLightTransform1 = new TransformComponent(position6);
		
		Entity spotLight1Entity = getEntityManager().createEntity("spotLight1", spotLightComponnet1, spotLightTransform1);
		
		/* Setup Camera */
		
		cameraTransform 	= new TransformComponent(new Vector3f(0f, 0f, 0f), new Vector3f(1.0f, 1.0f, 1.0f), new Quaternion());
		cameraComponent 	= new PerspectiveCameraComponent(60.0f, getApplication().getWindow().getAspect(), 0.0001f, 1000f);
		cameraEntity		= getEntityManager().createEntity("camera", cameraTransform, cameraComponent);
		
		/* Setup Systems */
		
		phongRenderer 	= new PhongRenderer();
		lineRenderer	= new LineRenderer();
		
		phongRenderer.initialize(this, gfx);
		lineRenderer.initialize(this, gfx);

		lineEntity = getEntityManager().createEntity(new TransformComponent(), new LineRenderComponent(cameraTransform.position, getMouseRay(cameraEntity), new Vector4f(0, 1, 1, 1)));
	
	}

	public Vector3f getMouseRay(Entity camera)
	{
		TransformComponent transformComponent = camera.getComponent(TransformComponent.class);
		CameraComponent cameraComponent = camera.getComponent(PerspectiveCameraComponent.class);
		
		float width = getApplication().getWindow().getWidth();
		float height = getApplication().getWindow().getHeight();
		
		Vector2f mousePosition = Input.getMouseAbsolute();
		
		float mousePosNormX = (mousePosition.x * 2.0f) / width - 1.0f;
		float mousePosNormY = 1.0f - (mousePosition.y * 2.0f) / height;
		
//		System.out.println("NormX: " + mousePosNormX + ", NormY: " + mousePosNormY);

		Vector3f rayNorm = new Vector3f(mousePosNormX, mousePosNormY, 1.0f);
		Vector4f rayClip = new Vector4f(rayNorm.x, rayNorm.y, -1.0f, 1.0f);
		
//		System.out.println("ClipX: " + rayClip.x + ", ClipY: " + rayClip.y);

		Matrix4f invProjection = new Matrix4f();
		cameraComponent.projection.invert(invProjection);
		
		Vector4f rayEye = rayClip.mul(invProjection);
		rayEye = new Vector4f(rayEye.x, rayEye.y, -1.0f, 0.0f);
		
//		System.out.println("EyeX: " + rayEye.x + ", EyeY: " + rayEye.y);
		
		Matrix4f view = transformComponent.getViewMatrix();
		
//		System.out.println(view); 
		
		Matrix4f invView = new Matrix4f();
		view.invert(invView);

		Vector4f rayWorld = rayEye.mul(invView);

		return rayWorld.xyz().normalize();
	}
	
	float sensitivity = 0.04f;
	float speed = 0.15f;
	
	Quaternion pitch = new Quaternion();
	Quaternion yaw = new Quaternion();
	
	@Override
	protected void onUpdate()
	{
		if(Input.keyHeld(Keys.KEY_W)) cameraTransform.position.add(cameraTransform.getForward().mul(speed));
		if(Input.keyHeld(Keys.KEY_S)) cameraTransform.position.add(cameraTransform.getBack().mul(speed));
		if(Input.keyHeld(Keys.KEY_A)) cameraTransform.position.add(cameraTransform.getLeft().mul(-speed));
		if(Input.keyHeld(Keys.KEY_D)) cameraTransform.position.add(cameraTransform.getRight().mul(-speed));
		
		if(Input.keyHeld(Keys.KEY_LEFT)) cameraTransform.orientation.rotateLocal(new Quaternion().setAxisAngle(Axis.FORWARD, 0.5f * sensitivity));
		if(Input.keyHeld(Keys.KEY_RIGHT)) cameraTransform.orientation.rotateLocal(new Quaternion().setAxisAngle(Axis.FORWARD, 0.5f * -sensitivity));
		
		if(Input.keyPressed(Keys.KEY_UP)) 	speed *= 2.0;
		if(Input.keyPressed(Keys.KEY_DOWN)) speed /= 2.0;
		
		if(Input.isMouseCaptured())
		{
			pitch.setAxisAngle(Axis.RIGHT, Input.getMouseRelative().y * sensitivity);
			yaw.setAxisAngle(Axis.UP, Input.getMouseRelative().x * sensitivity);
			
			cameraTransform.orientation.rotate(yaw);
			cameraTransform.orientation.rotateLocal(pitch);
		}
		
		if(Input.buttonPressed(Buttons.BUTTON_LEFT)) Input.captureMouse(false);
		if(Input.keyPressed(Keys.KEY_ESCAPE)) Input.releaseMouse();
	
		Graphics gfx = getGraphics();
		
		Vector3f ray = getMouseRay(cameraEntity);
//		System.out.println("---\nRayForward:" + ray);
//		System.out.println("CameraForward: " + cameraTransform.orientation.getForward());
		
		lineEntity.getComponent(LineRenderComponent.class).p1 = cameraTransform.position;
		lineEntity.getComponent(LineRenderComponent.class).p2 = ray;
//		lineEntity.getComponent(TransformComponent.class).position = cameraTransform.position;
		 
//		System.out.println(lineEntity.getComponent(LineRenderComponent.class).p1);
//		System.out.println(lineEntity.getComponent(LineRenderComponent.class).p2);
//		System.out.println(lineEntity.getComponent(TransformComponent.class).position);
		
		phongRenderer.begin(gfx, getEntityManager());
		phongRenderer.render(cameraEntity, gfx,getEntityManager());
		phongRenderer.end(gfx, getEntityManager());
		
//		lineRenderer.begin(gfx, getEntityManager());
//		lineRenderer.render(cameraEntity, gfx,getEntityManager());
//		lineRenderer.end(gfx, getEntityManager());
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
