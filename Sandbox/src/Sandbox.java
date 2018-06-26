import core.Application;
import core.Window;
import graphics.Graphics;
import graphics.GraphicsAPI;
import graphics.Program;
import graphics.Shader;
import graphics.ShaderType;

public class Sandbox
{
	public static void main(String[] args)
	{
		Application app = Application.createApplication("VelvetEngine", GraphicsAPI.GRAPHICS_OPENGL3);
		app.createWindow("VelvetEngine", 640, 480, 0, 0, Window.CENTERED);
		
		Graphics gfx = app.getGraphics();
		gfx.setClearColor(0.0f, 0.08f, 0.1f, 1.0f);
		
		Program program 	= gfx.createProgram("testShaderProgram");
		Shader 	vertex 		= gfx.createShader("testShaderVert", ShaderType.SHADER_TYPE_VERTEX, "/shaders/test.vert");
		Shader 	fragment 	= gfx.createShader("testShaderFrag", ShaderType.SHADER_TYPE_FRAGMENT, "/shaders/test.frag");
		
		gfx.attachShader(program, vertex);
		gfx.attachShader(program, fragment);
		
		gfx.finalizeProgram(program);
		
		while(!app.shouldClose())
		{
			gfx.clearBuffers();
			app.update();
		}
	}

}
