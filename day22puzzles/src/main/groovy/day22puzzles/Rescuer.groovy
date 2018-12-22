package day22puzzles

class Rescuer {

	int calculateRescuePrice(Cave cave) {
		Path p = new Path()
		p.tool=Tool.TORCH
		p = p.enter(cave.start)
		Map<Coord, Integer> visited = new HashMap<>()
		Deque<Path> queue = new ArrayDeque<>()
		queue.offer(p)
		Path bestPath = null
		def steps = 0
		int maxPrice = (cave.start.coord.distance(cave.target.coord) +1 ) *8
		while(queue.peek()) {
			++steps
			Path current = queue.poll()
			if((current.price < maxPrice) && (bestPath == null || bestPath.price > current.price)) {
				if((!visited.containsKey(current.position)) || visited[current.position] > current.price) {
					visited[current.position] = current.price
					if(current.position == cave.target.coord) {
						current.price--
						if(current.tool != Tool.TORCH) {
							current = current.switchFor(Tool.TORCH)
						}
						if(bestPath == null) {
							bestPath = current
							println "first guess: ${bestPath.asHistory()}"
						} else if (bestPath.price>current.price){
							bestPath = current
							println "new best: ${bestPath.asHistory()}"
						}
						visited[bestPath.position] = bestPath.price
					} else {
						List<Coord> neighbours = current.position.getValidNeighBours()
						for(int i=0; i<neighbours.size(); ++i) {
							CaveArea ca = cave.getCaveArea(neighbours[i])
						}
						neighbours.each { n ->
							CaveArea ca = cave.getCaveArea(n)
							if(!visited.containsKey(n) || visited[n] > current.price) {
								if(!current.hasBeen(ca)) {
									if(current.canEnter(ca)) {
										queue.offer(current.enter(ca))
									} else {
										Path switched = current.switchFor(ca)
										switched.price--
										queue.offer(switched.enter(ca))
									}
								}
							}
						}
					}
				}
			}
			if(steps % 1_000 == 0) {
				println "steps: $steps \t queue length: ${queue.size()} \t best price: ${bestPath?.price} \t ${current.way.size()}"
			}
		}
		StringBuilder sb = new StringBuilder()
		for(int y = cave.minY; y<=cave.maxY; ++y) {
			if(y==0) {
				sb.append('    ')
				for(int x=cave.minX; x<=cave.maxX; ++x) {
					sb.append(String.format("%3d ", x))
				}
				sb.append('\n')
			}
			for(int x=cave.minX; x<=cave.maxX; ++x) {
				if(x==0) {
					sb.append(String.format("%3d ", y))
				}
				sb.append(String.format("%3d ", visited[new Coord(x,y)]?:-1))
			}
			sb.append('\n')
		}
		println sb.toString()
		sb = new StringBuilder()
		for(int y = cave.minY; y<=cave.maxY; ++y) {
			for(int x=cave.minX; x<=cave.maxX; ++x) {
				Coord c = new Coord(x,y)
				CaveArea ca = cave.getCaveArea(c)
				if(ca == cave.start) {
					sb.append('M')
				} else if (ca == cave.target) {
					sb.append('T')
				} else {
					sb.append(ca.type)
				}
			}
			sb.append('\t')
			for(int x=cave.minX; x<=cave.maxX; ++x) {
				Coord c = new Coord(x,y)
				if(bestPath.way.contains(c)) {
					if(bestPath.used[c] == Tool.TORCH) {
						sb.append('$')
					} else if (bestPath.used[c] == Tool.CLIMBING_GEAR) {
						sb.append('^')
					} else {
						sb.append('o')
					}
				} else {
					CaveArea ca = cave.getCaveArea(c)
					sb.append(ca.type)
				}
			}
			sb.append('\n')
		}
		println sb.toString()
		return bestPath.price
	}
}
