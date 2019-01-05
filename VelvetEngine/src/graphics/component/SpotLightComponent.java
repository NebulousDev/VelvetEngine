package graphics.component;

import entity.Component;
import math.Vector3f;

public class SpotLightComponent implements Component {

	public Vector3f direction;
	public Vector3f attenuation;
	public Vector3f color;
	public float radius;
	public float intensity;

	public SpotLightComponent(Vector3f direction, Vector3f attenuation, Vector3f color, float radius, float intensity)
	{
		this.direction = direction;
		this.attenuation = attenuation;
		this.color = color;
		this.radius = radius;
		this.intensity = intensity;
	}
}
