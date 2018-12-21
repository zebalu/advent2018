package day15puzzles;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class BoardTest extends Specification {

	
	def example1() {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream('/example1.txt'))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		expect:
		b.outCome == 27730
	}

	def readOrder() {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream('/example_read_order.txt'))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		expect:
		b.outCome == 22800
	}
	
	
	def movement() {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream('/movement_example.txt'))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		expect:
		b.outCome == 27828
	}
	
	def choise() {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream('/choise_example.txt'))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		expect:
		b.outCome == 16533
	}
		
	def example2() {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream('/example2.txt'))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		expect:
		b.outCome == 36334
	}
	
	def allExamples(String resource, int outcome) {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream("/$resource"))
		while(!b.isWarOver) {
			b.doARound()
		}
		
		expect:
		b.outCome == outcome
		
		where:
		resource       | outcome
		'example1.txt' | 27730
		'example2.txt' | 36334
		'example3.txt' | 39514
		'example4.txt' | 27755
		'example5.txt' | 28944
		'example6.txt' | 18740
	}
	
	def allExamplesModified(String resource, int attackPower, int outcome) {
		Warior.elfAttackPower = attackPower
		Board  b = new Board(getClass().getResourceAsStream("/$resource"))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		
		expect:
		b.outCome == outcome
		!b.isAnElfKilled
		
		where:
		resource       | attackPower | outcome
		'example1.txt' | 15          | 4988
		'example3.txt' | 4           | 31284
		'example4.txt' | 15          | 3478
		'example5.txt' | 12          | 6474
		'example6.txt' | 34          | 1140
	}
	/*
	def input() {
		Warior.elfAttackPower = 3
		Board  b = new Board(getClass().getResourceAsStream('/input.txt'))
		while(!b.isWarOver) {
			b.doARound()
			//println b
		}
		expect:
		b.outCome == 179968
	}
	*/
	def modifiedExample1() {
		Warior.elfAttackPower = 15
		Board  b = new Board(getClass().getResourceAsStream('/example1.txt'))
		while(!b.isWarOver) {
			b.doARound()
			println b
		}
		expect:
		b.outCome == 4988
		!b.isAnElfKilled
	}
	/*
	def modifiedInput() {
		Warior.elfAttackPower = 3
		boolean attackPowerFound = false
		Board  b = null
		while(!attackPowerFound) {
			++Warior.elfAttackPower
			b = new Board(getClass().getResourceAsStream('/input.txt'))
			println b
			while(!b.isWarOver && !b.isAnElfKilled) {
				b.doARound()
				//println b
			}
			attackPowerFound = !b.isAnElfKilled
		}
		
		println "final attack power: $Warior.elfAttackPower"
		expect:
		b.outCome == 42098
		Warior.elfAttackPower==20
	}
	*/
}
