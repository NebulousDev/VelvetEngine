package core;

import entity.EntityManager;
import entity.system.UpdateSystem;
import graphics.Graphics;
import graphics.system.RenderSystem;
import resource.ResourceManager;

public abstract class Game {
	
	enum State
	{
		LOADING,
		RUNNING,
		PAUSED,
		UNLOADING,
		STOPPED
	}
	
	Application 		application;
	Graphics 			graphics;
	EntityManager		entityManager;
	ResourceManager		resourceManager;
	
	UpdateSystem		updateSystem;
	RenderSystem		renderSystem;

	boolean 			initialized;
	boolean				loaded;
	
	State				state;
	
	void initialize(Application application, Graphics graphics)
	{
		this.application = application;
		this.graphics = graphics;
		
		resourceManager = application.getResourceManager();
		entityManager = new EntityManager();
		updateSystem = new UpdateSystem();
		renderSystem = new RenderSystem();
		
		initialized = true;
		state = State.STOPPED;
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
				state = State.LOADING;
				onLoad();
				loaded = true;
			}
			
			state = State.RUNNING;
		}
	}
	
	public void update()
	{
		if(initialized && state == State.RUNNING)
		{
			onUpdate();
			updateSystem.update(this, entityManager, 0.0f);
		}
	}
	
	public void render()
	{
		if(initialized && state == State.RUNNING)
		{
			renderSystem.update(this, entityManager, 0.0f);
		}
	}
	
	public void pause()
	{
		state = State.PAUSED;
	}
	
	public void unpause()
	{
		state = State.RUNNING;
	}
	
	public void stop()
	{
		if(loaded)
		{
			state = State.UNLOADING;
			onUnload();
			loaded = false;
		}
		
		state = State.STOPPED;
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
