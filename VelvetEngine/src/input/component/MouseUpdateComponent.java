package input.component;

import entity.Component;
import input.MouseButton;

public interface MouseUpdateComponent extends Component {
	
	void mouseMoved(int posX, int posY);
	
	void mouseClicked(MouseButton button, int posX, int posY);
	
	@Override
	public default Class<? extends Component> getCastType()
	{
		return MouseUpdateComponent.class;
	}

}
