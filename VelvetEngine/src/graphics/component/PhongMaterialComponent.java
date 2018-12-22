package graphics.component;

import entity.Component;
import graphics.Texture;
import math.Vector4f;

public class PhongMaterialComponent implements Component {

	public Texture 	diffuse;
	public Texture 	normal;
	public Vector4f color;
	public float	specularIntensity;
	public float 	specularExponent;
	
}
