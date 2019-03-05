package entity.component;

import core.Game;
import entity.Component;
import entity.Entity;

public interface UpdateComponent extends Component {
	
	public void update(Game game, Entity entity, float delta);

	@Override
	public default Class<? extends Component> getCastType()
	{
		return UpdateComponent.class;
	}
	
}
