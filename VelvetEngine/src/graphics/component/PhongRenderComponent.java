package graphics.component;

import entity.Component;
import entity.Require;

@Require(MeshComponent.class)
@Require(PhongMaterialComponent.class)
public class PhongRenderComponent implements Component {

	public MeshComponent 			meshComponent;
	public PhongMaterialComponent 	materialComponent;
	
	public PhongRenderComponent(MeshComponent meshComponent, PhongMaterialComponent materialComponent)
	{
		this.meshComponent = meshComponent;
		this.materialComponent = materialComponent;
	}
	
}
