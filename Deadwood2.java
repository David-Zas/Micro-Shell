import java.io.*;
import java.util.*;
import javax.xml.*;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
public class Deadwood2{
/*
MAIN CONTROLLER
*/
    /*CONSTANTS*/
    public static Player[] players;
    public static Board board;
    public static Day curDay;
    public static Room[] rooms;
    public static Card[] cards;
    public static Card[] cardsOnBoard;
    public static Manager gManager;
    public static String action = "none";
    public static String roomToMoveTo = "none";
    public static String roleSelected = "none";
    public static String rankSelected = "none";
    public static String currencySelected = "none";
    public static int numCardsLeft;

    public static void main(String[] args) {
        cards = loadCards();
        cards = shuffleCards(cards);
        // load in first 10 cards
        Card[] cardsOnBoard = new Card[10];
        for (int i = 0; i < 10; i++) {
            cardsOnBoard[i] = cards[i];
        }
        // load in the rooms
        rooms = loadBoard();
        for (int i = 0; i < 10; i++) {
            rooms[i].setCurCard(cardsOnBoard[i]);
        }
        // VIEW: GAME START
        View view = new View();
        view.setVisible(true);
        int numPlayers = view.getNumPlayers(view);
        // init all objects
        int numDays = setUpGameRules(gManager, numPlayers);
        board = setupBoard(players, cardsOnBoard, rooms);
        
        // load opjects on the GUI
        view.loadShotCntrs(rooms);
        view.loadCardsGUI(rooms);
        view.loadPlayerDie(numPlayers, players);
        rooms[10].setNumPlayersInRoom(numPlayers);
        int i = 0;
        int layer = 2;
        // loop for all days
        while (curDay.getDayNum() < numDays + 1) {
            ArrayList<String> playerOptions = new ArrayList<String>();
            view.showAvailMoves(playerOptions);           
            if(curDay.getDayNum() < numDays + 1){
                playerOptions = playerOpt(players[i]);
                // GUI SHOW AVAILIBLE MOVES, and current acting players stats
                view.showAvailMoves(playerOptions);
                view.setPlayerStat(players[i], layer);
                layer++;
                while (action.equals("none")) {
                    System.out.print("");
                }

                if (action.equalsIgnoreCase("MOVE")) {
                    move(players[i], i, view);
                    if(players[i].getCurrentLocation().getRoomName().equals("office")){
                        upgrade(players[i], view);
                    }else if(!players[i].getCurrentLocation().getRoomName().equals("trailer")){
                    takeRole(players[i], view); 
                    }

                } else if (action.equalsIgnoreCase("IDLE")) {
                    idle(players[i]);

                } else if (action.equalsIgnoreCase("ACT")) {
                    Act playerAct = new Act(players[i], players[i].getCurrentLocation().getCurCard(), players[i].getCurrentLocation(), players[i].getRole());
                    act(players[i], playerAct, view);

                } else if (action.equalsIgnoreCase("REHEARSE")) {
                    rehearse(players[i]);

                } else if (action.equalsIgnoreCase("TAKE ROLE")) {
                    takeRole(players[i], view);

                } else if (action.equalsIgnoreCase("UPGRADE")) {
                    upgrade(players[i], view);

                }
                curDay.setNumCardsOnBoard(numCardsLeft);
                action = "none";
                i++;

                if(i == players.length)
                    i = 0;
                
                /*END DAY*/
                System.out.println("num cards left: " + numCardsLeft);
                if(numCardsLeft == 1 && (curDay.getDayNum() < numDays + 1)){
                    endDay(view, numPlayers, numDays);
                }
            }
        }
        /*END GAME*/
        System.out.println("game over");
        int[] playerPoints = new int[numPlayers];
        for(int x = 0; x < numPlayers; x++){
            playerPoints[x] = gManager.calcUserPoints(players[x]);
        }
        view.endGameGUI(players, playerPoints);

    }
    /*
    *shuffleCards:
    *   shuffles cards so they are random every game
    */
    public static Card[] shuffleCards(Card[] cardsToShuffle){
        Card[] shuffled = new Card[40];
        ArrayList<Integer> randomIndex = new ArrayList<Integer>(40);
        for(int i = 0; i < 40; i++){
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex);
        for(int i = 0; i < 40; i++){
            shuffled[randomIndex.get(i)] = cards[i];
        }
        return shuffled;
    }
    /*
    *endDay:
    *   ends a day and resets back to beggining game status
    */
    public static void endDay(View view, int numPlayers, int numDays){
        cardsOnBoard = new Card[10];
        //remove last card
        int indexOfLastCard = -1;
        for(int o = 0; o < 10; o++){
            boolean takenRoles = true;
            for(int j = 0; j < rooms[o].getRoles().size(); j++){
                if(!rooms[o].getRoles().get(j).getRoleTaken())
                    takenRoles = false;
            }
                for(int j = 0; j < rooms[o].getCurCard().getRoles().size(); j++){
                if(!rooms[o].getCurCard().getRoles().get(j).getRoleTaken())
                    takenRoles = false;                        
            } 
            if(!takenRoles){
                if(!rooms[o].getCurCard().getFlipped())
                    view.flipCard(rooms[o]);
            view.beatCard(rooms[o]);  
            }                 
        }
        System.out.println("NEW DAY " + (curDay.getDayNum() + 1));
        //update the day
        curDay.setDay(curDay.getDayNum() + 1);
        numCardsLeft = 10;
        //reload cards
        int k = 0;
        for(int x = curDay.getDayNum()*10 - 10; x < curDay.getDayNum()*10; x++){
            cardsOnBoard[k] = cards[x]; 
            k++;
        }
        //reload board
        for (int x = 0; x < 10; x++) {
            rooms[x].setNumShotCounters(rooms[x].getMaxShotCounters());
            rooms[x].setCurCard(cardsOnBoard[x]);
        }
        view.loadShotCntrs(rooms);
        view.loadCardsGUI(rooms);
        rooms[10].setNumPlayersInRoom(0);
        for(int x = 0; x < players.length; x++){
            players[x].setInRole(false);
            players[x].setNumPracticeChips(0);
            players[x].setRole(null);
            players[x].setLocation(rooms[10]);
            rooms[10].setNumPlayersInRoom(rooms[10].getNumPlayersInRoom() + 1);
            view.moveGUI(players[x], rooms[10]);
        }

        //make all board roles availible
        for(int x = 0; x < 10; x++){
            for(int j = 0; j < rooms[x].getRoles().size(); j++){
                rooms[x].getRoles().get(j).setRoleTaken(false);
            }
        }
        gManager = new Manager(numPlayers, numDays, cardsOnBoard);
        board = new Board(players, cardsOnBoard, rooms);
    }
    /*
    *selectAction:
    *   grabs the players desired action from the jbutton in the view class
    */
    public static void selectAction(String action2) {
        action = action2;
    }
    /*
    *setUpGameRules:
    *   sets up the players and returns the number of days to play
    *   RETURNS: the number of days to play
    */
    private static int setUpGameRules(Manager gManager, int numPlayers){
        numCardsLeft = 10;
        int startingCredits = 0;
        int startingRank = 1;
        int numDays = 4;
        if(numPlayers == 2 || numPlayers == 3)
            numDays--;
        
        if(numPlayers > 4)
            startingCredits += 2;

        if(numPlayers > 5)
            startingCredits += 2;
        
        if(numPlayers == 7 || numPlayers == 8)
            startingRank++;
        
        
        gManager = new Manager(numPlayers, numDays, cards);   
        players = gManager.startGame(numPlayers, null, 0, startingCredits, startingRank, rooms[10]);  //NOTE: maybe impliment color choosing later null for now
        curDay = new Day(1, numCardsLeft);
        return numDays;
    }
    /*
    *playerOpt:
    *   takes in a player and returns what actions that player may do
    *   RETURNS: an array of strings that indicate the availible buttons for player to press
    */
    private static ArrayList<String> playerOpt(Player player){
        //TO-DO: GUI, say whose turn it is...optional because player stat will show
        Room curRoom = new Room();
        curRoom = player.getCurrentLocation();        
        ArrayList<String> playerOptions = new ArrayList<String>();

        //if(player is at the Trailer)
        if(curRoom.getRoomName().equalsIgnoreCase("Trailer")){
            playerOptions.add("MOVE");
            playerOptions.add("IDLE");
        
        //if(player is at the casting office)
        }else if(curRoom.getRoomName().equalsIgnoreCase("Office")){
            playerOptions.add("MOVE");
            playerOptions.add("IDLE");   
            playerOptions.add("UPGRADE");         
        
        //if(player is in a role && there is atleast 1 shotCounter && players numPracticeChips is less than the budget for the card)
        }else if(curRoom.getNumShotCounters() > 0 && player.playerInRole() && player.getNumPracticeChips() < curRoom.getCurCard().getBudget()){
            playerOptions.add("ACT");
            playerOptions.add("IDLE");   
            playerOptions.add("REHEARSE");             
  
        //if(there is atleast 1 shot counter && player in role && players numPracticeChips == the budget for the card)
        }else if(curRoom.getNumShotCounters() > 0 && player.playerInRole() && player.getNumPracticeChips() == curRoom.getCurCard().getBudget()){
            playerOptions.add("ACT");
            playerOptions.add("IDLE");  
        
        //if(there is atleast 1 shot counter && player is not in a role)
        }else if(curRoom.getNumShotCounters() > 0 && !player.playerInRole()){
            playerOptions.add("MOVE");
            playerOptions.add("IDLE");
            playerOptions.add("TAKE ROLE");
        
        //if(card has been beaten && player is not in a role)
        }else if(curRoom.getNumShotCounters() == 0 && !player.playerInRole()){
            playerOptions.add("MOVE");
            playerOptions.add("IDLE");

        }
        
        return playerOptions;
    }
    /*
    *move: DONE
    *   -Takes a player and a player number (i) then finds all adj rooms and promps user
    *   for a room to move to.  Then moves the player to that room.  Will also flip a card
    *   at the chosen room if it is not yet flipped
    *   -will call the take role function to check if a user wants to take a role in new location
    *   
    *   RETURNS: true if user move is valid, false if not
    */
    private static void move(Player player, int playerNum, View view){
        System.out.println("move selected"); 
        //finds all adj rooms
        Room curRoom = new Room();
        curRoom = player.getCurrentLocation();
        curRoom.setNumPlayersInRoom(curRoom.getNumPlayersInRoom() - 1);
        view.adjRoomsGUI(curRoom.getAdjRooms());
        while(roomToMoveTo.equals("none")){
            System.out.print("");
        }
        System.out.println("room selected: " + roomToMoveTo);
        Room roomSelected = new Room();
        for(int i = 0; i < 12; i++){
            if(rooms[i].getRoomName().equalsIgnoreCase(roomToMoveTo))
                roomSelected = rooms[i];
        }
        board.movePlayer(roomSelected, player); 
        //GUI: move players dice to adj room
        view.moveGUI(player, roomSelected);     
        if(!roomSelected.getRoomName().equals("trailer") && !roomSelected.getRoomName().equals("office") && !roomSelected.getCurCard().getFlipped()){
            board.flipCard(roomSelected.getCurCard());
            //GUI: flip card on board
            view.flipCard(roomSelected);
            roomSelected.getCurCard().setFlipped(true);
        }
        player.setLocation(roomSelected);
        roomSelected.setNumPlayersInRoom(roomSelected.getNumPlayersInRoom()+1);
        roomToMoveTo = "none";            
    }
    /*
    *roomSelect:
    *   grabs the room the player wants to move to from the jbutton in the view
    */
    public static void roomSelect(String room){
        roomToMoveTo = room;
    }
    /*
    *idle:
    *   idles the player, basically skips their turn
    */
    private static void idle(Player player){
        //GUI says player has idle'd
        System.out.println("idle selected");     
    }
    /*
    *act:
    *   rolls a die and determines if user won or lost then updates the game logic
    *   and the GUI
    */
    private static void act(Player player, Act playerAct, View view){
        System.out.println("act selected");
        Banker bank = new Banker();
        Room curRoom = new Room();
        curRoom = player.getCurrentLocation();
        boolean wonAct = player.act(player, playerAct, curRoom.getCurCard());
        //won the act
        if(wonAct){
            view.removeShotCntr(curRoom, curRoom.getNumShotCounters());
            curRoom.setNumShotCounters(curRoom.getNumShotCounters() - 1);
            if(player.getRole().getOnCard()){
                bank.awardPlayer(0, 2, player);
            }else{
                bank.awardPlayer(1, 1, player);
            }
            //card is beaten
            if(playerAct.beatCard(curRoom)){
                view.beatCard(curRoom);
                beatCard(players, playerAct, curRoom, curRoom.getCurCard());
            }
        //lost the act
        }else{
            if(!player.getRole().getOnCard()){
                bank.awardPlayer(1, 0, player);
            }
        }     
    }
    /*
    *beatCard:
    *   handles logic and GUI change if a player has beat a card
    */
    private static void beatCard(Player[] players, Act playerAct, Room curRoom, Card curCard){
        int[] moneyDieArr = playerAct.rollMoneyDice(curRoom, curCard);   
        for(int i = 0; i < moneyDieArr.length; i++){
            if(curCard.getRoles().get(i).getRoleTaken()){
                for(int j = 0; j < players.length; j++){
                    if(players[j].getRole() != null){
                        if(players[j].getRole().getRoleDescription().equals(curCard.getRoles().get(i).getRoleDescription())){
                            System.out.println("player awarded " + moneyDieArr[i] + " dollars");
                            players[j].setDollars(players[j].getDollars() + moneyDieArr[i]); 
                        } 
                    }          
                }
            }
        }
        for(int i = 0; i < players.length; i++){
            if(players[i].getCurrentLocation().getRoomName().equals(curRoom.getRoomName())){
                if(!players[i].getRole().getOnCard()){
                    players[i].setDollars(players[i].getDollars() + players[i].getRole().getRank());
                }
                players[i].setInRole(false);
                players[i].setNumPracticeChips(0);
                players[i].setRole(null);
            }
        }
        //take card off avail card list and all that logic
        numCardsLeft--;
        for(int j = 0; j < curRoom.getRoles().size(); j++){
            curRoom.getRoles().get(j).setRoleTaken(true);
        }
        for(int i = 0; i < curCard.getRoles().size(); i++){
            curCard.getRoles().get(i).setRoleTaken(true);
        }
    }
    /*
    *rehearse:
    *   increments the players number of practice chips
    */
    private static void rehearse(Player player){
        System.out.println("rehearse selected");
        player.setNumPracticeChips(player.getNumPracticeChips() + 1);
    }
    /*
    *takeRole:
    *   gives user list of availible roles they can take and places them on that role
    */
    private static void takeRole(Player player, View view){
        Room curRoom = new Room();
        curRoom = player.getCurrentLocation();
        ArrayList<Role> roles = new ArrayList<Role>();
        if(!curRoom.getRoomName().equals("trailer") && !curRoom.getRoomName().equals("office")){
            for(int i = 0; i < curRoom.getRoles().size() + curRoom.getCurCard().getNumRoles(); i++){
                if(i < curRoom.getRoles().size() && !curRoom.getRoles().get(i).getRoleTaken() && player.getRank() >= curRoom.getRoles().get(i).getRank())
                    roles.add(curRoom.getRoles().get(i));
                if(i >= curRoom.getRoles().size() && !curRoom.getCurCard().getRoles().get(i - curRoom.getRoles().size()).getRoleTaken() && player.getRank() >= curRoom.getCurCard().getRoles().get(i - curRoom.getRoles().size()).getRank())
                    roles.add(curRoom.getCurCard().getRoles().get(i - curRoom.getRoles().size()));
            }        
        }
        view.rolesGUI(roles);
        while(roleSelected.equals("none")){
            System.out.print("");
        }
        if(!roleSelected.equals("no role")){
            Role roleChosen = new Role();
            for(int i = 0; i < roles.size(); i++){
                if(roleSelected.equalsIgnoreCase(roles.get(i).getRoleDescription()))
                    roleChosen = roles.get(i);
            }
            view.takeRoleGUI(player, curRoom, roleChosen);
            player.setInRole(true);
            player.setRole(roleChosen);
            roleChosen.setRoleTaken(true);
        }
        roleSelected = "none";
    }
    /*
    *roleSelect:
    *   grabs users role selected from GUI/view
    */
    public static void roleSelect(String role){
        roleSelected = role;
    }
    /*
    *rankSelect:
    *   grabs the rank selected for upgrade from the GUI/view
    */
    public static void rankSelect(String rank){
        rankSelected = rank;
    }
    /*
    *currencySelect:
    * grabs the currency selected to upgrade with from the GUI/view
    */
    public static void currencySelect(String cur){
        currencySelected = cur;
    }
    /*
    *upgrade:
    *   upgrade logic for upgrading and calls the view GUI upgrade methods
    */
    private static void upgrade(Player player, View view){
        Banker bank = new Banker();
        boolean[] availRanks = {false, false, false, false, false, false};         
        for(int i = player.getRank()+1; i <= availRanks.length; i++){
            boolean validateDollars = bank.hasSufficientFunds(player, i, "dollars");
            boolean validateCredits = bank.hasSufficientFunds(player, i, "credits");
            if(validateCredits || validateDollars)
                availRanks[i - 1] = true;
        }
        view.upgradeGUI(availRanks);
        //get rank chosen to upgrade to
        while(rankSelected.equals("none")){
            System.out.print("");
        }
        if(!rankSelected.equals("no upgrade")){
            System.out.println("player upgraded to rank " + rankSelected);
            boolean validateDollars = bank.hasSufficientFunds(player, Integer.parseInt(rankSelected), "dollars");
            boolean validateCredits = bank.hasSufficientFunds(player, Integer.parseInt(rankSelected), "credits");
            view.currencyGUI(validateDollars, validateCredits);
            while(currencySelected.equals("none")){
                System.out.print("");
            }
            int bill = bank.bill(currencySelected, Integer.parseInt(rankSelected), player);
            if(currencySelected.equals("dollars")){
                bank.collectMoney(bill, 0, player);
            }else{
                bank.collectMoney(0, bill, player);
            } 
            view.upgradePlayerDie(player, rankSelected);  
            view.moveGUI(player, player.getCurrentLocation());
        }
        if(!rankSelected.equals("no upgrade"))
            player.setRank(Integer.parseInt(rankSelected));
        currencySelected = "none";
        rankSelected = "none";
    }
    /*
    setupBoard:
        initializes the board object
    */
    private static Board setupBoard(Player[] players, Card[] cardsOnBoard, Room[] sets){
        Board board = new Board(players, cardsOnBoard, sets);
        return board;
    }
    /*
    *loadCards:
    *   loads the cards from the XML file
    */
    private static Card[] loadCards(){
        Document doc = null;
        parseCards parsing = new parseCards();
        try {
            doc = parsing.getDocFromFile("cards.xml");
            parsing.readCardData(doc);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    
        Card[] allCards = new Card[40];
        allCards = parsing.getAllCards(); 
        return allCards;       
    }
    /*
    *loadBoard:
    * loads and initializes all the rooms 
    *   RETURNS: array of rooms
    */
    private static Room[] loadBoard(){
        Document doc2 = null;
        parseBoard parsing = new parseBoard();
        try {
            doc2 = parsing.getDocFromFile("board.xml");
            parsing.readBoardData(doc2);
        } catch (Exception e) {
            System.out.println("Error = " + e);
        }
    
        Room[] allRooms = new Room[12]; 
        allRooms = parsing.getAllRooms();
        
        //TODO: load in last 2 rooms
        ArrayList<String> adjRooms = new ArrayList<String>();
        adjRooms.add("Main Street");
        adjRooms.add("Saloon");
        adjRooms.add("Hotel");
        ArrayList<String> adjRooms2 = new ArrayList<String>();
        adjRooms2.add("Train Station");
        adjRooms2.add("Ranch");
        adjRooms2.add("Secret Hideout");

        Room trailer = new Room("trailer", 0, null, 0, adjRooms, null, null, null,null,null);
        Room office = new Room("office", 0, null, 0, adjRooms2, null, null, null, null,null);
        allRooms[10] = trailer;
        allRooms[11] = office;
        return allRooms;    
    }    
}