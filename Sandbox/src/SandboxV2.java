import core.Application;
import core.Window;
import graphics.Graphics;
import graphics.GraphicsAPI;

public class SandboxV2
{
	public static void main(String[] args)
	{
		Application app = Application.createApplication("Sandbox V2", GraphicsAPI.GRAPHICS_OPENGL);
		Window window = app.createWindow("SandboxV2", 1920, 1080, 0, 0, Window.CENTERED);
		Graphics gfx = app.getGraphics();
		
	}
}
