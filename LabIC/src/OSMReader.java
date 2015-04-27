import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
import org.xml.sax.SAXException;


public class OSMReader {
	
    public static void main(String[] args)  {
        Document doc = null;
        try {
            doc = readXMLDocument("res/map(1)");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println( );
    }


	public static Document readXMLDocument(String filename)  
			throws ParserConfigurationException, SAXException, IOException{ 
		DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder; 
		Document document; 
		builder = factory.newDocumentBuilder();  
		document = builder.parse( new File( filename ) ); 
		return document; 
	} 
}

