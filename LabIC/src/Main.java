import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class Main {

	public static void main(String[] args) throws SQLException {
		Connection conn = new Connection();
//		Node node = new Node();
		
		PostgreFunctions var1 = new PostgreFunctions();
		var1.createTable(conn.getPostgreConnection());
		var1.dropTable(conn.getPostgreConnection());
		
        Document doc = null;
        try {
//        	Original file
//            doc = OSMReader.readXMLDocument("res/map(1)");
        	
//        	Gekürzte file
        	doc = OSMReader.readXMLDocument("res/map(1)_geschnitten");
        	
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        OSMReader.PrintToConsole(doc);
//        OSMReader.PrintToConsole(doc.getElementById("21402303"));

        
        
        

        
//        System.out.println(doc.toString());
		
		
//		Statement stmt = conn.createStatement(); 
//
//		// Relation geomtest hat nur das Attribut mycirc 
//		// mit dem Datentyp circle 
//		stmt.execute("CREATE TEMP TABLE geomtest(mycirc circle)"); 
//		stmt.close(); 
//		PostGreFunctions.insertCircle(conn); 
//		PostGreFunctions.retrieveCircle(conn); 
//		conn.close(); 

	}

}
