package entity;

import java.util.List;

/**
 * A simple object handle for an entityID.
 * Generated from an {@link EntityManager}.
 * @see EntityManager
 * @see ComponentContainer
 * 
 * @author Ben Ratcliff (NebulousDev)
 */
public class Entity implements ComponentContainer {
	
	EntityManager 	entityManager;
	long 			entityID;
	
	/**
	 * Constructs a new Entity. For internal use only.
	 * 
	 * @param entityManger
	 * @param id
	 */
	Entity(EntityManager entityManger, long id)
	{
		this.entityManager = entityManger;
		this.entityID = id;
	}

	@Override
	public <T extends Component> void addComponent(T component)
	{
		entityManager.addComponent(entityID, component);
	}

	@Override
	public <T extends Component> T removeComponent(Class<T> componentType)
	{
		return entityManager.removeComponent(componentType, entityID);
	}

	@Override
	public <T extends Component> T getComponent(Class<T> componentType)
	{
		return entityManager.getComponent(componentType, entityID);
	}

	@Override
	public boolean hasComponent(Class<? extends Component> componentType)
	{
		return entityManager.hasComponent(componentType, entityID);
	}
	
	/**
	 * @return the associated {@link EntityManager}
	 */
	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	/**
	 * @return the associated entityID
	 */
	public long getEntityID()
	{
		return entityID;
	}
	
	/**
	 * Checks if the current entity is valid
	 * 
	 * @return true if entity is valid, else false
	 */
	public boolean isValid()
	{
		return entityManager.isValidEntityID(entityID);
	}

	/**
	 * <strong>[WARNING]</strong> This method is slow and should be used for debugging purposes only!
	 * <br><br>
	 * Returns a list of associated components.
	 */
	public List<Component> getAllComponents()
	{
		return entityManager.getAllComponents(entityID);
	}

}
