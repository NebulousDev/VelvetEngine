package math;

public class Vector3f
{
	public static final int SIZE 	= 3;
	public static final int BYTES 	= 3 * Float.BYTES;
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f(float fill)
	{
		this.x = fill;
		this.y = fill;
		this.z = fill;
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector2f vec2, float z)
	{
		this.x = vec2.x;
		this.y = vec2.y;
		this.z = z;
	}
	
	public Vector3f add(Vector3f vec3)
	{
		x += vec3.x;
		y += vec3.y;
		z += vec3.z;
		return this;
	}
	
	public Vector3f add(Vector2f vec2)
	{
		x += vec2.x;
		y += vec2.y;
		return this;
	}
	
	public Vector3f sub(Vector3f vec3)
	{
		x -= vec3.x;
		y -= vec3.y;
		z -= vec3.z;
		return this;
	}
	
	public Vector3f sub(Vector2f vec2)
	{
		x -= vec2.x;
		y -= vec2.y;
		return this;
	}
	
	public Vector3f mul(Vector3f vec3)
	{
		x *= vec3.x;
		y *= vec3.y;
		z *= vec3.z;
		return this;
	}
	
	public Vector3f mul(Vector2f vec2)
	{
		x *= vec2.x;
		y *= vec2.y;
		return this;
	}
	
	public Vector3f mul(float num)
	{
		x *= num;
		y *= num;
		z *= num;
		return this;
	}
	
	public Vector3f div(Vector3f vec3)
	{
		x /= vec3.x;
		y /= vec3.y;
		z /= vec3.z;
		return this;
	}
	
	public Vector3f div(Vector2f vec2)
	{
		x /= vec2.x;
		y /= vec2.y;
		return this;
	}
	
	public float magnitude()
	{
		return (float)Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public float dot(Vector3f vec3)
	{
		return (x * vec3.x) + (y * vec3.y) + (z * vec3.z);
	}
	
	public Vector3f cross(Vector3f vec3)
	{
		float rx = (y * vec3.z) - (z * vec3.y);
		float ry = (z * vec3.x) - (x * vec3.z);
		float rz = (x * vec3.y) - (y * vec3.x);
		return new Vector3f(rx, ry, rz);
	}
}
