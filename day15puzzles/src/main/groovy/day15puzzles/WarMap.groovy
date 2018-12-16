package day15puzzles

class WarMap {

	Map<Coordinate, MapElement> map = [:]
	int minX = Integer.MAX_VALUE
	int minY = Integer.MAX_VALUE
	int maxX = Integer.MIN_VALUE
	int maxY = Integer.MIN_VALUE

	boolean recognise(String str) {
		return str=='.' || str == '#'
	}

	void add(Coordinate coord, MapElement element) {
		map.put(coord, element)
		if(coord.x<minX) {
			minX=coord.x
		}
		if(coord.y<minY) {
			minY=coord.y
		}
		if(coord.x > maxX) {
			maxX=coord.x
		}
		if(coord.y>maxY) {
			maxY=coord.y
		}
	}
	
	MapElement getAt(Coordinate coord) {
		map[coord]
	}

	public static enum MapElement {
		WALL('#'), FREE('.');
		private final String representation;

		private MapElement(String representation) {
			this.representation=representation
		}

		@Override
		public String toString() {
			return representation
		}

		public static MapElement fromString(String str) {
			for(MapElement me : values()) {
				if(me.representation==str) {
					return me
				}
			}
			throw new IllegalArgumentException("can not recognise $str")
		}
	}
}
