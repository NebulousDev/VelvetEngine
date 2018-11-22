package entity.system;

import java.util.Iterator;

import core.Game;
import entity.EntityManager;
import entity.System;
import entity.component.UpdateComponent;

public class UpdateSystem implements System {
	
	public void updateAll(Game game, EntityManager manager, float delta)
	{
		Iterator<Long> iterator = manager.getComponentTable().getEntityIterator(UpdateComponent.class);
		if(iterator != null)
		{
			while(iterator.hasNext())
			{
				long entityID = iterator.next();
				UpdateComponent update = manager.getComponent(UpdateComponent.class, entityID);
				update.update(game, manager.entityFromID(entityID), delta);
			}
		}
	}

	@Override
	public String getLocalName()
	{
		return "Update System";
	}

}
