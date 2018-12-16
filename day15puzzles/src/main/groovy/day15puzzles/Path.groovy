package day15puzzles

import groovy.transform.Canonical

@Canonical
class Path {
	List<Coordinate> pathCoordinates = []
	
	Path() {
		this(null, null)
	}
	
	Path(Path previous, Coordinate next) {
		if(previous) {
			pathCoordinates.addAll(previous.pathCoordinates)
		}
		if(next) {
			pathCoordinates.add(next)
		}
	}
	
	Coordinate firstStep() {
		if(pathCoordinates.isEmpty()) {
			throw new IllegalStateException("There is no first step on this path!")
		}
		return pathCoordinates[0]
	}
	
	Coordinate lastStep() {
		if(pathCoordinates.isEmpty()) {
			throw new IllegalStateException("There is no last step on this path!")
		}
		return pathCoordinates[-1]
	}
	
	int size() {
		return pathCoordinates.size()
	}
 }
