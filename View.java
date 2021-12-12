/*
   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane
*/
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
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class View extends JFrame {
 
  // JLabels
  JLabel boardlabel;
 
  // card label upsidedown
  JLabel cardlabel;
  JLabel cardlabel2;
  JLabel cardlabel3;
  JLabel cardLabel4;
  JLabel cardlabel5;
  JLabel cardlabel6;
  JLabel cardlabel7;
  JLabel cardLabel8;
  JLabel cardlabel9;
  JLabel cardlabel10;
  JLabel[] cardLabels = {cardlabel, cardlabel2, cardlabel3, cardLabel4, cardlabel5, cardlabel6, cardlabel7, cardLabel8, cardlabel9, cardlabel10};
  
  // card label flipped
  JLabel ccardlabel;
  JLabel ccardlabel2;
  JLabel ccardlabel3;
  JLabel ccardLabel4;
  JLabel ccardlabel5;
  JLabel ccardlabel6;
  JLabel ccardlabel7;
  JLabel ccardLabel8;
  JLabel ccardlabel9;
  JLabel ccardlabel10;
  JLabel[] cardLabels2 = {ccardlabel, ccardlabel2, ccardlabel3, ccardLabel4, ccardlabel5, ccardlabel6, ccardlabel7, ccardLabel8, ccardlabel9, ccardlabel10};
  // player label
  JLabel playerlabel;
  JLabel playerlabel2;
  JLabel playerlabel3;
  JLabel playerlabel4;
  JLabel playerlabel5;
  JLabel playerlabel6;
  JLabel playerlabel7;
  JLabel playerlabel8;
  JLabel[] playerDie = {playerlabel, playerlabel2, playerlabel3, playerlabel4, playerlabel5, playerlabel6, playerlabel7, playerlabel8};
 
  JLabel mLabel;
  JLabel statsLabel;
 
  // shot counters
  JLabel shotLabel;
  JLabel shotLabel2;
  JLabel shotLabel3;
  JLabel shotLabel4;
  JLabel shotLabel5;
  JLabel shotLabel6;
  JLabel shotLabel7;
  JLabel shotLabel8;
  JLabel shotLabel9;
  JLabel shotLabel10;
  JLabel shotLabel11;
  JLabel shotLabel12;
  JLabel shotLabel13;
  JLabel shotLabel14;
  JLabel shotLabel15;
  JLabel shotLabel16;
  JLabel shotLabel17;
  JLabel shotLabel18;
  JLabel shotLabel19;
  JLabel shotLabel20;
  JLabel shotLabel21;
  JLabel shotLabel22;
  JLabel[] shotCounters = {shotLabel, shotLabel2, shotLabel3, shotLabel4, shotLabel5, shotLabel6, shotLabel7, shotLabel8, shotLabel9, shotLabel10, shotLabel11, shotLabel12, shotLabel13, shotLabel14,
  shotLabel15, shotLabel16, shotLabel17, shotLabel18, shotLabel19, shotLabel20, shotLabel21, shotLabel22};
 
  //JButtons
  //player moves
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JButton bIdle;
  JButton bTakeRole;
  JButton bUpgrade;
  JButton[] buttons = new JButton[6];
  String playerAction = "none";
  //getter for player action
  public String getPlayerAction(){
   return playerAction;
  }
  // JLayered Pane
  JLayeredPane bPane;
  
  //
  JPanel panelStatus;
  JLabel numberLabel;
  JLabel rankLabel;
  JLabel dollarLabel;
  JLabel creditLabel;
   
   ImageIcon icon;
  // Constructor
  public View() {
       // Set the title of the JFrame
       super("DEADWOOD");
      
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
       
       // Create the deadwood board
       boardlabel = new JLabel();
       icon =  new ImageIcon("board.jpg");
       boardlabel.setIcon(icon);
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
       
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
       
       // Set the size of the GUI
       setSize(icon.getIconWidth(),icon.getIconHeight());

       setButtons();
   }
   /*
   *endGameGUI:
   */
   public void endGameGUI(Player[] players, int[] points){
      String endGameMsg = new String();
      for(int i = 0; i < players.length; i++){
         String color = new String();
         if(players[i].getColor().equals("b"))
            color = "Blue";

         if(players[i].getColor().equals("c"))
            color = "Cyan";

         if(players[i].getColor().equals("g"))
            color = "Green";

         if(players[i].getColor().equals("o"))
            color = "Orange";

         if(players[i].getColor().equals("p"))
            color = "Pink";

         if(players[i].getColor().equals("r"))
            color = "Red";

         if(players[i].getColor().equals("v"))
            color = "Violet";

         if(players[i].getColor().equals("y"))
            color = "Yellow";

                        
         endGameMsg += (color + " Player Scored " + points[i] + "!\n"); 
      }
      JOptionPane.showMessageDialog(null, endGameMsg);
      super.dispose();
   }
   /*
   *setButtons:
   * 
   */
   public void setButtons(){
       // Create the Menu for action buttons
       mLabel = new JLabel("OPTIONS");
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,new Integer(2));
       
       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 60,150, 20);
       bAct.addActionListener(new boardMouseListener());
       buttons[0] = bAct;

       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,90,150, 20);
       bRehearse.addActionListener(new boardMouseListener());
       buttons[1] = bRehearse;

       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,30,150, 20);
       bMove.addActionListener(new boardMouseListener());
       buttons[2] = bMove; 
      
       bIdle = new JButton("IDLE");
       bIdle.setBackground(Color.white);
       bIdle.setBounds(icon.getIconWidth()+10,120,150, 20);
       bIdle.addActionListener(new boardMouseListener());
       buttons[3] = bIdle;
       
       bTakeRole = new JButton("TAKEROLE");
       bTakeRole.setBackground(Color.white);
       bTakeRole.setBounds(icon.getIconWidth()+10,150,150, 20);
       bTakeRole.addActionListener(new boardMouseListener());
       buttons[4] = bTakeRole;

       bUpgrade = new JButton("UPGRADE");
       bUpgrade.setBackground(Color.white);
       bUpgrade.setBounds(icon.getIconWidth()+10,180,150, 20);
       bUpgrade.addActionListener(new boardMouseListener());
       buttons[5] = bUpgrade;

       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(2));
       bPane.add(bRehearse, new Integer(2));
       bPane.add(bMove, new Integer(2));
       bPane.add(bIdle, new Integer(2));
       bPane.add(bTakeRole, new Integer(2));
       bPane.add(bUpgrade, new Integer(2));       
   }
   /*
   *setPlayerStat:
   */
   public void setPlayerStat(Player player, int layer){
      playerStatusPanel ps = new playerStatusPanel(player);
      ps.setBounds(icon.getIconWidth() + 10, 210, 200, 200);
      ps.setBackground(Color.WHITE);
      bPane.add(ps, new Integer(layer));
   }
   /*
   *getNumPlayers: DONE
   * RETURNS: the number of players playing from a prompt 
   */ 
   public int getNumPlayers(View view){
      boolean firstIteration = true;
      String input = JOptionPane.showInputDialog(view, "Welcome to Deadwood, How many players are playing?"); 
      int numPlayers = -1;

      while(numPlayers < 2 || numPlayers > 8){
         if(!firstIteration)
            input = JOptionPane.showInputDialog(view, "please enter a valid integer between 2-8");          
         try {
            numPlayers = Integer.parseInt(input);  
         } catch (Exception e) {
            
         }  
         firstIteration = false;
      }
      if(firstIteration)
         numPlayers = Integer.parseInt(input);

      return numPlayers;       
   }
   /*
   *loadPlayerDie: DONE
   * creates all player die objects and loads them into the trailers
   */
   public void loadPlayerDie(int numPlayers, Player[] players){
      ImageIcon[] rank1Icons = new ImageIcon[8];
      
      ImageIcon pIcon = new ImageIcon("b1.png");
      players[0].setColor("b");
      rank1Icons[0] = pIcon;
      ImageIcon pIcon2 = new ImageIcon("c1.png");
      players[1].setColor("c");
      rank1Icons[1] = pIcon2;
      if(numPlayers > 2){
         ImageIcon pIcon3 = new ImageIcon("g1.png");
         players[2].setColor("g");
         rank1Icons[2] = pIcon3;
      }
      if(numPlayers > 3){
         ImageIcon pIcon4 = new ImageIcon("o1.png");
         players[3].setColor("o");
         rank1Icons[3] = pIcon4;
      }
      if(numPlayers > 4){
         ImageIcon pIcon5 = new ImageIcon("p1.png");
         players[4].setColor("p");
         rank1Icons[4] = pIcon5;
      } 
      if(numPlayers > 5){
         ImageIcon pIcon6 = new ImageIcon("r1.png");
         players[5].setColor("r");
         rank1Icons[5] = pIcon6;
      }
      if(numPlayers > 6){
         ImageIcon pIcon7 = new ImageIcon("v1.png");
         players[6].setColor("v");
         rank1Icons[6] = pIcon7;
      }
      if(numPlayers > 7){
         ImageIcon pIcon8 = new ImageIcon("y1.png");
         players[7].setColor("y");
         rank1Icons[7] = pIcon8;
      }
      

      ImageIcon ppIcon = new ImageIcon("b2.png");
      ImageIcon ppIcon2 = new ImageIcon("c2.png");
      ImageIcon ppIcon3 = new ImageIcon("g2.png");
      ImageIcon ppIcon4 = new ImageIcon("o2.png");
      ImageIcon ppIcon5 = new ImageIcon("p2.png");
      ImageIcon ppIcon6 = new ImageIcon("r2.png");
      ImageIcon ppIcon7 = new ImageIcon("v2.png");
      ImageIcon ppIcon8 = new ImageIcon("y2.png");      
      ImageIcon[] rank2Icons = {ppIcon, ppIcon2, ppIcon3, ppIcon4, ppIcon5, ppIcon6, ppIcon7, ppIcon8};
      int scewDie = 0;
      if(numPlayers < 7){      
         for(int i = 0; i < numPlayers; i++){
            
            playerDie[i] = new JLabel();    
            playerDie[i].setIcon(rank1Icons[i]);
            if(i < 4){
               playerDie[i].setBounds(1000+scewDie,280,46,46);
            }else{
               playerDie[i].setBounds(1000+scewDie,336,46,46);
            }
            playerDie[i].setVisible(true);
            bPane.add(playerDie[i],new Integer(3));
            scewDie += 46;
            if(i == 3)
               scewDie = 0;
            players[i].setPlayerDie(playerDie[i]);
            players[i].setPlayerIcon(rank1Icons[i]);
         }
      }else{
         for(int i = 0; i < numPlayers; i++){
            playerDie[i] = new JLabel();    
            playerDie[i].setIcon(rank2Icons[i]);
            if(i < 4){
               playerDie[i].setBounds(1000+scewDie,280,46,46);
            }else{
               playerDie[i].setBounds(1000+scewDie,336,46,46);
            }
            playerDie[i].setVisible(true);
            bPane.add(playerDie[i],new Integer(3));
            scewDie += 46;
            if(i == 3)
               scewDie = 0;
            players[i].setPlayerDie(playerDie[i]);
            players[i].setPlayerIcon(rank2Icons[i]);
         }         
      }
   }
   /*
   *rolesGUI:
   */
   public void rolesGUI(ArrayList<Role> roles){
      String[] rolesArr = new String[roles.size() + 1];
      for(int i = 0; i < rolesArr.length - 1; i++){
         rolesArr[i] = roles.get(i).getRoleDescription();
      }
      rolesArr[roles.size()] = "none";
      rolesClass rc = new rolesClass();
      rc.setAvailRoles(rolesArr);
      rc.initComboBox();
      rc.pack();
      rc.setLocationRelativeTo(null);
      rc.setBounds(260, 260, 260, 260);
      // centerFrame(ar);
      rc.setVisible(true);     
   }
   /*
   *adjRoomsGUI:
   *
   */
   public void adjRoomsGUI(ArrayList<String> adjRooms){
      String[] adjRoomsArr = new String[adjRooms.size()];
      for (int i = 0; i < adjRooms.size(); i++) {
          adjRoomsArr[i] = adjRooms.get(i);
      }
      // activate adj rooms list
      adjRoomsClass ar = new adjRoomsClass();
      ar.setAdjRooms(adjRoomsArr);
      ar.initComboBox();
      ar.pack();
      ar.setLocationRelativeTo(null);
      ar.setBounds(260, 260, 260, 260);
      // centerFrame(ar);
      ar.setVisible(true);
   }
   /*
   *upgradePlayerDie: 
   */
   public void upgradePlayerDie(Player player, String rank){
      player.getPlayerDie().setVisible(false);
      ImageIcon ppIcon = new ImageIcon(player.getColor() + rank + ".png");
      System.out.println(player.getColor() + rank + ".png");
      JLabel newDie = new JLabel();
      newDie.setIcon(ppIcon);        
      newDie.setVisible(true);
      bPane.add(newDie,new Integer(4));
      player.setPlayerDie(newDie);     
      moveGUI(player, player.getCurrentLocation());
   }
   /*
   *upgradeGUI:
   *
   */
   public void upgradeGUI(boolean[] availRanks){
      ArrayList<String> availRanks2 = new ArrayList<String>();
      for(int i = 0; i < 6; i++){
         if(availRanks[i])
            availRanks2.add("" + (i+1));
      }
      String[] availRanksArr = new String[availRanks2.size() + 1];
      for(int i = 0; i < availRanksArr.length - 1; i++){
         availRanksArr[i] = availRanks2.get(i);
      }
      availRanksArr[availRanksArr.length - 1] = "none";
      upgradeClass uc = new upgradeClass();
      uc.setAvailRanks(availRanksArr);
      uc.initComboBox();
      uc.pack();
      uc.setLocationRelativeTo(null);
      uc.setBounds(260, 260, 260, 260);
      uc.setVisible(true);
   }
   /*
   *currencyGUI:
   *
   */
   public void currencyGUI(boolean dollars, boolean credits){
      int curSize = 0;
      if(dollars)
         curSize++;
      if(credits)
         curSize++;
      String[] currencyArr = new String[curSize];
      if(dollars && credits){
         currencyArr[0] = "dollars";
         currencyArr[1] = "credits";
      }else if(dollars){
         currencyArr[0] = "dollars";
      }else if(credits){
         currencyArr[0] = "credits";
      }
      currencyClass cc = new currencyClass();
      cc.setAvailCur(currencyArr);
      cc.initComboBox();
      cc.pack();
      cc.setLocationRelativeTo(null);
      cc.setBounds(260, 260, 260, 260);
      cc.setVisible(true);      
   }   
   /*
   *moveGUI: DONE
   * 
   */
   public void moveGUI(Player player, Room room){
      //moves the players die to correct location
      int adjust = room.getNumPlayersInRoom();
      
      if(room.getRoomName().equalsIgnoreCase("trailer") || room.getRoomName().equalsIgnoreCase("office")){
         if(room.getRoomName().equalsIgnoreCase("trailer")){
            player.getPlayerDie().setBounds(1000+(46*adjust),280,46,46);
         }else{
            player.getPlayerDie().setBounds(10+(46*adjust), 459, 46, 46);
         }

      }else{
         player.getPlayerDie().setBounds(room.getCardPlacement().get(0).getX() + (46*adjust), room.getCardPlacement().get(0).getY() + 120, 46, 46);
      }
   }
   /*
   *takeRoleGUI: DONE
   * 
   */
   public void takeRoleGUI(Player player, Room room, Role role){
      if(!role.getOnCard())
         player.getPlayerDie().setBounds(role.getRolePlacement().getX(), role.getRolePlacement().getY(), 46, 46);
     
      if(role.getOnCard())
         player.getPlayerDie().setBounds(room.getCardPlacement().get(0).getX() + role.getRolePlacement().getX(), room.getCardPlacement().get(0).getY()+role.getRolePlacement().getY() , 46, 46);
   }
   /*
   *loadCardsGUI: DONE
   *  loads the card backs on the gui
   */
   public void loadCardsGUI(Room[] rooms){
      ImageIcon cIcon = new ImageIcon("01.png");
      int width = cIcon.getIconWidth();
      int height = cIcon.getIconHeight();
      for(int i = 0; i < 10; i++){
         JLabel[] cardz = new JLabel[2];

         cardLabels[i] = new JLabel();
         ImageIcon cIcon2 = new ImageIcon("CardBack.jpg");       
         Image image = cIcon2.getImage();
         Image scaled = image.getScaledInstance(width, height,Image.SCALE_SMOOTH);
         cIcon2 = new ImageIcon(scaled);
         cardLabels[i].setIcon(cIcon2);
         cardLabels[i].setBounds(rooms[i].getCardPlacement().get(0).getX(), rooms[i].getCardPlacement().get(0).getY(), width, height);
         cardLabels[i].setOpaque(true);
         bPane.add(cardLabels[i],new Integer(1)); 
         cardz[0] = cardLabels[i];

         cardLabels2[i] = new JLabel();  
         ImageIcon cIcon3 = new ImageIcon(rooms[i].getCurCard().getCardImg());       
         cardLabels2[i].setIcon(cIcon3);
         cardLabels2[i].setBounds(rooms[i].getCardPlacement().get(0).getX(), rooms[i].getCardPlacement().get(0).getY(), width, height);
         cardLabels2[i].setOpaque(true);
         bPane.add(cardLabels2[i],new Integer(1)); 
         cardz[1] = cardLabels2[i];
         cardLabels2[i].setVisible(false);
         
         rooms[i].setCardLabel(cardz);         
      }
   } 
   /*
   *flipCard: DONE
   * NOTE: have to get rid of card back too
   */
   public void flipCard(Room room){
      room.getCardLabel()[0].setVisible(false);
      room.getCardLabel()[1].setVisible(true);
   }
   /*
   *beatCard: DONE
   */
  public void beatCard(Room room){
     room.getCardLabel()[1].setVisible(false);
  }
   /*
   *loadShotCntrs: DONE
   *  takes in an array of rooms and loads all the shotCounters
   */
   public void loadShotCntrs(Room[] rooms){
      for(int i = 0; i < 10; i++){
         JLabel[] roomCounters = new JLabel[rooms[i].getNumShotCounters()];
         for(int j = 0; j < rooms[i].getNumShotCounters();j++){
            shotCounters[j] = new JLabel();
            ImageIcon sIcon = new ImageIcon("shot.png");
            shotCounters[j].setIcon(sIcon);
            shotCounters[j].setBounds(rooms[i].getShotPlacement().get(j).getX(), rooms[i].getShotPlacement().get(j).getY(), 47, 47);
            shotCounters[j].setVisible(true);
            bPane.add(shotCounters[j],new Integer(1));
            roomCounters[j] = shotCounters[j];
         }
         rooms[i].setShotCounters(roomCounters);
      }
   }
   /*
   *removeShotCntr: DONE
   * 
   */
   public void removeShotCntr(Room room, int NumShotCounters){
      room.getShotCounters()[NumShotCounters - 1].setVisible(false);
   }
   /*
   *showAvailMoves: DONE
   * NOTE: make cleaner
   */
   public void showAvailMoves(ArrayList<String> playerOpt){
      for(int i = 0; i < buttons.length; i++){
         buttons[i].setEnabled(false);
      } 
      for(int i = 0; i < playerOpt.size(); i++){
         if(playerOpt.get(i).equals("MOVE"))
            bMove.setEnabled(true);

         if(playerOpt.get(i).equals("IDLE"))
            bIdle.setEnabled(true);
         
         if(playerOpt.get(i).equals("ACT"))
            bAct.setEnabled(true);
         
         if(playerOpt.get(i).equals("REHEARSE"))
            bRehearse.setEnabled(true);
        
         if(playerOpt.get(i).equals("UPGRADE"))
            bUpgrade.setEnabled(true);
        
         if(playerOpt.get(i).equals("TAKE ROLE"))
            bTakeRole.setEnabled(true);            
      }
   }
  // This class implements Mouse Events
  class boardMouseListener implements ActionListener{    
      /*CONSTANTS*/
      ArrayList<String> adjRooms = new ArrayList<String>();

      // Code for the different button clicks
      public void actionPerformed(ActionEvent e){
         if (e.getSource()== bAct){         
            Deadwood2.selectAction("ACT");
            
         }else if (e.getSource()== bRehearse){
            Deadwood2.selectAction("REHEARSE");
        
         }else if (e.getSource()== bMove){
            Deadwood2.selectAction("MOVE");

         }else if(e.getSource()== bIdle){
            Deadwood2.selectAction("IDLE");
         
         }else if(e.getSource()== bTakeRole){
            Deadwood2.selectAction("TAKE ROLE");
         
         }else if(e.getSource()== bUpgrade){
            Deadwood2.selectAction("UPGRADE");
         }          
      }
   }
   /*
   *rolesClass:
   */
   public static class currencyClass extends JFrame implements ActionListener{
      private String[] availCur;
      JComboBox curList;
      JLabel dropBox = new JLabel();

      public currencyClass(){
         super("what would you like to pay with?");
      }
      public void setAvailCur(String[] availCur){
         this.availCur = availCur;
      }

      public void initComboBox(){
         curList = new JComboBox(this.availCur);
         setLayout(new FlowLayout());
         setSize(150,100);
         setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

         curList.setSelectedIndex(this.availCur.length - 1);
         curList.addActionListener(this);
         add(curList);
         add(dropBox);
      }
      public void actionPerformed(ActionEvent e){
         if(e.getSource() == curList){
            JComboBox curBox = (JComboBox)e.getSource();
            String curOfChoice = (String)curBox.getSelectedItem();

            Deadwood2.currencySelect(curOfChoice);
            super.dispose();              
         }
      }
   }   
   /*
   *upgradeClass:
   */
   public static class upgradeClass extends JFrame implements ActionListener{
      private String[] availRanks;
      JComboBox rankList;
      JLabel dropBox = new JLabel();

      public upgradeClass(){
         super("choose a rank");
      }
      public void setAvailRanks(String[] availRanks){
         this.availRanks = availRanks;
      }

      public void initComboBox(){
         rankList = new JComboBox(this.availRanks);
         setLayout(new FlowLayout());
         setSize(150,100);
         setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

         rankList.setSelectedIndex(this.availRanks.length - 1);
         rankList.addActionListener(this);
         add(rankList);
         add(dropBox);
      }
      public void actionPerformed(ActionEvent e){
         if(e.getSource() == rankList){
            JComboBox ranksBox = (JComboBox)e.getSource();
            String rankOfChoice = (String)ranksBox.getSelectedItem();
               if(rankOfChoice.equals("none")){
                  Deadwood2.rankSelect("no upgrade");
                  super.dispose();
               }else{
                  Deadwood2.rankSelect(rankOfChoice);
                  super.dispose();            
               }         
            
         }
      }
   }       
   /*
   *rolesClass:
   */
   public static class rolesClass extends JFrame implements ActionListener{
      private String[] availRoles;
      JComboBox rolesList;
      JLabel dropBox = new JLabel();

      public rolesClass(){
         super("choose a role");
      }
      public void setAvailRoles(String[] availRoles){
         this.availRoles = availRoles;
      }

      public void initComboBox(){
         rolesList = new JComboBox(this.availRoles);
         setLayout(new FlowLayout());
         setSize(150,100);
         setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

         rolesList.setSelectedIndex(this.availRoles.length - 1);
         rolesList.addActionListener(this);
         add(rolesList);
         add(dropBox);
      }
      public void actionPerformed(ActionEvent e){
         if(e.getSource() == rolesList){
            JComboBox rolesBox = (JComboBox)e.getSource();
            String roleOfChoice = (String)rolesBox.getSelectedItem();
            if(roleOfChoice.equals("none")){
               Deadwood2.roleSelect("no role");
               super.dispose();
            }else{
               Deadwood2.roleSelect(roleOfChoice);
               super.dispose();            
            }
         }
      }
   }    
   /*
   *adjRoomsClass:
   */
   public static class adjRoomsClass extends JFrame implements ActionListener{
      private String[] adjRooms;
      JComboBox adjRoomsList;
      JLabel dropBox = new JLabel();
      
      public adjRoomsClass(){
         super("Choose desired location");
      } 
      public void initComboBox(){
         adjRoomsList = new JComboBox(this.adjRooms);
         setLayout(new FlowLayout());
         setSize(150,100);
         setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

         adjRoomsList.addActionListener(this);
         add(adjRoomsList);
         add(dropBox);
      }

      public void setAdjRooms(String[] adjRooms){
         this.adjRooms = adjRooms;
      }

      public void actionPerformed(ActionEvent e){
         if(e.getSource() == adjRoomsList){
            JComboBox adjRoomBox = (JComboBox)e.getSource();
            String RoomOfChoice = (String)adjRoomBox.getSelectedItem();
            switch(RoomOfChoice){
               case "Train Station": Deadwood2.roomSelect("Train Station"); super.dispose();
                  break;

               case "Secret Hideout": Deadwood2.roomSelect("Secret Hideout"); super.dispose();
                  break;

               case "Church": Deadwood2.roomSelect("Church"); super.dispose();
                  break;

               case "Hotel": Deadwood2.roomSelect("Hotel"); super.dispose();
                  break;

               case "Main Street": Deadwood2.roomSelect("Main Street"); super.dispose();
                  break;

               case "Jail": Deadwood2.roomSelect("Jail"); super.dispose();
                  break;

               case "General Store": Deadwood2.roomSelect("General Store"); super.dispose();
                  break;

               case "Ranch": Deadwood2.roomSelect("Ranch"); super.dispose();
                  break;

               case "Bank": Deadwood2.roomSelect("Bank"); super.dispose();
                  break;

               case "Saloon": Deadwood2.roomSelect("Saloon"); super.dispose();
                  break;

               case "trailer": Deadwood2.roomSelect("trailer"); super.dispose();
                  break;

               case "office": Deadwood2.roomSelect("office"); super.dispose();
                  break;
            }

         }

      }
      
   } 
   /*
   *playerStatusPanel:
   */
   public class playerStatusPanel extends JPanel {
 
      private JLabel numberLabel;
      private JLabel rankLabel;
      private JLabel dollarLabel;
      private JLabel creditLabel;
      private JLabel numChipsLabel;
     
      private JLabel curPlayer;
      private JLabel rankField;
      private JLabel dollarField;
      private JLabel creditField;
      private JLabel numChipsField;
   
      public playerStatusPanel(Player player) {
         
         Player curplayer = new Player(); // to refer to a current player
         numberLabel = new JLabel("Player: ");//
         rankLabel = new JLabel("rank: ");//
         dollarLabel = new JLabel("dollars: ");
         creditLabel = new JLabel("credits: ");
         numChipsLabel = new JLabel("pratice chips: ");
         curPlayer = new JLabel();
         curPlayer.setIcon(player.getPlayerIcon());
         rankField = new JLabel("" + player.getRank());
         dollarField = new JLabel("" + player.getDollars());
         creditField = new JLabel("" + player.getCredits());
         numChipsField = new JLabel("" + player.getNumPracticeChips());
  
         Border innerBorder = BorderFactory.createTitledBorder("Player Status");
         Border outerBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
         setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
  
         setLayout(new GridBagLayout());
  
         GridBagConstraints gc = new GridBagConstraints();
  
         // controls how much space it takes up
         gc.weightx = 1;
         gc.weighty = 0.1;
  
         //////// 1ST ROW /////////////////
         gc.gridx = 0;
         gc.gridy = 0;
         gc.fill = GridBagConstraints.NONE; // tells components wheter to take up all the space in a cell or not
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         gc.insets = new Insets(0, 0, 0, 0); // adds space of 5 pixels between namefield and namelabel
         add(numberLabel, gc); // adding nameLabel
  
         // add nameField to panel
         gc.gridx = 1;
         gc.gridy = 0;
         gc.insets = new Insets(0, 0, 0, 0); // adds space between labe and box
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(curPlayer, gc);
  
         //////// 2ND ROW /////////////////
  
         // controls how much space it takes up
         gc.weightx = 1;
         gc.weighty = 0.1;
  
         // add occupation label to panel
         gc.gridy = 1;
         gc.gridx = 0;
         gc.insets = new Insets(0, 0, 0, 5);
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(rankLabel, gc);
        
         // add nameField to panel
         gc.gridx = 1;
         gc.gridy = 1;
         gc.insets = new Insets(0, 0, 0, 0); // adds space between labe and box
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(rankField, gc);
        
         ////////3RD ROW /////////////////
  
         // controls how much space it takes up
         gc.weightx = 1;
         gc.weighty = 0.1;
  
         // add occupation label to panel
         gc.gridy = 2;
         gc.gridx = 0;
         //gc.insets = new Insets(0, 0, 0, 5);
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(dollarLabel, gc);
        
         // add nameField to panel
         gc.gridx = 1;
         gc.gridy = 2;
         gc.insets = new Insets(0, 0, 0, 0); // adds space between labe and box
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(dollarField, gc);
        
         ////////4TH ROW /////////////////
  
         // controls how much space it takes up
         gc.weightx = 1;
         gc.weighty = 0.1;
  
         // add occupation label to panel
         gc.gridy = 3;
         gc.gridx = 0;
         gc.insets = new Insets(0, 0, 0, 5);
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(creditLabel, gc);
        
         // add creditField to panel
         gc.gridx = 1;
         gc.gridy = 3;
         gc.insets = new Insets(0, 0, 0, 0); // adds space between labe and box
         gc.anchor = GridBagConstraints.LINE_START; // do this before you add component
         add(creditField, gc);
        
              
 ////////////////////////// 5TH ROW //////////////////////////////////////////
        
         gc.weightx = 1;
         gc.weighty = 0.1;
        
         // add numPraticeChipsLabel
         gc.gridy = 4;
         gc.gridx = 0;
         gc.insets = new Insets(0,0,0,5);
         gc.anchor = GridBagConstraints.LINE_START;
         add(numChipsLabel,gc);
        
         // add practiceChipField
         gc.gridx = 1;
         gc.gridy = 4;
         gc.insets = new Insets (0,0,0,0);
         gc.anchor = GridBagConstraints.LINE_START;
         add(numChipsField, gc);
    
     }
 }     
}