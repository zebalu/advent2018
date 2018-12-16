package day15puzzles

import groovy.transform.Canonical

@Canonical
class Coordinate {

	int x
	int y
	
	List<Coordinate> adjecents() {
		return [new Coordinate(x, y-1), new Coordinate(x-1, y), new Coordinate(x+1, y), new Coordinate(x, y+1)]
	}
}
