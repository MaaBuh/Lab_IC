import java.sql.*;


public class Main {

	public static void main(String[] args) throws SQLException {
		Connection conn = new Connection();
		System.out.println("########");
		
		PostgreFunctions var1 = new PostgreFunctions();
		var1.createTable(conn.getPostgreConnection());
		var1.dropTable(conn.getPostgreConnection());
		
		
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
