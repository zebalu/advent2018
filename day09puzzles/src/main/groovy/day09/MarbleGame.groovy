package day09

import java.util.regex.Pattern

class MarbleGame {

	private static final Pattern DESCRIPTION = Pattern.compile($/(\d+)( players; last marble is worth )(\d+)( points)/$)

	List<Long> players = []
	int marbleCount
	MarbleList board = new MarbleList()

	MarbleGame(int playerCount, int marbleCount) {
		(1..playerCount).each { players.add(0L) }
		this.marbleCount=marbleCount
		board.add(0)
	}

	def play() {
		(1..marbleCount).each {
			if(it % 23 == 0) {
				def playerIdx = (it-1)%players.size()
				players[playerIdx]=players[playerIdx] + it + board.remove(7)
			} else {
				board+it
			}
		}
		return players.max { a,b -> a-b }
	}

	static MarbleGame fromDescrption(String description) {
		def matcher = DESCRIPTION.matcher(description.trim())
		if(matcher.matches()) {
			return new MarbleGame(matcher.group(1) as Integer, matcher.group(3) as Integer)
		}
		return null
	}
}
