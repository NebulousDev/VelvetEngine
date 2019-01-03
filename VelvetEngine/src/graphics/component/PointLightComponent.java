package graphics.component;

import entity.Component;
import entity.Require;
import entity.component.TransformComponent;
import math.Vector3f;

@Require(TransformComponent.class)
public class PointLightComponent implements Component {
	
	//TODO: USE TRANSFORM COMPONENT INSTEAD!
	public Vector3f position;
	public Vector3f attenuation;
	public Vector3f color;
	public float 	intensity;

	public PointLightComponent(Vector3f attenuation, Vector3f color, float intensity)
	{
		this.color = color;
		this.attenuation = attenuation;
		this.intensity = intensity;
	}
	
}
