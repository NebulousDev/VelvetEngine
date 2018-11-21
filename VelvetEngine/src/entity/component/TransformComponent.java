package entity.component;

import entity.Component;
import math.Quaternion;
import math.Vector3f;

public class TransformComponent implements Component {

	public Vector3f position;
	public Quaternion orientation;
	
	public TransformComponent(Vector3f position, Quaternion orientation)
	{
		this.position = position;
		this.orientation = orientation;
	}
	
}
