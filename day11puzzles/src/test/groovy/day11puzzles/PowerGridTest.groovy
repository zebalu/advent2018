package day11puzzles;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class PowerGridTest extends Specification {

	def "example 3,5"() {
		PowerGrid pg = new PowerGrid(3, 5, 8)


		expect:
		pg[2, 4] == 4
	}

	def "example 42"() {
		PowerGrid pg = new PowerGrid(300, 300, 42)


		def t3 = pg.maxCorrigatedXY

		expect:
		t3.first == 30
		t3.second == 21
		t3.third == 61
	}

	def "input"() {
		PowerGrid pg = new PowerGrid(300, 300, 7803)


		def t3 = pg.maxCorrigatedXY

		println t3

		expect:
		t3.first == 31
		t3.second == 20
		t3.third == 51
	}
	/*
	def "anyMax"() {
		PowerGrid pg = new PowerGrid(300, 300, 7803)


		def t4 = pg.maxAny

		println t4

		println "maxmum sum area wth size: $t4.fourth"
		
		expect:
		t4.first == 125
		t4.second == 230
		t4.third == 272
		t4.fourth == 17
	}
	*/
}
