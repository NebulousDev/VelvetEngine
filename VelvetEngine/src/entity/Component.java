package entity;

/**
 * Contains data for use by an {@link Entity}. Acted on by a {@link System}.
 * @see Entity
 * @see System
 * 
 * @author Ben Ratcliff (NebulousDev)
 */
public interface Component {
	
	default Class<? extends Component> getCastType()
	{
		return this.getClass();
	}
}
