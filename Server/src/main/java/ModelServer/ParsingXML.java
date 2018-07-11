package ModelServer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class ParsingXML {

    private Message message;

    //читаем Из потока XML
    public Message readXmlFromStream(BufferedReader in) throws JAXBException, IOException {
        StringBuffer ans = new StringBuffer();
            while (true) {

                String str;
                str = in.readLine();
                ans.append(str);
                if (str == null || str.equals("</XMLMess>")) {
                    break;
                }
            }

            System.out.println(ans.toString());
            JAXBContext context = JAXBContext.newInstance(Message.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Message) unmarshaller.unmarshal(new StringReader(ans.toString()));
    }

    // пишем в поток XML
    public void writeXMLinStream(Message message, BufferedWriter out) throws IOException, JAXBException {
        StringWriter writer = new StringWriter();


         // сообщение в формат XML
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        // с переносами
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(message,out);
        out.flush();

    }
}
