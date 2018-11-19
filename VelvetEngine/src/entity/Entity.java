package entity;

import java.util.HashMap;

public class Entity
{
	private int id;
	private String name;
	
	private ComponentManager manager;
	
	// TODO: Eventually move this to EntityManager
	private HashMap<Integer, Integer> componentMap;
	
	Entity(ComponentManager manager, int id, String name)
	{
		// only visible to package
		this.id = id;
		this.name = name;
		this.manager = manager;
		componentMap = new HashMap<>();
	}
	
	/**
	 * Creates then attaches a component of the given type
	 * Returns false if the component already exists
	 */
	public boolean createAndAttachComponent(int typeID)
	{
		Component<?> component = manager.createComponent(typeID);
		return attachComponent(component);
	}
	
	/**
	 * Attaches the given component
	 * Returns false if the component already exists
	 * or if the component is invalid
	 */
	public boolean attachComponent(Component<?> component)
	{
		//TODO: Add asserts
		componentMap.put(component.getTypeID(), component.id);
		return true;
	}
	
	/**
	 * Removes a component of the given typeID.
	 * Returns false if the component does not exist.
	 */
	public boolean detachComponent(int typeID)
	{
		//TODO: Add asserts
		return componentMap.remove(typeID) != null;
	}
	
	/**
	 * Returns a linked component of the given id.
	 * Returns null if component does not exist.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Component<E>> E getComponent(int typeID)
	{
		Integer offsetID = componentMap.get(typeID);
		return offsetID != null ? (E) manager.getComponent(typeID, offsetID) : null;
	}
	
	/**
	 * Returns true if a component of the given id exists
	 */
	public boolean hasComponent(int typeID)
	{
		return componentMap.containsKey(typeID);
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Entity[" + name + ":" + id + "]");
		for(int i : componentMap.keySet())
			builder.append("\n\t- " + manager.getComponent(i, componentMap.get(i)));
		
		return builder.toString();
	}
	
}
