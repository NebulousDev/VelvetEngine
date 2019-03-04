package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * A 4x4 matrix of float values
 * 
 * @author Ben Ratcliff (NebulousDev)
 * 
 * @see Quaternion
 * @see Vector4f
 * @see Vector3f
 * @see Vector2f
 */

public class Matrix4f {
	
	/**
	 * Matrix data - 16 float elements
	 */
	public float[] elements;
    
	/**
	 * Construct 4x4 float matrix
	 */
    public Matrix4f()
    {
    	elements = new float[16];
    	
    	elements[0 + 0 * 4] = 1.0f;
    	elements[1 + 1 * 4] = 1.0f;
    	elements[2 + 2 * 4] = 1.0f;
    	elements[3 + 3 * 4] = 1.0f;
    }
    
    /**
	 * Construct 4x4 float matrix
	 * 
	 * @param elements - 16 float elements
	 */
    public Matrix4f(float[] elements)
    {
    	if(elements.length == 16)
    		this.elements = elements;
    	
    	else
    	{
    		for(int i = 0; i < Math.min(elements.length, 16); i++)
    			this.elements[i] = elements[i];
    	}
    }
    
    /**
     * Multiply by a scalar value
     * 
     * @param scalar - scalar float value
     * 
     * @return this
     */
    public Matrix4f mul(float scalar)
	{
		for(int i = 0; i < 16; i++)
			elements[i] *= scalar;
		
		return this;
	}
    
