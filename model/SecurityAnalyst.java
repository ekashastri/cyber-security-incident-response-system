package model;

public class SecurityAnalyst extends User 
{
	private String level;

	public SecurityAnalyst(String id, String name, String password, String level) 
	{
		super(id, name, password, "ANALYST");
		this.level = level;
	}

	public String getLevel() 
	{
		return level; 
	}
}