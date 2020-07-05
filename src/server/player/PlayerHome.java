package server.player;

public class PlayerHome { //Structure data
	public int id;
	public String name;
	public double x;
	public double y;
	public double z;
	public String world;
	
	public  PlayerHome(int id ,String name, double x, double y,double z,String world) //Constructor Class
	{
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	

	public int getID()//Return ID
	{
		return this.id;
	}
	
	public String getName() //Return Name
	{
		return this.name;
	}
	
	public double getX() //Return X
	{
		return this.x;
	}
	
	public double getY() //Return Y
	{
		return this.y;
	}
	
	public double getZ() //Return Z
	{
		return this.z;
	}
	
	public String getWorld() //Return World
	{
		return this.world;
	}

	public void setID(int id)
	{
		this.id = id;
	}
	
	public void setName(String name) //Set name
	{
		this.name = name;
	}
	
	public void setX(double x) //Set X
	{
		this.x = x;
	}
	
	public void setY(double Y) //Set Y
	{
		this.y = Y;
	}
	
	public void setZ(double z) //Set Z
	{
		this.z = z;
	}
	
	public void setWorld(String world) //Set World
	{
		this.world = world;
	}
}
