package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f
{
	public static final int SIZE 	= 16;
	public static final int BYTES 	= 16 * Float.BYTES;
	
	float[] elements;
	
	Matrix4f(float[] elements)
	{
		this.elements = elements;
	}
	
	public Matrix4f mul(Matrix4f other)
	{
		float data[] = new float[16];
		
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				float sum = 0.0f;
				for (int i = 0; i < 4; i++)
					sum += elements[x + i * 4] * other.elements[i + y * 4];
				data[x + y * 4] = sum;
			}
		}
		
		elements = data;
		
		return this;
	}
	
	public static Matrix4f Identity()
	{
		float[] elements = 
		{
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		};
		
		return new Matrix4f(elements);
	}
	
	public static Matrix4f Transform(Vector3f vec3)
	{
		float[] elements = 
		{
			0.0f, 0.0f, 0.0f, vec3.x,
			0.0f, 0.0f, 0.0f, vec3.y,
			0.0f, 0.0f, 0.0f, vec3.z,
			0.0f, 0.0f, 0.0f, 0.0f
		};
		
		return new Matrix4f(elements);
	}
	
	//TODO: Redo rotation matrix
	public static Matrix4f Rotation(Vector3f vec3)
	{
		Matrix4f matrix = Matrix4f.Identity();
		
		float cosX = (float)Math.cos(Math.toRadians(vec3.x));
		float cosY = (float)Math.cos(Math.toRadians(vec3.y));
		float cosZ = (float)Math.cos(Math.toRadians(vec3.z));

		float sinX = (float)Math.sin(Math.toRadians(vec3.x));
		float sinY = (float)Math.sin(Math.toRadians(vec3.y));
		float sinZ = (float)Math.sin(Math.toRadians(vec3.z));

		matrix.elements[0 + 0 * 4] = cosY * cosZ;
		matrix.elements[1 + 0 * 4] = (sinX * sinY * cosZ) + (cosX * sinZ);
		matrix.elements[2 + 0 * 4] = -(cosX * sinY * cosZ) + (sinX * sinZ);

		matrix.elements[0 + 1 * 4] = -(cosY * sinZ);
		matrix.elements[1 + 1 * 4] = -(sinX * sinY * sinZ) + (cosX * cosZ);
		matrix.elements[2 + 1 * 4] = (cosX * sinY * sinZ) + (sinX * cosZ);

		matrix.elements[0 + 2 * 4] = sinY;
		matrix.elements[1 + 2 * 4] = -(sinX * cosY);
		matrix.elements[2 + 2 * 4] = (cosX * cosY);

		return matrix;
	}
	
	//TODO: Redo perspective matrix
	public static Matrix4f Perspective(float fov, float aspect, float near, float far)
	{
		Matrix4f matrix = Matrix4f.Identity();
		float q = (float)(1.0f / Math.tan(Math.toRadians(0.5f * fov)));
		float w = q / aspect;
		matrix.elements[0 + 0 * 4] = w;
		matrix.elements[1 + 1 * 4] = q;
		matrix.elements[2 + 2 * 4] = (near + far) / (near - far);
		matrix.elements[2 + 3 * 4] = (2.0f * near * far) / (near - far);
		matrix.elements[3 + 2 * 4] = -1.0f;
		return matrix;
	}

	//TODO: Redo orthographic matrix
	public static Matrix4f Orthographic(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4f matrix = Matrix4f.Identity();
		matrix.elements[0 + 0 * 4] = 2.0f / (right - left);
		matrix.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		matrix.elements[2 + 2 * 4] = -2.0f / (far - near);
		matrix.elements[0 + 3 * 4] = -(left + right) / (left - right);
		matrix.elements[1 + 3 * 4] = -(bottom + top) / (bottom - top);
		matrix.elements[2 + 3 * 4] = -(far + near) / (far - near);
		matrix.elements[3 + 3 * 4] = 1.0f;
		return matrix;
	}
	
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(SIZE);
		buffer.put(elements);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
}
