package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A manager for creating, retrieving, and destroying entities.
 * @see Entity
 * 
 * @author Ben Ratcliff (NebulousDev)
 */
public final class EntityManager {
	
	private ComponentTable 		componentTable;
	private List<Long>			validEntities;
	private Map<Long, String>	entityNames;
	
	private long nextUniqueID;
	
	/**
	 * Constructs a new EntityManager. For internal use only.
	 */
	public EntityManager()
	{
		componentTable = new ComponentTable();
		validEntities = new ArrayList<>();
		entityNames = new HashMap<>();
	}
	
	/**
	 * Creates a new blank entity with no attached components and a generated name
	 * 
	 * @return the new {@Link Entity}
	 */
	public Entity createEntity()
	{
		long entityID = ++nextUniqueID;
		validEntities.add(entityID);
		return new Entity(this, entityID);
	}
	
	/**
	 * Creates a new blank entity with no attached components and the given name
	 * 
	 * @return the new {@Link Entity}
	 */
	public Entity createEntity(String name)
	{
		long entityID = ++nextUniqueID;
		validEntities.add(entityID);
		entityNames.put(entityID, name);
		return new Entity(this, entityID);
	}
	
	/**
	 * Creates a new blank entity with the given components and a generated name
	 * 
	 * @return the new {@Link Entity}
	 */
	public Entity createEntity(Component... components)
	{
		long entityID = ++nextUniqueID;
		validEntities.add(entityID);
		entityNames.put(entityID, "Entity#" + entityID);
		
		for(Component c : components)
			componentTable.put(entityID, c);
		
		return new Entity(this, entityID);
	}
	
	/**
	 * Creates a new blank entity with the given components and name
	 * 
	 * @return the new {@Link Entity}
	 */
	public Entity createEntity(String name, Component... components)
	{
		long entityID = ++nextUniqueID;
		validEntities.add(entityID);
		entityNames.put(entityID, name);
		
		for(Component c : components)
			componentTable.put(entityID, c);
		
		return new Entity(this, entityID);
	}

	/**
	 * Attaches the given component to the given entityID
	 * 
	 * @param entityID
	 * @param component
	 * @return true if entityID is valid and component was added or false if the entityID
	 * does not exist or the component could not be added.
	 */
	public <T extends Component> boolean addComponent(long entityID, T component)
	{
		if(!isValidEntityID(entityID)) return false;
		return componentTable.put(entityID, component);
	}

	/**
	 * Removes a component of the given component type from the given entityID 
	 * 
	 * @param componentType
	 * @param entityID
	 * @return the component removed or null if no component was removed
	 */
	public <T extends Component> T removeComponent(Class<T> componentType, long entityID)
	{
		if(!isValidEntityID(entityID)) return null;
		return componentTable.remove(componentType, entityID);
	}
	
	/**
	 * Retrieves the associated component of the given component type of the given entityID
	 * 
	 * @param componentType
	 * @param entityID
	 * @return the component or null if the entity is not valid or the component does not exist
	 */
	public <T extends Component> T getComponent(Class<T> componentType, long entityID)
	{
		if(!isValidEntityID(entityID)) return null;
		return componentTable.get(componentType, entityID);
	}
	
	/**
	 * Checks if the given entityID contains a component of the given type
	 * 
	 * @param componentType
	 * @param entityID
	 * @return true if the entityID is valid and is associated with the component type, else false
	 */
	public <T extends Component> boolean hasComponent(Class<T> componentType, long entityID)
	{
		if(!isValidEntityID(entityID)) return false;
		return componentTable.has(componentType, entityID);
	}
	
	/**
	 * Checks if the given entityID is valid for this manager
	 * 
	 * @param entityID
	 * @return true if valid, else false
	 */
	public boolean isValidEntityID(long entityID)
	{
		if(entityID <= 0) return false;
		return validEntities.contains(entityID);
	}

	/**
	 * <strong>[WARNING]</strong> This method is slow and should be used for debugging purposes only!
	 * <br><br>
	 * Returns a list of components associated with the given entityID.
	 */
	public List<Component> getAllComponents(long entityID)
	{
		List<Component> components = new ArrayList<>();
		for(Class<? extends Component> type : componentTable.getTypes())
			if(hasComponent(type, entityID))
				components.add(getComponent(type, entityID));
		return components;
	}
	
	/**
	 * Returns the internal ComponentTable.
	 */
	public ComponentTable getComponentTable()
	{
		return componentTable;
	}
}
