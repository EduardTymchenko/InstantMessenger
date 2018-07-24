package ModelServer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.*;

public class ParsingXML {

    private MessageXML messageXML;


    //читаем Из потока XML
    public MessageXML readXmlFromStream(BufferedReader in) throws JAXBException, IOException {
        StringBuffer ans = new StringBuffer();
            while (true) {

                String str = null;
                str = in.readLine();
                ans.append(str);
                if (str == null || str.equals("</XMLMess>")) {
                    break;
                }
            }

            System.out.println(ans.toString());
            JAXBContext context = JAXBContext.newInstance(MessageXML.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            MessageXML messageXMLIn = (MessageXML) unmarshaller.unmarshal(new StringReader(ans.toString()));

    return messageXMLIn;
    }

    // пишем в поток XML
    public void writeXMLinStream(MessageXML messageXML, BufferedWriter out) throws IOException, JAXBException {
        StringWriter writer = new StringWriter();


         // сообщение в формат XML
        JAXBContext context = JAXBContext.newInstance(MessageXML.class);
        Marshaller marshaller = context.createMarshaller();
        // с переносами
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(messageXML,out);


        /*
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        document = builder.newDocument();
        Element root = document.createElement("XMLMess");
        Element command = document.createElement("commandMess");
        Text textCommand = document.createTextNode(messageXML.getCommandMess());
        Element fromNode = document.createElement("from");
        Text textFrom = document.createTextNode(messageXML.getFrom());
        Element bodyMessNode = document.createElement("bodyMess");
        Text textBody = document.createTextNode(messageXML.getBodyMess());

        document.appendChild(root);
        root.appendChild(command);
        command.appendChild(textCommand);
        root.appendChild(fromNode);
        fromNode.appendChild(textFrom);
        root.appendChild(bodyMessNode);
        bodyMessNode.appendChild(textBody);

        Source source = new DOMSource(document);
        Result result = new StreamResult(out);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.transform(source,result);

            out.flush();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
*/

        out.flush();

    }



}
