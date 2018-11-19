package entity;

import java.util.ArrayList;

import core.Game;

public final class EntityManager
{
	private Game game;
	private ComponentManager manager;
	
	private ArrayList<Entity> entityList = new ArrayList<>();
	
	public void initialize(Game game, ComponentManager manager)
	{
		this.game = game;
		this.manager = manager;
	}
	
	public Entity createEntity(String localName)
	{
		Entity entity = new Entity(manager, entityList.size(), localName); // TODO: look into this
		entityList.add(entity);
		return entity;
	}
	
	public Entity getEntityByID(int entityID)
	{
		return entityList.get(entityID);
	}
}
