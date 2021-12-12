import javax.swing.*;
public class Player{
    //ATTRIBUTES
    private String color;
    private int rank;
    private int credits;
    private int dollars;
    private boolean inRole;
    private Room current_location;
    private int numPracticeChips; 
    private Role role; 
    private JLabel playerDie;
    private Icon playerIcon;
    

    //CONSTRUCTORS
    public Player(){}
    public Player(String color, int rank, int credits, int dollars, boolean inRole, Room current_location, int numPracticeChips,Role role, JLabel playerDie, Icon playerIcon){
        this.color = color;
        this.rank = rank;
        this.credits = credits;
        this.dollars = dollars;
        this.inRole = inRole;
        this.current_location = current_location;
        this.numPracticeChips = numPracticeChips;
        this.role = role;
        this.playerDie = playerDie;
        this.playerIcon = playerIcon;
    }

    //SETTERS
    public void setColor(String color){
        this.color = color;
    }
    
    public void setRank(int rank){
        this.rank = rank;
    }
   
    public void setCredits(int credits){
        this.credits = credits;
    }    
    
    public void setDollars(int dollars){
        this.dollars = dollars;
    }
   
    public void setInRole(boolean playerInRole){
        this.inRole = playerInRole;
    }        
    
    public void setLocation(Room current_location){
        this.current_location = current_location;
    }

    public void setNumPracticeChips(int numPracticeChips){
        this.numPracticeChips = numPracticeChips;
    }


    public void setRole(Role role){
        this.role = role;
    }

    public void setPlayerIcon(Icon playerIcon){
        this.playerIcon = playerIcon;
    }

    public void setPlayerDie(JLabel playerDie){
        this.playerDie = playerDie;
    }
    //GETTERS
    public String getColor(){
        return this.color;
    }

    public int getRank(){
        return this.rank;
    }

    public int getCredits(){
        return this.credits;
    }
    
    public int getDollars(){
        return this.dollars;
    }

    public boolean playerInRole(){
        return this.inRole;
    }

    public Room getCurrentLocation(){
        return this.current_location;
    }


    public int getNumPracticeChips(){
        return this.numPracticeChips;
    }
    
    public Role getRole(){
        return this.role;
    }

    public JLabel getPlayerDie(){
        return this.playerDie;
    }  
    
    public Icon getPlayerIcon(){
        return this.playerIcon;
    }
  //METHODS

    /* act(): DONE
     * act will determine if the dice rolled by user
     * is a successful roll and will return true,
     * else,
     * false
    */
    public boolean act(Player player, Act act, Card card){      
        
    	int roll = act.rollDice();
    	
    	// check if die is equal to or greater than the budget of the card
    	// return true if player rolled a successful die
    	if(roll+player.getNumPracticeChips() >= card.getBudget()) {
    		return true;
    	}
    	return false;
    
    }
   
    /*
     *  rehearse(): DONE
        user receives a practice chip and -numPractice chips on each roll
    	returns -1 on fail, and 1 on success
    */
    
    public int rehearse(Player player, int numPracticeChips, Card card){
        
    	// if the number of practice chips does not equal the budget of the card
    	// add a practice chip
    	
    	if(numPracticeChips != card.getBudget()) {
    		player.setNumPracticeChips(player.getNumPracticeChips() + 1);
    		return 1;
    	}
    	return -1;    
    }
}