package core;

import entity.ComponentManager;
import entity.EntityManager;
import graphics.Graphics;
import resource.AssetManager;

public abstract class Game {
	
	Application 		application;
	Graphics 			graphics;
	ComponentManager	componentManager;
	EntityManager		entityManager;
	AssetManager		resourceManager;

	boolean 			initialized;
	
	void initialize(Application application)
	{
		this.application = application;
		this.graphics = application.getGraphics();	// TODO: Maybe move graphics into Game only?
		
		componentManager = new ComponentManager();
		resourceManager = new AssetManager();
		entityManager = new EntityManager();
		
		componentManager.initialize(this);
		entityManager.initialize(this, componentManager);
		resourceManager.initialize(this);
		
		initialized = true;
	}
	
	protected abstract void load();
	protected abstract void unload();
	
	public void start()
	{
		if(!initialized) assert(false); // TODO: improve
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
	
	public ComponentManager getComponentManager()
	{
		return componentManager;
	}

	public AssetManager getResourceManager()
	{
		return resourceManager;
	}

}
