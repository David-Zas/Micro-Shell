import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.awt.Rectangle;
 
public class parseBoard {
 
    private Room[] allRooms;
 
    public Room[] getAllRooms() {
        return this.allRooms;
    }
 
    public Document getDocFromFile(String filename) throws ParserConfigurationException {
 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;
        try {
            doc = db.parse(filename);
        } catch (Exception ex) {
            System.out.println("XML parse failure");
            ex.printStackTrace();
        }
        return doc;
        // exception handling
    }
 
    public void readBoardData(Document d) {
        Element root = d.getDocumentElement();
        // make a nodeList for all the sets in the file
        NodeList sets = root.getElementsByTagName("set"); // get set tag name
        Room[] rooms = new Room[sets.getLength() + 2];
 
        // loop through each set
        for (int i = 0; i < sets.getLength(); i++) {
 
            Room curRoom = new Room();
            ArrayList<cardPlacement> cards = new ArrayList<cardPlacement>();
            ArrayList<shotPlacement> shots = new ArrayList<shotPlacement>();
 
            // Read data from nodes
            Node set = sets.item(i);
 
            // Print out each set name
            String setName = set.getAttributes().getNamedItem("name").getNodeValue(); // get set name
 
            // set name
            curRoom.setRoomName(setName);
 
            // This will make a nodeList representing the children of set
            NodeList children = set.getChildNodes();
 
            // Traverse through each set
            for (int x = 0; x < children.getLength(); x++) {
                Node sub = children.item(x);
 
                // enter neighbors root node
                if ("neighbors".equals(sub.getNodeName())) {
                    NodeList neighbor = sub.getChildNodes();
 
                    ArrayList<String> adjRooms = new ArrayList<>();
 
                    // traverse through children of NEIGHBORS
                    for (int z = 0; z < neighbor.getLength(); z++) { // for loop for neighbors
                        Node neighbors = neighbor.item(z);
 
                        // neighbors children
                        if ("neighbor".equals(neighbors.getNodeName())) {
 
                            // get name of adjRooms
                            String neighbors1 = neighbors.getAttributes().getNamedItem("name").getNodeValue();
                            adjRooms.add(neighbors1);
                        }
 
                    }
                    // set adjRooms
                    curRoom.setAdjRooms(adjRooms);
                }
 
                // get area
                else if ("area".equals(sub.getNodeName())) {
                   
                    // get coordinates of placement of card
                    int setX = Integer.parseInt(sub.getAttributes().getNamedItem("x").getNodeValue());
                    int setY = Integer.parseInt(sub.getAttributes().getNamedItem("y").getNodeValue());
                    int setH = Integer.parseInt(sub.getAttributes().getNamedItem("h").getNodeValue());
                    int setW = Integer.parseInt(sub.getAttributes().getNamedItem("w").getNodeValue());
                   
                    // load them in
                    cardPlacement cardPlacement = new cardPlacement(setX,setY,setH,setW);
                    cards.add(cardPlacement);
                    curRoom.setCardPlacement(cards);
 
                } else if ("takes".equals(sub.getNodeName())) {
 
                    int numShotCounters = 0;
                    NodeList take = sub.getChildNodes();
                   
 
                    for (int r = 0; r < take.getLength(); r++) { // loop through takes children
 
                        Node takes = take.item(r);
                       
                        if ("take".contentEquals(takes.getNodeName())) {
                            // get the take number
                            int takes1 = Integer.parseInt(takes.getAttributes().getNamedItem("number").getNodeValue());
                           
                            numShotCounters++; // for logic
                           
                            NodeList takes2 = takes.getChildNodes();
                            for(int e = 0; e < takes2.getLength(); e++) {
                                Node take2 = takes2.item(e);
                                if("area".contentEquals(take2.getNodeName())) {
                                   
                                    // get coordinates of shots
                                    int takeX = Integer.parseInt(take2.getAttributes().getNamedItem("x").getNodeValue());
                                    int takeY = Integer.parseInt(take2.getAttributes().getNamedItem("y").getNodeValue());
                                    int takeH = Integer.parseInt(take2.getAttributes().getNamedItem("h").getNodeValue());
                                    int takeW = Integer.parseInt(take2.getAttributes().getNamedItem("w").getNodeValue());
                                   
                                    // load them in
                                    shotPlacement shotPlace = new shotPlacement(takeX,takeY,takeH,takeW,takes1);
                                    shots.add(shotPlace);
                                    curRoom.setShotPlacement(shots);
                                }  
                            }
                        }
                    }
                    curRoom.setNumShotCounters(numShotCounters);
                    curRoom.setMaxShotCounters(numShotCounters);
                } else if ("parts".equals(sub.getNodeName())) {
 
                    NodeList part = sub.getChildNodes();
                    ArrayList<Role> roles = new ArrayList<Role>();
                    for (int k = 0; k < part.getLength(); k++) {
                        
                        Node parts = part.item(k);
 
                        if ("part".equals(parts.getNodeName())) {
                            Role curRole = new Role();
                            // get part name
                            String parts1 = parts.getAttributes().getNamedItem("name").getNodeValue();
 
                            // get rank
                            String rank = parts.getAttributes().getNamedItem("level").getNodeValue();
                            int intRank = Integer.parseInt(rank);
                            
                            NodeList areaNodes = parts.getChildNodes();
                            for(int l = 0; l < areaNodes.getLength(); l++){
                                Node areas = areaNodes.item(l);
                                if("area".contentEquals(areas.getNodeName())){
                                    //part area
                                    int partX = Integer.parseInt(areas.getAttributes().getNamedItem("x").getNodeValue());
                                    int partY = Integer.parseInt(areas.getAttributes().getNamedItem("y").getNodeValue());
                                    int partH = Integer.parseInt(areas.getAttributes().getNamedItem("h").getNodeValue());
                                    int partW = Integer.parseInt(areas.getAttributes().getNamedItem("w").getNodeValue());
                                    roleArea curRole2 = new roleArea(partX, partY, partH, partW);
                                    curRole.setRolePlacement(curRole2);
                                }
                            }

                            // get line said
                            String line = parts.getTextContent();
 
                            // load it up
                            curRole.setRoleDescription(parts1);
                            curRole.setRank(intRank);
                            curRole.setLine(line);
                            curRole.setOnCard(false);
                            roles.add(curRole);
                        }
                        curRoom.setRoles(roles);
                    }
 
                }
 
            }
            rooms[i] = curRoom;
        }
        this.allRooms = rooms;
    }
}