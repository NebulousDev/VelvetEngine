package input.system;
import java.util.Iterator;

import core.Game;
import entity.EntityManager;
import entity.System;
import input.component.MouseUpdateComponent;

public class MouseUpdateSystem implements System {
	
	@Override
	public void update(Game game, EntityManager manager, float delta)
	{
		Iterator<Long> iterator = manager.getComponentTable().getEntityIterator(MouseUpdateComponent.class);
		if(iterator != null)
		{
			while(iterator.hasNext())
			{
				//long entityID = iterator.next();
				//MouseUpdateComponent mouseUpdate = manager.getComponent(MouseUpdateComponent.class, entityID);
				//update.update(game, manager.entityFromID(entityID), delta);
			}
		}
	}
	
	@Override
	public String getLocalName()
	{
		return "MouseUpdateSystem";
	}

}
