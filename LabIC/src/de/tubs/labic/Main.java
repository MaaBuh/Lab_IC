package de.tubs.labic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.tubs.labic.db.Connection;
import de.tubs.labic.db.EntityManager;
import de.tubs.labic.db.PostgreFunctions;
import de.tubs.labic.osm.OSMReader;

public class Main {

	public static void main(String[] args) {
		testInsertAmpel();
	}

	public static void testInsertAmpel() {
		EntityManager em = EntityManager.getInstance();

		try {
			PGpoint geo = new PGpoint("(2,2)");
			em.persistAmpel("VIBRATION", geo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testInsertGebaeude() {
		EntityManager em = EntityManager.getInstance();

		try {
			PGpolygon geo = new PGpolygon("((2,2))");
			em.persistGebaeude("test2", 12, 38300, "adsaseeed", "BS", 4, 200.5,
					"Flat", "UNI", "shop", "einrichtung", "irgendwer", 12313,
					geo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test1() throws SQLException {
		Connection conn = new Connection();
		// Node node = new Node();

		PostgreFunctions var1 = new PostgreFunctions();
		var1.createTable(conn.getPostgreConnection());
		var1.dropTable(conn.getPostgreConnection());

		Document doc = null;
		try {
			// Original file
			// doc = OSMReader.readXMLDocument("res/map(1)");

			// Gek�rzte file
			doc = OSMReader.readXMLDocument("res/map(1)_geschnitten");

		} catch (ParserConfigurationException ex) {
			Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (SAXException ex) {
			Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (IOException ex) {
			Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		OSMReader.PrintToConsole(doc);
		// OSMReader.PrintToConsole(doc.getElementById("21402303"));

		// System.out.println(doc.toString());

		// Statement stmt = conn.createStatement();
		//
		// // Relation geomtest hat nur das Attribut mycirc
		// // mit dem Datentyp circle
		// stmt.execute("CREATE TEMP TABLE geomtest(mycirc circle)");
		// stmt.close();
		// PostGreFunctions.insertCircle(conn);
		// PostGreFunctions.retrieveCircle(conn);
		// conn.close();
	}

}
