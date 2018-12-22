package day22puzzles

class Cave {

	Map<Coord, CaveArea> caveTerrain = [:]

	int minX = Integer.MAX_VALUE
	int minY = Integer.MAX_VALUE
	int maxX = Integer.MIN_VALUE
	int maxY = Integer.MIN_VALUE

	CaveArea start
	CaveArea target
	int depth

	Cave(int depth, Coord target) {
		this.depth=depth
		this.start =new CaveArea(coord: new Coord(x:0,y:0), erosionLevel: 0, geologicalIndex: 0, type: RegionType.ROCKY)
		this.target=new CaveArea(coord: target, erosionLevel: 0, geologicalIndex: 0, type: RegionType.ROCKY)
		addArea(start)
		addArea(this.target)
		generateTerrain()
	}

	void addArea(CaveArea area) {
		caveTerrain[area.coord] = area
		saveMinMax(area.coord)
	}
	
	private void saveMinMax(Coord coord) {
		coord.with {
			if(x<minX) {
				minX=x
			}
			if(y<minY) {
				minY=y
			}
			if(x>maxX) {
				maxX=x
			}
			if(y>maxY) {
				maxY=y
			}
		}
	}

	void generateTerrain() {
		int depth = Math.max(target.coord.x, target.coord.y) +1
		for(int x=0; x<=depth; ++x) {
			for(int y=0; y<=depth; ++y) {
				Coord c = new Coord(x,y)
				getCaveArea(c)
			}
		}
	}
	
	CaveArea getCaveArea(Coord c) {
		return caveTerrain.computeIfAbsent(c, {
			CaveArea ca = new CaveArea(coord: c)
			ca.geologicalIndex = geologicalIndexAt(ca.coord)
			ca.erosionLevel = (ca.geologicalIndex + depth) % 20183
			ca.type = RegionType.forInt(ca.erosionLevel)
			saveMinMax(c)
			return ca
		})
	}

	int geologicalIndexAt(Coord coord) {
		if(coord.y==0) {
			return coord.x*16807
		}
		if(coord.x==0) {
			return coord.y*48271
		}
		return getCaveArea(coord.left).erosionLevel * getCaveArea(coord.up).erosionLevel
	}
	
	int getRiskLevel() {
		int sum = 0
		for(int x=start.coord.x; x<=target.coord.x; ++x) {
			for(int y=start.coord.y; y<=target.coord.y; ++y) {
				Coord coord = new Coord(x: x, y: y)
				CaveArea ca = caveTerrain[coord]
				sum += ca.type.riskLevel
			}
		}
		return sum
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		for(int y=start.coord.y; y<=target.coord.y; ++y) {
			for(int x=start.coord.x; x<=target.coord.x; ++x) {
				Coord c = new Coord(x:x, y:y)
				CaveArea ca = caveTerrain.get(c)
				if(ca.is(start)) {
					sb.append('M')
				} else if(ca.is(target)) {
					sb.append('T')
				} else {
					sb.append(ca.type.representation)
				}
			}
			sb.append('\n')
		}
		return sb.toString()
	}
}
