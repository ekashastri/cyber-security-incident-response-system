package model;

public abstract class User 
{
	protected String id, name, password, role;

	public User(String id, String name, String password, String role) 
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public String getId() 
	{ 	
		return id; 
	}
	public String getName() 
	{ 
		return name; 
	}
	public String getPassword() 
	{ 
		return password; 
	}
	public String getRole() 
	{ 
		return role; 
	}
}