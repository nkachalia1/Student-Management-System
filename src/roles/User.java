package roles;

public abstract class User {
	
	protected String ID;
	
	protected String name;
	
	protected String username;
	
	protected String password;
	
	//constructor
	public User(String ID, String name, String username, String password) {
		this.ID = ID;
		this.name = name;
		this.username = username;
		this.password = password;
	}
	
	//method to check login credentials
	public boolean login(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}
	
}