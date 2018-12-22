package loaders.ini;

/**
 * A builder for creating an INIConfig
 * @see INIConfig
 * 
 * @author NebulousDev
 */
public class INIBuilder {
	
	private INIConfig config;
	
	/**
	 * Constructs a new INIConfigBuilder
	 */
	public INIBuilder()
	{
		this.config = new INIConfig();
	}
	
	/**
	 * Adds a property of type Integer
	 * 
	 * @param name - the name of the property
	 * @param defaultValue - the default value
	 * @return the INIConfigBuilder instance
	 */
	public INIBuilder addIntProperty(String name, int defaultValue)
	{
		config.addProperty(Integer.class, name, defaultValue);
		return this;
	}
	
	/**
	 * Adds a property of type Float
	 * 
	 * @param name - the name of the property
	 * @param defaultValue - the default value
	 * @return the INIConfigBuilder instance
	 */
	public INIBuilder addFloatProperty(String name, float defaultValue)
	{
		config.addProperty(Float.class, name, defaultValue);
		return this;
	}
	
	/**
	 * Adds a property of type Double
	 * 
	 * @param name - the name of the property
	 * @param defaultValue - the default value
	 * @return the INIConfigBuilder instance
	 */
	public INIBuilder addDoubleProperty(String name, double defaultValue)
	{
		config.addProperty(Double.class, name, defaultValue);
		return this;
	}
	
	/**
	 * Adds a property of type String
	 * 
	 * @param name - the name of the property
	 * @param defaultValue - the default value
	 * @return the INIConfigBuilder instance
	 */
	public INIBuilder addStringProperty(String name, String defaultValue)
	{
		config.addProperty(String.class, name, defaultValue);
		return this;
	}
	
	/**
	 * Adds a property of type Boolean
	 * 
	 * @param name - the name of the property
	 * @param defaultValue - the default value
	 * @return the INIConfigBuilder instance
	 */
	public INIBuilder addBooleanProperty(String name, boolean defaultValue)
	{
		config.addProperty(Boolean.class, name, defaultValue);
		return this;
	}
	
	/**
	 * Builds and returns the resulting INIConfig
	 * 
	 * @return the resulting INIConfig
	 */
	public INIConfig build()
	{
		return config;
	}
	
}
