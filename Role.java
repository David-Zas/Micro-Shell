public class Role {

	//ATTRIBUTES
	private String roleDescription;
	private int rank;
	private String line;
	private Boolean onCard;
	private Boolean roleTaken = false;
	private roleArea rolePlacement;
	
	
	//CONSTRUCTORS
	public Role(){}
	public Role(String roleDescription, int rank, String line, Boolean onCard, roleArea rolePlacement) {
		this.roleDescription = roleDescription;
		this.rank = rank;
		this.line = line;
		this.onCard = onCard;
		this.rolePlacement = rolePlacement;
	}
	
	//SETTERS
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public void setOnCard(Boolean onCard) {
		this.onCard = onCard;
	}

	public void setRoleTaken(Boolean roleTaken){
		this.roleTaken = roleTaken;
	}
	
	public void setRolePlacement(roleArea rolePlacement){
		this.rolePlacement = rolePlacement;
	}
	//GETTERS
	public String getRoleDescription() {
		return roleDescription;
	}
	public int getRank() {
		return rank;
	}
	public String getLine() {
		return line;
	}
	public Boolean getOnCard() {
		return onCard;
	}

	public Boolean getRoleTaken(){
		return this.roleTaken;
	}

	public roleArea getRolePlacement(){
		return this.rolePlacement;
	}
	
}