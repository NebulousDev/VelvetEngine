package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * A container (vector) of 2 float values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Matrix4f
 * @see Vector4f
 * @see Vector3f
 */

public class Vector2f {

	/**
	 * The x component
	 */
	public float x;
	
	/**
	 * The y component
	 */
	public float y;
	
	/**
	 * Construct a 2-float vector
	 */
	public Vector2f()
	{
		this(0, 0);
	}
	
	/**
	 * Construct a 2-float vector
	 * 
	 * @param value - x, and y components
	 */
	public Vector2f(float value)
	{
		this(value, value);
	}
	
	/**
	 * Construct a 2-float vector
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 */
	public Vector2f(float vx, float vy)
	{
		x = vx;
		y = vy;
	}
	
	/**
	 * Construct a 2-float vector
	 * 
	 * @param vec2f - x and y components
	 * @param vz - z component
	 */
	public Vector2f(Vector2f vec2f)
	{
		this(vec2f.x, vec2f.y);
	}
	
	/**
	 * Construct a 2-float vector
	 * 
	 * @param vec2i - x, y, and z components
	 * @param vw - w component
	 */
	public Vector2f(Vector2i vec2i)
	{
		this((float)vec2i.x, (float)vec2i.y);
	}
	
	/**
	 * Set component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2f set(float vx, float vy)
	{
		this.x = vx;
		this.y = vy;
		
		return this;
	}
	
	/**
	 * Set component values
	 * 
	 * @param vec2f - x and y components
	 * 
	 * @return this
	 */
	public Vector2f set(Vector2f vec2f)
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
	public Vector2f set(Vector2i vec2i)
	{
		this.x = (float)vec2i.x;
		this.y = (float)vec2i.y;
		
		return this;
	}
	
	/**
	 * Set a component by index
	 * 
	 * @param index - component index
	 * @param value - component value
	 * @throws IndexOutOfBoundsException if index < 0 or index > 1
	 */
	public void set(int index, float value)
	{
		switch (index)
		{
			case 0: x = value; return;
			case 1: y = value; return;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Retrieve a component by index
	 * 
	 * @param index - component index
	 * @throws IndexOutOfBoundsException if index < 0 or index > 1
	 * @return value at index
	 */
	public float get(int index)
	{
		switch (index)
		{
			case 0: return x;
			case 1: return y;
			default: break;
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Copies components to a new instance
	 * 
	 * @return new copy
	 */
	public Vector2f copy()
	{
		return new Vector2f(x, y);
	}
	
	/**
	 * Copies components to destination
	 * 
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f copy(Vector2f dest)
	{
		return dest.set(this);
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f add(float vx, float vy, Vector2f dest)
	{
		dest.x = x + vx;
		dest.y = y + vy;
		
		return this;
	}
	
	/**
	 * Add component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2f add(float vx, float vy)
	{
		return add(vx, vy, this);
	}
	
	/**
	 * Add vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f add(Vector2f vec2f, Vector2f dest)
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
	public Vector2f add(Vector2i vec2i, Vector2f dest)
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
	public Vector2f add(Vector2f vec2f)
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
	public Vector2f add(Vector2i vec2i)
	{
		return add(vec2i, this);
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f sub(float vx, float vy, Vector2f dest)
	{
		dest.x = x - vx;
		dest.y = y - vy;
		
		return this;
	}
	
	/**
	 * Subtract component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2f sub(float vx, float vy)
	{
		return sub(vx, vy, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f sub(Vector2f vec2f, Vector2f dest)
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
	public Vector2f sub(Vector2i vec2i, Vector2f dest)
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
	public Vector2f sub(Vector2f vec2f)
	{
		return sub(vec2f, this);
	}
	
	/**
	 * Subtract vector components
	 * 
	 * @param vec2i - x, and y components
	 * 
	 * @return this
	 */
	public Vector2f sub(Vector2i vec2i)
	{
		return sub(vec2i, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param value - x, y, z, and w components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f mul(float scalar, Vector2f dest)
	{
		dest.x = x * scalar;
		dest.y = x * scalar;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param value - x, y, z, and w components
	 * 
	 * @return this
	 */
	public Vector2f mul(float scalar)
	{
		return mul(scalar, this);
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f mul(float vx, float vy, Vector2f dest)
	{
		dest.x = x * vx;
		dest.y = y * vy;
		
		return this;
	}
	
	/**
	 * Multiply component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2f mul(float vx, float vy)
	{
		return mul(vx, vy, this);
	}
	
	/**
	 * Multiply vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f mul(Vector2f vec2f, Vector2f dest)
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
	public Vector2f mul(Vector2i vec2i, Vector2f dest)
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
	public Vector2f mul(Vector2f vec2f)
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
	public Vector2f mul(Vector2i vec2i)
	{
		return mul(vec2i, this);
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f div(float vx, float vy, Vector2f dest)
	{
		dest.x = x / vx;
		dest.y = y / vy;
		
		return this;
	}
	
	/**
	 * Divide component values
	 * 
	 * @param vx - x component
	 * @param vy - y component
	 * 
	 * @return this
	 */
	public Vector2f div(float vx, float vy)
	{
		return div(vx, vy, this);
	}
	
	/**
	 * Divide vector components
	 * 
	 * @param vec2f - x, and y components
	 * @param dest - result vector
	 * 
	 * @return this
	 */
	public Vector2f div(Vector2f vec2f, Vector2f dest)
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
	public Vector2f div(Vector2i vec2i, Vector2f dest)
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
	public Vector2f div(Vector2f vec2f)
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
	public Vector2f div(Vector2i vec2i)
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
		return x * x + y * y;
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
	public Vector2f normalize(Vector2f dest)
	{
		float inv = 1.0f / magnitude();
		dest.x = x * inv;
		dest.y = y * inv;
		
		return this;
	}
	
	/**
	 * Normalize components
	 * 
	 * @return this
	 */
	public Vector2f normalize()
	{
		return normalize(this);
	}
	
	/**
	 * Dot product
	 * 
	 * @param vec3f - dotted vector
	 * 
	 * @return dot product
	 */
	public float dot(Vector2f vec2f)
	{
		return x * vec2f.x + y * vec2f.y;
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Float.SIZE * 2;
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
	public Vector2f putFloatBuffer(FloatBuffer buffer, boolean flip)
	{
		buffer.put(x);
		buffer.put(y);
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
	public Vector2f putFloatBuffer(FloatBuffer buffer)
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
		return "[" + x + ", " + y + "]";
	}
	
}
