package day17

import static org.junit.Assert.formatClassAndValue

class WaterMap {

	Map<Coord, MapElement> points = [:]

	int minX = Integer.MAX_VALUE
	int minY = Integer.MAX_VALUE
	int maxX = Integer.MIN_VALUE
	int maxY = Integer.MIN_VALUE

	MapElement getAt(Coord coord) {
		def result = points[coord]
		if(!result) {
			return MapElement.SAND
		}
		return result
	}

	void putAt(Coord coord, MapElement e) {
		if(e!=MapElement.SAND) {
			points[coord]=e
		}
		if(coord.x<minX) {
			minX=coord.x
		}
		if(coord.x>maxX) {
			maxX = coord.x
		}
		if(coord.y<minY) {
			minY=coord.y
		}
		if(coord.y>maxY) {
			maxY=coord.y
		}
	}

	int getWaterCount() {
		points.entrySet().count { it.value == MapElement.FLOWING_WATER || it.value == MapElement.RESTING_WATER }
	}
	
	int getRestingWaterCount() {
		points.entrySet().count { it.value == MapElement.RESTING_WATER }
	}

	void flowFrom(Coord start) {
		def flow = start.down
		while(!withinBoundaries(flow)) {
			flow = flow.down
		}
		Set<Coord> overflownPoints = new HashSet<>()
		//Stack<Coord> stack = new Stack<>()
		Deque<Coord> stack = new ArrayDeque<>()
		stack.push(flow)
		int count = 0
		while(!stack.isEmpty()) {
			++count
			Coord next = stack.pop() //poll()
			overflownPoints.add(next)
			if(withinBoundaries(next) && !(this[next] == MapElement.CLAY)) {
				this[next] = MapElement.FLOWING_WATER
				def down = next.down
				if(isBlocked(down)) {
					Tuple2<Coord, Coord> sideClays = findSideClays(next)
					if(sideClays.first && sideClays.second) {
						for(int x=(sideClays.first.x+1); x<sideClays.second.x; ++x) {
							this[new Coord(x, next.y)] = MapElement.RESTING_WATER
						}
						stack.push(next.up)
					} else {
						int startX = 0
						int endX = 0
						if(sideClays.first) {
							startX=sideClays.first.x+1
							def right = flowDown(next, 1)
							endX=right.x
							if(!(right in overflownPoints)) {
								stack.push(right)
							}
						} else if(sideClays.second) {
							endX=sideClays.second.x
							def left = flowDown(next, -1)
							startX=left.x
							if(!(left in overflownPoints)) {
								stack.push(left)
							}
						} else {
							def left = flowDown(next, -1)
							def right = flowDown(next, 1)
							startX = left.x
							endX=right.x
							if(!(left in overflownPoints)) {
								stack.push(left)
							}
							if(!(right in overflownPoints)) {
								stack.push(right)
							}
						}
						for(int x=startX; x<=endX && !(this[new Coord(x,next.y)] == MapElement.CLAY); ++x) {
							this[new Coord(x, next.y)]= MapElement.FLOWING_WATER
						}
					}
				} else {
					stack.push(next.down)
				}
				if(count % 1_000_000 == 0) {
					println "$valuableDataCount from ${width * height} and ${stack.size()} water: $waterCount \t $next \t $count"
				}
			}
		}
	}

	Tuple2<Coord, Coord> findSideClays(Coord coord) {
		Coord leftClay = null
		Coord rightClay = null
		boolean shouldLookForLeftClay = true
		boolean shouldLookForRightClay = true
		int y = coord.y
		int minx=coord.x
		int maxx =coord.x
		while((minx>=minX || maxx<=maxX) && (!leftClay || !rightClay) && (shouldLookForLeftClay || shouldLookForRightClay)) {
			--minx
			++maxx
			def leftCoord = new Coord(minx, y)
			def rightCoord = new Coord(maxx, y)
			if(isBlocked(leftCoord.down)) {
				if(shouldLookForLeftClay && !leftClay && this[leftCoord] == MapElement.CLAY) {
					leftClay = leftCoord
					shouldLookForLeftClay=false
				}
			} else {
				shouldLookForLeftClay=false
			}
			if(isBlocked(rightCoord.down)) {
				if(shouldLookForRightClay && !rightClay && this[rightCoord] == MapElement.CLAY) {
					rightClay = rightCoord
					shouldLookForRightClay = false
				}
			} else {
				shouldLookForRightClay = false
			}
		}
		return new Tuple2(leftClay, rightClay)
	}

	private Coord flowDown(Coord coord, int step) {
		Coord down = coord.down
		int x = coord.x
		for(; minX<=x && x <= maxX; x=x+step) {
			Coord c = new Coord(x, down.y)
			if(this[c] == MapElement.SAND || this[c] == MapElement.FLOWING_WATER) {
				return new Coord(x, coord.y)
			}
		}
		return new Coord(x, coord.y)
	}

	boolean isSurrounded(Coord coord) {
		this[coord.left] != MapElement.SAND && this[coord.right] != MapElement.SAND && this[coord.down] != MapElement.SAND && this[coord.up] != MapElement.SAND
	}

	boolean withinBoundaries(Coord coord) {
		return minX <= coord.x && coord.x <= maxX && minY <= coord.y && coord.y <= maxY
	}

	boolean isBlocked(Coord coord) {
		return !(this[coord] == MapElement.SAND || this[coord] == MapElement.FLOWING_WATER) // || !withinBoundaries(coord)
	}

	int getValuableDataCount() {
		return points.size()
	}

	int getWidth() {
		return maxX-minX+1
	}

	int getHeight() {
		return maxY-minY+1
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		for(int y=minY; y<=maxY; ++y) {
			for(int x = minX; x<=maxX; ++x) {
				MapElement me = this[new Coord(x,y)]
				switch(me) {
					case MapElement.CLAY:
						sb.append('#')
						break;
					case MapElement.SAND:
						sb.append('.')
						break;
					case MapElement.FLOWING_WATER:
						sb.append('|')
						break;
					case MapElement.RESTING_WATER:
						sb.append('~')
						break;
					case MapElement.SPRING:
						sb.append('+')
						break;
					default:
						sb.append('?')
				}
			}
			sb.append('\n')
		}
		return sb.toString()
	}

	static WaterMap fromStream(InputStream is) {
		WaterMap wm = new WaterMap()
		is.withReader { r ->
			r.eachLine { l ->
				def xy = l.split(', ')
				def xs = []
				def ys = []
				for(int i=0; i<2; ++i) {
					if(xy[i].startsWith('x=')) {
						xs = numsFrom(xy[i].substring(2))
					} else {
						ys = numsFrom(xy[i].substring(2))
					}
				}
				for(int x : xs) {
					for(int y: ys) {
						wm[new Coord(x,y)] = MapElement.CLAY
					}
				}
			}
		}
		return wm
	}

	private static List<Integer> numsFrom(String s) {
		if(s.contains('..')) {
			def splited = s.split(/\.\./)
			return ((splited[0] as Integer)..(splited[1] as Integer)).collect()
		} else {
			return [s as Integer]
		}
	}
}
