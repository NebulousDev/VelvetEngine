package entity;

import java.util.HashMap;

public final class ComponentManager
{	
	@SuppressWarnings("rawtypes")
	public static HashMap<Integer, ComponentPool> componentPools = new HashMap<>();;
	
	public static <E extends Component<E>> boolean registerComponentType(Component<E> instance)
	{
		componentPools.put(instance.getTypeID(), new ComponentPool<E>(instance));
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Component<E>> E createComponent(int typeID)
	{
		return (E) componentPools.get(typeID).createComponent();
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Component<E>> E getComponent(int typeID, int offsetID)
	{
		// TODO: assert valid inputs
		return (E) componentPools.get(typeID).getComponent(offsetID);
	}
	
	public static void debugPrintComponents(int typeID)
	{
		componentPools.get(typeID).debugPrintPool();
	}

	public static void debugPrintAllComponents()
	{
		for(int i : componentPools.keySet()) debugPrintComponents(i);
	}
}
