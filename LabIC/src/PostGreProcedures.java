import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.geometric.PGpoint; 
import org.postgresql.geometric.PGcircle; 


public class PostGreProcedures {
	// die Methode insertCircle fuegt einen Kreis 
	// in die Relation mycirc ein (ein Tupel mit einem Attributwert)  
	public static void insertCircle(Connection conn) throws SQLException { 
		// Konstruktion des Kreises aus center und radius 
		PGpoint center = new PGpoint(1, 2.5); 
		double radius = 4; 
		PGcircle circle = new PGcircle(center, radius);     
		// vorbereiten des Einfuegens mit "?" als Parameter 
		PreparedStatement ps = conn.prepareStatement( 
				"INSERT INTO geomtest(mycirc) VALUES (?)"); 
		// setzen des Parameters und ausfuehren der Anweisung 
		ps.setObject(1, circle); 
		ps.executeUpdate(); 
		ps.close(); 
	} 

	// die Methode retrieveCircle holt einen Kreis mit dessen berechneter  
	// Flaeche aus der Relation mycirc und gibt diese Daten aus      
	public static void retrieveCircle(Connection conn) throws SQLException { 
		Statement stmt = conn.createStatement(); 
		// Anfrage fuer Kreis und Kreisflaeche 
		ResultSet rs = stmt.executeQuery( 
				"SELECT mycirc, area(mycirc) FROM geomtest"); 
		rs.next(); 
		// Kreisdaten holen per getObject und casten auf PGcircle 
		PGcircle circle = (PGcircle)rs.getObject(1);  
		// berechnete Kreisflaeche holen per getDouble 
		double area = rs.getDouble(2); 
		// center und radius extrahieren 
		PGpoint center = circle.center; 
		double radius = circle.radius; 
		// alle Daten ausgeben 
		System.out.println("Center (X, Y) = (" + center.x + ", " + center.y + ")"); 
		System.out.println("Radius = " + radius); 
		System.out.println("Area = " + area); 
	} 
}
