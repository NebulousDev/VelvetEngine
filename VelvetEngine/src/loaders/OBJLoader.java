package loaders;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import loaders.OBJFormat.OBJMesh;
import loaders.OBJFormat.OBJModel;
import loaders.OBJFormat.OBJVertex;
import math.Vector2f;
import math.Vector3f;

@SuppressWarnings("unused")
public class OBJLoader
{
	private final static String OBJ_COMMENT = "#";
	private final static String OBJ_MTLLIB = "mtllib";
	private final static String OBJ_USEMTL = "usemtl";
	private final static String OBJ_VERTEX = "v";
	private final static String OBJ_TEXTURE = "vt";
    private final static String OBJ_NORMAL = "vn";
    private final static String OBJ_FACE = "f";
    private final static String OBJ_GROUP = "g";
    private final static String OBJ_OBJECT = "o";
    private final static String OBJ_SMOOTH = "s";
    
    private final static String MTL_NEWMTL = "newmtl";
    private final static String MTL_KA = "Ka";
    private final static String MTL_KD = "Kd";
    private final static String MTL_KS = "Ks";
    private final static String MTL_TF = "Tf";
    private final static String MTL_ILLUM = "illum";
    private final static String MTL_D = "d";
    private final static String MTL_NS = "Ns";
    private final static String MTL_NI = "Ni";
    private final static String MTL_MAP_KA = "map_Ka";
    private final static String MTL_MAP_KD = "map_Kd";
    private final static String MTL_MAP_KS = "map_Ks";
    private final static String MTL_MAP_NS = "map_Ns";
    private final static String MTL_MAP_D = "map_d";
    private final static String MTL_DISP = "disp";
    private final static String MTL_DECAL = "decal";
    private final static String MTL_BUMP = "bump";
    private final static String MTL_REFL = "refl";
	
    private static final int NO_INDEX = -2;
    
    private static class OBJIndexGroup
    {
    	public int positionIndex 	= NO_INDEX;
    	public int texCoordIndex 	= NO_INDEX;
    	public int normalIndex 		= NO_INDEX;
    }
    
	public static OBJModel readOBJ(String filepath)
	{
		String data = FileUtils.readFileAsString(filepath);
		
		if(data == null)
		{
			System.out.println("Error reading OBJ file: No such file exists: " + filepath);
			return null;
		}
		
		OBJModel model = new OBJModel();
		model.name = FileUtils.getFilename(filepath);
		
		ArrayList<Vector3f>			positionList 	= new ArrayList<>();
		ArrayList<Vector2f>			texCoordList	= new ArrayList<>();
		ArrayList<Vector3f>			normalList		= new ArrayList<>();
		ArrayList<OBJIndexGroup>	indexGroupList	= new ArrayList<>();
		
		String[] lines = data.split("\r\n|\r|\n");
		
		for(String line : lines)
			if(!parseLine(line, positionList, texCoordList, normalList, indexGroupList, filepath)) return null;
		
		HashMap<String, Integer> 	vertexMap 		= new HashMap<>();
		ArrayList<OBJVertex> 		vertexList 		= new ArrayList<>();
		ArrayList<Integer> 			indexList 		= new ArrayList<>();
		ArrayList<OBJMesh> 			meshList 		= new ArrayList<>();
		
		int currentIndex = 0;
		
		for(OBJIndexGroup index : indexGroupList)
		{
			String hash = createIndexHash(index);
			
			if(vertexMap.containsKey(hash))
				indexList.add(vertexMap.get(hash));

			else
			{
				OBJVertex vertex = new OBJVertex();
				
				if(index.positionIndex != NO_INDEX) vertex.position = positionList.get(index.positionIndex);
				else vertex.position = new Vector3f(0);
				
				if(index.texCoordIndex != NO_INDEX) vertex.texCoord = texCoordList.get(index.texCoordIndex);
				else vertex.texCoord = new Vector2f(0);
				
				if(index.normalIndex   != NO_INDEX) vertex.normal 	= normalList.get(index.normalIndex);
				else vertex.normal = new Vector3f(0);
					
				vertexList.add(vertex);
				indexList.add(currentIndex);
				vertexMap.put(hash, currentIndex++);
			}
		}
		
		OBJVertex[] finalVertices = new OBJVertex[vertexList.size()];
		vertexList.toArray(finalVertices);
		
		model.vertices = finalVertices;
		model.indices = indexList.stream().mapToInt(i->i).toArray();
		
		return model;
	}

