
import java.sql.*;




// in der main-Methode wird 
// - Treiber fuer postgresql geladen 
// - Verbindung zur Datenbank ’test’ auf PostgreSQL auf Server localhost und    
//   Port 5432 aufgebaut 
// - Relation geomtest eingerichtet 
// - Methoden insertCircle und retrieveCircle aufgerufen 
// - Verbindung zur Datenbank geschlossen 
public class ConnectPostgreSQL {

	public static void main(String args[]) throws Exception { 
		Class.forName("org.postgresql.Driver"); 
		String url = "jdbc:postgresql://localhost:5432/test"; 

		Connection conn = DriverManager.getConnection(url,"test",""); 
		Statement stmt = conn.createStatement(); 

		// Relation geomtest hat nur das Attribut mycirc 
		// mit dem Datentyp circle 
		stmt.execute("CREATE TEMP TABLE geomtest(mycirc circle)"); 
		stmt.close(); 
		PostGreProcedures.insertCircle(conn); 
		PostGreProcedures.retrieveCircle(conn); 
		conn.close(); 

	} 
	
}
