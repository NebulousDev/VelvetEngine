package graphics.renderers;

import entity.Entity;
import entity.EntityManager;
import entity.System;
import graphics.Graphics;

public abstract class RenderSystem implements System {

	public abstract void begin(Graphics graphics, EntityManager entityManager);
	
	public abstract void render(Entity camera, Graphics graphics, EntityManager entityManager);
	
	public abstract void end(Graphics graphics, EntityManager entityManager);
	

}
