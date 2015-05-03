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
//		testInsertAmpel();
            try {
                test1();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                PostgreFunctions v2 = new PostgreFunctions();
                v2.insertInto(conn.getPostgreConnection());

		Document doc = null;
		try {
			
			doc = OSMReader.readXMLDocument("res/map(1)");

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

		OSMReader.ways(doc);
                NodeList d = doc.getElementsByTagName("way");
                
                NodeList n = doc.getElementsByTagName("node");
                NodeList wayNodes = doc.getElementsByTagName("node");
                OSMReader.justNodes(n);
                
                int p = OSMReader.position("26189935", n);
                System.out.println(p);
                
                
                System.out.println(d.getLength());
                System.out.println(n.getLength());
                for(int i = 0, l = wayNodes.getLength(); i < l; i++) {
                    Node node = n.item(i);
                    //System.out.println("node = " + node.getAttributes().getNamedItem("id").getNodeValue() + " " + node.hasChildNodes());
                    if(node.hasChildNodes()) {
                        //OSMReader.printCh(node.getChildNodes());
                    }
                }
                int p1 = d.getLength() / 2;
                int p2 = p1 /2;
                
                Node w = d.item(0);
                System.out.println("id = " + w.getAttributes().getNamedItem("id").getNodeValue());
                PGpolygon pg = OSMReader.wayNodess(w, n);
                System.out.println(pg);

                ArrayList<Node> aaa = OSMReader.tupel(d, "landuse");
                ArrayList<Node> see = OSMReader.tupel(d, "natural", "water");
                ArrayList<Node> riv = OSMReader.tupel(d, "waterway", "river");
                ArrayList<Node> park = OSMReader.tupel(d, "leisure", "park");
                ArrayList<Node> spiel = OSMReader.tupel(d, "leisure", "playground");
                ArrayList<Node> tram = OSMReader.tupel(d, "railway", "tram");
                ArrayList<Node> rail = OSMReader.tupel(d, "railway", "rail");
                ArrayList<Node> parking = OSMReader.tupel(d, "amenity", "parking");
                ArrayList<Geo> geo = OSMReader.justNodes(n);
                ArrayList<Node> house = OSMReader.tupel(d, "building", "yes");
                ArrayList<Node> ampel = OSMReader.tupel(n, "highway", "traffic_signals");
                ArrayList<Node> ampel2 = OSMReader.tupel(n, "crossing", "traffic_signals");
                ArrayList<String> sign = new ArrayList<String>();
                ArrayList<Node> stop = OSMReader.tupel(n, "highway", "bus_stop");
                ArrayList<Node> stop2 = OSMReader.tupel(n, "railway", "tram_stop");
                ArrayList<String> st = new ArrayList<String>();
                st.add("name");
                st.add("shelter");
                st.add("bus_routes");
                st.add("highway");
                ArrayList<NodeTupel> stops = OSMReader.nodeTable(stop, geo, st);
                ArrayList<NodeTupel> stops2 = OSMReader.nodeTable(stop2, geo, st);                
                ArrayList<Node> houseNodes = OSMReader.tupel(n, "addr:housenumber");
                ArrayList<String> hoa = new ArrayList<String>();
                hoa.add("addr:housenumber");
                hoa.add("addr:street");
                hoa.add("addr:postal_code");
                hoa.add("addr:city");
                hoa.add("buidling:levels");
                hoa.add("height");
                hoa.add("buidling:roof:shape");
                hoa.add("amenity");
                hoa.add("shop");
                hoa.add("tourism");
                hoa.add("operator");
                hoa.add("name");
                hoa.add("geb_nr");
                ArrayList<NodeTupel> houseNodess = OSMReader.nodeTable(houseNodes, geo, hoa);
                for(int i = 0; i < houseNodess.size(); i++) {
                    System.out.println(houseNodess.get(i));
                }
                sign.add("traffic_signals:sound");
                sign.add("traffic_signals:vibration");
                System.out.println("ampeln " + ampel.size());
                ArrayList<NodeTupel> ampeln = OSMReader.nodeTable(ampel, geo, sign);
                ArrayList<NodeTupel> ampeln2 = OSMReader.nodeTable(ampel2, geo, sign);
                System.out.println("ampeln " + ampeln.size());
                for(int i = 0; i < ampeln.size(); i++) {
                    System.out.println(ampeln.get(i));
                }
                System.out.println("ampeln2 " + ampeln2.size());
                for(int i = 0; i < ampeln2.size(); i++) {
                    System.out.println(ampeln2.get(i));
                }
                System.out.println("\n \n");
                //Tupel zur Landnutzung
                ArrayList<Tupel> landuse = new ArrayList<Tupel>();
                for(int i = 0, l = aaa.size(); i < l; i++) {
                    Node nd = aaa.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "landuse"));
                    landuse.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }
                
                //Tupel eines Flusses
                ArrayList<Tupel> river = new ArrayList<Tupel>();
                for(int i = 0, l = riv.size(); i < l; i++) {
                    Node nd = riv.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "name"));
                    river.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }
                

                //Tupel der Seen
                ArrayList<Tupel> sea = new ArrayList<Tupel>();
                for(int i = 0, l = see.size(); i < l; i++) {
                    Node nd = see.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "name"));
                    sea.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }
                
                //Tupel der Seen
                ArrayList<Tupel> parks = new ArrayList<Tupel>();
                for(int i = 0, l = park.size(); i < l; i++) {
                    Node nd = park.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "name"));
                    parks.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }
                //Tupel der Spielplätze
                ArrayList<Tupel> play = new ArrayList<Tupel>();
                for(int i = 0, l = spiel.size(); i < l; i++) {
                    Node nd = spiel.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "name"));
                    play.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }     
                //Tupel der Spielplätze
                ArrayList<Tupel> rails = new ArrayList<Tupel>();
                for(int i = 0, l = rail.size(); i < l; i++) {
                    Node nd = rail.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    //t.addString(OSMReader.valueOf(nd, "name"));
                    rails.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                } 
                //Tupel der Spielplätze
                ArrayList<Tupel> trams = new ArrayList<Tupel>();
                for(int i = 0, l = tram.size(); i < l; i++) {
                    Node nd = tram.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    //t.addString(OSMReader.valueOf(nd, "name"));
                    trams.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }    
                //Tupel der Spielplätze
                ArrayList<Tupel> parkings = new ArrayList<Tupel>();
                for(int i = 0, l = parking.size(); i < l; i++) {
                    Node nd = parking.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "name"));
                    t.addString(OSMReader.valueOf(nd, "access"));
                    t.addString(OSMReader.valueOf(nd, "fee"));
                    t.addString(OSMReader.valueOf(nd, "capacity"));
                    t.addString(OSMReader.valueOf(nd, "operator"));
                    parkings.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                } 
                ArrayList<Tupel> houses = new ArrayList<Tupel>();
                for(int i = 0, l = house.size(); i < l; i++) {
                    Node nd = house.get(i);
                    Tupel t = new Tupel();
                    t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                    t.addGeo(OSMReader.wayNodess(nd, geo));
                    t.addString(OSMReader.valueOf(nd, "addr:housenumber"));
                    t.addString(OSMReader.valueOf(nd, "addr:street"));
                    t.addString(OSMReader.valueOf(nd, "addr:postcode"));
                    t.addString(OSMReader.valueOf(nd, "addr:city"));
                    t.addString(OSMReader.valueOf(nd, "buidling:levels"));
                    t.addString(OSMReader.valueOf(nd, "height"));
                    t.addString(OSMReader.valueOf(nd, "buidling:roof:shape"));
                    t.addString(OSMReader.valueOf(nd, "amenity"));
                    t.addString(OSMReader.valueOf(nd, "shop"));
                    t.addString(OSMReader.valueOf(nd, "tourism"));
                    t.addString(OSMReader.valueOf(nd, "operator"));
                    t.addString(OSMReader.valueOf(nd, "name"));
                    t.addString(OSMReader.valueOf(nd, "geb_nr"));
                    houses.add(t);
                    //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
                }  
                System.out.println(aaa.size());
                System.out.println(landuse.size());
                EntityManager em = EntityManager.getInstance();
                System.out.println("landuse" + landuse.size());
