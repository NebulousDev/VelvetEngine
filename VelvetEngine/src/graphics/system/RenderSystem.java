package graphics.system;

import core.Game;
import entity.EntityManager;
import entity.System;

public class RenderSystem implements System {

	@Override
	public void update(Game game, EntityManager manager, float delta)
	{
		
	}
	
	public double getFPS()
	{
		return 0;
	}
	
	@Override
	public String getLocalName()
	{
		return "RenderSystem";
	}

}
