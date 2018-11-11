package entity;

import java.util.ArrayList;

public final class EntityManager
{
	private static ArrayList<Entity> entityList = new ArrayList<>();
	
	public static Entity createEntity(String localName)
	{
		Entity entity = new Entity(entityList.size(), localName); // TODO: look into this
		entityList.add(entity);
		return entity;
	}
	
	public static Entity getEntityByID(int entityID)
	{
		return entityList.get(entityID);
	}
}
