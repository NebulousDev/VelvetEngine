package core;

import entity.EntityManager;
import graphics.Graphics;
import resource.AssetManager;

public abstract class Game {
	
	Application 		application;
	Graphics 			graphics;
	EntityManager		entityManager;
	AssetManager		resourceManager;

	boolean 			initialized;
	
	void initialize(Application application)
	{
		this.application = application;
		this.graphics = application.getGraphics();	// TODO: Maybe move graphics into Game only?
		
		resourceManager = new AssetManager();
		entityManager = new EntityManager();
		
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

	public AssetManager getResourceManager()
	{
		return resourceManager;
	}

}
