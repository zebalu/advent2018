package day22puzzles

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

@Canonical
@Sortable(includes=['x', 'y'])
@ToString(includes=['x', 'y'])
@EqualsAndHashCode(includes=['x', 'y'])
class Coord {

	int x
	int y
	
	Coord getUp() {
		return new Coord(x, y-1)
	}
	
	Coord getLeft() {
		return new Coord(x-1, y)
	}
	
	Coord getDown() {
		return new Coord(x, y+1)
	}
	
	Coord getRight() {
		return new Coord(x+1, y)
	}
	
	List<Coord> getValidNeighBours() {
		def result = []
		if(y>0) {
			result << up
		}
		result << down
		result << right
		if(x>0) {
			result << left
		}
		return result
	}
	
	boolean isAdjecent(Coord coord) {
		if((Math.abs(coord.x-x) + Math.abs(coord.y+y)) == 1) {
			return true
		}
		return false
	}
	
	int distance(Coord c) {
		return Math.abs(c.x-x)+Math.abs(c.y-y)
	}
}
