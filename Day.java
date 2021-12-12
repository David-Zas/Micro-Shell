public class Day{
    
    //ATTRIBUTES
    private int day_num;
    private int numCardsOnBoard;

    //CONSTRUCTORS
    public Day(){}
    public Day(int day_num, int numCardsOnBoard){
        this.day_num = day_num;
        this.numCardsOnBoard = numCardsOnBoard;
    }

    //SETTERS
    public void setDay(int day_num){
        this.day_num = day_num;
    }

    public void setNumCardsOnBoard(int cardsOnBoard){
        this.numCardsOnBoard = numCardsOnBoard;
    }

    //GETTERS
    public int getDayNum(){
        return this.day_num;
    }

    public int getNumCardsOnBoard(){
        return this.numCardsOnBoard;

    }
   
    //METHODS
    /*
    checkDay(): DONE
        returns number of cards left on the board
    */
    public int checkDay(int numCardsOnBoard){
        int numCardsLeft = this.numCardsOnBoard;
        return numCardsLeft; 
    }
}