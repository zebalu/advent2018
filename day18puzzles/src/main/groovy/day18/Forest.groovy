package day18

import static day18.AreaType.*;

class Forest {

	Map<Coord, AreaType> area = [:]

	byte minX = Byte.MAX_VALUE
	byte maxX = Byte.MIN_VALUE
	byte minY = Byte.MAX_VALUE
	byte maxY = Byte.MIN_VALUE

	void putAt(Coord c, AreaType a) {
		area[c]=a
		if(c.x<minX) {
			minX=c.x
		}
		if(maxX<c.x) {
			maxX=c.x
		}
		if(maxY<c.y) {
			maxY=c.y
		}
		if(c.y<minY) {
			minY=c.y
		}
	}

	AreaType getAt(Coord c) {
		return area[c]
	}

	List<AreaType> getAll(List<Coord> cs) {
		cs.collect { area[it] }.findAll { it != null }
	}

	int countAll(List<Coord> coords, AreaType at) {
		return getAll(coords).count { it == at }
	}

	void evolve() {
		Map<Coord, AreaType> evolved = [:]
		area.entrySet().each {
			switch(it.value) {
				case OPEN:
					if(countAll(it.key.adjecentCoords, WOODS)>2) {
						evolved[it.key]=WOODS
					} else {
						evolved[it.key]=OPEN
					}
					break
				case WOODS:
					if(countAll(it.key.adjecentCoords, LUMBER)>2) {
						evolved[it.key]=LUMBER
					} else {
						evolved[it.key]=WOODS
					}
					break
				case LUMBER:
					if(!(countAll(it.key.adjecentCoords, LUMBER)>=1 && countAll(it.key.adjecentCoords, WOODS)>=1)) {
						evolved[it.key]=OPEN
					} else {
						evolved[it.key]=LUMBER
					}
					break
				default:
					throw new IllegalStateException("on coord: $it.key value is: $it.value which is not known")
			}
		}
		area=evolved
	}
	
	int countAll(AreaType at) {
		area.entrySet().count { it.value==at }
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		for(byte y=minY; y<=maxY; ++y) {
			for(byte x=minX; x<=maxX; ++x) {
				sb.append(this[new Coord(x,y)])
			}
			sb.append('\n')
		}
		return sb.toString()
	}
	
	static Forest fromStream(InputStream is) {
		Forest forest = new Forest()
		is.withReader { r ->
			byte y = 0
			r.eachLine { l ->
				for(byte x=0; x<l.size(); ++x) {
					forest[new Coord(x,y)]=AreaType.fromString(l[x])
				}
				++y
			}
		}
		return forest
	}
}
