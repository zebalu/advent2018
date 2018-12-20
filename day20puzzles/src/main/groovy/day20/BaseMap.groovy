package day20

class BaseMap {

	Map<Coord, Room> rooms = [:]

	int minX = Integer.MAX_VALUE
	int minY = Integer.MAX_VALUE
	int maxX = Integer.MIN_VALUE
	int maxY = Integer.MIN_VALUE

	Room youAreHere

	Room addRoom(Room r) {
		if(!youAreHere) {
			youAreHere = r
		}
		if(rooms[r.coord]) {
			copyDoors(rooms[r.coord], r)
		} else {
			rooms[r.coord] = r
			
			if(r.coord.x < minX) {
				minX = r.coord.x
			}
			if(r.coord.y < minY) {
				minY = r.coord.y
			}
			if(r.coord.x > maxX) {
				maxX = r.coord.x
			}
			if(r.coord.y > maxY) {
				maxY = r.coord.y
			}
		}
		return r
	}

	private void copyDoors(Room r1, Room r2) {
		def dirs = ['east', 'west', 'north', 'south']
		dirs.each {
			if(r1."$it") {
				r2."$it"=r1."$it"
			} else if(r2."$it") {
				r1."$it"=r2."$it"
			}
		}
	}
	
	Path longestPath() {
		Tuple2<Map<Integer, Path>, Map<Room, Path>> endedPath = fullPathes()
		return endedPath.first.entrySet().max { a,b -> a.key - b.key }.value
	}
	
	int countPathsBiggerThan(int limit) {
		Tuple2<Map<Integer, Path>, Map<Room, Path>> endedPath = fullPathes()		
		Map<Room, Path> pathes = endedPath.second
		return new HashSet(pathes.entrySet().findAll { it.value.size > limit }.collect{it.value}).size()
	}
	
	private Tuple2<Map<Integer, Path>, Map<Room, Path>> fullPathes() {
		Deque<Room> stack = new ArrayDeque<>()
		Path current = new Path([], youAreHere)
		Set<Path> visited = new HashSet<>()
		Set<Room> visitedRooms = new HashSet<>()
		Map<Integer, Path> endedPath = [:]
		Map<Room, Path> pathesToRooms = [:]
		visited.add(current)
		stack.push(current)
		visitedRooms.add(youAreHere)
		int steps = 0;
		while(!stack.isEmpty()) {
			++steps
			if(steps % 1_000 == 0) {
				println "$steps \t stackSize: ${stack.size()} \t endPaths size: ${endedPath.size()} \t visited: ${visited.size()}"
			}
			Path p = stack.pop()
			visited.add(p)
			if(!pathesToRooms.containsKey(p.last)) {
				pathesToRooms[p.last] = p
			}
			visitedRooms.add(p.last)
			def dirs = ['east', 'west', 'north', 'south']
			boolean wentOn = false
			dirs.each {
				if(p.last."$it" && !visitedRooms.contains(p.last."$it")) {
					Path next = new Path(p.rooms, p.last."$it")
					if(!visited.contains(next)) {
						stack.push(next)
						wentOn = true
					}
				}
			}
			if(!wentOn) {
				endedPath[p.size] = p
			}
		}
		return new Tuple2(endedPath, pathesToRooms)
	}
	
	Path shortestPathToRoom(Room target) {
		Deque<Room> stack = new ArrayDeque<>()
		Path current = new Path([], youAreHere)
		Set<Path> visited = new HashSet<>()
		Set<Room> visitedRooms = new HashSet<>()
		visited.add(current)
		stack.push(current)
		visitedRooms.add(youAreHere)
		int steps = 0;
		println "target: $target"
		while(!stack.isEmpty()) {
			++steps
			if(steps % 1_000 == 0) {
				println "$steps \t stackSize: ${stack.size()} \t visited: ${visited.size()}"
			}
			Path p = stack.pop()
			visited.add(p)
			visitedRooms.add(p.last)
			if(p.last == target) {
				return p
			}
			def dirs = ['east', 'west', 'north', 'south']
			dirs.each {
				if(p.last."$it" && !visitedRooms.contains(p.last."$it")) {
					Path next = new Path(p.rooms, p.last."$it")
					if(!visited.contains(next)) {
						stack.push(next)
					}
				}
			}
		}

	}

	@Override
	String toString() {
		return toStringWithPath(new Path())
	}
	
	String toStringWithPath(Path path) {
		StringBuilder sb = new StringBuilder()
		for(int y = minY; y <= maxY; ++y) {
			def row = []
			for(int x = minX; x<=maxX; ++x) {
				row << rooms[new Coord(x: x, y: y)]
			}
			sb.append('#')
			row.each {
				if(it.north) {
					sb.append('-')
				} else {
					sb.append('#')
				}
				sb.append('#')
			}
			sb.append('\n')
			row.each {
				if(it.west) {
					sb.append('|')
				} else {
					sb.append('#')
				}
				if(it == youAreHere) {
					sb.append('X')
				} else if(path.rooms.contains(it)){
					sb.append('o')
				} else {
					sb.append('.')
				}
			}
			sb.append('#\n')
		}
		for(int x = minX; x<=maxX; ++x) {
			sb.append('##')
		}
		sb.append('#\n')
		return sb.toString()
	}

	static BaseMap fromStream(InputStream is) {
		String dirs = is.text
		Deque<Room> stack = new ArrayDeque<>()
		BaseMap result = new BaseMap()
		Room current = null
		Room next = null
		dirs.each { c ->
			if(c=='^') {
				current = new Room(coord: new Coord(x: 0, y:0))
				result.addRoom(current)
			} else if(c in ['E', 'W', 'S', 'N']) {
				Coord nextCoord = getCoordByDirection(current.coord, c)
				next = new Room(coord: nextCoord)
				connectBy(current, next, c)
				result.addRoom(next)
				current = next
			} else if(c in ['(', ')', '|']) {
				if(c == '(') {
					stack.push(current)
				} else if(c == '|') {
					current = stack.peek()
				} else if(c == ')') {
					current = stack.pop()
				}
			} else if( c=='$') {
				println 'stream has ended'
				println "readed ${result.rooms.size()} rooms"
			}
		}
		return result
	}

	private static Coord getCoordByDirection(Coord coord, String c) {
		switch(c) {
			case 'E':
				return coord.east
			case 'W':
				return coord.west
			case 'S':
				return coord.south
			case 'N':
				return coord.north
			default:
				throw new IllegalStateException("unknown dorection: $c")
		}
	}

	private static void connectBy(Room from, Room to, String direction) {
		switch(direction) {
			case 'E':
				from.east=to
				to.west=from
				break;
			case 'W':
				from.west=to
				to.east=from
				break
			case 'S':
				from.south=to
				to.north=from
				break
			case 'N':
				from.north=to
				to.south=from
				break
			default:
				throw new IllegalStateException("unknown dorection: $direction")
		}
	}
}
