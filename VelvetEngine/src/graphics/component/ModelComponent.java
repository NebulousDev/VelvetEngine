package graphics.component;

import entity.Component;
import graphics.Model;

public class ModelComponent implements Component {
	
	public Model model;

	public ModelComponent(Model model)
	{
		this.model = model;
	}
	
}
