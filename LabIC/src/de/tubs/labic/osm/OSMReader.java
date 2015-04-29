package de.tubs.labic.osm;
import java.io.File;
import java.io.IOException;



import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;


public class OSMReader {
	
	public static Document readXMLDocument(String filename)  
			throws ParserConfigurationException, SAXException, IOException{ 
		DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder; 
		Document document; 
		builder = factory.newDocumentBuilder();  
		document = builder.parse( new File( filename ) ); 
		return document; 
	} 
	
    /*
     * Ausgelesenes Dokument in Konsolge anzeigen
     */
	public static void PrintToConsole(Document doc) {
        DOMImplementationLS domImplLS = (DOMImplementationLS) doc
            .getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(doc);
        System.out.println(str);
	}
    /*
     * Ausgelesenen Node in Konsolge anzeigen
     * Funktioniert noch nicht richtig
     */
//	public static void PrintToConsole(Node node) {
//		StringWriter writer = new StringWriter();
//		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		transformer.transform(new DOMSource(node), new StreamResult(writer));
//		String str = writer.toString();
//	}
//	
}

