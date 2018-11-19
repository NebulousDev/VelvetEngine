package entity.components;

import entity.Component;
import graphics.Mesh;

public class MeshComponent extends Component<MeshComponent>
{
	public final static int 	TYPE	= getNextTypeID();
	public final static String 	NAME	= "Mesh Component";
	
	private Mesh mesh = null;

	public void setMesh(Mesh mesh)
	{
		// TODO: update mesh references?
		this.mesh = mesh;
	}
	
	public Mesh getMesh()
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
