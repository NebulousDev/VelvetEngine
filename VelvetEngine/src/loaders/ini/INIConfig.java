package loaders.ini;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An accessor for the INI file type
 * 
 * @author NebulousDev
 */
@SuppressWarnings("unchecked")
public class INIConfig {

	/**
	 * A property handle for an INIConfig
	 *
	 * @param <T> the class type of the property
	 * @see INIConfig
	 */
	public static class INIProperty<T>
	{
		String 		name;
		Class<T> 	type;
		T 			value;
		
		/**
		 * Internal INIProperty constructor
		 */
		INIProperty(Class<T> type, String name, T value)
		{
			this.name = name;
			this.type = type;
			this.value = value;
		}
		
		/**
		 * Get the property name
		 */
		public String getName()
		{
			return name;
		}
		
		/**
		 * Get the property class type
		 */
		public Class<T> getType()
		{
			return type;
		}
		
		/**
		 * Get the property value
		 */
		public T getValue()
		{
			return value;
		}
	}
	
	private HashMap<String, INIProperty<?>> properties;
	private ArrayList<INIProperty<?>>		modified;
	
	/**
	 * Default internal constructor
	 */
	INIConfig()
	{
		this.properties = new HashMap<>();
	}
	
	/**
	 * Creates and loads contents of the INI file to an INIConfig
	 * 
	 * @param filepath - the path to the INI file
	 */
	public void load(String filepath)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			
			String line = null;
			while((line = reader.readLine()) != null)
			{
				String[] tokens = line.split("=");
				
				if(tokens.length > 1)
				{
					String name = tokens[0].toLowerCase().trim();
					
					if(name.startsWith("[") || name.startsWith("#") || name.startsWith("#") || name == "") continue;
					
					String value = tokens[1].trim();
					
					INIProperty<?> property = properties.get(name.toLowerCase());
					
					if(property != null)
					{
						switch (property.type.getSimpleName())
						{
							case "Integer": ((INIProperty<Integer>)property).value = Integer.parseInt(value); 		break;
							case "Float": 	((INIProperty<Float>)property).value = Float.parseFloat(value); 		break;
							case "Double": 	((INIProperty<Double>)property).value = Double.parseDouble(value); 		break;
							case "String": 	((INIProperty<String>)property).value = value; 							break;
							case "Boolean": ((INIProperty<Boolean>)property).value = Boolean.parseBoolean(value); 	break;
							default: break;
						}
					}
				}
				
			}
				
			reader.close();
		}
		
		catch (FileNotFoundException e)
		{
			System.err.println("[VelvetINI] Failed to locate file '" + filepath + '.');
			return;
		}
		
		catch (IOException e) { return; }
	}
	
	/**
	 * Saves the current state of this INIConfig to file
	 * 
	 * @param filepath - the path to the INI file
	 */
	public void save(String filepath)
	{
		PrintWriter writer = null;
		
		try
		{
			writer = new PrintWriter(filepath, "UTF-8");
		}
		
		catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			System.err.println("[VelvetINI]Failed to save file '" + filepath + "'.");
			return;
		}
		
		for(INIProperty<?> property : properties.values())
			writer.printf("%s=%s\r\n",property.name, property.value);
		
		writer.close();
		
		modified = new ArrayList<>();
	}
	
	/**
	 * Adds a new property to the INIConfig
	 * 
	 * @param type - the class type of the property
	 * @param name - the key name of the property
	 * @param defaultValue - the default value of the property
	 */
	public <T> void addProperty(Class<T> type, String name, T defaultValue)
	{
		properties.put(name.toLowerCase(), new INIProperty<T>(type, name, defaultValue));
	}
	
	/**
	 * Removes a property from the INIConfig
	 * 
	 * @param name - the key name of the property
	 */
	public void removeProperty(String name)
	{
		properties.remove(name.toLowerCase());
	}
	
	/**
	 * Sets the value of a property given it's type, name, and new value
	 * 
	 * @param type - the class type of the property
	 * @param name - the key name of the property
	 * @param value - the new value
	 */
	public <T> void setAs(Class<T> type, String name, T value)
	{
		INIProperty<?> property = properties.get(name.toLowerCase());
		
		if(property != null && property.type == type)
		{
			((INIProperty<T>)property).value = value;
			modified.add(property);
		}
	}
	
	/**
	 * Returns the value of the property as the given class type
	 * 
	 * @param type - the class type of the property
	 * @param name - the key name of the property
	 * @return The property value as the correct type or null if no property exists
	 */
	public <T> T getAs(Class<T> type, String name)
	{
		INIProperty<?> property = properties.get(name.toLowerCase());
		
		if(property != null)
			return type.cast(property.value);
					
		return null;
	}
	
	/**
	 * Sets an Integer property's value
	 * 
	 * @param name - the name of the property
	 * @param value - the new value
	 */
	public void setInt(String name, int value)
	{
		setAs(Integer.class, name, value);
	}
	
	/**
	 * Sets a Float property's value
	 * 
	 * @param name - the name of the property
	 * @param value - the new value
	 */
	public void setFloat(String name, Float value)
	{
		setAs(Float.class, name, value);
	}
	
	/**
	 * Sets a Double property's value
	 * 
	 * @param name - the name of the property
	 * @param value - the new value
	 */
	public void setDouble(String name, Double value)
	{
		setAs(Double.class, name, value);
	}
	
	/**
	 * Sets a String property's value
	 * 
	 * @param name - the name of the property
	 * @param value - the new value
	 */
	public void setString(String name, String value)
	{
		setAs(String.class, name, value);
	}
	
	/**
	 * Sets a Boolean property's value
	 * 
	 * @param name - the name of the property
	 * @param value - the new value
	 */
	public void setBoolean(String name, boolean value)
	{
		setAs(Boolean.class, name, value);
	}
	
	/**
	 * Gets an Integer property's value
	 * 
	 * @param name - the name of the property
	 * @return The property's value or null if no property exists
	 */
	public int getInt(String name)
	{
		return getAs(Integer.class, name);
	}
	
	/**
	 * Gets a Float property's value
	 * 
	 * @param name - the name of the property
	 * @return The property's value or null if no property exists
	 */
	public float getFloat(String name)
	{
		return getAs(Float.class, name);
	}
	
	/**
	 * Gets a Double property's value
	 * 
	 * @param name - the name of the property
	 * @return The property's value or null if no property exists
	 */
	public double getDouble(String name)
	{
		return getAs(Double.class, name);
	}
	
	/**
	 * Gets a String property's value
	 * 
	 * @param name - the name of the property
	 * @return The property's value or null if no property exists
	 */
	public String getString(String name)
	{
		return getAs(String.class, name);
	}
	
	/**
	 * Gets a Boolean property's value
	 * 
	 * @param name - the name of the property
	 * @return The property's value or null if no property exists
	 */
	public boolean getBoolean(String name)
	{
		return getAs(Boolean.class, name);
	}
	
	/**
	 * Returns a list of all properties.
	 * 
	 * @return the list of all properties.
	 */
	public ArrayList<INIProperty<?>> getAllProperties()
	{
		ArrayList<INIProperty<?>> result = new ArrayList<>();
		
		for(String key : properties.keySet())
			result.add(properties.get(key));
		
		return result;
	}

}
