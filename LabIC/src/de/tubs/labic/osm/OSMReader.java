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
	
        
        public static void nodes(Document doc) {
            int c = 0;
            NodeList n = doc.getElementsByTagName("node");
            for(int i = 0; i < n.getLength(); i++) {
                Node n2 = n.item(i);
                if(n2.hasChildNodes() && n2.getFirstChild() != null) {
                    for(int j = 0; j < n2.getChildNodes().getLength(); j++) {
                        Node ch = n2.getChildNodes().item(j);
                        //Attribute werden als NamedNodeMap gespeichert 
                        // diese können der Reeihe nach abgefragt werden
                        if(ch.getAttributes()!= null && ch.getAttributes().getNamedItem("k").getNodeValue().equals("crossing") && ch.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals")) {
                    
                            System.out.println("Japp ");}
                
                         }
                        //System.out.println("-------------");
                    }
                }     
        }
        
        public static void ways(Document doc) {
            NodeList n = doc.getElementsByTagName("way");
            System.out.println(n.getLength());
        }
        

        
        public static void classifier(Document doc) {
            NodeList w = doc.getElementsByTagName("way");
            NodeList n = doc.getElementsByTagName("highway");
            System.out.println(n.getLength());
            
           
            
        }
        
        public static NodeList stops(NodeList n) {
            System.out.println(n.getLength());
            for(int i = 0; i < n.getLength(); i++) {
                NodeList ns = n.item(i).getChildNodes();
                for(int j = 0; j < ns.getLength(); j++) {
                    Node no = ns.item(j);
                    if(no != null && no.getNodeName().contains("tag")) {
                        if(no.getAttributes().getNamedItem("k").getNodeValue().equals("highway") && no.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals")) {

                            System.out.println(no.getParentNode().getAttributes().getNamedItem("id"));
                        }
                    }
                }
            }
            return null;
        }
        
        public static NodeList stops(NodeList n, int p1, int p2) {
           ArrayList<Node> nd = new ArrayList<Node>();
           

            System.out.println(n.getLength());
            for(int i = p1; i < p2; i++) {
                NodeList ns = n.item(i).getChildNodes();
                for(int j = 0; j < ns.getLength(); j++) {
                    Node no = ns.item(j);
                    if(no != null && no.getNodeName().contains("tag")) {
                        if(no.getAttributes().getNamedItem("k").getNodeValue().equals("highway") && no.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals") || no.getAttributes().getNamedItem("k").getNodeValue().equals("crossing") && no.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals")) {
                            nd.add(no.getParentNode());
                            System.out.println(no.getParentNode().getAttributes().getNamedItem("id") + " " + no.getParentNode().getAttributes().getNamedItem("lat") + " " + no.getParentNode().getAttributes().getNamedItem("lon"));
                            float lon = Float.valueOf(no.getParentNode().getAttributes().getNamedItem("lon").getNodeValue());
                            
                            BigInteger id = new BigInteger(no.getParentNode().getAttributes().getNamedItem("id").getNodeValue());
                            System.out.println("lon = " + lon + ", id " + id);
                            
                        }
                    }
                }
            }
            System.out.println("-------------------");
            System.out.println(nd.size());
            for(int p = 0; p < nd.size(); p++) {
                Node node  = nd.get(p);
                for(int i = 0; i < node.getAttributes().getLength(); i++) {
                    System.out.println(node.getAttributes().item(i));
                }
                NodeList child = node.getChildNodes();
                printCh(child);
                
                System.out.println("******");
            }
            System.out.println(nd.size());
            return null;
        }
        
        //Testumgebubng
        public static NodeList stops(labic.db.Connection conn, PostgreFunctions pgf, NodeList n, int p1, int p2) {
           ArrayList<Node> nd = new ArrayList<Node>();
           

            System.out.println(n.getLength());
            for(int i = p1; i < p2; i++) {
                NodeList ns = n.item(i).getChildNodes();
                for(int j = 0; j < ns.getLength(); j++) {
                    Node no = ns.item(j);
                    if(no != null && no.getNodeName().contains("tag")) {
                        //if( (no.getAttributes().getNamedItem("k").getNodeValue().equals("highway") && no.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals") ) || (no.getAttributes().getNamedItem("k").getNodeValue().equals("crossing") && no.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals")) )
                        //if( (no.getAttributes().getNamedItem("k").getNodeValue().equals("highway") && no.getAttributes().getNamedItem("v").getNodeValue().equals("traffic_signals") )  ) {
                        if( (no.getAttributes().getNamedItem("k").getNodeValue().equals("traffic_signals:vibration"))  ) {
                            if(!nd.contains(no.getParentNode())) {
                            
                            nd.add(no.getParentNode());
                            System.out.println(no.getParentNode().getAttributes().getNamedItem("id") + " " + no.getParentNode().getAttributes().getNamedItem("lat") + " " + no.getParentNode().getAttributes().getNamedItem("lon"));
                            
                            }

                        }
                    }
                }
            }
            System.out.println("-------------------");
            System.out.println(nd.size());
            for(int p = 0; p < nd.size(); p++) {
                Node node  = nd.get(p);
                for(int i = 0; i < node.getAttributes().getLength(); i++) {
                    System.out.println(node.getAttributes().item(i));
                }
                NodeList child = node.getChildNodes();
                printCh(child);
                
                System.out.println("******");
            }
            System.out.println(nd.size());
            try {
                //SQL INSERT
                updateAmpel(nd, conn.getPostgreConnection(), "vibration");
                
            } catch (SQLException ex) {
                Logger.getLogger(OSMReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }     
        
        
        
	public static void persistAmpel(ArrayList<Node> nd, java.sql.Connection conn, String val)
			throws SQLException {
		PreparedStatement s = conn.prepareStatement("INSERT INTO test2(id, geo, lage) VALUES(?, POINT(?, ?), ?);");
                for(int i = 0; i < nd.size(); i++) {
                    nd.get(i).getAttributes().getNamedItem("id").getNodeValue();
                    s.setLong(1, Long.valueOf(nd.get(i).getAttributes().getNamedItem("id").getNodeValue()));
                    s.setFloat(2, Float.valueOf(nd.get(i).getAttributes().getNamedItem("lat").getNodeValue()));
                    s.setFloat(3, Float.valueOf(nd.get(i).getAttributes().getNamedItem("lon").getNodeValue()));
                    s.setString(4, val);
                    int ret = s.executeUpdate();
                }
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}
        
	public static void updateAmpel(ArrayList<Node> nd, java.sql.Connection conn, String val)
			throws SQLException {
		PreparedStatement s = conn.prepareStatement("UPDATE test2 SET signal = ? WHERE id = ?");
                for(int i = 0; i < nd.size(); i++) {
                    nd.get(i).getAttributes().getNamedItem("id").getNodeValue();
                    s.setString(1, val);
                    s.setLong(2, Long.valueOf(nd.get(i).getAttributes().getNamedItem("id").getNodeValue()) );
                    int ret = s.executeUpdate();
                }
		// System.out.println("INSERT Gebaeude returned: " + ret);
		s.close();
	}      
        public static void printCh(NodeList n) {
            if(n != null) {
                for(int i = 0; i < n.getLength(); i++) {
                    Node node = n.item(i);
                    if(node.getAttributes() != null) {
                        for(int j = 0; j < node.getAttributes().getLength(); j++) {
                            if(node.getNodeName().equals("tag")) {
                                if(node.getAttributes().getNamedItem("k").getNodeValue().equals("name")) {
                                    System.out.println(node.getAttributes().getNamedItem("v").getNodeValue());
                                }
                                    
                            }
                            
                        }
                    }
                    
                }
            }
        }
        
        
        //Ausgabe eines bestimmtes Wertes für ein Tag,
        //z.B. name eines Flusses
        // Tupel kann z.B. durch id + Attribute in einem String erzeugt werden
        public static String valueOf(Node n, String v) {
            String s = "";
            if(n != null) {
                NodeList nl = n.getChildNodes();
                for(int i = 0; i < nl.getLength(); i++) {
                    Node node = nl.item(i);
                    if(node.getAttributes() != null) {
                        if(node.getNodeName().equals("tag")) {
                            if(node.getAttributes().getNamedItem("k").getNodeValue().equals(v)) {
                                //System.out.println(node.getParentNode().getAttributes().getNamedItem("id") + "Hier >>> " + node.getAttributes().getNamedItem("v").getNodeValue());
                                s = node.getAttributes().getNamedItem("v").getNodeValue();
                            }
                        }
                    }    
                }
            }
            return s;
        }
        

        
        
     
                  
                    //getNodeName() >> gibt k oder v aus
                    //getNamedItem für einzelne tags wie k oder v
                    //getNodeValue() >> gibt Wert aus, z.B. traffic_signals
                    //System.out.println(nnm.item(i).getNodeName());
                    //Node n = nnm.item(i);

        
        
    /*
     * Ausgelesenes Dokument in Konsolge anzeigen
     */
	public static void printToConsole(Document doc) {
        DOMImplementationLS domImplLS = (DOMImplementationLS) doc
            .getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(doc);
        System.out.println(str);
	}

   
        
        //Liefert alle Nodes, die ein bestimmtes tag erfüllen
        public static ArrayList<Node> tupel(NodeList n, String k) {
            ArrayList<Node> nd = new ArrayList<Node>();
            if(n != null) { 
                System.out.println(n.getLength());
                for(int i = 0, l = n.getLength(); i < l; i++) {
                    NodeList ns = n.item(i).getChildNodes();
                    for(int j = 0; j < ns.getLength(); j++) {
                        Node no = ns.item(j);
                        PGHelper p = new PGHelper();
                        if(no != null && no.getNodeName().contains("tag")) {
                            if( (no.getAttributes().getNamedItem("k").getNodeValue().equals(k))) {
                                nd.add(no.getParentNode());
                                //System.out.println("way" + no.getParentNode().getAttributes().getNamedItem("id") + " " + no.getAttributes().getNamedItem("k").getNodeValue() + " " + no.getAttributes().getNamedItem("v").getNodeValue());
                            }
                        }
                    }
                }    
            }
            return nd;
        }
        
        //Liefert alle Nodes, die ein bestimmtes tag erfüllen
        public static ArrayList<Node> tupel(NodeList n, String k, String v) {
            ArrayList<Node> nd = new ArrayList<Node>();
            if(n != null) { 
                System.out.println(n.getLength());
                for(int i = 0, l = n.getLength(); i < l; i++) {
                    NodeList ns = n.item(i).getChildNodes();
                    for(int j = 0; j < ns.getLength(); j++) {
                        Node no = ns.item(j);
                        PGHelper p = new PGHelper();
                        if(no != null && no.getNodeName().contains("tag")) {
                            if( (no.getAttributes().getNamedItem("k").getNodeValue().equals(k)) && (no.getAttributes().getNamedItem("v").getNodeValue().equals(v))) {
                                nd.add(no.getParentNode());
                               // System.out.println("way" + no.getParentNode().getAttributes().getNamedItem("id") + " " + no.getAttributes().getNamedItem("k").getNodeValue() + " " + no.getAttributes().getNamedItem("v").getNodeValue());
                            }
                        }
                    }
                }    
            }
            return nd;
        }

       //Sucht eine Knotenposition inenrhalb der geordneten NodeList
        public static int position(String s, NodeList n) {
            long val = Long.valueOf(s);
            int lo = 0;
            int hi = n.getLength() - 1;
            while (hi >= lo) {
                int mid = (lo + hi) / 2;
                long v2 = Long.valueOf(n.item(mid).getAttributes().getNamedItem("id").getNodeValue());
                //System.out.println(v2 + " lo = " + lo + " mid = " + mid + " hi = "+ hi);
                if(v2 == val) {
                    return mid;
                } 
                if (v2 < val) {
                    lo = mid + 1;
                }
                if (v2 > val) {
                    hi = mid - 1;
                }

            }
            return -1;
        }
        
        //Sucht eine Knotenposition inenrhalb der geordneten NodeList
        public static int position(String s, ArrayList<Geo> n) {
            long val = Long.valueOf(s);
            int lo = 0;
            int hi = n.size() - 1;
            while (hi >= lo) {
                int mid = (lo + hi) / 2;
                long v2 = Long.valueOf(n.get(mid).id());
                //System.out.println(v2 + " lo = " + lo + " mid = " + mid + " hi = "+ hi);
                if(v2 == val) {
                    return mid;
                } 
                if (v2 < val) {
                    lo = mid + 1;
                }
                if (v2 > val) {
                    hi = mid - 1;
                }

            }
            return -1;
        }
        
        //Methode zum Auslesen der Knoten eines Weges
        //Beispiel-way mit Knoten
        //        7975411 ref 59654705
        //        7975411 ref 443459322
        //        7975411 ref 59654706
        //        7975411 ref 27375096
        public static ArrayList<Node> wayNodes(Node w, NodeList nl) {
            PGHelper pg = new PGHelper();
            NodeList n = w.getChildNodes();
            ArrayList<Node> nodes = new ArrayList<Node>();
            if(n != null) {
                for(int i = 0, l = n.getLength(); i < l; i++) {
                   Node no = n.item(i);
                   if(no != null && no.getNodeName().contains("nd")) {
                       String ref = no.getAttributes().getNamedItem("ref").getNodeValue();
                       //zugehöriger Knoten zur Referenz
                       Node no2 = nl.item(position(ref, nl));
                       //Findet die zugehörigen Knoten eines Weges
                       //System.out.println("ref " + no.getAttributes().getNamedItem("ref").getNodeValue() + " orig " + no2.getAttributes().getNamedItem("id").getNodeValue());
                       nodes.add(no2);
                       pg.addKoords(no2.getAttributes().getNamedItem("lat").getNodeValue(), no2.getAttributes().getNamedItem("lon").getNodeValue());
                        
                   }
                    
                }
            }
            //System.out.println("list >> " + nodes.size() + " nodes" );
            //System.out.println( w.getAttributes().getNamedItem("id").getNodeValue() + "," + pg.polygonToString() );
            return nodes;
        }
        
        public static PGpolygon wayNodess(Node w, NodeList nl) {
            PGHelper pg = new PGHelper();
            NodeList n = w.getChildNodes();
            ArrayList<Node> nodes = new ArrayList<Node>();
            if(n != null) {
                for(int i = 0, l = n.getLength(); i < l; i++) {
                   Node no = n.item(i);
                   if(no != null && no.getNodeName().contains("nd")) {
                       String ref = no.getAttributes().getNamedItem("ref").getNodeValue();
                       //zugehöriger Knoten zur Referenz
                       Node no2 = nl.item(position(ref, nl));
                       //Findet die zugehörigen Knoten eines Weges
                       //System.out.println("ref " + no.getAttributes().getNamedItem("ref").getNodeValue() + " orig " + no2.getAttributes().getNamedItem("id").getNodeValue());
                       nodes.add(no2);
                       pg.addKoords(no2.getAttributes().getNamedItem("lat").getNodeValue(), no2.getAttributes().getNamedItem("lon").getNodeValue());
                   }
                }
            }
//            System.out.println("list >> " + nodes.size() + " nodes" );
//            System.out.println( w.getAttributes().getNamedItem("id").getNodeValue() + "," + pg.polygonToString() );
//            System.out.println(pg.getPGPolygon());
            return pg.getPGPolygon();
        }
        
        public static PGpolygon wayNodess(Node w, ArrayList<Geo> nl) {
            PGHelper pg = new PGHelper();
            NodeList n = w.getChildNodes();
            ArrayList<Geo> nodes = new ArrayList<Geo>();
            if(n != null) {
                for(int i = 0, l = n.getLength(); i < l; i++) {
                   Node no = n.item(i);
                   if(no != null && no.getNodeName().contains("nd")) {
                       String ref = no.getAttributes().getNamedItem("ref").getNodeValue();
                       //zugehöriger Knoten zur Referenz
                       Geo no2 = nl.get(position(ref, nl));
                       //Findet die zugehörigen Knoten eines Weges
                       //System.out.println("ref " + no.getAttributes().getNamedItem("ref").getNodeValue() + " orig " + no2.getAttributes().getNamedItem("id").getNodeValue());
                       nodes.add(no2);
                       pg.addKoords(no2.co().x, no2.co().y);
                   }
                }
            }
//            System.out.println("list >> " + nodes.size() + " nodes" );
//            System.out.println( w.getAttributes().getNamedItem("id").getNodeValue() + "," + pg.polygonToString() );
//            System.out.println(pg.getPGPolygon());
            return pg.getPGPolygon();
        }
        
        
        //Methode zur Ausgabe der Koordinaten der Knoten eines ways
        public static ArrayList<String> coord(Node n) {
            ArrayList<String> l = new ArrayList<String>();
            l.add(n.getAttributes().getNamedItem("lat").getNodeValue());
            l.add(n.getAttributes().getNamedItem("lon").getNodeValue());
            for(int i = 0; i < l.size(); i++) {
                System.out.println(l.get(i));
            }
            return l;
        }
              
        //Liefert nur die id und die x/y-Koordinaten aller Knoten in der Liste n
        //Dadurch bekommt man die Knoten für way-Typen effizienter
        public static ArrayList<Geo> justNodes(NodeList n) {
            ArrayList<Geo> nodes = new ArrayList<Geo>();
            for(int i = 0, l = n.getLength(); i < l; i++) {
                Node node = n.item(i);
                    Geo g = new Geo(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()), Float.valueOf(node.getAttributes().getNamedItem("lat").getNodeValue()), Float.valueOf(node.getAttributes().getNamedItem("lon").getNodeValue()));
                    nodes.add(g);
            }
            System.out.println(n.getLength() + " " + nodes.size());
            return nodes;
         }
        
        
        //Tupel der Spielplätze
        public static ArrayList<Tupel> table(ArrayList<Node> nodes, ArrayList<Geo> geo, ArrayList<String> s){
            ArrayList<Tupel> table = new ArrayList<Tupel>();
            for(int i = 0, l = nodes.size(); i < l; i++) {
                Node nd = nodes.get(i);
                Tupel t = new Tupel();
                t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                t.addGeo(OSMReader.wayNodess(nd, geo));
                for(int j = 0, ls = s.size(); j < ls; j++) {
                    t.addString(OSMReader.valueOf(nd, s.get(j)));
                }
                table.add(t);
            }
            return table;
            //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
        } 
        
                //Tupel der Spielplätze
        public static ArrayList<NodeTupel> nodeTable(ArrayList<Node> nodes, ArrayList<Geo> geo, ArrayList<String> s){
            ArrayList<NodeTupel> table = new ArrayList<NodeTupel>();
            for(int i = 0, l = nodes.size(); i < l; i++) {
                Node nd = nodes.get(i);
                NodeTupel t = new NodeTupel();
                t.addId(nd.getAttributes().getNamedItem("id").getNodeValue());
                PGpoint p = new PGpoint(Float.valueOf(nd.getAttributes().getNamedItem("lat").getNodeValue()), Float.valueOf(nd.getAttributes().getNamedItem("lat").getNodeValue()));
                t.addGeo(p);
                for(int j = 0, ls = s.size(); j < ls; j++) {
                    t.addString(OSMReader.valueOf(nd, s.get(j)));
                }
                table.add(t);
            }
            return table;
            //System.out.println(aaa.get(i).getAttributes().getNamedItem("id").getNodeValue() + " " + OSMReader.wayNodess(aaa.get(i), n) + " " + OSMReader.valueOf(aaa.get(i), "landuse"));
        } 
}
