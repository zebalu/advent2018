package day15puzzles

import static day15puzzles.WarMap.MapElement.FREE
import static day15puzzles.WarMap.MapElement.WALL
import static day15puzzles.Warior.Race.GOBLIN
import static day15puzzles.Warior.Race.ELF

import day15puzzles.WarMap.MapElement
import day15puzzles.Warior.Race

class Board {

	WarMap map = new WarMap()
	List<Warior> wariors = []
	List<Warior> killedWariors = []
	Map<Coordinate, Warior> wariorsByCoordinates = new TreeMap<>({Coordinate a, Coordinate b->
		a.y-b.y?:a.x-b.x
	})
	
	def wariorByCoordinateSorter = {Warior a, Warior b -> a.coord.y-b.coord.y?:a.coord.x-b.coord.x}
	
	boolean isWarOver = false
	
	int roundCounter = 0
	
	boolean isAnElfKilled = false

	Board() {}
	Board(InputStream is) {
		is.withReader { r->
			int y = 0;
			r.eachLine { l->
				int x = 0;
				l.each { c->
					Coordinate coord = new Coordinate(x,y)
					if(map.recognise(c)) {
						map.add(coord, MapElement.fromString(c))
					} else if(Warior.canRecognise(c)) {
						addWarior(new Warior(coord, Race.fromString(c)))
						map.add(coord, FREE)
					}
					++x
				}
				++y
			}
		}
	}
	
	Warior getWariorAt(Coordinate c) {
		wariorsByCoordinates[c]
	}
	
	void addWarior(Warior w) {
		wariors.add(w)
		wariorsByCoordinates.put(w.coord, w)
		wariors.sort(wariorByCoordinateSorter)
	}
	
	boolean isItFree(Coordinate c) {
		if(getWariorAt(c)) {
			return false
		} else {
			map[c] == FREE
		}
	}
	
	void reportKilled(Warior w) {
		if(w.race == ELF) {
			isAnElfKilled = true
		}
		killedWariors.add(w)
		wariorsByCoordinates.remove(w.coord)
		def participatingRaces = wariors.findAll{it.hp>0}.groupBy { it.race }
		isWarOver = !participatingRaces[GOBLIN] || !participatingRaces[ELF]
	}
	
	void reportMoved(Coordinate from, Warior warior) {
		wariorsByCoordinates.remove(from)
		wariorsByCoordinates[warior.coord] = warior
	}
	
	void doARound() {
		wariors.sort(wariorByCoordinateSorter)
		for(int i=0; i<wariors.size() && !isWarOver; ++i) {
			if(!killedWariors.contains(wariors[i])) {
				wariors[i].act(this)
				if(isWarOver && (i == wariors.size()-1)) {
					++roundCounter
				}
			}
		}
		killedWariors.each {
			wariors.remove(it)
		}
		killedWariors.clear()
		if(!isWarOver) {
			++roundCounter
		}
	}
	
	int getOutCome() {
		int sum = wariors.collect { it.hp }.sum()
		wariors.each {
			println it
		}
		println "hps: $sum"
		println "rounds: $roundCounter"
		return roundCounter * sum
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		for(int i=map.minY; i<=map.maxY;++i) {
			def wariorsInLine = []
			for(int j=map.minX; j<=map.maxX; ++j) {
				def coord = new Coordinate(j, i)
				Warior w = getWariorAt(coord)
				if(w) {
					wariorsInLine.add(w)
					sb.append(w.race)
				} else {
					sb.append(map[coord])
				}
			}
			if(wariorsInLine) {
				sb.append("\t")
				for(Warior w : wariorsInLine) {
					if(w.race == GOBLIN) {
						sb.append("G(")
					} else if(w.race == ELF) {
						sb.append("E(")
					} else {
						sb.append("?(")
					}
					sb.append(String.format("%3d", w.hp))
					sb.append(') ')
				}
			}
			sb.append('\n')
		}
		return sb.toString();
	}
}
