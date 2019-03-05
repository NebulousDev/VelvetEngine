package entity;

import core.Game;

public interface System {
	
	public void update(Game game, EntityManager manager, float delta);
	
	public String getLocalName();
	
}