//                for(int i = 0; i < landuse.size(); i++) {
//                    System.out.println(landuse.get(i));
//                }
                System.out.println("\n \n");
                System.out.println("sea " + sea.size());
//                for(int i = 0; i < sea.size(); i++) {
//                    System.out.println(sea.get(i));
//                }
                System.out.println("\n \n");
                System.out.println("river " + river.size());
//                for(int i = 0; i < river.size(); i++) {
//                    System.out.println(river.get(i));
//                }
//                System.out.println("\n \n");
                System.out.println("play " + play.size());
                for(int i = 0; i < play.size(); i++) {
                    System.out.println(play.get(i));
                }
                System.out.println("\n \n");
                System.out.println("parks " + parks.size());
//                for(int i = 0; i < parks.size(); i++) {
//                    System.out.println(parks.get(i));
//                }
                System.out.println("rail " + rails.size());
                System.out.println("trams " + trams.size());
                System.out.println("\n \n");
                System.out.println("parkings " + parkings.size());
//                for(int i = 0; i < parkings.size(); i++) {
//                    System.out.println(parkings.get(i));
//                }
                System.out.println("houses " + houses.size());
//                for(int i = 0; i < houses.size(); i++) {
//                    System.out.println(houses.get(i));
//                }
                ArrayList<Node> sec = OSMReader.tupel(d, "highway", "secondary");
                ArrayList<String> str = new ArrayList<String>();
                str.add("highway");
                str.add("name");
                str.add("oneway");
                str.add("maxspeed");
                str.add("surface");
                str.add("lanes");
                ArrayList<Tupel> secs = OSMReader.table(sec, geo, str);
                System.out.println("secs " + secs.size());
                for(int i = 0; i < secs.size(); i++) {
                    System.out.println(secs.get(i));
                }
                ArrayList<Node> tert = OSMReader.tupel(d, "highway", "tertiary");
                ArrayList<Tupel> terts = OSMReader.table(tert, geo, str);
                System.out.println("terts " + terts.size());
                ArrayList<Node> res = OSMReader.tupel(d, "highway", "residential");
                ArrayList<Tupel> ress = OSMReader.table(res, geo, str);
                System.out.println("ress " + ress.size());
                ArrayList<Node> liv = OSMReader.tupel(d, "highway", "living_street");
                ArrayList<Tupel> livs = OSMReader.table(liv, geo, str);
                System.out.println("livs " + livs.size());
                ArrayList<Node> unc = OSMReader.tupel(d, "highway", "unclassified");
                ArrayList<Tupel> uncs = OSMReader.table(unc, geo, str);
                System.out.println("uncs " + uncs.size());
                ArrayList<Node> serv = OSMReader.tupel(d, "highway", "service");
                ArrayList<Tupel> servs = OSMReader.table(serv, geo, str);
                System.out.println("servs " + servs.size());
                ArrayList<Node> brd = OSMReader.tupel(d, "bridge", "yes");
                ArrayList<Tupel> brds = OSMReader.table(brd, geo, str);
                System.out.println("brds " + brds.size());
                ArrayList<Node> tun = OSMReader.tupel(d, "tunnel", "yes");
                ArrayList<Tupel> tuns = OSMReader.table(tun, geo, str);
                System.out.println("tuns " + tuns.size());
                
                //em.persistRails(parkings, "parkplatz");
                
                //Einfügen aller Straßen
//                em.persist(secs, "straße");
//                em.persist(terts, "straße");
//                em.persist(ress, "straße");
//                em.persist(livs, "straße");
//                em.persist(uncs, "straße");
//                em.persist(servs, "straße");
                //Einfügen der Spielplätze
                //em.persist(play, "spielplatz");
                
                em.persist(houses, "haus");
                em.persist2(houseNodess, "haus");
                
	}

}
