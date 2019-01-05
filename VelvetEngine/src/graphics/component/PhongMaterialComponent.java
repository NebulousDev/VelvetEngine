package graphics.component;

import entity.Component;
import graphics.Texture;

public class PhongMaterialComponent implements Component {

	public Texture 	diffuse;
	public Texture 	normal;
	public Texture 	specular;
	public float	intensity;
	public float 	exponent;
	
	public PhongMaterialComponent(Texture diffuse, Texture normal, float intensity, float exponent)
	{
		this.diffuse = diffuse;
		this.normal = normal;
		this.intensity = intensity;
		this.exponent = exponent;
	}
	
}
