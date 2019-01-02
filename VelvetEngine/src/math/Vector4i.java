package math;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * A container (vector) of 4 integer values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Vector3i
 * @see Vector2i
 */

public class Vector4i {

	/**
	 * The x component
	 */
	public int x;
	
	/**
	 * The y component
	 */
	public int  y;
	
	/**
	 * The z component
	 */
	public int  z;
	
	/**
	 * The w component
	 */
	public int  w;
	
	/**
	 * Construct a 4-integer vector
	 */
	public Vector4i()
	{
		this(0, 0, 0, 0);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param value - x, y, and z components
	 */
	public Vector4i(int value)
	{
		this(value, value, value, value);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param vz - z component
	 * @param vw - w component
	 */
	public Vector4i(int vx, int vy, int vz, int vw)
	{
		x = vx;
		y = vy;
		z = vz;
		w = vw;
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vec4f - x, y, z, and w components
	 */
	public Vector4i(Vector4f vec4f)
	{
		this((int)(int)vec4f.x, (int)(int)vec4f.y, (int)(int)vec4f.z, (int)(int)vec4f.w);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vec4i - x, y, z, and w components
	 */
	public Vector4i(Vector4i vec4i)
	{
		this(vec4i.x, vec4i.y, vec4i.z, vec4i.w);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vec3f - x, y, and z components
	 * @param vw - w component
	 */
	public Vector4i(Vector3f vec3f, int vw)
	{
		this((int)vec3f.x, (int)vec3f.y, (int)vec3f.z, vw);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vec3i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector4i(Vector3i vec3i, int vw)
	{
		this(vec3i.x, vec3i.y, vec3i.z, vw);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vec2f - x and y components
	 * @param vz - z component
	 * @param vw - w component
	 */
	public Vector4i(Vector2f vec2f, int vz, int vw)
	{
		this((int)vec2f.x, (int)vec2f.y, vz, vw);
	}
	
	/**
	 * Construct a 4-integer vector
	 * 
	 * @param vec2i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector4i(Vector2i vec2i, int vz, int vw)
	{
		this(vec2i.x, vec2i.y, vz, vw);
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
	public Vector4i set(int vx, int vy, int vz, int vw)
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
	public Vector4i set(Vector4f vec4f)
	{
		return set((int)(int)vec4f.x, (int)(int)vec4f.y, (int)(int)vec4f.z, (int)(int)vec4f.w);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i set(Vector4i vec4i)
	{
		return set(vec4i.x, vec4i.y, vec4i.z, vec4i.w);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4i set(Vector3f vec3f)
	{
		this.x = (int)vec3f.x;
		this.y = (int)vec3f.y;
		this.z = (int)vec3f.z;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3i - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4i set(Vector3i vec3i)
	{
		this.x = vec3i.x;
		this.y = vec3i.y;
		this.z = vec3i.z;
		
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
	public Vector4i set(Vector3f vec3f, int vw)
	{
		return set((int)vec3f.x, (int)vec3f.y, (int)vec3f.z, vw);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec3i - x, y, and z components
	 * @param vw - the w component
	 * 
	 * @return this
	 */
	public Vector4i set(Vector3i vec3i, int vw)
	{
		return set(vec3i.x, vec3i.y, vec3i.z, vw);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * 
	 * @return this
	 */
	public Vector4i set(Vector2f vec2f)
	{
		this.x = (int)vec2f.x;
		this.y = (int)vec2f.y;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2i - x and y components
	 * 
	 * @return this
	 */
	public Vector4i set(Vector2i vec2i)
	{
		this.x = vec2i.x;
		this.y = vec2i.y;
		
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
	public Vector4i set(Vector2f vec2f, int vz, int vw)
	{
		return set((int)vec2f.x, (int)vec2f.y, vz, vw);
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
	public Vector4i set(Vector2i vec2i, int vz, int vw)
	{
		return set(vec2i.x, vec2i.y, vz, vw);
	}
	
	/**
	 * Set a component by index
	 * 
	 * @param index - component index
	 * @param value - component value
	 * @throws IndexOutOfBoundsException if index < 0 or index > 3
	 */
	public void set(int index, int value)
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
	public int get(int index)
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
	public Vector4i copy()
	{
		return new Vector4i(x, y, z, w);
	}
	
	/**
	 * Copies components to destination
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i copy(Vector4i dest)
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
	public Vector4i add(int vx, int vy, int vz, int vw, Vector4i dest)
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
	public Vector4i add(int vx, int vy, int vz, int vw)
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
	public Vector4i add(Vector4f vec4f, Vector4i dest)
	{
		return add((int)(int)vec4f.x, (int)(int)vec4f.y, (int)(int)vec4f.z, (int)(int)vec4f.w, dest);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i add(Vector4i vec4i, Vector4i dest)
	{
		return add(vec4i.x, vec4i.y, vec4i.z, vec4i.w, dest);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i add(Vector4f vec4f)
	{
		return add((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i add(Vector4i vec4i)
	{
		return add(vec4i.x, vec4i.y, vec4i.z, vec4i.w, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i add(Vector3f vec3f, Vector4i dest)
	{
		dest.x = (int)(x + vec3f.x);
		dest.y = (int)(y + vec3f.y);
		dest.z = (int)(z + vec3f.z);
		
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
	public Vector4i add(Vector3i vec3i, Vector4i dest)
	{
		dest.x = x + vec3i.x;
		dest.y = y + vec3i.y;
		dest.z = z + vec3i.z;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4i add(Vector3f vec3f)
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
	public Vector4i add(Vector3i vec3i)
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
	public Vector4i add(Vector2f vec2f, Vector4i dest)
	{
		dest.x = (int)(x + vec2f.x);
		dest.y = (int)(y + vec2f.y);
		
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
	public Vector4i add(Vector2i vec2i, Vector4i dest)
	{
		dest.x = x + vec2i.x;
		dest.y = y + vec2i.y;
		
		return this;
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4i add(Vector2f vec2f)
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
	public Vector4i add(Vector2i vec2i)
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
	public Vector4i sub(int vx, int vy, int vz, int vw, Vector4i dest)
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
	public Vector4i sub(int vx, int vy, int vz, int vw)
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
	public Vector4i sub(Vector4f vec4f, Vector4i dest)
	{
		return add((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, dest);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i sub(Vector4i vec4i, Vector4i dest)
	{
		return add(vec4i.x, vec4i.y, vec4i.z, vec4i.w, dest);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i sub(Vector4f vec4f)
	{
		return add((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i sub(Vector4i vec4i)
	{
		return add(vec4i.x, vec4i.y, vec4i.z, vec4i.w, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i sub(Vector3f vec3f, Vector4i dest)
	{
		dest.x = (int)(x - vec3f.x);
		dest.y = (int)(y - vec3f.y);
		dest.z = (int)(z - vec3f.z);
		
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
	public Vector4i sub(Vector3i vec3i, Vector4i dest)
	{
		dest.x = x - vec3i.x;
		dest.y = y - vec3i.y;
		dest.z = z - vec3i.z;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4i sub(Vector3f vec3f)
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
	public Vector4i sub(Vector3i vec3i)
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
	public Vector4i sub(Vector2f vec2f, Vector4i dest)
	{
		dest.x = (int)(x - vec2f.x);
		dest.y = (int)(y - vec2f.y);
		
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
	public Vector4i sub(Vector2i vec2i, Vector4i dest)
	{
		dest.x = x - vec2i.x;
		dest.y = y - vec2i.y;
		
		return this;
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4i sub(Vector2f vec2f)
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
	public Vector4i sub(Vector2i vec2i)
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
	public Vector4i mul(int scalar, Vector4i dest)
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
	public Vector4i mul(int scalar)
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
	public Vector4i mul(Matrix4f mat4f, Vector4i dest)
	{
		dest.x = (int)(mat4f.elements[0 + 0 * 4] * x + mat4f.elements[0 + 1 * 4] * y + mat4f.elements[0 + 2 * 4] * z + mat4f.elements[0 + 3 * 4] * w);
		dest.y = (int)(mat4f.elements[1 + 0 * 4] * x + mat4f.elements[1 + 1 * 4] * y + mat4f.elements[1 + 2 * 4] * z + mat4f.elements[1 + 3 * 4] * w);
		dest.z = (int)(mat4f.elements[2 + 0 * 4] * x + mat4f.elements[2 + 1 * 4] * y + mat4f.elements[2 + 2 * 4] * z + mat4f.elements[2 + 3 * 4] * w);
		dest.w = (int)(mat4f.elements[3 + 0 * 4] * x + mat4f.elements[3 + 1 * 4] * y + mat4f.elements[3 + 2 * 4] * z + mat4f.elements[3 + 3 * 4] * w);
		return this;
	}
	
	/**
	 * Multiply by Matrix4f
	 * 
	 * @return this
	 * 
	 * @see Matrix4f
	 */
	public Vector4i mul(Matrix4f mat4f)
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
	public Vector4i mul(int vx, int vy, int vz, int vw, Vector4i dest)
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
	public Vector4i mul(int vx, int vy, int vz, int vw)
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
	public Vector4i mul(Vector4f vec4f, Vector4i dest)
	{
		return mul((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, dest);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i mul(Vector4i vec4i, Vector4i dest)
	{
		return mul(vec4i.x, vec4i.y, vec4i.z, vec4i.w, dest);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i mul(Vector4f vec4f)
	{
		return mul((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i mul(Vector4i vec4i)
	{
		return mul(vec4i.x, vec4i.y, vec4i.z, vec4i.w, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i mul(Vector3f vec3f, Vector4i dest)
	{
		dest.x = (int)(x * vec3f.x);
		dest.y = (int)(y * vec3f.y);
		dest.z = (int)(z * vec3f.z);
		
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
	public Vector4i mul(Vector3i vec3i, Vector4i dest)
	{
		dest.x = x * vec3i.x;
		dest.y = y * vec3i.y;
		dest.z = z * vec3i.z;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4i mul(Vector3f vec3f)
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
	public Vector4i mul(Vector3i vec3i)
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
	public Vector4i mul(Vector2f vec2f, Vector4i dest)
	{
		dest.x = (int)(x * vec2f.x);
		dest.y = (int)(y * vec2f.y);
		
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
	public Vector4i mul(Vector2i vec2i, Vector4i dest)
	{
		dest.x = x * vec2i.x;
		dest.y = y * vec2i.y;
		
		return this;
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4i mul(Vector2f vec2f)
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
	public Vector4i mul(Vector2i vec2i)
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
	public Vector4i div(int vx, int vy, int vz, int vw, Vector4i dest)
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
	public Vector4i div(int vx, int vy, int vz, int vw)
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
	public Vector4i div(Vector4f vec4f, Vector4i dest)
	{
		return div((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, dest);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i div(Vector4i vec4i, Vector4i dest)
	{
		return div(vec4i.x, vec4i.y, vec4i.z, vec4i.w, dest);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4f - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i div(Vector4f vec4f)
	{
		return div((int)vec4f.x, (int)vec4f.y, (int)vec4f.z, (int)vec4f.w, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec4i - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector4i div(Vector4i vec4i)
	{
		return div(vec4i.x, vec4i.y, vec4i.z, vec4i.w, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i div(Vector3f vec3f, Vector4i dest)
	{
		dest.x = (int)(x / vec3f.x);
		dest.y = (int)(y / vec3f.y);
		dest.z = (int)(z / vec3f.z);
		
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
	public Vector4i div(Vector3i vec3i, Vector4i dest)
	{
		dest.x = x / vec3i.x;
		dest.y = y / vec3i.y;
		dest.z = z / vec3i.z;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec3f - x, y, and z components
	 * 
	 * @return this
	 */
	public Vector4i div(Vector3f vec3f)
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
	public Vector4i div(Vector3i vec3i)
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
	public Vector4i div(Vector2f vec2f, Vector4i dest)
	{
		dest.x = (int)(x / vec2f.x);
		dest.y = (int)(y / vec2f.y);
		
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
	public Vector4i div(Vector2i vec2i, Vector4i dest)
	{
		dest.x = x / vec2i.x;
		dest.y = y / vec2i.y;
		
		return this;
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * 
	 * @return this
	 */
	public Vector4i div(Vector2f vec2f)
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
	public Vector4i div(Vector2i vec2i)
	{
		return div(vec2i, this);
	}
	
	/**
	 * The squared magnitude
	 * 
	 * @return magnitude
	 */
	public int magnitudeSquared()
	{
		return x * x + y * y + z * z + w * w;
	}
	
	/**
	 * The magnitude
	 * 
	 * @return magnitude
	 */
	public int magnitude()
	{
		return (int)Math.sqrt((double)magnitudeSquared() + 0.5);
	}
	
	/**
	 * Normalize components
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i normalize(Vector4i dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = (int)(x * inv);
		dest.y = (int)(y * inv);
		dest.z = (int)(z * inv);
		dest.w = (int)(w * inv);
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Vector4i normalize()
	{
		return normalize(this);
	}
	
	/**
	 * Dot product
	 * 
	 * @param vec4i - dotted vector
	 * 
	 * @return dot product
	 */
	public int dot(Vector4i vec4i)
	{
		return x * (int)vec4i.x + y * (int)vec4i.y + z * (int)vec4i.z + w * (int)vec4i.w;
	}
	
	/**
	 * Swizzle to Vector3i
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i xyz(Vector3i dest)
	{
		dest.x = x;
		dest.y = y;
		dest.z = z;
		
		return this;
	}
	
	/**
	 * Swizzle to Vector3i
	 * 
	 * @param dest - result vector
	 * 
	 * @return swizzled Vector3i
	 */
	public Vector3i xyz()
	{
		return new Vector3i(x, y, z);
	}
	
	/**
	 * Swizzle to Vector2i
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector4i xy(Vector2i dest)
	{
		dest.x = x;
		dest.y = y;
		
		return this;
	}
	
	/**
	 * Swizzle to Vector2i
	 * 
	 * @param dest - result vector
	 * 
	 * @return swizzled Vector2i
	 */
	public Vector2i xy()
	{
		return new Vector2i(x, y);
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Integer.SIZE * 4;
	}
	
	/**
	 * Store components in a IntBuffer
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return this
	 * 
	 * @see IntBuffer
	 */
	public Vector4i putIntBuffer(IntBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return this;
	}
	
	/**
	 * Store components in a IntBuffer
	 * 
	 * @return this
	 * 
	 * @see IntBuffer
	 */
	public Vector4i putIntBuffer(IntBuffer buffer)
	{
		return putIntBuffer(buffer, false);
	}
	
	/**
	 * Return new IntBuffer of components
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return new buffer
	 * 
	 * @see IntBuffer
	 */
	public IntBuffer toIntBuffer(boolean flip)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(sizeInBytes());
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.put(w);
		if(flip) buffer.flip();
		return buffer;
	}
	
	/**
	 * Return new IntBuffer of components
	 * 
	 * @return new buffer
	 * 
	 * @see IntBuffer
	 */
	public IntBuffer toIntBuffer()
	{
		return toIntBuffer(false);
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
