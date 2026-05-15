package exception;

public class DuplicateIncidentException extends Exception 
{
	public DuplicateIncidentException()
	{
		super("Duplicate Incident ID");
	}
}