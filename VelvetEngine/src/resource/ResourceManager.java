package resource;

import java.util.HashMap;
import java.util.Map;

import utils.FileUtils;

public class ResourceManager
{
	public static final int PRELOAD	= 0x1;
	
	//TODO: perhaps add JSON filepaths in the future
	
	private static Map<Integer, ResourceType<?>> 	types;
	private static Map<String, Resource<?>> 		resources;
	private static Map<String, Integer> 			references;
	private static boolean 							initialized;
	
	public static void initialize()
	{
		types = new HashMap<>();
		resources = new HashMap<>();
		references = new HashMap<>();
		initialized = true;
	}
	
	public static <T extends ResourceType<T>> void registerResourceType(ResourceType<T> type, String string)
	{
		types.put(type.getTypeID(), type);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends ResourceType<T>> void registerResource(int resourceType, String localName, String filepath, int resourceFlags, int loadFlags)
	{
		if(!FileUtils.fileExists(filepath))
		{
			System.out.println("Error! Failed to register resource '" + localName + "'. File not found at '" + filepath + "'.");
			return;
		}
		
		Resource<T> resource = new Resource<T>((ResourceType<T>) types.get(resourceType), localName, filepath, resourceFlags);
		resources.put(localName, resource);
		
		references.put(localName, 0);
		if(resource.isPermenent()) resource.increment();
		
		if((loadFlags & PRELOAD) != 0)
			resource.load();
	}
	
	public static void registerResource(int resourceType, String localName, String filepath)
	{
		registerResource(resourceType, localName, filepath, 0, 0);
	}
	
	static void incrementReference(String localName)
	{
		references.put(localName, references.get(localName) + 1);
	}
	
	static void decrementReference(String localName)
	{
		references.put(localName, references.get(localName) - 1);
	}
	
	@SuppressWarnings("unchecked")
	public static <R extends ResourceType<R>, T extends Resource<R>> T getResource(String localName)
	{
		Resource<R> resource = (Resource<R>) resources.get(localName);
		resource.increment();
		return (T) resource;
	}
	
	public static boolean resourceExists(String localName)
	{
		return resources.containsKey(localName);
	}
	
	public static boolean isInitialized()
	{
		return initialized;
	}

	public static boolean hasReferences(String localName)
	{
		return references.get(localName) != 0;
	}

	public static int getReferenceCount(String localName)
	{
		return references.get(localName);
	}
}
