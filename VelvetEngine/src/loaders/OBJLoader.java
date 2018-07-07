package loaders;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import loaders.OBJFormat.OBJMaterial;
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
    
    private static class OBJGroup
    {
    	public String name;
    	public OBJMaterial material;
    	public ArrayList<OBJIndexGroup> indexGroupList;
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
		ArrayList<OBJGroup>			groupList		= new ArrayList<>();
		
		OBJGroup activeGroup = null;
		
		String[] lines = data.split("\r\n|\r|\n");
		
		for(String line : lines)
		{
			String tokens[] = tokenize(line);
			
			if(tokens.length < 2) continue;
			
			try
			{
				switch(tokens[0])
				{
					case OBJ_COMMENT: 
					{
						break;
					}
					
					case OBJ_VERTEX:
					{
						if(tokens.length < 4)
						{
							System.out.println("Error parsing OBJ vertex. Too many elements. File: " + filepath);
							return null;
						}
						
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						float z = Float.parseFloat(tokens[3]);
						
						positionList.add(new Vector3f(x, y, z));
						
						break;
					}
					
					case OBJ_TEXTURE:
					{
						if(tokens.length < 3)
						{
							System.out.println("Error parsing OBJ texture coord. Too many elements. File: " + filepath);
							return null;
						}
						
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						
						texCoordList.add(new Vector2f(x, y));
						
						break;
					}
					
					case OBJ_NORMAL:
					{
						if(tokens.length < 4)
						{
							System.out.println("Error parsing OBJ normal. Too many elements. File: " + filepath);
							return null;
						}
						
						float x = Float.parseFloat(tokens[1]);
						float y = Float.parseFloat(tokens[2]);
						float z = Float.parseFloat(tokens[3]);
						
						normalList.add(new Vector3f(x, y, z));
						
						break;
					}
					
					case OBJ_FACE:
					{
						if(tokens.length < 4)
						{
							System.out.println("Error parsing OBJ face. Too few vertices. File: " + filepath);
							return null;
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
									
									if(activeGroup == null)
									{
										activeGroup = new OBJGroup();
										activeGroup.name = model.name;
										activeGroup.material = null;
										activeGroup.indexGroupList = new ArrayList<>();
									}
									
									activeGroup.indexGroupList.add(index);
									
								}
								
								catch(NumberFormatException e)
								{
									System.out.println("Error parsing OBJ. Incorrect number format. File: " + filepath);
									return null;
								}
							}
						}
						
						break;
					}
					
					case OBJ_OBJECT:
					case OBJ_GROUP:
					{
						if(activeGroup != null)
						{
							groupList.add(activeGroup);
						}
						
						activeGroup = new OBJGroup();
						activeGroup.name = tokens[1];
						activeGroup.indexGroupList = new ArrayList<>();
						
						break;
					}
					
					default:
					{
						break;
					}
				}
			}
			
			catch(NumberFormatException e)
			{
				System.out.println("Error parsing OBJ. Incorrect number format. File: " + filepath);
				return null;
			}
		}
		
		groupList.add(activeGroup);
		
		ArrayList<OBJMesh> 			meshList 		= new ArrayList<>();
		HashMap<String, Integer> 	vertexMap 		= new HashMap<>();
		ArrayList<OBJVertex> 		vertexList 		= new ArrayList<>();
		ArrayList<Integer> 			indexList 		= new ArrayList<>();
		
		int currentIndex = 0;
		int currentCount = 0;
		
		for(OBJGroup group : groupList)
		{
			OBJMesh mesh = new OBJMesh();
			mesh.name = group.name;
			mesh.idxOffset = currentCount;
			mesh.hasPositions = true;
			mesh.hasTexCoords = true;
			mesh.hasNormals = true;
			
			for(OBJIndexGroup index : group.indexGroupList)
			{
				String hash = createIndexHash(index);
				
				if(vertexMap.containsKey(hash))
					indexList.add(vertexMap.get(hash));
				
				else
				{
					OBJVertex vertex = new OBJVertex();
					
					if(index.positionIndex != NO_INDEX)
						vertex.position = positionList.get(index.positionIndex);
					
					else
					{
						vertex.position = new Vector3f(0);
						mesh.hasPositions = false;
					}
					
					if(index.texCoordIndex != NO_INDEX)
						vertex.texCoord = texCoordList.get(index.texCoordIndex);
					
					else
					{
						vertex.texCoord = new Vector2f(0);
						mesh.hasTexCoords = false;
					}
					
					if(index.normalIndex != NO_INDEX)
						vertex.normal = normalList.get(index.normalIndex);
					
					else
					{
						vertex.normal = new Vector3f(0);
						mesh.hasNormals = false;
					}
					
					vertexList.add(vertex);
					indexList.add(currentIndex);
					vertexMap.put(hash, currentIndex++);
					
				}
				
				currentCount++;
			}
			
			mesh.idxCount = currentCount;
			meshList.add(mesh);
		}
		
		OBJVertex[] finalVertices = new OBJVertex[vertexList.size()];
		vertexList.toArray(finalVertices);
		
		model.vertices = finalVertices;
		model.indices = indexList.stream().mapToInt(i->i).toArray();
		
		if(meshList.isEmpty())
		{
			OBJMesh activeMesh = new OBJMesh();
			activeMesh.name = model.name;
			activeMesh.idxOffset = 0;
			activeMesh.idxCount = model.indices.length;
		}
		
		else
		{
			OBJMesh[] finalMeshes = new OBJMesh[meshList.size()];
			meshList.toArray(finalMeshes);
			model.meshes = finalMeshes;
		}
		
		return model;
	}
	
	private static String[] tokenize(String line)
	{
		return line.trim().toLowerCase().split("\\s+");
	}
	
	private static String createIndexHash(OBJIndexGroup index)
	{
		return "" + index.positionIndex + index.texCoordIndex + index.normalIndex;
	}
	
}
