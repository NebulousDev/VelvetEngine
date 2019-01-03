package graphics.component;

import entity.Component;
import math.Vector3f;

public class DirectionalLightComponent implements Component {
	
	public Vector3f direction;
	public Vector3f color;
	public float 	intensity;

	public DirectionalLightComponent(Vector3f direction, Vector3f color, float intensity)
	{
		this.direction = direction;
		this.color = color;
		this.intensity = intensity;
	}
	
}
