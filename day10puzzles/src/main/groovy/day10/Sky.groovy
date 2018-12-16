package day10;

public class Sky {

	List<MovingPoint> points = []
	Map<Integer, Map<Integer, Integer>> starMap = [:]

	int maxX
	int maxY
	int minX
	int minY

	Sky(List<MovingPoint> points) {
		this.points = points
		mapPoints()
	}

	private mapPoints() {
		starMap.clear()
		maxX = Integer.MIN_VALUE
		maxY = Integer.MIN_VALUE
		minX = Integer.MAX_VALUE
		minY = Integer.MAX_VALUE
		points.each {
			starMap.computeIfAbsent(it.x, {[:]}).put(it.y, it)
			if(maxX<it.x) {
				maxX=it.x
			}
			if(maxY < it.y) {
				maxY = it.y
			}
			if(minY > it.y) {
				minY = it.y
			}
			if(minX > it.x) {
				minX = it.x
			}
		}
	}

	int getWidth() {
		return maxX-minX
	}

	int getHeight() {
		return maxY-minY
	}

	long getArea() {
		return (long)width * (long)height
	}

	void move() {
		points.each { it.move() }
		mapPoints()
	}
	
	void deMove() {
		points.each { it.deMove() }
		mapPoints()
	}
	
	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		for(int i=0; i<=width; ++i) {
			for(int j=0; j<=height; ++j) {
				MovingPoint mp = starMap.get(i+minX)?.get(j+minY)
				if(mp) {
					sb.append("#")
				} else {
					sb.append(' ')
				}
			}
			sb.append('\n')
		}
		return sb.toString()
	}
}