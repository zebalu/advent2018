package day20

import groovy.transform.EqualsAndHashCode
import groovy.transform.MapConstructor
import groovy.transform.TupleConstructor

@TupleConstructor
@MapConstructor
@EqualsAndHashCode(includes='coord')
class Room implements Comparable<Room> {

	final Coord coord
	Room east
	Room west
	Room north
	Room south
	
	List<Room> getConnectedRooms() {
		[west, north, east, south]
	}
	
	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append('#')
		if(north) {
			sb.append('-')
		} else {
			sb.append('#')
		}
		sb.append('#\n')
		if(west) {
			sb.append('|')
		} else {
			sb.append('#')
		}
		sb.append('.')
		if(east) {
			sb.append('|')
		} else {
			sb.append('#')
		}
		sb.append('\n')
		sb.append('#')
		if(south) {
			sb.append('-')
		} else {
			sb.append('#')
		}
		sb.append('#')
	}
	
	@Override
	public int compareTo(Room o) {
		coord.x - o.coord.x ?: coord.y - o.coord.y
	}
}
