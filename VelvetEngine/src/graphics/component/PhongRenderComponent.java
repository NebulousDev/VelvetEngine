package graphics.component;

import entity.Component;
import entity.Require;

@Require(MeshComponent.class)
@Require(PhongMaterialComponent.class)
public class PhongRenderComponent implements Component {
	
}
