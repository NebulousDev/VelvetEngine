package graphics.component;

import entity.Component;
import graphics.Mesh;

public class MeshComponent implements Component {
	
	public Mesh mesh;

	public MeshComponent(Mesh mesh)
	{
		this.mesh = mesh;
	}
	
}
