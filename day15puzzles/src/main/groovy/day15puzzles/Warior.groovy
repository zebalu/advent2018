package day15puzzles

import groovy.transform.Canonical

@Canonical
class Warior {

	static int elfAttackPower = 3

	Coordinate coord
	int hp
	Race race


	Warior(Coordinate coord, Race r) {
		this.coord = coord
		this.hp = 200
		this.race=r
	}

	void act(Board board) {
		def adjecentEnemies = adjectentEnemies(board)
		if(adjecentEnemies) {
			//			println adjecentEnemies
			if(race==Race.ELF) {
				adjecentEnemies[0].hp-=elfAttackPower
			} else {
				adjecentEnemies[0].hp-=3
			}
			if(adjecentEnemies[0].hp<=0) {
				board.reportKilled(adjecentEnemies[0])
			}
		} else {
			Set<Coordinate> visitedCoords = new HashSet()
			visitedCoords.add(coord)
			visitedCoords.add(coord.adjecents())
			Queue<Path> queue = new LinkedList<>()
			queue.addAll(coord.adjecents().findAll{board.isItFree(it)}.collect { new Path(null, it) })
			List<Path> waysToEnemies = []
			Path current = null
			boolean enemyFound=false
			int stepCount =0;
			while(!enemyFound && !queue.isEmpty()) {
				current = queue.poll()
				def possibleEnds = current.lastStep().adjecents()
				for(int i=0; i<possibleEnds.size() && !enemyFound; ++i) {
					Coordinate next = possibleEnds[i]
					if(board.isItFree(next)) {
						if(!visitedCoords.contains(next)) {
							visitedCoords.add(next)
							if(waysToEnemies.isEmpty() || waysToEnemies.size()>current.size()) {
								queue.add(new Path(current, next))
							}
						}
					} else {
						Warior w = board.getWariorAt(next)
						if(w && w.race!=race) {
							if(waysToEnemies.isEmpty() || waysToEnemies[0].size()==current.size()) {
								waysToEnemies.add(current)
							} else if(!waysToEnemies.isEmpty() && waysToEnemies[0].size()>current.size()) {
								waysToEnemies.clear()
								waysToEnemies.add(current)
							}
						}
					}
				}
			}
			def mins = waysToEnemies?.groupBy{it.size()}.sort{a,b->a.key-b.key}
			def minKey = mins?.collect { it.key }.min{a,b->a-b}
			def sorted = minKey?mins[minKey]?.sort{Path a, Path b->
				a.lastStep().y - b.lastStep().y ?: a.lastStep().x-b.lastStep().x ?: a.firstStep().y-b.firstStep().y?:a.firstStep().x-b.firstStep().x}:null
			if(sorted) {
				current = sorted[0]
				enemyFound = true
			}
			if(enemyFound) {
				//				println current
				Coordinate from = coord
				coord = current.firstStep()
				board.reportMoved(from, this)
				adjecentEnemies = adjectentEnemies(board)
				if(adjecentEnemies) {
					//println "could step to enemy: $adjecentEnemies"
					if(race==Race.ELF) {
						adjecentEnemies[0].hp-=elfAttackPower
					} else {
						adjecentEnemies[0].hp-=3
					}
					if(adjecentEnemies[0].hp<=0) {
						board.reportKilled(adjecentEnemies[0])
					}
				}
			}
		}
	}

	List<Warior> adjectentEnemies(Board board) {
		def adjecentBlocks = coord.adjecents()
		adjecentBlocks.collect { board.getWariorAt(it) }.findAll { it != null && it.race != race }.sort{a,b->a.hp-b.hp?:a.coord.y-b.coord.y?:a.coord.x-b.coord.x}
	}

	public static boolean canRecognise(String str) {
		return str == 'G' || str == 'E'
	}

	public static enum Race {
		GOBLIN('G'), ELF('E');

		private final String representation

		Race(String representation) {
			this.representation = representation
		}

		@Override
		String toString() {
			return representation
		}

		public static Race fromString(String str) {
			for(Race r : values()) {
				if(r.representation == str) {
					return r
				}
			}
			throw new IllegalArgumentException("Can not recognise: $str")
		}
	}
}
