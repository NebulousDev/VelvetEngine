package graphics.component;

import entity.Component;
import graphics.Texture;
import math.Vector4f;
import resource.Asset;

public class PhongMaterialComponent implements Component {

	public Asset<Texture> 	diffuse;
	public Asset<Texture> 	normal;
	public Vector4f 		color;
	public float			specularIntensity;
	public float 			specularExponent;
	
}
