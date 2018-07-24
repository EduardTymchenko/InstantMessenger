package ModelServer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class ParsingXML {
    //
    public Message readXmlFromStream(BufferedReader in) throws JAXBException, IOException {
        StringBuffer ans = new StringBuffer();
        String str;
            while (true) {
                str = in.readLine();
                ans.append(str);
                if (str == null || str.equals("</XMLMess>")) {
                    break;
                }
            }
            JAXBContext context = JAXBContext.newInstance(Message.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Message) unmarshaller.unmarshal(new StringReader(ans.toString()));
    }

    // write stream to XML
    public void writeXMLinStream(Message message, BufferedWriter out) throws IOException, JAXBException {
         // message to format XML
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        // good format
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(message,out);
        out.flush();
    }


}
