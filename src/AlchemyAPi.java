import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alchemyapi.api.AlchemyAPI;

public class AlchemyAPi {
    
    private static JSONObject alchemyJson = new JSONObject();

    public static JSONObject get_twitter_obj(){

        TwitterApp.twitterProcess();
        return TwitterApp.getSearchResults();		
    }

    public static JSONObject getAlchemyResponse() {
        return alchemyJson;
    }

    public static void main(String[] args) {

        String tweetText, tweetTime;

        JSONObject jsonObject = get_twitter_obj();
        JSONArray alchemyArray = new JSONArray();
        alchemyJson.put("result", alchemyArray);

        // loop array
        JSONArray result = (JSONArray) jsonObject.get("result");

        Iterator<JSONObject> iterator = result.iterator();
        while (iterator.hasNext()) {
            JSONObject item = iterator.next();
            tweetText = (String)item.get("text");
            System.out.println(tweetText);

            tweetTime = (String)item.get("date");
            System.out.println(tweetTime);
            String apiKey = "642e61ef771e576819a18805f71ad5cb6e8ebc9a";
            //out.println("The received line is "+text);

            AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString(apiKey);
            try {
                System.out.print("Given text["+tweetText+"]");
                //					System.out.print("\nCategory:");
                //org.w3c.dom.Document doc = alchemyObj.TextGetCombined(text);

                org.w3c.dom.Document mood_doc=alchemyObj.TextGetTextSentiment(tweetText);
                mood_doc.getDocumentElement().normalize();

                System.out.println("Root element :" + mood_doc.getDocumentElement().getNodeName());
                Node root = mood_doc.getDocumentElement();
                //Node root1=mood_doc.getDocu+mentElement();
                //System.out.println(root.getTextContent());
                //traverse(root);
                Node senti=mood_doc.getElementsByTagName("docSentiment").item(0);
                NodeList senti_mem = senti.getChildNodes();

                String type = mood_doc.getElementsByTagName("type").item(0).getTextContent();
                int score = 0;
                try {
                    score = Integer.parseInt(mood_doc.getElementsByTagName("score").item(0).getTextContent());
                }  catch (Exception e) {
                    
                }

                JSONObject alchemyItem = new JSONObject();
                alchemyItem.put("time", tweetTime);
                alchemyItem.put("type", type);
                alchemyItem.put("score", score);

                alchemyArray.add(alchemyItem);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        getAlchemyResponse();
  }
/*
    public static void traverse(Node tree) {
        String nodeName="";
        if(tree.hasChildNodes()) {
            //System.out.println("-");
            count++;
            //int level_count=0;
            for (int i=0;i<count;i++)
                System.out.print("---");
            System.out.print("|");
            nodeName=tree.getNodeName();
            System.out.println('<'+nodeName+'>');
            NodeList nodes=tree.getChildNodes();			

            for(int i=0; i<nodes.getLength(); i++){
                Node node=nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    traverse(node);
                }
            }
        }
        else
            System.out.print(tree.getTextContent());

        //		for (int i=0;i<count;i++)
        //			System.out.print(" ");
        //		System.out.println("</"+nodeName+'>');
        count--;
    }
*/
}
