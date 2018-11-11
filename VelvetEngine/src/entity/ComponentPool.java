package entity;

import java.util.ArrayList;
import java.util.List;

public class ComponentPool<T extends Component<T>>
{
	private List<T> components;
	private Component<T> creator;
	
	public ComponentPool(Component<T> creator)
	{
		this.components = new ArrayList<T>();
		this.creator = creator;
	}
	
	/**
	 * Extends the component array
	 * and returns a new component object
	 */
	public T createComponent()
	{
		T component = creator.create();
		components.add(component);
		component.setID(size() - 1); //TODO: I dont like this
		return component;
	}
	
	public T getComponent(int offsetID)
	{
		return components.get(offsetID);
	}
	
	public boolean isEmpty()
	{
		return components.isEmpty();
	}
	
	public int size()
	{
		return components.size();
	}

	public void debugPrintPool()
	{
		java.lang.System.out.println("ComponentPool[" + creator.getTypeName() + ":" + creator.getTypeID() + "]:");
		for(int i = 0; i < size(); i++)
			java.lang.System.out.println("\t - " + getComponent(i));
	}
}
