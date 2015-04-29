package de.tubs.labic.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.geometric.PGpath;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;

public class EntityManager {

	private final static String INSERT_BRUECKE_SQL = "";

	private java.sql.Connection connection;

	private static final EntityManager instance;

	static {
		try {
			java.sql.Connection c = new Connection().getPostgreConnection();
			c.setAutoCommit(true);
			instance = new EntityManager(c);
		} catch (Exception e) {
			throw new RuntimeException("Fehler aufgetreten!", e);
		}
	}

	public static EntityManager getInstance() {
		return instance;
	}

	private EntityManager(java.sql.Connection connection) {
		this.connection = connection;
	}

	private final static String INSERT_GEBAEUDE_SQL = "INSERT INTO gebaeude "
			+ "(name,nummer,plz,strasse,stadt,stockwerke,hoehe,dach,einrichtung,shop,tourismus,betreiber,geb_nr,geo) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	public void persistGebaeude(String name, int nummer, int plz,
			String strasse, String stadt, int stockwerke, double hoehe,
			String dach, String einrichtung, String shop, String tourismus,
			String betreiber, int geb_nr, PGpolygon geo) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_GEBAEUDE_SQL);

		s.setString(1, name);
		s.setInt(2, nummer);
		s.setInt(3, plz);
		s.setString(4, strasse);
		s.setString(5, stadt);
		s.setInt(6, stockwerke);
		s.setDouble(7, hoehe);
		s.setString(8, dach);
		s.setString(9, einrichtung);
		s.setString(10, shop);
		s.setString(11, tourismus);
		s.setString(12, betreiber);
		s.setInt(13, geb_nr);
		s.setObject(14, geo);

		int ret = s.executeUpdate();
		System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_AMPEL_SQL = "INSERT INTO ampel (signal,geo) VALUES (?,?);";

	public void persistAmpel(String signalType, PGpoint point)
			throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_AMPEL_SQL);

		s.setString(1, signalType);
		s.setObject(2, point);
		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	public void persistBruecke(int f) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement(INSERT_BRUECKE_SQL);

		// stmt.setObject(parameterIndex, x);
		int ret = stmt.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		stmt.close();
	}

	private final static String INSERT_FLUSS_SQL = "INSERT INTO fluss (name,geo) VALUES (?,?);";

	public void persistFluss(String name, PGpath geo) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_FLUSS_SQL);

		s.setString(1, name);
		s.setObject(2, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_TUNNEL_FLUSS_SQL = "INSERT INTO tunnel_fluss (name,geo) VALUES (?,?);";

	public void persistTunnelFluss(String name, PGpath geo) throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_TUNNEL_FLUSS_SQL);

		s.setString(1, name);
		s.setObject(2, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_HALTESTELLE_SQL = "INSERT INTO haltestelle (typ,name,unterstand,routen,geo) VALUES (?,?,?,?,?);";

	public void persistHaltestelle(String typ, String name, boolean unterstand,
			String routen, PGpoint geo) throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_HALTESTELLE_SQL);

		s.setString(1, typ);
		s.setString(2, name);
		s.setBoolean(3, unterstand);
		s.setString(4, routen);
		s.setObject(5, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_LANDNUTZUNG_SQL = "INSERT INTO landnutzung (geo) VALUES (?);";

	public void persistLandnutzung(PGpolygon geo) throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_LANDNUTZUNG_SQL);

		s.setObject(1, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_PARK_SQL = "INSERT INTO platz (typ,name,geo) VALUES (?,?,?);";

	public void persistPark(String name, PGpolygon geo) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_PARK_SQL);

		s.setString(1, "park");
		s.setString(2, name);
		s.setObject(3, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_PARKPLATZ_SQL = "INSERT INTO parkplatz (name,zugang,entgelt,kapazitaet,betreiber,geo) VALUES (?,?,?,?,?,?);";

	public void persistParkplatz(String name, String zugang, String entgelt,
			int kapazitaet, String betreiber, PGpath geo) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_PARKPLATZ_SQL);

		s.setString(1, name);
		s.setString(2, zugang);
		s.setString(3, entgelt);
		s.setInt(4, kapazitaet);
		s.setString(5, betreiber);
		s.setObject(6, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_SEE_SQL = "INSERT INTO see (name,geo) VALUES (?,?);";

	public void persistSee(String name, PGpolygon geo) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_SEE_SQL);

		s.setString(1, name);
		s.setObject(2, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_SPIELPLATZ_SQL = "INSERT INTO platz (typ,name,geo) VALUES (?,?,?);";

	public void persistSpielplatz(String name, PGpolygon geo)
			throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_SPIELPLATZ_SQL);

		s.setString(1, "playground");
		s.setString(2, name);
		s.setObject(3, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_STRASSE_SQL = "INSERT INTO strasse (name,typ,einbahnstrasse,geschwindigkeit,belag,spuren,geo) VALUES (?,?,?,?,?,?,?);";

	public void persistStrasse(String name, String typ, boolean einbahnstrasse,
			int geschwindigkeit, String belag, int spuren, PGpath geo)
			throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_STRASSE_SQL);

		s.setString(1, name);
		s.setString(2, typ);
		s.setBoolean(3, einbahnstrasse);
		s.setInt(4, geschwindigkeit);
		s.setString(5, belag);
		s.setInt(6, spuren);
		s.setObject(7, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_TUNNEL_STRASSE_SQL = "INSERT INTO tunnel_strasse (name,typ,einbahnstrasse,geschwindigkeit,belag,spuren,geo) VALUES (?,?,?,?,?,?,?);";

	public void persistTunnelStrasse(String name, String typ,
			boolean einbahnstrasse, int geschwindigkeit, String belag,
			int spuren, PGpath geo) throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_TUNNEL_STRASSE_SQL);

		s.setString(1, name);
		s.setString(2, typ);
		s.setBoolean(3, einbahnstrasse);
		s.setInt(4, geschwindigkeit);
		s.setString(5, belag);
		s.setInt(6, spuren);
		s.setObject(7, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_BRUECKE_STRASSE_SQL = "INSERT INTO bruecke_tunnel_strasse (name,typ,einbahnstrasse,geschwindigkeit,belag,spuren,geo) VALUES (?,?,?,?,?,?,?);";

	public void persistBrueckeStrasse(String name, String typ,
			boolean einbahnstrasse, int geschwindigkeit, String belag,
			int spuren, PGpath geo) throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_BRUECKE_STRASSE_SQL);

		s.setString(1, name);
		s.setString(2, typ);
		s.setBoolean(3, einbahnstrasse);
		s.setInt(4, geschwindigkeit);
		s.setString(5, belag);
		s.setInt(6, spuren);
		s.setObject(7, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_STRASSENBAHN_SQL = "INSERT INTO bahn (typ,geo) VALUES (?,?);";

	public void persistStrassenbahn(PGpath geo) throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_STRASSENBAHN_SQL);

		s.setString(1, "tram");
		s.setObject(2, geo);

		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_EISENBAHN_SQL = "INSERT INTO bahn (typ,geo) VALUES (?,?);";

	public void persistEisenbahn(PGpath geo) throws SQLException {
		PreparedStatement s = connection.prepareStatement(INSERT_EISENBAHN_SQL);

		s.setString(1, "rail");
		s.setObject(2, geo);

		// stmt.setObject(parameterIndex, x);
		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_TUNNEL_SCHIENE_SQL = "INSERT INTO tunnel_schiene (typ,geo) VALUES (?,?);";

	public void persistTunnelSchiene(String typ, PGpath geo)
			throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_TUNNEL_SCHIENE_SQL);
		s.setString(1, typ);
		s.setObject(2, geo);
		// stmt.setObject(parameterIndex, x);
		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}

	private final static String INSERT_BRUECKE_SCHIENE_SQL = "INSERT INTO bruecke_schiene (typ,geo) VALUES (?,?);";

	public void persistBrueckeSchiene(String typ, PGpath geo)
			throws SQLException {
		PreparedStatement s = connection
				.prepareStatement(INSERT_BRUECKE_SCHIENE_SQL);
		s.setString(1, typ);
		s.setObject(2, geo);
		// stmt.setObject(parameterIndex, x);
		int ret = s.executeUpdate();
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}
}
