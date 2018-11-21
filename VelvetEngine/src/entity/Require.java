package entity;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(entity.Require.Requirements.class)
public @interface Require {
	
	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Requirements {
		Require[] value() default {};
	}
	
	Class<? extends Component> value();
}
