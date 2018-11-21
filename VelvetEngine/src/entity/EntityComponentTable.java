package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A data structure designed for quickly retrieving, removing, and iterating associations between
 * entityIDs and {@link Component}s.
 * @see Entity
 * @see Component
 * 
 * @author Nebulous
 */
public final class EntityComponentTable {
	
	private Map<Class<? extends Component>, Map<Long, Component>> entityComponentMap;
	
	/**
	 * Creates a new EntityComponentTable. For internal use only.
	 */
	EntityComponentTable()
	{
		entityComponentMap = new HashMap<>();
	}
	
	/**
	 * Attaches the given component to the given entityID
	 * 
	 * @param entityID
	 * @param component
	 * @return true if the component was successfully added, else false
	 */
	public <T extends Component> boolean put(long entityID, T component)
	{
		Class<? extends Component> componentType = component.getClass();
		Map<Long, Component> entityMap = entityComponentMap.get(componentType);
		
		if(entityMap == null)
		{
			entityMap = new HashMap<>();
			entityComponentMap.put(componentType, entityMap);
		}
		
		entityMap.put(entityID, component);
		return true;
	}
	
	/**
	 * Removes a component of the given component type associated with the entityID
	 * 
	 * @param componentType
	 * @param entityID
	 * @return the component removed or null if no component is removed
	 */
	public <T extends Component> T remove(Class<T> componentType, long entityID)
	{
		Component component = null;
		Map<Long, Component> entityMap = entityComponentMap.get(componentType);
		if(entityMap != null && (component = entityMap.remove(entityID)) != null)
		{
			if(entityMap.isEmpty())
				entityComponentMap.remove(componentType);
			return componentType.cast(component);
		}
		return null;
	}
	
	/**
	 * Retrieves a component of the given type associated with the entityID
	 * 
	 * @param componentType
	 * @param entityID
	 * @return the component associated with the given entity, or null if there is no component
	 * associated with the given entity
	 */
	public <T extends Component> T get(Class<T> componentType, long entityID)
	{
		Component component = null;
		Map<Long, Component> entityMap = entityComponentMap.get(componentType);
		if(entityMap != null && (component = entityMap.get(entityID)) != null)
			return componentType.cast(component);
		return null;
	}

	/**
	 * Checks if the given entityID is associated with the component type
	 * 
	 * @param componentType
	 * @param entityID
	 * @return true if the given entityID is associated with a given component type, else false
	 */
	public <T extends Component> boolean has(Class<T> componentType, long entityID)
	{
		Map<Long, Component> entityMap = entityComponentMap.get(componentType);
		if(entityMap != null && entityMap.get(entityID) != null)
			return true;
		return false;
	}

	/**
	 * <strong>NOTE</strong>: You should probably use {@link #getComponentIterator(Class)} for better performance
	 * <br><br>
	 * Retrieves all components of the given type
	 * 
	 * @param entityID
	 * @return list of all the components or null if none exist
	 */
	public <T extends Component> List<T> getAll(Class<T> componentType)
	{
		List<T> components = new ArrayList<>();
		Iterator<T> iterator = getComponentIterator(componentType);
		while(iterator.hasNext())
			components.add(iterator.next());
		return components;
	}
	
	/**
	 * <strong>NOTE</strong>: You should probably use {@link #getTypeIterator()} for better performance
	 * <br><br>
	 * Retrieves all available types
	 * 
	 * @return list of all the available types or null if none exist
	 */
	public List<Class<? extends Component>> getTypes()
	{
		List<Class<? extends Component>> types = new ArrayList<>();
		Iterator<Class<? extends Component>> iterator = getTypeIterator();
		while(iterator.hasNext())
			types.add(iterator.next());
		return types;
	}

	/**
	 * Checks that the given componentType has any active associations
	 * 
	 * @param componentType
	 * @return true if associations exist, else false
	 */
	private boolean typeExists(Class<? extends Component> componentType)
	{
		return entityComponentMap.get(componentType) != null;
	}
	
	/**
	 * Creates an iterator of the given component type
	 * 
	 * @param componentType
	 * @return the iterator or null if no components exist
	 */
	public <T extends Component> Iterator<T> getComponentIterator(Class<T> componentType)
	{
		if(!typeExists(componentType))
			return null;
		
		return new Iterator<T>()
		{
			Map<Long, Component> components = entityComponentMap.get(componentType);
			Iterator<Long> entityIterator = components.keySet().iterator();
			
			@Override
			public boolean hasNext()
			{
				return entityIterator.hasNext();
			}
			
			@Override
			public T next()
			{
				return componentType.cast(components.get(entityIterator.next()));
			}
			
		};
	}
	
	/**
	 * Creates an iterator of the available component types
	 * 
	 * @return the iterator of component types, or null if none exist
	 */
	public Iterator<Class<? extends Component>> getTypeIterator()
	{
		if(entityComponentMap.isEmpty()) return null;
		return entityComponentMap.keySet().iterator();
	}

}
