package entity;

import java.util.HashMap;

import core.Game;

public final class ComponentManager
{	
	private Game game;
	
	@SuppressWarnings("rawtypes")
	public static HashMap<Integer, ComponentPool> componentPools;
	
	public void initialize(Game game)
	{
		this.game = game;
		componentPools = new HashMap<>();
	}
	
	public <E extends Component<E>> boolean registerComponentType(Component<E> instance)
	{
		componentPools.put(instance.getTypeID(), new ComponentPool<E>(instance));
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Component<E>> E createComponent(int typeID)
	{
		return (E) componentPools.get(typeID).createComponent();
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Component<E>> E getComponent(int typeID, int offsetID)
	{
		// TODO: assert valid inputs
		return (E) componentPools.get(typeID).getComponent(offsetID);
	}
	
	public void debugPrintComponents(int typeID)
	{
		componentPools.get(typeID).debugPrintPool();
	}

	public void debugPrintAllComponents()
	{
		for(int i : componentPools.keySet()) debugPrintComponents(i);
	}
}
