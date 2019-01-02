package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * A container (vector) of 4 float values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Matrix4f
 * @see Vector3f
 * @see Vector2f
 */

public class Vector4f {

	/**
	 * The x component
	 */
	public float x;
	
	/**
	 * The y component
	 */
	public float y;
	
	/**
	 * The z component
	 */
	public float z;
	
	/**
	 * The w component
	 */
	public float w;
	
	/**
	 * Construct a 4-float vector
	 */
	public Vector4f()
	{
		this(0, 0, 0, 0);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param value - x, y, and z components
	 */
	public Vector4f(float value)
	{
		this(value, value, value, value);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 */
	public Vector4f(float vx, float vy, float vz, float vw)
	{
		x = vx;
		y = vy;
		z = vz;
		w = vw;
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vec4f - x, y, z, and w components
	 */
	public Vector4f(Vector4f vec4f)
	{
		this(vec4f.x, vec4f.y, vec4f.z, vec4f.w);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vec4i - x, y, z, and w components
	 */
	public Vector4f(Vector4i vec4i)
	{
		this((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vec3f - x, y, and z components
	 * @param vw - w component
	 */
	public Vector4f(Vector3f vec3f, float vw)
	{
		this(vec3f.x, vec3f.y, vec3f.z, vw);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vec3i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector4f(Vector3i vec3i, float vw)
	{
		this((float)vec3i.x, (float)vec3i.y, (float)vec3i.z, vw);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vec2f - x and y components
	 * @param vz - z component
	 * @param vw - w component
	 */
	public Vector4f(Vector2f vec2f, float vz, float vw)
	{
		this(vec2f.x, vec2f.y, vz, vw);
	}
	
	/**
	 * Construct a 4-float vector
	 * 
	 * @param vec2i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector4f(Vector2i vec2i, float vz, float vw)
	{
		this((float)vec2i.x, (float)vec2i.y, vz, vw);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * 
	 * @return this
	 */
	public Vector4f set(float vx, float vy, float vz, float vw)
	{
		this.x = vx;
		this.y = vy;
		this.z = vz;
		this.w = vw;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f set(Vector4f vec4f)
	{
		return set(vec4f.x, vec4f.y, vec4f.z, vec4f.w);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f set(Vector4i vec4i)
	{
		return set((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f set(Vector3f vec3f)
	{
		this.x = vec3f.x;
		this.y = vec3f.y;
		this.z = vec3f.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f set(Vector3i vec3i)
	{
		this.x = (float)vec3i.x;
		this.y = (float)vec3i.y;
		this.z = (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3f - x, y, and z components
	 * @param vw - the w component
	 * 
	 * @return this
	 */
	public Vector4f set(Vector3f vec3f, float vw)
	{
		return set(vec3f.x, vec3f.y, vec3f.z, vw);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3i - x, y, and z components
	 * @param vw - the w component
	 * 
	 * @return this
	 */
	public Vector4f set(Vector3i vec3i, float vw)
	{
		return set((float)vec3i.x, (float)vec3i.y, (float)vec3i.z, vw);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * 
	 * @return this
	 */
	public Vector4f set(Vector2f vec2f)
	{
		this.x = vec2f.x;
		this.y = vec2f.y;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * 
	 * @return this
	 */
	public Vector4f set(Vector2i vec2i)
	{
		this.x = (float)vec2i.x;
		this.y = (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * @param vz - the z component
	 * @param vw - the w component
	 * 
	 * @return this
	 */
	public Vector4f set(Vector2f vec2f, float vz, float vw)
	{
		return set(vec2f.x, vec2f.y, vz, vw);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * @param vz - the z component
	 * @param vw - the w component
	 * 
	 * @return this
	 */
	public Vector4f set(Vector2i vec2i, float vz, float vw)
	{
		return set((float)vec2i.x, (float)vec2i.y, vz, vw);
	}
	
	/**
	 * Set a component by index
	 * 
	 * @param index - component index
	 * @param value - component value
	 * @throws IndexOutOfBoundsException if index < 0 or index > 3
	 */
	public void set(int index, float value)
	{
		switch (index)
		{
			case 0: x = value; return;
			case 1: y = value; return;
			case 2: z = value; return;
			case 3: w = value; return;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Retrieve a component by index
	 * 
	 * @param index - component index
	 * @throws IndexOutOfBoundsException if index < 0 or index > 3
	 * @return value at index
	 */
	public float get(int index)
	{
		switch (index)
		{
			case 0: return x;
			case 1: return y;
			case 2: return z;
			case 3: return w;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Copies components to a new instance
	 * 
	 * @return new copy
	 */
	public Vector4f copy()
	{
		return new Vector4f(x, y, z, w);
	}
	
	/**
	 * Copies components to destination
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f copy(Vector4f dest)
	{
		return dest.set(this);
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(float vx, float vy, float vz, float vw, Vector4f dest)
	{
		dest.x = x + vx;
		dest.y = y + vy;
		dest.z = z + vz;
		dest.w = w + vw;
		
		return this;
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * 
	 * @return this
	 */
	public Vector4f add(float vx, float vy, float vz, float vw)
	{
		return add(vx, vy, vz, vw, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(Vector4f vec4f, Vector4f dest)
	{
		return add(vec4f.x, vec4f.y, vec4f.z, vec4f.w, dest);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(Vector4i vec4i, Vector4f dest)
	{
		return add((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, dest);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f add(Vector4f vec4f)
	{
		return add(vec4f.x, vec4f.y, vec4f.z, vec4f.w, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f add(Vector4i vec4i)
	{
		return add((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(Vector3f vec3f, Vector4f dest)
	{
		dest.x = x + vec3f.x;
		dest.y = y + vec3f.y;
		dest.z = z + vec3f.z;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(Vector3i vec3i, Vector4f dest)
	{
		dest.x = x + (float)vec3i.x;
		dest.y = y + (float)vec3i.y;
		dest.z = z + (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f add(Vector3f vec3f)
	{
		return add(vec3f, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f add(Vector3i vec3i)
	{
		return add(vec3i, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(Vector2f vec2f, Vector4f dest)
	{
		dest.x = x + vec2f.x;
		dest.y = y + vec2f.y;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f add(Vector2i vec2i, Vector4f dest)
	{
		dest.x = x + (float)vec2i.x;
		dest.y = y + (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f add(Vector2f vec2f)
	{
		return add(vec2f, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f add(Vector2i vec2i)
	{
		return add(vec2i, this);
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(float vx, float vy, float vz, float vw, Vector4f dest)
	{
		dest.x = x - vx;
		dest.y = y - vy;
		dest.z = z - vz;
		dest.w = w - vw;
		
		return this;
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * 
	 * @return this
	 */
	public Vector4f sub(float vx, float vy, float vz, float vw)
	{
		return add(vx, vy, vz, vw, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector4f vec4f, Vector4f dest)
	{
		return add(vec4f.x, vec4f.y, vec4f.z, vec4f.w, dest);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector4i vec4i, Vector4f dest)
	{
		return add((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, dest);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector4f vec4f)
	{
		return add(vec4f.x, vec4f.y, vec4f.z, vec4f.w, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector4i vec4i)
	{
		return add((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector3f vec3f, Vector4f dest)
	{
		dest.x = x - vec3f.x;
		dest.y = y - vec3f.y;
		dest.z = z - vec3f.z;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector3i vec3i, Vector4f dest)
	{
		dest.x = x - (float)vec3i.x;
		dest.y = y - (float)vec3i.y;
		dest.z = z - (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector3f vec3f)
	{
		return add(vec3f, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector3i vec3i)
	{
		return add(vec3i, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector2f vec2f, Vector4f dest)
	{
		dest.x = x - vec2f.x;
		dest.y = y - vec2f.y;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector2i vec2i, Vector4f dest)
	{
		dest.x = x - (float)vec2i.x;
		dest.y = y - (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector2f vec2f)
	{
		return add(vec2f, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f sub(Vector2i vec2i)
	{
		return add(vec2i, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param value - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(float scalar, Vector4f dest)
	{
		dest.x = x * scalar;
		dest.y = y * scalar;
		dest.z = z * scalar;
		dest.w = w * scalar;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param value - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f mul(float scalar)
	{
		return mul(scalar, this);
	}
	
	/**
	 * Multiply by Matrix4f
	 * 
	 * @param mat4 - transformation
	 * 
	 * @return this
	 * 
	 * @see Matrix4f
	 */
	public Vector4f mul(Matrix4f mat4f, Vector4f dest)
	{
		dest.x = mat4f.elements[0 + 0 * 4] * x + mat4f.elements[0 + 1 * 4] * y + mat4f.elements[0 + 2 * 4] * z + mat4f.elements[0 + 3 * 4] * w;
		dest.y = mat4f.elements[1 + 0 * 4] * x + mat4f.elements[1 + 1 * 4] * y + mat4f.elements[1 + 2 * 4] * z + mat4f.elements[1 + 3 * 4] * w;
		dest.z = mat4f.elements[2 + 0 * 4] * x + mat4f.elements[2 + 1 * 4] * y + mat4f.elements[2 + 2 * 4] * z + mat4f.elements[2 + 3 * 4] * w;
		dest.w = mat4f.elements[3 + 0 * 4] * x + mat4f.elements[3 + 1 * 4] * y + mat4f.elements[3 + 2 * 4] * z + mat4f.elements[3 + 3 * 4] * w;
		return this;
	}
	
	/**
	 * Multiply by Matrix4f
	 * 
	 * @return this
	 * 
	 * @see Matrix4f
	 */
	public Vector4f mul(Matrix4f mat4f)
	{
		return mul(mat4f, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(float vx, float vy, float vz, float vw, Vector4f dest)
	{
		dest.x = x * vx;
		dest.y = y * vy;
		dest.z = z * vz;
		dest.w = w * vw;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * 
	 * @return this
	 */
	public Vector4f mul(float vx, float vy, float vz, float vw)
	{
		return mul(vx, vy, vz, vw, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector4f vec4f, Vector4f dest)
	{
		return mul(vec4f.x, vec4f.y, vec4f.z, vec4f.w, dest);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector4i vec4i, Vector4f dest)
	{
		return mul((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, dest);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector4f vec4f)
	{
		return mul(vec4f.x, vec4f.y, vec4f.z, vec4f.w, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector4i vec4i)
	{
		return mul((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector3f vec3f, Vector4f dest)
	{
		dest.x = x * vec3f.x;
		dest.y = y * vec3f.y;
		dest.z = z * vec3f.z;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector3i vec3i, Vector4f dest)
	{
		dest.x = x * (float)vec3i.x;
		dest.y = y * (float)vec3i.y;
		dest.z = z * (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector3f vec3f)
	{
		return mul(vec3f, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector3i vec3i)
	{
		return mul(vec3i, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector2f vec2f, Vector4f dest)
	{
		dest.x = x * vec2f.x;
		dest.y = y * vec2f.y;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector2i vec2i, Vector4f dest)
	{
		dest.x = x * (float)vec2i.x;
		dest.y = y * (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector2f vec2f)
	{
		return mul(vec2f, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f mul(Vector2i vec2i)
	{
		return mul(vec2i, this);
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(float vx, float vy, float vz, float vw, Vector4f dest)
	{
		dest.x = x / vx;
		dest.y = y / vy;
		dest.z = z / vz;
		dest.w = w / vw;
		
		return this;
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 * 
	 * @return this
	 */
	public Vector4f div(float vx, float vy, float vz, float vw)
	{
		return div(vx, vy, vz, vw, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(Vector4f vec4f, Vector4f dest)
	{
		return div(vec4f.x, vec4f.y, vec4f.z, vec4f.w, dest);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(Vector4i vec4i, Vector4f dest)
	{
		return div((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, dest);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f div(Vector4f vec4f)
	{
		return div(vec4f.x, vec4f.y, vec4f.z, vec4f.w, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4f div(Vector4i vec4i)
	{
		return div((float)vec4i.x, (float)vec4i.y, (float)vec4i.z, (float)vec4i.w, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(Vector3f vec3f, Vector4f dest)
	{
		dest.x = x / vec3f.x;
		dest.y = y / vec3f.y;
		dest.z = z / vec3f.z;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(Vector3i vec3i, Vector4f dest)
	{
		dest.x = x / (float)vec3i.x;
		dest.y = y / (float)vec3i.y;
		dest.z = z / (float)vec3i.z;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f div(Vector3f vec3f)
	{
		return div(vec3f, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4f div(Vector3i vec3i)
	{
		return div(vec3i, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(Vector2f vec2f, Vector4f dest)
	{
		dest.x = x / vec2f.x;
		dest.y = y / vec2f.y;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2i - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f div(Vector2i vec2i, Vector4f dest)
	{
		dest.x = x / (float)vec2i.x;
		dest.y = y / (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f div(Vector2f vec2f)
	{
		return div(vec2f, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector4f div(Vector2i vec2i)
	{
		return div(vec2i, this);
	}
	
	/**
	 * The squared magnitude
	 * 
	 * @return magnitude
	 */
	public float magnitudeSquared()
	{
		return x * x + y * y + z * z + w * w;
	}
	
	/**
	 * The magnitude
	 * 
	 * @return magnitude
	 */
	public float magnitude()
	{
		return (float)Math.sqrt((double)magnitudeSquared() + 0.5);
	}
	
	/**
	 * Normalize components
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f normalize(Vector4f dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = x * inv;
		dest.y = y * inv;
		dest.z = z * inv;
		dest.w = w * inv;
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Vector4f normalize()
	{
		return normalize(this);
	}
	
	/**
	 * Dot product
	 * 
	 * @param vec4f - dotted vector
	 * 
	 * @return dot product
	 */
	public float dot(Vector4f vec4f)
	{
		return x * vec4f.x + y * vec4f.y + z * vec4f.z + w * vec4f.w;
	}
	
	/**
	 * Swizzle to Vector3f
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f xyz(Vector3f dest)
	{
		dest.x = x;
		dest.y = y;
		dest.z = z;
		
		return this;
	}
	
	/**
	 * Swizzle to Vector3f
	 * 
	 * @param dest - result vector
	 * 
	 * @return swizzled Vector3f
	 */
	public Vector3f xyz()
	{
		return new Vector3f(x, y, z);
	}
	
	/**
	 * Swizzle to Vector2f
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4f xy(Vector2f dest)
	{
		dest.x = x;
		dest.y = y;
		
		return this;
	}
	
	/**
	 * Swizzle to Vector2f
	 * 
	 * @param dest - result vector
	 * 
	 * @return swizzled Vector2f
	 */
	public Vector2f xy()
	{
		return new Vector2f(x, y);
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Float.SIZE * 4;
	}
	
	/**
	 * Store components in a FloatBuffer
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return this
	 * 
	 * @see FloatBuffer
	 */
	public Vector4f putFloatBuffer(FloatBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return this;
	}
	
	/**
	 * Store components in a FloatBuffer
	 * 
	 * @return this
	 * 
	 * @see FloatBuffer
	 */
	public Vector4f putFloatBuffer(FloatBuffer buffer)
	{
		return putFloatBuffer(buffer, false);
	}
	
	/**
	 * Return new FloatBuffer of components
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return new buffer
	 * 
	 * @see FloatBuffer
	 */
	public FloatBuffer toFloatBuffer(boolean flip)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(sizeInBytes());
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return buffer;
	}
	
	/**
	 * Return new FloatBuffer of components
	 * 
	 * @return new buffer
	 * 
	 * @see FloatBuffer
	 */
	public FloatBuffer toFloatBuffer()
	{
		return toFloatBuffer(false);
	}
	
	/**
	 * Return string value
	 */
	@Override
	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}
}
