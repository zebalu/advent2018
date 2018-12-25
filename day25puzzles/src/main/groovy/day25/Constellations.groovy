package day25

class Constellations {

	List<List<Coord>> constellations = []
	
	void add(Coord c) {
		List<Integer> constellationIds = []
		for(int i=0; i<constellations.size(); ++i) {
			if(fitsInConstellation(constellations[i], c)) {
				constellationIds.add(i)
			}
		}
		if(constellationIds.isEmpty()) {
			constellations.add([c])
		} else if(constellations.size()==1) {
			constellations[constellationIds[0]].add(c)
		} else {
			List<Integer> desc = constellationIds.sort{a,b->b-a}
			List<Coord> coords = [c]
			for(int i=0; i<desc.size(); ++i) {
				coords.addAll(constellations[desc[i]])
			}
			for(int i=0; i<desc.size(); ++i) {
				constellations.remove(desc[i])
			}
			constellations.add(coords)
		}
	}
	
	int getConstellationsCount() {
		return constellations.size()
	}
	
	private boolean fitsInConstellation(List<Coord> coords, Coord coord) {
		for(int i=0; i<coords.size(); ++i) {
			if(coord.distance(coords[i]) <= 3) {
				return true
			} 
		}
		return false
	}
	
	static Constellations fromInputStream(InputStream is) {
		Constellations consts = new Constellations()
		is.withReader { r -> 
			r.eachLine { l ->
				consts.add(Coord.parse(l))
			}
		}
		return consts;
	}
}
