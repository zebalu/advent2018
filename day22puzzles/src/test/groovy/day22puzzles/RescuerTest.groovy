package day22puzzles;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class RescuerTest extends Specification {

	def 'with example'() {
		Cave c = new Cave(510, new Coord(10,10))
		Rescuer r = new Rescuer()
		int price = r.calculateRescuePrice(c)
		
		expect:
		price == 45
	}
	/*
	
	def 'with input'() {
		Cave c = new Cave(9171, new Coord(7,721))
		Rescuer r = new Rescuer()
		int price = r.calculateRescuePrice(c)
		
		expect:
		price == 724
	}
	*/
}
