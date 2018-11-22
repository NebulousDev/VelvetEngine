package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Quaternion
{
	public static final int SIZE 	= 4;
	public static final int BYTES 	= 4 * Float.BYTES;
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Quaternion()
	{
		this(0,0,0,1);
	}
	
	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Vector3f vec3)
	{
		this.x = vec3.x;
		this.y = vec3.y;
		this.z = vec3.z;
		this.w = 1;
	}
	
	public Quaternion(Vector3f vec3, float w)
	{
		this.x = vec3.x;
		this.y = vec3.y;
		this.z = vec3.z;
		this.w = w;
	}
	
	public Quaternion copy()
	{
		return new Quaternion(x, y, z, w);
	}
	
	public static Quaternion Identity()
	{
		return new Quaternion();
	}
	
	public static Quaternion Rotation(Vector3f axis, float angle)
	{
		float halfSin = (float)Math.sin(angle / 2.0f);
		float halfCos = (float)Math.cos(angle / 2.0f);
		return new Quaternion(axis.x * halfSin, axis.y * halfSin, axis.z * halfSin, halfCos);
	}
	
	public static Quaternion Rotation(Vector3f axis, double angle)
	{
		float halfSin = (float)Math.sin(angle / 2.0f);
		float halfCos = (float)Math.cos(angle / 2.0f);
		return new Quaternion(axis.x * halfSin, axis.y * halfSin, axis.z * halfSin, halfCos);
	}
	
	public Quaternion set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Quaternion set(Quaternion quat)
	{
		this.x = quat.x;
		this.y = quat.y;
		this.z = quat.z;
		this.w = quat.w;
		return this;
	}
	
	public Quaternion mul(Quaternion quat)
	{
		float rx = (x * quat.w) + (w * quat.x) + (y * quat.z) - (z * quat.y);
		float ry = (y * quat.w) + (w * quat.y) + (z * quat.x) - (x * quat.z);
		float rz = (z * quat.w) + (w * quat.z) + (x * quat.y) - (y * quat.x);
		float rw = (w * quat.w) - (x * quat.x) - (y * quat.y) - (z * quat.z);

		x = rx;
		y = ry;
		z = rz;
		w = rw;
		
		return this;
	}
	
	public Quaternion mul(Vector3f vec3)
	{
		float rx = ( w * vec3.x) + (y * vec3.z) - (z * vec3.y);
		float ry = ( w * vec3.y) + (z * vec3.x) - (x * vec3.z);
		float rz = ( w * vec3.z) + (x * vec3.y) - (y * vec3.x);
		float rw = (-x * vec3.x) - (y * vec3.y) - (z * vec3.z);
		
		x = rx;
		y = ry;
		z = rz;
		w = rw;
		
		return this;
	}
	
	public Vector3f getForward()
	{
		return new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y)).normalize();
	}

	public Vector3f getBack()
	{
		return new Vector3f(-2.0f * (x * z - w * y), -2.0f * (y * z + w * x), -(1.0f - 2.0f * (x * x + y * y))).normalize();
	}

	public Vector3f getUp()
	{
		return new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x)).normalize();
	}

	public Vector3f getDown()
	{
		return new Vector3f(-2.0f * (x * y + w * z), -(1.0f - 2.0f * (x * x + z * z)), -2.0f * (y * z - w * x)).normalize();
	}

	public Vector3f getRight()
	{
		return new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y)).normalize();
	}

	public Vector3f getLeft()
	{
		return new Vector3f(-(1.0f - 2.0f * (y * y + z * z)), -2.0f * (x * y - w * z), -2.0f * (x * z + w * y)).normalize();
	}
	
	public Quaternion normalize()
	{
		float magnitude = length();
		x /= magnitude;
		y /= magnitude;
		z /= magnitude;
		w /= magnitude;
		return this;
	}
	
	public float length()
	{
		return (float)Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
	}
	
	public Quaternion conjugate()
	{
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	//TODO: look into using rotate getters for this:
	public Matrix4f toMatrix()
	{
		float[] elements = new float[]
		{
			1.0f - 2.0f * (y * y + z * z), 	2.0f * (x * y + w * z),			2.0f * (x * z - w * y),			0,
			2.0f * (x * y - w * z),			1.0f - 2.0f * (x * x + z * z),	2.0f * (y * z + w * x),			0,
			2.0f * (x * z + w * y),			2.0f * (y * z - w * x),			1.0f - 2.0f * (x * x + y * y),	0,
			0,								0,								0,								1
		};
		
		return new Matrix4f(elements);
	}
	
	public Matrix4f toMatrix(Matrix4f mat4f)
	{
		mat4f.elements[0 + 0 * 4] = 1.0f - 2.0f * (y * y + z * z);
		mat4f.elements[0 + 1 * 4] = 2.0f * (x * y - w * z);
		mat4f.elements[0 + 2 * 4] = 2.0f * (x * z + w * y);
		mat4f.elements[0 + 3 * 4] = 0.0f;

		mat4f.elements[1 + 0 * 4] = 2.0f * (x * y + w * z);
		mat4f.elements[1 + 1 * 4] = 1.0f - 2.0f * (x * x + z * z);
		mat4f.elements[1 + 2 * 4] = 2.0f * (y * z - w * x);
		mat4f.elements[1 + 3 * 4] = 0.0f;
		
		mat4f.elements[2 + 0 * 4] = 2.0f * (x * z - w * y);
		mat4f.elements[2 + 1 * 4] = 2.0f * (y * z + w * x);
		mat4f.elements[2 + 2 * 4] = 1.0f - 2.0f * (x * x + y * y);
		mat4f.elements[2 + 3 * 4] = 0.0f;
		
		mat4f.elements[3 + 0 * 4] = 0.0f;
		mat4f.elements[3 + 1 * 4] = 0.0f;
		mat4f.elements[3 + 2 * 4] = 0.0f;
		mat4f.elements[3 + 3 * 4] = 1.0f;
		
		return mat4f;
	}
	
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(SIZE);
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return buffer;
	}
	
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
	
	public float[] toArray()
	{
		return new float[] { x, y, z, w };
	}
	
	@Override
	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}
}
