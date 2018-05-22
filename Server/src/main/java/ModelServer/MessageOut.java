package ModelServer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class MessageOut {
    private String from;
    private String bodyMess;
    private Document document;

    public MessageOut(String from, String bodyMess) {
        this.from = from;
        this.bodyMess = bodyMess;


    }
    public Document messToXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            Element root = document.createElement("XMLMess");
            Element fromNode = document.createElement("from");
            Text textFrom = document.createTextNode(from);
            Element bodyMessNode = document.createElement("bodyMess");
            Text textBody = document.createTextNode(bodyMess);

            document.appendChild(root);
            root.appendChild(fromNode);
            fromNode.appendChild(textFrom);
            root.appendChild(bodyMessNode);
            bodyMessNode.appendChild(textBody);
            return document;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

}