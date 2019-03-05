package entity.component;

import core.Game;
import entity.EntityManager;

public interface UpdateEvent {

	void update(Game game, EntityManager manager, float delta);
	
}
