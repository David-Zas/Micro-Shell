import java.util.ArrayList;

import javax.swing.JLabel;
 
public class Room{
    //ATTRIBUTES
    private String room_name;
    private int numShotCounters;
    private int maxShotCounters;
    private Card curCard;
    private int numPlayersInRoom;
    private ArrayList<String> adjRooms;
    private ArrayList<Role> roles;
    private ArrayList<cardPlacement> cardPlacement; // where to place the cards on board
    private ArrayList<shotPlacement> shotPlacement; // where to place shot counter on board
    private JLabel[] shotCounters;
    private JLabel[] cardLabel;
   
    //CONSTRUCTORS
    public Room(){}
    public Room(String room_name, int numShotCounters, Card curCard, int numPlayersInRoom, ArrayList<String> adjRooms, ArrayList<Role> roles, 
        ArrayList<cardPlacement> cardPlacement, ArrayList<shotPlacement> shotPlacement, JLabel[] shotCounters, JLabel[] cardLabel){
        this.room_name = room_name;
        this.numShotCounters = numShotCounters;
        this.curCard = curCard;
        this.numPlayersInRoom = numPlayersInRoom;
        this.adjRooms = adjRooms;
        this.roles = roles;
        this.cardPlacement = cardPlacement;
        this.shotPlacement = shotPlacement;
        this.shotCounters = shotCounters;
        this.cardLabel = cardLabel;
    }
 
    //SETTERS
    public void setRoomName(String room_name){
        this.room_name = room_name;
    }
 
    public void setNumShotCounters(int numShotCounters){
        this.numShotCounters = numShotCounters;
    }
 
    public void setCurCard(Card curCard){
        this.curCard = curCard;
    }
 
    public void setNumPlayersInRoom(int numPlayersInRoom){
        this.numPlayersInRoom = numPlayersInRoom;
    }
 
    public void setAdjRooms(ArrayList<String> adjRooms){
        this.adjRooms = adjRooms;
    }
 
    public void setMaxShotCounters(int maxShotCounters){
        this.maxShotCounters = maxShotCounters;
    }
    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
    public void setCardPlacement(ArrayList<cardPlacement> cardPlacement) {
        this.cardPlacement = cardPlacement;
    }
    public void setShotPlacement(ArrayList<shotPlacement> shotPlacement) {
        this.shotPlacement = shotPlacement;
    }
    public void setShotCounters(JLabel[] shotCounters){
        this.shotCounters = shotCounters;
    }
    public void setCardLabel(JLabel[] cardLabel){
        this.cardLabel = cardLabel;
    }
    //GETTERS
    public String getRoomName(){
        return this.room_name;
    }
 
    public int getNumShotCounters(){
        return this.numShotCounters;
    }
 
    public Card getCurCard(){
        return this.curCard;
    }
 
    public int getNumPlayersInRoom(){
        return this.numPlayersInRoom;
    }
 
    public ArrayList<String> getAdjRooms(){
        return this.adjRooms;
    }
 
    public int getMaxShotCounters(){
        return this.maxShotCounters;
    }
    public ArrayList<Role> getRoles(){
        return this.roles;
    }
   
    public ArrayList<cardPlacement> getCardPlacement() {
        return this.cardPlacement;
    }
    public ArrayList<shotPlacement> getShotPlacement(){
        return this.shotPlacement;
    }
    public JLabel[] getShotCounters(){
        return this.shotCounters;
    }
    public JLabel[] getCardLabel(){
        return this.cardLabel;
    } 
}