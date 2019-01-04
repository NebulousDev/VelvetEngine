package graphics.system;

import core.Game;
import entity.Entity;
import entity.EntityManager;
import graphics.Graphics;

public abstract class Renderer {

	public abstract void initialize(Game game, Graphics graphics);
	
	public abstract void begin(Graphics graphics, EntityManager entityManager);
	
	public abstract void render(Entity camera, Graphics graphics, EntityManager entityManager);
	
	public abstract void end(Graphics graphics, EntityManager entityManager);
}