	private static boolean parseLine(String line, ArrayList<Vector3f> positionList, ArrayList<Vector2f> texCoordList, 
			ArrayList<Vector3f> normalList, ArrayList<OBJIndexGroup> indexGroupList, String filepath)
	{
		String tokens[] = tokenize(line);
		
		if(tokens.length < 2) return true;
		
		try
		{
			switch(tokens[0])
			{
				case OBJ_COMMENT: 	return true;
				case OBJ_VERTEX: 	return parsePositions(tokens, positionList, filepath);
				case OBJ_TEXTURE: 	return parseTextureCoords(tokens, texCoordList, filepath);
				case OBJ_NORMAL: 	return parseNormals(tokens, normalList, filepath);
				case OBJ_FACE: 		return parseFace(tokens, indexGroupList, filepath);
				default:			return true;
			}
		}
		
		catch(NumberFormatException e)
		{
			System.out.println("Error parsing OBJ. Incorrect number format. File: " + filepath);
			return false;
		}
	}
	
	private static boolean parsePositions(String[] tokens, ArrayList<Vector3f> positions, String filepath)
	{
		if(tokens.length < 4)
		{
			System.out.println("Error parsing OBJ vertex. Too many elements. File: " + filepath);
			return false;
		}
		
		float x = Float.parseFloat(tokens[1]);
		float y = Float.parseFloat(tokens[2]);
		float z = Float.parseFloat(tokens[3]);
		
		positions.add(new Vector3f(x, y, z));
		
		return true;
	}
	
	private static boolean parseTextureCoords(String[] tokens, ArrayList<Vector2f> texCoords, String filepath)
	{
		if(tokens.length < 3)
		{
			System.out.println("Error parsing OBJ texture coord. Too many elements. File: " + filepath);
			return false;
		}
		
		float x = Float.parseFloat(tokens[1]);
		float y = Float.parseFloat(tokens[2]);
		
		texCoords.add(new Vector2f(x, y));
		
		return true;
	}
	
	private static boolean parseNormals(String[] tokens, ArrayList<Vector3f> normals, String filepath)
	{
		if(tokens.length < 4)
		{
			System.out.println("Error parsing OBJ normal. Too many elements. File: " + filepath);
			return false;
		}
		
		float x = Float.parseFloat(tokens[1]);
		float y = Float.parseFloat(tokens[2]);
		float z = Float.parseFloat(tokens[3]);
		
		normals.add(new Vector3f(x, y, z));
		
		return true;
	}
	
	private static boolean parseFace(String[] tokens, ArrayList<OBJIndexGroup> indexGroups, String filepath)
	{
		if(tokens.length < 4)
		{
			System.out.println("Error parsing OBJ face. Too few vertices. File: " + filepath);
			return false;
		}
		
		if(tokens.length > 4)
		{
			//triangulate
		}
		
		else
		{
			for(int i = 1; i < tokens.length; i++)
			{
				OBJIndexGroup index = new OBJIndexGroup();
				
				String[] elements = tokens[i].split("/");
				
				try
				{
					if(elements.length == 1)
					{
						// TODO: Set hasPositions flag
						index.positionIndex = Integer.parseInt(elements[0]) - 1;
					}
					
					else if (elements.length == 2)
					{
						// TODO: Set hasTexCoords flag
						index.positionIndex = Integer.parseInt(elements[0]) - 1;
						index.texCoordIndex = Integer.parseInt(elements[1]) - 1;
					}
					
					else if (elements.length == 3)
					{
						if(elements[1].equals(""))
						{
							// TODO: Set hasPositions flag
							// TODO: Set hasNormals flag
							index.positionIndex = Integer.parseInt(elements[0]) - 1;
							index.normalIndex 	= Integer.parseInt(elements[2]) - 1;
						}
						
						else
						{
							// TODO: Set all flags
							index.positionIndex = Integer.parseInt(elements[0]) - 1;
							index.texCoordIndex = Integer.parseInt(elements[1]) - 1;
							index.normalIndex 	= Integer.parseInt(elements[2]) - 1;
						}
					}
					
					indexGroups.add(index);
				}
				
				catch(NumberFormatException e)
				{
					System.out.println("Error parsing OBJ. Incorrect number format. File: " + filepath);
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static String[] tokenize(String line)
	{
		return line.trim().toLowerCase().split("\\s+");
	}
	
	private static String createIndexHash(OBJIndexGroup index)
	{
		return "" + index.positionIndex + index.texCoordIndex + index.normalIndex;
	}
	
	/*
	public static FloatBuffer vertexListToFloatBuffer(OBJVertex[] verts)
	{
		int bufferSize = 0;
		
		if(verts[0].position != null) bufferSize += 3;
		if(verts[0].texCoord != null) bufferSize += 2;
		if(verts[0].normal   != null) bufferSize += 3;
		
		FloatBuffer buffer = FloatBuffer.allocate(bufferSize * verts.length);
		
	}
	*/
}
