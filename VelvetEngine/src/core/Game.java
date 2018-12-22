package core;

import entity.EntityManager;
import entity.system.UpdateSystem;
import graphics.Graphics;
import resource.ResourceManager;

public abstract class Game {
	
	Application 		application;
	Graphics 			graphics;
	EntityManager		entityManager;
	ResourceManager		resourceManager;
	
	UpdateSystem		updateSystem;

	boolean 			initialized;
	boolean				loaded;
	boolean				running;
	boolean				paused;
	
	void initialize(Application application, Graphics graphics)
	{
		this.application = application;
		this.graphics = graphics;
		
		resourceManager = application.getResourceManager();
		entityManager = new EntityManager();
		updateSystem = new UpdateSystem();
		
		initialized = true;
		running = false;
	}
	
	protected abstract void onLoad();
	protected abstract void onUpdate();
	protected abstract void onUnload();

	public void start()
	{
		if(initialized)
		{
			if(!loaded)
			{
				onLoad();
				loaded = true;
			}
			
			running = true;
		}
	}
	
	public void update()
	{
		if(initialized && running && !paused)
		{
			onUpdate();
			updateSystem.updateAll(this, entityManager, 0.0f);
		}
	}
	
	public void pause()
	{
		paused = true;
	}
	
	public void unpause()
	{
		paused = false;
	}
	
	public void stop()
	{
		if(loaded)
		{
			onUnload();
			loaded = false;
		}
		
		running = false;
	}
	
	public Application getApplication()
	{
		return application;
	}

	public Graphics getGraphics()
	{
		return graphics;
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public ResourceManager getResourceManager()
	{
		return resourceManager;
	}

}
