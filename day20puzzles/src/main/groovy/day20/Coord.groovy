package day20

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.MapConstructor
import groovy.transform.ToString

@Canonical
@EqualsAndHashCode(includes=['x','y'])
@ToString(includeFields=true, includes=['x', 'y'])
@MapConstructor
class Coord {

	final int x
	final int y
	
	Coord getWest() {
		return new Coord(x: x-1, y: y)
	}
	
	Coord getEast() {
		return new Coord(x: x+1, y: y)
	}
	
	Coord getNorth() {
		return new Coord(x: x, y: y-1)
	}
	
	Coord getSouth() {
		return new Coord(x: x, y: y+1)
	}
}
