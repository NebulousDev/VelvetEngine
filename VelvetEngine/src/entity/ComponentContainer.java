package entity;

/**
 * A container for {@link Component}s.
 * @see Component
 * 
 * @author Ben Ratcliff (NebulousDev)
 */
public interface ComponentContainer {

	/**
	 * Attaches a component
	 * @param component
	 */
	public <T extends Component> void addComponent(T component);
	
	/**
	 * removes a component of the given component type
	 * @param componentType
	 * @return the removed component
	 */
	public <T extends Component> T removeComponent(Class<T> componentType);
	
	/**
	 * Retrieves a component of the given component type
	 * @param componentType
	 * @return the component or null if component does not exist
	 */
	public <T extends Component> T getComponent(Class<T> componentType);
	
	/**
	 * Checks if this object contains the given component type
	 * @param componentType
	 * @return true if component exists else false
	 */
	public boolean hasComponent(Class<? extends Component> componentType);
	
}
