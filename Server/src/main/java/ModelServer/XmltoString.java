package ModelServer;

import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XmltoString {

    public static String xmlString(org.w3c.dom.Document document){

        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            //t.setOutputProperty(OutputKeys.INDENT,"yes");
            StringWriter stringWriter = new StringWriter();
            t.transform(new DOMSource((Node) document),new StreamResult(stringWriter));
            return stringWriter.getBuffer().toString();
        }  catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
