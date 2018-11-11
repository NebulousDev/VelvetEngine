import entity.ComponentManager;
import entity.Entity;
import entity.EntityManager;
import entity.components.MeshComponent;
import entity.components.TransformComponent;
import graphics.GraphicsMesh;
import graphics.GraphicsTexture;
import loaders.ModelLoader;
import loaders.TextureLoader;

@SuppressWarnings("unused")
public class EntityTests
{
	public static void main(String[] args)
	{
//		ComponentManager.registerComponentType(new MeshComponent());
//		ComponentManager.registerComponentType(new TransformComponent());
//		
//		MeshComponent m1 = ComponentManager.createComponent(MeshComponent.TYPE);
//		m1.setMesh(new GraphicsMesh());
//		
//		TransformComponent t1 = ComponentManager.createComponent(TransformComponent.TYPE);
//		TransformComponent t2 = ComponentManager.createComponent(TransformComponent.TYPE);
//		TransformComponent rt1 = ComponentManager.getComponent(TransformComponent.TYPE, 0);
//		TransformComponent rt2 = ComponentManager.getComponent(TransformComponent.TYPE, 1);
//		
//		MeshComponent rm1 = ComponentManager.getComponent(MeshComponent.TYPE, 0);
//		System.out.println(rm1.getID());
//		System.out.println(rm1.getTypeID());
//		System.out.println(rm1.getTypeName());
//		System.out.println(rm1.getMesh());
//		
//		ComponentManager.debugPrintAllComponents();
//		
//		Entity e1 = EntityManager.createEntity("TestEntity");
//		e1.attachComponent(m1);
//		e1.attachComponent(t2);
//		
//		System.out.println(e1);
		
		Entity testEntity = EntityManager.createEntity("TestEntity");
		MeshComponent meshComponent = ComponentManager.createComponent(MeshComponent.TYPE);
		meshComponent.setMesh(null);
		
	}
}
