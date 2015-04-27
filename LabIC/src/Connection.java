import java.sql.DriverManager;
import java.sql.SQLException;



public class Connection {
	/*
	 * Datenbankverbindung herstellen.
	 * @return conn Datenbankverbindungsobjekt
	 */
	public java.sql.Connection getPostgreConnection() throws SQLException {
		java.sql.Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver"); 
			String url = "jdbc:postgresql://localhost:5432/postgres"; 
			conn = DriverManager.getConnection(url,"postgres","sommer15");

			if (!conn.isClosed()) {
				System.out.println("######## Datenbankverbindung steht #########");
			}
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		}

		return conn;
	}
}
