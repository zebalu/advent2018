package day18

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includes=['x', 'y'])
class Coord {

	byte x
	byte y
	
	Coord getLeftUp() {
		return new Coord((byte)(x-1), (byte)(y-1))
	}
	
	Coord getUp() {
		return new Coord(x, (byte)(y-1))
	}
	
	Coord getRightUp() {
		return new Coord((byte)(x+1), (byte)(y-1))
	}
	
	Coord getRight() {
		return new Coord((byte)(x+1), y)
	}
	
	Coord getRightDown() {
		return new Coord((byte)(x+1), (byte)(y+1))
	}
	
	Coord getDown() {
		return new Coord(x, (byte)(y+1))
	}
	
	Coord getLeftDown() {
		return new Coord((byte)(x-1), (byte)(y+1))
	}
	
	Coord getLeft() {
		return new Coord((byte)(x-1), y)
	}
	
	List<Coord> getAdjecentCoords() {
		return [left, leftUp, up, rightUp, right, rightDown, down, leftDown]
	}
}
