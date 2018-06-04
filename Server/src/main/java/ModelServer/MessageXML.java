package ModelServer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;


public class MessageXML {
    private String from;
    private String bodyMess;
    private Document document;

    public MessageXML(String from, String bodyMess) throws ParserConfigurationException {
        this.from = from;
        this.bodyMess = bodyMess;
        //messToXML();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBodyMess() {
        return bodyMess;
    }

    public void setBodyMess(String bodyMess) {
        this.bodyMess = bodyMess;
    }

    public Document messToXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
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
    }
    public String xmlString(){
        messToXML();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //t.setOutputProperty(OutputKeys.INDENT,"yes");
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource((Node) document),new StreamResult(stringWriter));
            return stringWriter.getBuffer().toString();
        }  catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return "Error message!";
    }

}