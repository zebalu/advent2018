package day25

import groovy.transform.Canonical

@Canonical
class Coord {

	int x
	int y
	int z
	int t
	
	int distance(Coord c) {
		return Math.abs(x-c.x)+Math.abs(y-c.y)+Math.abs(z-c.z)+Math.abs(t-c.t)
	}
	
	static Coord parse(String line) {
		String[] coords = line.split(',')
		return new Coord(
				x: coords[0] as Integer,
				y: coords[1] as Integer,
				z: coords[2] as Integer,
				t: coords[3] as Integer,
			)
	}
	
}
