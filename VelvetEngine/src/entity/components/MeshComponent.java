package entity.components;

import entity.Component;
import graphics.GraphicsMesh;

public class MeshComponent extends Component<MeshComponent>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Mesh Component";
	
	private GraphicsMesh mesh = null;

	public void setMesh(GraphicsMesh mesh)
	{
		// TODO: update mesh references?
		this.mesh = mesh;
	}
	
	public GraphicsMesh getMesh()
	{
		return mesh;
	}
	
	@Override
	public int getTypeID()
	{
		return TYPE;
	}
	
	@Override
	public String getTypeName()
	{
		return NAME;
	}

	@Override
	public MeshComponent create() {
		return new MeshComponent();
	}
}
