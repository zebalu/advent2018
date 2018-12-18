package day18

enum AreaType {

	WOODS, OPEN, LUMBER

	public String toString() {
		switch(this) {
			case WOODS:
				return '|'
			case OPEN:
				return '.'
			case LUMBER:
				return '#'
			default:
				throw new IllegalStateException("uknown value");
		}
	}

	public static AreaType fromString(String str) {
		switch(str) {
			case '|':
				return WOODS
			case '.':
				return OPEN
			case '#':
				return LUMBER
			default:
				throw new IllegalArgumentException("$str is not a valid value")
		}
	}
}
