package graphics.component;

import entity.Component;
import math.Vector3f;
import math.Vector4f;

public class LineRenderComponent implements Component {

	public Vector3f p1, p2;
	public Vector4f color;
	
	public LineRenderComponent(Vector3f p1, Vector3f p2, Vector4f color)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.color = color;
	}
	
}
