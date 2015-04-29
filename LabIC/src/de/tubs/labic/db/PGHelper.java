package de.tubs.labic.db;

import java.util.ArrayList;
import java.util.List;

import org.postgresql.geometric.PGpath;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;

public class PGHelper {
	private List<Double[]> list;

	public PGHelper() {
		list = new ArrayList<Double[]>();
	}

	public void addKoords(String lat, String lon) {
		addKoords(Double.valueOf(lat), Double.valueOf(lon));
	}

	public void addKoords(Double lat, Double lon) {
		list.add(new Double[] { lat, lon });
	}

	private static PGpoint convert(Double[] d) {
		PGpoint p = new PGpoint(d[0], d[1]);

		return p;
	}

	private static PGpoint[] convertList(List<Double[]> list) {
		PGpoint[] points = new PGpoint[list.size()];

		for (int i = 0; i < list.size(); i++) {
			points[i] = convert(list.get(i));
		}

		return points;
	}

	public PGpoint getPGPoint() {
		if (list.size() == 1) {
			return convert(list.get(0));
		}

		throw new IllegalStateException("Falsche list-groesse getPGPoint: "
				+ list.size());
	}

	public PGpath getPGPath() {
		if (list.size() > 1) {
			PGpath p = new PGpath(convertList(list), true);

			return p;
		}

		throw new IllegalStateException("Falsche list-groesse getPGPath: "
				+ list.size());
	}

	public PGpolygon getPGPolygon() {
		if (list.size() > 0) {
			PGpolygon p = new PGpolygon(convertList(list));

			return p;
		}

		throw new IllegalStateException("Falsche list-groesse getPGPolygon: "
				+ list.size());
	}
}