    /**
     * Multiply by another Matrix4f
     * 
     * @param mat4f - multiplied matrix
     * @param dest - result matrix
     * 
     * @return this
     */
    public Matrix4f mul(Matrix4f mat4f, Matrix4f dest)
    {
    	float[] result = new float[16];
    	
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				float sum = 0.0f;
				for (int i = 0; i < 4; i++)
					sum += elements[x + i * 4] * mat4f.elements[i + y * 4];
				result[x + y * 4] = sum;
			}
		}
		
		dest.set(result);
		
		return this; 
    }
    
    /**
     * Multiply by another Matrix4f
     * 
     * @param mat4f - multiplied matrix
     * 
     * @return this
     */
    public Matrix4f mul(Matrix4f mat4f)
    {
    	return mul(mat4f, this);
    	
    	/*
    	return mul(
			mat4f.elements[0 + 0 * 4], mat4f.elements[1 + 0 * 4], mat4f.elements[2 + 0 * 4], mat4f.elements[3 + 0 * 4], 
			mat4f.elements[0 + 1 * 4], mat4f.elements[1 + 1 * 4], mat4f.elements[2 + 1 * 4], mat4f.elements[3 + 1 * 4], 
			mat4f.elements[0 + 2 * 4], mat4f.elements[1 + 2 * 4], mat4f.elements[2 + 2 * 4], mat4f.elements[3 + 2 * 4], 
			mat4f.elements[0 + 3 * 4], mat4f.elements[1 + 3 * 4], mat4f.elements[2 + 3 * 4], mat4f.elements[3 + 3 * 4], 
			this);
    	 */
    	
    }
    
    /**
     * Multiply by element values
     * 
     * @param m00 -> m33 - element values
     * 
     * @return this
     */
    public Matrix4f mul(
    		float m00, float m01, float m02, float m03,
    		float m10, float m11, float m12, float m13,
    		float m20, float m21, float m22, float m23,
    		float m30, float m31, float m32, float m33,
    		Matrix4f dest)
    {
    	float[] result = new float[16];
    	
    	result[0 + 0 * 4] = elements[0 + 0 * 4] * m00 + elements[0 + 1 * 4] * m01 + elements[0 + 2 * 4] * m02 + elements[0 + 3 * 4] * m03;
    	result[1 + 0 * 4] = elements[1 + 0 * 4] * m00 + elements[1 + 1 * 4] * m01 + elements[1 + 2 * 4] * m02 + elements[1 + 3 * 4] * m03;
    	result[2 + 0 * 4] = elements[2 + 0 * 4] * m00 + elements[2 + 1 * 4] * m01 + elements[2 + 2 * 4] * m02 + elements[2 + 3 * 4] * m03;
    	result[3 + 0 * 4] = elements[3 + 0 * 4] * m00 + elements[3 + 1 * 4] * m01 + elements[3 + 2 * 4] * m02 + elements[3 + 3 * 4] * m03;
    	result[0 + 1 * 4] = elements[0 + 0 * 4] * m10 + elements[0 + 1 * 4] * m11 + elements[0 + 2 * 4] * m12 + elements[0 + 3 * 4] * m13;
    	result[1 + 1 * 4] = elements[1 + 0 * 4] * m10 + elements[1 + 1 * 4] * m11 + elements[1 + 2 * 4] * m12 + elements[1 + 3 * 4] * m13;
    	result[2 + 1 * 4] = elements[2 + 0 * 4] * m10 + elements[2 + 1 * 4] * m11 + elements[2 + 2 * 4] * m12 + elements[2 + 3 * 4] * m13;
    	result[3 + 1 * 4] = elements[3 + 0 * 4] * m10 + elements[3 + 1 * 4] * m11 + elements[3 + 2 * 4] * m12 + elements[3 + 3 * 4] * m13;
    	result[0 + 2 * 4] = elements[0 + 0 * 4] * m20 + elements[0 + 1 * 4] * m21 + elements[0 + 2 * 4] * m22 + elements[0 + 3 * 4] * m23;
    	result[1 + 2 * 4] = elements[1 + 0 * 4] * m20 + elements[1 + 1 * 4] * m21 + elements[1 + 2 * 4] * m22 + elements[1 + 3 * 4] * m23;
    	result[2 + 2 * 4] = elements[2 + 0 * 4] * m20 + elements[2 + 1 * 4] * m21 + elements[2 + 2 * 4] * m22 + elements[2 + 3 * 4] * m23;
    	result[3 + 2 * 4] = elements[3 + 0 * 4] * m20 + elements[3 + 1 * 4] * m21 + elements[3 + 2 * 4] * m22 + elements[3 + 3 * 4] * m23;
    	result[0 + 3 * 4] = elements[0 + 0 * 4] * m30 + elements[0 + 1 * 4] * m31 + elements[0 + 2 * 4] * m32 + elements[0 + 3 * 4] * m33;
    	result[1 + 3 * 4] = elements[1 + 0 * 4] * m30 + elements[1 + 1 * 4] * m31 + elements[1 + 2 * 4] * m32 + elements[1 + 3 * 4] * m33;
    	result[2 + 3 * 4] = elements[2 + 0 * 4] * m30 + elements[2 + 1 * 4] * m31 + elements[2 + 2 * 4] * m32 + elements[2 + 3 * 4] * m33;
    	result[3 + 3 * 4] = elements[3 + 0 * 4] * m30 + elements[3 + 1 * 4] * m31 + elements[3 + 2 * 4] * m32 + elements[3 + 3 * 4] * m33;
         
    	dest.set(result);
    	
		return this;
    }
    /**
     * Set element values
     * 
     * @param elements - element values
     * 
     * @return this
     */
    public Matrix4f set(float[] elements)
    {
		for(int i = 0; i < Math.min(elements.length, 16); i++)
			this.elements[i] = elements[i];
    	
    	return this;
	}
    
    /**
     * Set element values
     * 
     * @param mat4f - element values
     * 
     * @return this
     */
    public Matrix4f set(Matrix4f mat4f)
    {
    	for(int i = 0; i < Math.min(elements.length, 16); i++)
			this.elements[i] = mat4f.elements[i];
    	
    	return this;
	}
    
    /**
     * Set matrix as a perspective projection matrix
     * [This will clear all previous values]
     * 
     * @param fov - field of view in degrees
     * @param aspect - frame width / height
     * @param near - near clipping plane
     * @param far - far clipping plane
     * 
     * @return this
     */
    public Matrix4f setPerspective(float fov, float aspect, float near, float far)
    {
    	clear();
    	
    	float q = (float)Math.tan(Math.toRadians(0.5f * fov));
		
    	elements[0 + 0 * 4] = 1.0f / (q * aspect);
    	elements[1 + 1 * 4] = 1.0f / q;
    	elements[2 + 2 * 4] = (near + far) / (near - far);
    	elements[2 + 3 * 4] = (2.0f * near * far) / (near - far);
    	elements[3 + 2 * 4] = -1.0f;
    	
    	return this;
    }
    
    /**
     * Set matrix as an orthographic projection matrix
     * [This will clear all previous values]
     * 
     * @param left - frustum left
     * @param right - frustum right
     * @param bottom - frustum bottom
     * @param top - frustum top
     * @param near - near clipping plane
     * @param far - far clipping plane
     * 
     * @return this
     */
    public Matrix4f setOrthographic(float left, float right, float bottom, float top, float near, float far)
    {
    	clear();
		
    	elements[0 + 0 * 4] = 2.0f / (right - left);
    	elements[1 + 1 * 4] = 2.0f / (top - bottom);
    	elements[2 + 2 * 4] = 2.0f / (near - far);
    	elements[0 + 3 * 4] = (left + right) / (left - right);
    	elements[1 + 3 * 4] = (top + bottom) / (bottom - top);
    	elements[2 + 3 * 4] = (far + near) / (near - far);
    	
    	return this;
    }
    
    /**
     * Set matrix as a translation matrix
     * [This will clear all previous values]
     * 
     * @param vx - x translation
     * @param vy - y translation
     * @param vz - z translation
     * 
     * @return this
     */
    public Matrix4f setTranslation(float vx, float vy, float vz)
    {
    	clear();
    	
    	elements[0 + 3 * 4] = vx;
    	elements[1 + 3 * 4] = vy;
    	elements[2 + 3 * 4] = vz;
    	
    	return this;
    }
    
    /**
     * Set matrix as a translation matrix
     * [This will clear all previous values]
     * 
     * @param translation - x, y and z translation components
     * 
     * @return this
     */
    public Matrix4f setTranslation(Vector3f translation)
    {
    	return setTranslation(translation.x, translation.y, translation.z);
    }
    
    /**
     * Translate the matrix
     * 
     * @param vx - x translation
     * @param vy - y translation
     * @param vz - z translation
     * @param dest - result matrix
     * 
     * @return this
     */
    public Matrix4f translate(float vx, float vy, float vz, Matrix4f dest)
    {
    	dest.elements[0 + 3 * 4] = elements[0 + 0 * 4] * vx + elements[0 + 1 * 4] * vy + elements[0 + 2 * 4] * vz + elements[0 + 3 * 4];
    	dest.elements[1 + 3 * 4] = elements[1 + 0 * 4] * vx + elements[1 + 1 * 4] * vy + elements[1 + 2 * 4] * vz + elements[1 + 3 * 4];
    	dest.elements[2 + 3 * 4] = elements[2 + 0 * 4] * vx + elements[2 + 1 * 4] * vy + elements[2 + 2 * 4] * vz + elements[2 + 3 * 4];
    	dest.elements[3 + 3 * 4] = elements[3 + 0 * 4] * vx + elements[3 + 1 * 4] * vy + elements[3 + 2 * 4] * vz + elements[3 + 3 * 4];
    	
    	return this;
    }
    
    /**
     * Translate the matrix
     * 
     * @param translation - x, y, and z translation
     * @param dest - result matrix
     * 
     * @return this
     */
    public Matrix4f translate(Vector3f translation, Matrix4f dest)
    {
    	return translate(translation.x, translation.y, translation.z, dest);
    }
    
    /**
     * Translate the matrix
     * 
     * @param translation - x, y, and z translation
     * 
     * @return this
     */
    public Matrix4f translate(Vector3f translation)
    {
    	return translate(translation, this);
    }
    
    /**
     * Set matrix as a rotation matrix
     * [This will clear all previous values]
     * 
     * @param ax - x axis
     * @param ay - y axis
     * @param az - z axis
     * @param theta - angle
     * 
     * @return this
     */
    public Matrix4f setRotation(float ax, float ay, float az, float theta)
    {
    	clear();
    	
    	float sinT = (float)Math.sin(Math.toRadians(theta));
    	float cosT = (float)Math.cos(Math.toRadians(theta));
    	
    	elements[0 + 0 * 4] = cosT + (ax * ax) * (1 - cosT);
    	elements[1 + 0 * 4] = (ay * ax) * (1 - cosT) + (az * sinT);
    	elements[2 + 0 * 4] = (az * ax) * (1 - cosT) - (ay * sinT);
    	
    	elements[0 + 1 * 4] = (ax * ay) * (1 - cosT) - (az * sinT);
    	elements[1 + 1 * 4] = cosT + (ay * ay) * (1 - cosT);
    	elements[2 + 1 * 4] = (az * ay) * (1 - cosT) + (ax * sinT);
    	
    	elements[0 + 2 * 4] = (ax * az) * (1 - cosT) - (ay * sinT);
    	elements[1 + 2 * 4] = (ay * az) * (1 - cosT) + (ax * sinT);
    	elements[2 + 2 * 4] = cosT + (az * az) * (1 - cosT);
    	
    	return this;
    }
    
    /**
     * Set matrix as a rotation matrix
     * [This will clear all previous values]
     * 
     * @param axis - x, y, and z axis
     * @param theta - angle
     * 
     * @return this
     */
    public Matrix4f setRoataion(Vector3f axis, float theta)
    {
    	return setRotation(axis.x, axis.y, axis.z, theta);
    }
    
    /**
     * Set matrix as a rotation matrix
     * [This will clear all previous values]
     * 
     * @param quat - orientation
     * 
     * @return this
     */
    public Matrix4f setRotation(Quaternion quat)
    {
    	clear();
    	
		float xx = quat.x * quat.x;
		float xy = quat.x * quat.y;
		float xz = quat.x * quat.z;
		float xw = quat.x * quat.w;
		float yy = quat.y * quat.y;
		float yz = quat.y * quat.z;
		float yw = quat.y * quat.w;
		float zz = quat.z * quat.z;
		float zw = quat.z * quat.w;
		float ww = quat.w * quat.w;
		
		elements[0 + 0 * 4] = ww + xx - zz - yy;
		elements[1 + 0 * 4] = xy + zw + zw + xy;
		elements[2 + 0 * 4] = xz - yw + xz - yw;
		
		elements[0 + 1 * 4] = -zw + xy - zw + xy;
		elements[1 + 1 * 4] = yy - zz + ww - xx;
		elements[2 + 1 * 4] = yz + yz + xw + xw;
		
		elements[0 + 2 * 4] = yw + xz + xz + yw;
		elements[1 + 2 * 4] = yz + yz - xw - xw;
		elements[2 + 2 * 4] = zz - yy - xx + ww;
    	
		return this;
    }
    
    /**
     * Rotates the matrix
     * 
     * @param quat - orientation
     * @param dest - result matrix
     * 
     * @return this
     */
    public Matrix4f rotate(Quaternion quat, Matrix4f dest)
    {
    	float xx = quat.x * quat.x;
		float xy = quat.x * quat.y;
		float xz = quat.x * quat.z;
		float xw = quat.x * quat.w;
		float yy = quat.y * quat.y;
		float yz = quat.y * quat.z;
		float yw = quat.y * quat.w;
		float zz = quat.z * quat.z;
		float zw = quat.z * quat.w;
		float ww = quat.w * quat.w;
		
		float r00 = ww + xx - zz - yy;
		float r01 = xy + zw + zw + xy;
		float r02 = xz - yw + xz - yw;
		
		float r10 = -zw + xy - zw + xy;
		float r11 = yy - zz + ww - xx;
		float r12 = yz + yz + xw + xw;
		
		float r20 = yw + xz + xz + yw;
		float r21 = yz + yz - xw - xw;
		float r22 = zz - yy - xx + ww;
		
		this.mul
		(
			r00, r01, r02, 0,
			r10, r11, r12, 0,
			r20, r21, r22, 0,
			0, 0, 0, 1, dest
		);
		
		return this;
    }
    
    /**
     * Rotates the matrix
     * 
     * @param quat - orientation
     * 
     * @return this
     */
    public Matrix4f rotate(Quaternion orientation)
    {
    	return rotate(orientation, this);
    }
    
    /**
     * Set matrix as a scale matrix
     * [This will clear all previous values]
     * 
     * @param sx - x scale
     * @param sy - y scale
     * @param sz - z scale
     * 
     * @return this
     */
    public Matrix4f setScale(float sx, float sy, float sz)
    {
    	clear();
    	
    	elements[0 + 0 * 4] = sx;
    	elements[1 + 1 * 4] = sy;
    	elements[2 + 2 * 4] = sz;
    	
    	return this;
    }
    
    /**
     * Set matrix as a scale matrix
     * [This will clear all previous values]
     * 
     * @param scale - x, y, and z scale
     * 
     * @return this
     */
    public Matrix4f setScale(Vector3f scale)
    {
    	return setScale(scale.x, scale.y, scale.z);
    }
    
    /**
     * Scales the matrix
     * 
     * @param scale - x, y, and z scale
     * 
     * @return this
     */
    public Matrix4f scale(Vector3f scale)
    {
    	return this;
    }
    
    /**
     * Get matrix translation vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
    public Matrix4f getTranslation(Vector3f dest)
    {
    	dest.set(elements[3 + 0 * 4], elements[3 + 1 * 4], elements[3 + 2 * 4]);
    	return this;
    }
    
    /**
     * Get matrix translation vector
     * 
     * @return translation
     * 
     * @see Vector3f
     */
    public Vector3f getTranslation()
    {
    	return new Vector3f(elements[3 + 0 * 4], elements[3 + 1 * 4], elements[3 + 2 * 4]);
    }
    
    /**
     * Get matrix forward vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
    public Matrix4f getForward(Vector3f dest)
    {
    	dest.set(elements[2 + 0 * 4], elements[2 + 1 * 4], elements[2 + 2 * 4]);
    	return this;
    }
    
    /**
     * Get matrix forward vector
     * 
     * @return forward
     * 
     * @see Vector3f
     */
    public Vector3f getForward()
    {
    	return new Vector3f(elements[2 + 0 * 4], elements[2 + 1 * 4], elements[2 + 2 * 4]);
    }
    
    /**
     * Get matrix up vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
    public Matrix4f getUp(Vector3f dest)
    {
    	dest.set(elements[1 + 0 * 4], elements[1 + 1 * 4], elements[1 + 2 * 4]);
    	return this;
    }
    
    /**
     * Get matrix up vector
     * 
     * @return up
     * 
     * @see Vector3f
     */
    public Vector3f getUp()
    {
    	return new Vector3f(elements[1 + 0 * 4], elements[1 + 1 * 4], elements[1 + 2 * 4]);
    }
    
    /**
     * Get matrix right vector
     * 
     * @param dest - result vector
     * 
     * @return this
     * 
     * @see Vector3f
     */
    public Matrix4f getRight(Vector3f dest)
    {
    	dest.set(elements[0 + 0 * 4], elements[0 + 1 * 4], elements[0 + 2 * 4]);
    	return this;
    }
    
    /**
     * Get matrix right vector
     * 
     * @return right
     * 
     * @see Vector3f
     */
    public Vector3f getRight()
    {
    	return new Vector3f(elements[0 + 0 * 4], elements[0 + 1 * 4], elements[0 + 2 * 4]);
    }
    
    /**
     * Matrix determinant
     * 
     * @return determinant
     */
 	public float determinant() {
 		return    (elements[0 + 0 * 4] * elements[1 + 1 * 4] - elements[0 + 1 * 4] * elements[1 + 0 * 4])
 				* (elements[2 + 2 * 4] * elements[3 + 3 * 4] - elements[2 + 3 * 4] * elements[3 + 2 * 4])
 				+ (elements[0 + 2 * 4] * elements[1 + 0 * 4] - elements[0 + 0 * 4] * elements[1 + 2 * 4])
 				* (elements[2 + 1 * 4] * elements[3 + 3 * 4] - elements[2 + 3 * 4] * elements[3 + 1 * 4])
 				+ (elements[0 + 0 * 4] * elements[1 + 3 * 4] - elements[0 + 3 * 4] * elements[1 + 0 * 4])
 				* (elements[2 + 1 * 4] * elements[3 + 2 * 4] - elements[2 + 2 * 4] * elements[3 + 1 * 4])
 				+ (elements[0 + 1 * 4] * elements[1 + 2 * 4] - elements[0 + 2 * 4] * elements[1 + 1 * 4])
 				* (elements[2 + 0 * 4] * elements[3 + 3 * 4] - elements[2 + 3 * 4] * elements[3 + 0 * 4])
 				+ (elements[0 + 3 * 4] * elements[1 + 1 * 4] - elements[0 + 1 * 4] * elements[1 + 3 * 4])
 				* (elements[2 + 0 * 4] * elements[3 + 2 * 4] - elements[2 + 2 * 4] * elements[3 + 0 * 4])
 				+ (elements[0 + 2 * 4] * elements[1 + 3 * 4] - elements[0 + 3 * 4] * elements[1 + 2 * 4])
 				* (elements[2 + 0 * 4] * elements[3 + 1 * 4] - elements[2 + 1 * 4] * elements[3 + 0 * 4]);
 	}

 	/**
 	 * Inverse matrix
 	 * 
 	 * @param dest - result matrix
 	 * 
 	 * @return this;
 	 */
 	public Matrix4f invert(Matrix4f dest)
	{
		float a = elements[0 + 0 * 4] * elements[1 + 1 * 4] - elements[1 + 0 * 4] * elements[0 + 1 * 4];
		float b = elements[0 + 0 * 4] * elements[2 + 1 * 4] - elements[2 + 0 * 4] * elements[0 + 1 * 4];
		float c = elements[0 + 0 * 4] * elements[3 + 1 * 4] - elements[3 + 0 * 4] * elements[0 + 1 * 4];
		float d = elements[1 + 0 * 4] * elements[2 + 1 * 4] - elements[2 + 0 * 4] * elements[1 + 1 * 4];
		float e = elements[1 + 0 * 4] * elements[3 + 1 * 4] - elements[3 + 0 * 4] * elements[1 + 1 * 4];
		float f = elements[2 + 0 * 4] * elements[3 + 1 * 4] - elements[3 + 0 * 4] * elements[2 + 1 * 4];
		float g = elements[0 + 2 * 4] * elements[1 + 3 * 4] - elements[1 + 2 * 4] * elements[0 + 3 * 4];
		float h = elements[0 + 2 * 4] * elements[2 + 3 * 4] - elements[2 + 2 * 4] * elements[0 + 3 * 4];
		float i = elements[0 + 2 * 4] * elements[3 + 3 * 4] - elements[3 + 2 * 4] * elements[0 + 3 * 4];
		float j = elements[1 + 2 * 4] * elements[2 + 3 * 4] - elements[2 + 2 * 4] * elements[1 + 3 * 4];
		float k = elements[1 + 2 * 4] * elements[3 + 3 * 4] - elements[3 + 2 * 4] * elements[1 + 3 * 4];
		float l = elements[2 + 2 * 4] * elements[3 + 3 * 4] - elements[3 + 2 * 4] * elements[2 + 3 * 4];
		
		float det = 1.0f / (a * l - b * k + c * j + d * i - e * h + f * g);
		
		float m00 = ( elements[1 + 1 * 4] * l - elements[2 + 1 * 4] * k + elements[3 + 1 * 4] * j) * det;
		float m10 = (-elements[1 + 0 * 4] * l + elements[2 + 0 * 4] * k - elements[3 + 0 * 4] * j) * det;
		float m20 = ( elements[1 + 3 * 4] * f - elements[2 + 3 * 4] * e + elements[3 + 3 * 4] * d) * det;
		float m30 = (-elements[1 + 2 * 4] * f + elements[2 + 2 * 4] * e - elements[3 + 2 * 4] * d) * det;
		float m01 = (-elements[0 + 1 * 4] * l + elements[2 + 1 * 4] * i - elements[3 + 1 * 4] * h) * det;
		float m11 = ( elements[0 + 0 * 4] * l - elements[2 + 0 * 4] * i + elements[3 + 0 * 4] * h) * det;
		float m21 = (-elements[0 + 3 * 4] * f + elements[2 + 3 * 4] * c - elements[3 + 3 * 4] * b) * det;
		float m31 = ( elements[0 + 2 * 4] * f - elements[2 + 2 * 4] * c + elements[3 + 2 * 4] * b) * det;
		float m02 = ( elements[0 + 1 * 4] * k - elements[1 + 1 * 4] * i + elements[3 + 1 * 4] * g) * det;
		float m12 = (-elements[0 + 0 * 4] * k + elements[1 + 0 * 4] * i - elements[3 + 0 * 4] * g) * det;
		float m22 = ( elements[0 + 3 * 4] * e - elements[1 + 3 * 4] * c + elements[3 + 3 * 4] * a) * det;
		float m32 = (-elements[0 + 2 * 4] * e + elements[1 + 2 * 4] * c - elements[3 + 2 * 4] * a) * det;
		float m03 = (-elements[0 + 1 * 4] * j + elements[1 + 1 * 4] * h - elements[2 + 1 * 4] * g) * det;
		float m13 = ( elements[0 + 0 * 4] * j - elements[1 + 0 * 4] * h + elements[2 + 0 * 4] * g) * det;
		float m23 = (-elements[0 + 3 * 4] * d + elements[1 + 3 * 4] * b - elements[2 + 3 * 4] * a) * det;
		float m33 = ( elements[0 + 2 * 4] * d - elements[1 + 2 * 4] * b + elements[2 + 2 * 4] * a) * det;
		
		dest.elements[0 + 0 * 0] = m00;
		dest.elements[1 + 0 * 4] = m10;
		dest.elements[2 + 0 * 4] = m20;
		dest.elements[3 + 0 * 4] = m30;
		dest.elements[0 + 1 * 4] = m01;
		dest.elements[1 + 1 * 4] = m11;
		dest.elements[2 + 1 * 4] = m21;
		dest.elements[3 + 1 * 4] = m31;
		dest.elements[0 + 2 * 4] = m02;
		dest.elements[1 + 2 * 4] = m12;
		dest.elements[2 + 2 * 4] = m22;
		dest.elements[3 + 2 * 4] = m32;
		dest.elements[0 + 3 * 4] = m03;
		dest.elements[1 + 3 * 4] = m13;
		dest.elements[2 + 3 * 4] = m23;
		dest.elements[3 + 3 * 4] = m33;

		return this;
	}
 	
 	/**
 	 * Inverse matrix
 	 * 
 	 * @return this;
 	 */
 	public Matrix4f invert()
 	{
 		return invert(this);
 	}
 	
	/**
	 * Transpose the matrix
	 * 
	 * @param dest - result matrix
	 * 
	 * @return this
	 */
	public Matrix4f transpose(Matrix4f dest)
	{
		float m00 = elements[0 + 0 * 4];
        float m10 = elements[0 + 1 * 4];
        float m20 = elements[0 + 2 * 4];
        float m30 = elements[0 + 3 * 4];
        float m01 = elements[1 + 0 * 4];
        float m11 = elements[1 + 1 * 4];
        float m21 = elements[1 + 2 * 4];
        float m31 = elements[1 + 3 * 4];
        float m02 = elements[2 + 0 * 4];
        float m12 = elements[2 + 1 * 4];
        float m22 = elements[2 + 2 * 4];
        float m32 = elements[2 + 3 * 4];
        float m03 = elements[3 + 0 * 4];
        float m13 = elements[3 + 1 * 4];
        float m23 = elements[3 + 2 * 4];
        float m33 = elements[3 + 3 * 4];
        dest.elements[0 + 0 * 4] = (m00);
        dest.elements[1 + 0 * 4] = (m10);
        dest.elements[2 + 0 * 4] = (m20);
        dest.elements[3 + 0 * 4] = (m30);
        dest.elements[0 + 1 * 4] = (m01);
        dest.elements[1 + 1 * 4] = (m11);
        dest.elements[2 + 1 * 4] = (m21);
        dest.elements[3 + 1 * 4] = (m31);
        dest.elements[0 + 2 * 4] = (m02);
        dest.elements[1 + 2 * 4] = (m12);
        dest.elements[2 + 2 * 4] = (m22);
        dest.elements[3 + 2 * 4] = (m32);
        dest.elements[0 + 3 * 4] = (m03);
        dest.elements[1 + 3 * 4] = (m13);
        dest.elements[2 + 3 * 4] = (m23);
        dest.elements[3 + 3 * 4] = (m33);
        
        return this;
	}
	
	/**
	 * Transpose the matrix
	 * 
	 * @return this
	 */
	public Matrix4f transpose()
	{
		return transpose(this);
	}
	
	 /**
     * Set matrix as Identity matrix
     * [This will clear all previous values]
     * 
     * @return this
     */
	public Matrix4f clear()
	{
		for(int i = 0; i < 16; i++)
			elements[i] = 0;
		
		elements[0 + 0 * 4] = 1.0f;
    	elements[1 + 1 * 4] = 1.0f;
    	elements[2 + 2 * 4] = 1.0f;
    	elements[3 + 3 * 4] = 1.0f;
    	
		return this;
	}
	
	/**
	 * The size in bytes
	 * 
	 * @return size
	 */
	public int sizeInBytes()
	{
		return Float.SIZE * 16;
	}

	/**
	 * Store elements in a FloatBuffer
	 * 
	 * @param flip - automatically flip the buffer
	 * 
	 * @return this
	 * 
	 * @see FloatBuffer
	 */
	public Matrix4f putFloatBuffer(FloatBuffer buffer, boolean flip)
	{
		buffer.put(elements);
		if(flip) buffer.flip();
		return this;
	}
	
	/**
	 * Store elements in a FloatBuffer
	 * 
	 * @return this
	 * 
	 * @see FloatBuffer
	 */
	public Matrix4f putFloatBuffer(FloatBuffer buffer)
	{
		return putFloatBuffer(buffer, false);
	}
	
	/**
	 * Return new FloatBuffer of elements
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
		buffer.put(elements);
		if(flip) buffer.flip();
		return buffer;
	}
	
	/**
	 * Return new FloatBuffer of elements
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
		StringBuilder builder = new StringBuilder();
		
		for(int y = 0; y < 4; y++)
		{
			builder.append("[" + elements[0 + y * 4] + ", " + elements[1 + y * 4]
					+ ", " + elements[2 + y * 4] + ", " + elements[3 + y * 4] + "]");
			if(y != 3) builder.append("\n");
		}

		return builder.toString();
	}
	
}
