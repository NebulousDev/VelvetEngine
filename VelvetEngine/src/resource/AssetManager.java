package resource;

import java.util.HashMap;
import java.util.Map;

import core.Game;

public final class AssetManager
{
	public static final int PRELOAD	= 0x1;
	
	//TODO: perhaps add JSON filepaths in the future
	
	private Map<Integer, ResourceType<?>> 	types;
	private Map<String, Asset<?>> 			assets;
	private Map<String, Integer> 			users;
	private boolean 						initialized;
	
	private Game game;
	
	public void initialize(Game game)
	{
		this.game = game;
		types = new HashMap<>();
		assets = new HashMap<>();
		users = new HashMap<>();
		initialized = true;
	}
	
	public <T extends ResourceType<T>> void registerAssetType(ResourceType<T> resourceType, String string)
	{
		types.put(resourceType.getTypeID(), resourceType);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ResourceType<T>> void registerAsset(int resourceType, String localName, String filepath, int assetFlags, int loadFlags)
	{
		Asset<T> asset = new Asset<T>(game, this, (ResourceType<T>) types.get(resourceType), localName, filepath, assetFlags);
		assets.put(localName, asset);
		
		users.put(localName, 0);
		if(asset.isPermenent()) asset.increment();
		
		if((loadFlags & PRELOAD) != 0)
			asset.load(game);
	}
	
	public void registerAsset(int resourceType, String localName, String filepath)
	{
		registerAsset(resourceType, localName, filepath, 0, 0);
	}
	
	void incrementUser(String localName)
	{
		users.put(localName, users.get(localName) + 1);
	}
	
	void decrementUser(String localName)
	{
		users.put(localName, users.get(localName) - 1);
	}
	
	@SuppressWarnings("unchecked")
	public <R extends ResourceType<R>, T extends Asset<R>> T getAsset(String localName)
	{
		Asset<R> asset = (Asset<R>) assets.get(localName);
		if(asset == null) return null;
		if(!asset.isLoaded()) asset.load(game);
		asset.increment();
		return (T) asset;
	}
	
	public boolean assetExists(String localName)
	{
		return assets.containsKey(localName);
	}
	
	public boolean isInitialized()
	{
		return initialized;
	}

	public boolean hasUsers(String localName)
	{
		return users.get(localName) != 0;
	}

	public int getUserCount(String localName)
	{
		return users.get(localName);
	}
}
