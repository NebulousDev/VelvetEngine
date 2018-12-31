package math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f
{
	public static final int SIZE 	= 16;
	public static final int BYTES 	= 16 * Float.BYTES;
	
	float[] elements;
	
	public Matrix4f()
	{
		elements = new float[16];
	}
	
	public Matrix4f(float[] elements)
	{
		this.elements = elements;
	}
	
	public Matrix4f copy()
	{
		return new Matrix4f(elements);
	}
	
	public Matrix4f mul(float scalar)
	{
		for(int i = 0; i < 16; i++)
			elements[i] *= scalar;
		return this;
	}
	
	public Matrix4f mul(Matrix4f other)
	{
		float data[] = new float[SIZE];
		
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
	
	// From JOML math library
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
	
	// From LWJGL math library
	public float determinant2()
	{
		float result =
				  elements[0 + 0 * 4] * 
			    ((elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] 
			    + elements[1 + 2 * 4] * elements[2 + 3 * 4] * elements[3 + 1 * 4] 
			    + elements[1 + 3 * 4] * elements[2 + 1 * 4] * elements[3 + 2 * 4])
				- elements[1 + 3 * 4] * elements[2 + 2 * 4] * elements[3 + 1 * 4]
				- elements[1 + 1 * 4] * elements[2 + 3 * 4] * elements[3 + 2 * 4]
				- elements[1 + 2 * 4] * elements[2 + 1 * 4] * elements[3 + 3 * 4]);
		
		result -= elements[0 + 1 * 4] * 
				((elements[1 + 0 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4]
				+ elements[1 + 2 * 4] * elements[2 + 3 * 4] * elements[3 + 0 * 4]
				+ elements[1 + 3 * 4] * elements[2 + 0 * 4] * elements[3 + 2 * 4])
				- elements[1 + 3 * 4] * elements[2 + 2 * 4] * elements[3 + 0 * 4]
				- elements[1 + 0 * 4] * elements[2 + 3 * 4] * elements[3 + 2 * 4]
				- elements[1 + 2 * 4] * elements[2 + 0 * 4] * elements[3 + 3 * 4]);
		
		result += elements[0 + 2 * 4] * 
				((elements[1 + 0 * 4] * elements[2 + 1 * 4] * elements[3 + 3 * 4]
				+ elements[1 + 1 * 4] * elements[2 + 3 * 4] * elements[3 + 0 * 4]
				+ elements[1 + 3 * 4] * elements[2 + 0 * 4] * elements[3 + 1 * 4])
				- elements[1 + 3 * 4] * elements[2 + 1 * 4] * elements[3 + 0 * 4]
				- elements[1 + 0 * 4] * elements[2 + 3 * 4] * elements[3 + 1 * 4]
				- elements[1 + 1 * 4] * elements[2 + 0 * 4] * elements[3 + 3 * 4]);
		
		result -= elements[0 + 3 * 4] * 
				((elements[1 + 0 * 4] * elements[2 + 1 * 4] * elements[3 + 2 * 4]
				+ elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 0 * 4]
				+ elements[1 + 2 * 4] * elements[2 + 0 * 4] * elements[3 + 1 * 4])
				- elements[1 + 2 * 4] * elements[2 + 1 * 4] * elements[3 + 0 * 4]
				- elements[1 + 0 * 4] * elements[2 + 2 * 4] * elements[3 + 1 * 4]
				- elements[1 + 1 * 4] * elements[2 + 0 * 4] * elements[3 + 2 * 4]);
		
		return result;
	}
	
	// From JOML math library
	public Matrix4f inverse()
	{
		Matrix4f m = new Matrix4f();

		float a = elements[0 + 0 * 4] * elements[1 + 1 * 4] - elements[0 + 1 * 4] * elements[1 + 0 * 4];
		float b = elements[0 + 0 * 4] * elements[1 + 2 * 4] - elements[0 + 2 * 4] * elements[1 + 0 * 4];
		float c = elements[0 + 0 * 4] * elements[1 + 3 * 4] - elements[0 + 3 * 4] * elements[1 + 0 * 4];
		float d = elements[0 + 1 * 4] * elements[1 + 2 * 4] - elements[0 + 2 * 4] * elements[1 + 1 * 4];
		float e = elements[0 + 1 * 4] * elements[1 + 3 * 4] - elements[0 + 3 * 4] * elements[1 + 1 * 4];
		float f = elements[0 + 2 * 4] * elements[1 + 3 * 4] - elements[0 + 3 * 4] * elements[1 + 2 * 4];
		float g = elements[2 + 0 * 4] * elements[3 + 1 * 4] - elements[2 + 1 * 4] * elements[3 + 0 * 4];
		float h = elements[2 + 0 * 4] * elements[3 + 2 * 4] - elements[2 + 2 * 4] * elements[3 + 0 * 4];
		float i = elements[2 + 0 * 4] * elements[3 + 3 * 4] - elements[2 + 3 * 4] * elements[3 + 0 * 4];
		float j = elements[2 + 1 * 4] * elements[3 + 2 * 4] - elements[2 + 2 * 4] * elements[3 + 1 * 4];
		float k = elements[2 + 1 * 4] * elements[3 + 3 * 4] - elements[2 + 3 * 4] * elements[3 + 1 * 4];
		float l = elements[2 + 2 * 4] * elements[3 + 3 * 4] - elements[2 + 3 * 4] * elements[3 + 2 * 4];
		
		float det = 1.0f / (a * l - b * k + c * j + d * i - e * h + f * g);
		
		float nm00 = (elements[1 + 1 * 4] * l - elements[1 + 2 * 4] * k + elements[1 + 3 * 4] * j) * det;
		float nm01 = (-elements[0 + 1 * 4] * l + elements[0 + 2 * 4] * k - elements[0 + 3 * 4] * j) * det;
		float nm02 = (elements[3 + 1 * 4] * f - elements[3 + 2 * 4] * e + elements[3 + 3 * 4] * d) * det;
		float nm03 = (-elements[2 + 1 * 4] * f + elements[2 + 2 * 4] * e - elements[2 + 3 * 4] * d) * det;
		float nm10 = (-elements[1 + 0 * 4] * l + elements[1 + 2 * 4] * i - elements[1 + 3 * 4] * h) * det;
		float nm11 = (elements[0 + 0 * 4] * l - elements[0 + 2 * 4] * i + elements[0 + 3 * 4] * h) * det;
		float nm12 = (-elements[3 + 0 * 4] * f + elements[3 + 2 * 4] * c - elements[3 + 3 * 4] * b) * det;
		float nm13 = (elements[2 + 0 * 4] * f - elements[2 + 2 * 4] * c + elements[2 + 3 * 4] * b) * det;
		float nm20 = (elements[1 + 0 * 4] * k - elements[1 + 1 * 4] * i + elements[1 + 3 * 4] * g) * det;
		float nm21 = (-elements[0 + 0 * 4] * k + elements[0 + 1 * 4] * i - elements[0 + 3 * 4] * g) * det;
		float nm22 = (elements[3 + 0 * 4] * e - elements[3 + 1 * 4] * c + elements[3 + 3 * 4] * a) * det;
		float nm23 = (-elements[2 + 0 * 4] * e + elements[2 + 1 * 4] * c - elements[2 + 3 * 4] * a) * det;
		float nm30 = (-elements[1 + 0 * 4] * j + elements[1 + 1 * 4] * h - elements[1 + 2 * 4] * g) * det;
		float nm31 = (elements[0 + 0 * 4] * j - elements[0 + 1 * 4] * h + elements[0 + 2 * 4] * g) * det;
		float nm32 = (-elements[3 + 0 * 4] * d + elements[3 + 1 * 4] * b - elements[3 + 2 * 4] * a) * det;
		float nm33 = (elements[2 + 0 * 4] * d - elements[2 + 1 * 4] * b + elements[2 + 2 * 4] * a) * det;
		
		m.elements[0 + 0 * 0] = nm00;
		m.elements[0 + 1 * 4] = nm01;
		m.elements[0 + 2 * 4] = nm02;
		m.elements[0 + 3 * 4] = nm03;
		m.elements[1 + 0 * 4] = nm10;
		m.elements[1 + 1 * 4] = nm11;
		m.elements[1 + 2 * 4] = nm12;
		m.elements[1 + 3 * 4] = nm13;
		m.elements[2 + 0 * 4] = nm20;
		m.elements[2 + 1 * 4] = nm21;
		m.elements[2 + 2 * 4] = nm22;
		m.elements[2 + 3 * 4] = nm23;
		m.elements[3 + 0 * 4] = nm30;
		m.elements[3 + 1 * 4] = nm31;
		m.elements[3 + 2 * 4] = nm32;
		m.elements[3 + 3 * 4] = nm33;

		return m;
	}
	
	public void set(float[] elements)
	{
		this.elements = elements;
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
	
	public static Matrix4f Translation(Vector3f vec3)
	{
		float[] elements = 
		{
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			vec3.x, vec3.y, vec3.z, 1.0f
		};
		
		return new Matrix4f(elements);
	}
	
	public static Matrix4f LookAt(Vector3f eye, Vector3f point, Vector3f up)
	{
		Vector3f dir = new Vector3f(eye.x - point.x, eye.y - point.y, eye.z - point.z).normalize();
		Vector3f nleft = up.cross(dir).normalize();
		Vector3f nup = dir.cross(nleft);
		
		Matrix4f result = new Matrix4f();
		
		result.elements[0 + 0 * 4] = nleft.x;
		result.elements[1 + 0 * 4] = nup.x;
		result.elements[2 + 0 * 4] = dir.x;
		result.elements[3 + 0 * 4] = 0.0f;
		result.elements[0 + 1 * 4] = nleft.y;
		result.elements[1 + 1 * 4] = nup.y;
		result.elements[2 + 1 * 4] = dir.y;
		result.elements[3 + 1 * 4] = 0.0f;
		result.elements[0 + 2 * 4] = nleft.z;
		result.elements[1 + 2 * 4] = nup.z;
		result.elements[2 + 2 * 4] = dir.z;
		result.elements[3 + 2 * 4] = 0.0f;
		result.elements[0 + 3 * 4] = -(nleft.x * eye.x + nleft.y * eye.y + nleft.z * eye.z);
		result.elements[1 + 3 * 4] = -(nup.x * eye.x + nup.y * eye.y + nup.z * eye.z);
		result.elements[2 + 3 * 4] = -(dir.x * eye.x + dir.y * eye.y + dir.z * eye.z);
		result.elements[3 + 3 * 4] = 1.0f;
		
		return result;
	}
	
	public Matrix4f translate(Vector3f position)
	{
		elements[0 + 3 * 4] = elements[0 + 3 * 4] + position.x;
		elements[1 + 3 * 4] = elements[1 + 3 * 4] + position.y;
		elements[2 + 3 * 4] = elements[2 + 3 * 4] + position.z;
		return this;
	}
	
	public Matrix4f translate(Vector3f position, Matrix4f mat4f)
	{
		mat4f.elements[0 + 3 * 4] = mat4f.elements[0 + 3 * 4] + position.x;
		mat4f.elements[1 + 3 * 4] = mat4f.elements[1 + 3 * 4] + position.y;
		mat4f.elements[2 + 3 * 4] = mat4f.elements[2 + 3 * 4] + position.z;
		return this;
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
	
	public static Matrix4f Scale(Vector3f vec3)
	{
		float[] elements = 
		{
			vec3.x, 0.0f,   0.0f,   0.0f,
			0.0f,   vec3.y, 0.0f,   0.0f,
			0.0f,   0.0f,   vec3.z, 0.0f,
			0.0f,   0.0f,   0.0f,   1.0f
		};
		
		return new Matrix4f(elements);
	}
	
	public Matrix4f scale(Vector3f scale)
	{
		elements[0 + 0 * 4] = elements[0 + 0 * 4] * scale.x;
		elements[1 + 1 * 4] = elements[1 + 1 * 4] * scale.y;
		elements[2 + 2 * 4] = elements[2 + 2 * 4] * scale.z;
		return this;
	}
	
	public static Matrix4f Orientation(Vector3f forward, Vector3f right, Vector3f up) {
		
		float[] elements =
		{
			right.x, up.x, -forward.x, 0.0f,
			right.y, up.y, -forward.y, 0.0f,
			right.z, up.z, -forward.z, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
		};
			
		return new Matrix4f(elements);
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
	
	@Override
	public String toString()
	{
		String result = "";
		for(int y = 0; y < 4; y++)
		{
			for(int x = 0; x < 4; x++)
				result += elements[x + y * 4] + " ";
			result += "\n";
		}
		
		return result;
	}
	
}
