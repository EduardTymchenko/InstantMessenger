package ModelServer;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "XMLMess")
@XmlType(propOrder = {"commandMess", "from", "bodyMess"})
public class Message {
    private String commandMess = "sendAll";
    private String from;
    private String bodyMess;

    public String getCommandMess() {
        return commandMess;
    }
    @XmlElement
    public void setCommandMess(String commandMess) {
        this.commandMess = commandMess;
    }
/*
    public Message(String from, String bodyMess) {
        this.from = from;
        this.bodyMess = bodyMess;
        //messToXML();
    }
*/
    public String getFrom() {
        return from;
    }
    @XmlElement
    public void setFrom(String from) {
        this.from = from;
    }

    public String getBodyMess() {
        return bodyMess;
    }
    @XmlElement
    public void setBodyMess(String bodyMess) {
        this.bodyMess = bodyMess;
    }

   /*
    public String xmlString(){

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
*/
}