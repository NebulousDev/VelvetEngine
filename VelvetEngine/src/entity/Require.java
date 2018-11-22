package entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Requires a component to be attached to an entity that
 * contains the given component type
 * <br>
 * <br>
 * Must target a class or interface type
 * 
 * @see Entity
 * @see Component
 * 
 * @author Ben Ratcliff (NebulousDev)
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(entity.Require.Requirements.class)
public @interface Require {
	
	@Documented
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Requirements {
		Require[] value() default {};
	}
	
	Class<? extends Component> value();
}
