package graphics;

import java.util.ArrayList;

import core.Game;
import loaders.ModelLoader;
import resource.Resource;
import resource.ResourceManager;

public class Model extends Resource
{
	public ArrayList<Mesh> meshes;
	
	@Override
	protected Resource load(Game game, ResourceManager manager, String tag, String filepath)
	{
		return ModelLoader.load(this, manager, game.getGraphics(), filepath, false, false, false, false);
	}

	@Override
	protected void release(Game game, ResourceManager manager)
	{
		for(Mesh mesh : meshes)
			mesh.dispose(game.getGraphics());
	}

}
