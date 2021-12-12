import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
 
public class parseCards {
 
    private Card[] allCards;
 
    public Card[] getAllCards() {
        return this.allCards;
    }
 
    public Document getDocFromFile(String filename) throws ParserConfigurationException {
        {
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
        } // exception handling
    }
 
    public void readCardData(Document d) {
       
        Card[] deck = new Card[40];
        Element root = d.getDocumentElement();
        NodeList cards = root.getElementsByTagName("card");
       
        for (int i=0; i<cards.getLength();i++) {
            Card curCard = new Card();
            ArrayList<Role> roles = new ArrayList<Role>();
            // read data from nodes
            // card name
            Node card = cards.item(i);
         
            // set card name
            String cardName = card.getAttributes().getNamedItem("name").getNodeValue();
            curCard.setName(cardName);
           
            // set card img
            String cardImg = card.getAttributes().getNamedItem("img").getNodeValue();
            curCard.setCardImg(cardImg);
 
            // budget
            String budget = card.getAttributes().getNamedItem("budget").getNodeValue();
           
            int budgetresult = Integer.parseInt(budget);
            curCard.setBudget(budgetresult);
 
            // reads data
            NodeList children = card.getChildNodes();
   
           
           
            for (int j=0; j< children.getLength(); j++) {
                Node sub = children.item(j);
               
                if("scene".equals(sub.getNodeName())) {
                    // get scene number and scene description
                    int sceneNumber = Integer.parseInt(sub.getAttributes().getNamedItem("number").getNodeValue());
                    String scene = sub.getTextContent();
                   
                    // load them in
                    curCard.setDescription(scene);
                    curCard.setSceneNum(sceneNumber);
                }
               
                else if("part".contentEquals(sub.getNodeName())) {
                    Role curRole = new Role();
                    // get partName
                    String partName = sub.getAttributes().getNamedItem("name").getNodeValue();
                   
                    // get rank of part
                    String level = sub.getAttributes().getNamedItem("level").getNodeValue();
                    int rank = Integer.parseInt(level);
                   
                    // get line said
                    String lineName = sub.getTextContent();
                   
                    NodeList area = sub.getChildNodes();
                   
                    for(int b = 0; b < area.getLength(); b++) {
                        Node area2 = area.item(b);
                       
                        if("area".contentEquals(area2.getNodeName())) {
                           
                            int areaX = Integer.parseInt(area2.getAttributes().getNamedItem("x").getNodeValue());
                            int areaY = Integer.parseInt(area2.getAttributes().getNamedItem("y").getNodeValue());
                            int areaH = Integer.parseInt(area2.getAttributes().getNamedItem("h").getNodeValue());
                            int areaW = Integer.parseInt(area2.getAttributes().getNamedItem("w").getNodeValue());
                            roleArea curArea = new roleArea(areaX,areaY,areaH,areaW);
                            curRole.setRolePlacement(curArea);
                        }
                    }
                    
                    curRole.setRoleDescription(partName);
                    curRole.setRank(rank);
                    curRole.setLine(lineName);
                    curRole.setOnCard(true);
                    roles.add(curRole);
                }
            }
            curCard.setRoles(roles);
            curCard.setNumRoles(roles.size());
            deck[i] = curCard;  
        }
        this.allCards = deck;
    }
}