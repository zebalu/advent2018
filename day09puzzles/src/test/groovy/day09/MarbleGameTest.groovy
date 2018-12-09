package day09;

import spock.lang.Specification

class MarbleGameTest extends Specification {

	def 'examples'(String decsription, highestScore) {
		MarbleGame mg = MarbleGame.fromDescrption(decsription)
		int highest = mg.play()
		
		expect:
		highest == highestScore
		
		where:
		decsription                                    | highestScore
		'9 players; last marble is worth 25 points'    | 32
		'10 players; last marble is worth 1618 points' | 8317
		'13 players; last marble is worth 7999 points' | 146373
		'17 players; last marble is worth 1104 points' | 2764
		'21 players; last marble is worth 6111 points' | 54718
		'30 players; last marble is worth 5807 points' | 37305
		
	}
	
	def input() {
		MarbleGame mg = MarbleGame.fromDescrption(this.getClass().getResourceAsStream('/input17.txt').text)
		int highest = mg.play()
		
		expect:
		highest == 394486
	}
	/*
	def "input 100 times larger"() {
		MarbleGame mg = MarbleGame.fromDescrption(this.getClass().getResourceAsStream('/input17.txt').text)
		mg = new MarbleGame(mg.players.size(), mg.marbleCount*100)
		long highest = mg.play()
		
		expect:
		highest == 3276488008

	}
	*/
}
