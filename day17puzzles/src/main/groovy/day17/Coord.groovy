package day17

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includes=['x', 'y'])
class Coord {

	int x
	int y
	
	Coord getDown() {
		new Coord(x, y+1)
	}
	
	Coord getUp() {
		new Coord(x, y-1)
	}
	
	Coord getLeft() {
		new Coord(x-1, y)
	}
	
	Coord getRight() {
		new Coord(x+1, y)
	}
}
