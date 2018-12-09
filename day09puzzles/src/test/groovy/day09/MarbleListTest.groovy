package day09;

import spock.lang.Specification

class MarbleListTest extends Specification {

	def "first added"() {
		MarbleList ml = new MarbleList()
		ml.add(0)
		
		expect:
		'(0)' == ml.toString()
	}
	
	def "second added"() {
		MarbleList ml = new MarbleList()
		ml+0+1
		
		expect:
		'0 (1)' == ml.toString()
	}
	
	def "third added"() {
		MarbleList ml = new MarbleList()
		ml+0+1+2
		
		expect:
		'0 (2) 1' == ml.toString()
	}
	
	def "fourth added"() {
		MarbleList ml = new MarbleList()
		ml+0+1+2+3
		
		expect:
		'0 2 1 (3)' == ml.toString()
	}
	
	def addALot(int count, String toString) {
		MarbleList ml = new MarbleList()
		(0..count).each { ml+it }
		
		expect:
		toString == ml.toString()
		
		where:
		count | toString
		0     | '(0)'
		4     | '0 (4) 2 1 3'
		5     | '0 4 2 (5) 1 3'
		6     | '0 4 2 5 1 (6) 3'
		7     | '0 4 2 5 1 6 3 (7)'
		8     | '0 (8) 4 2 5 1 6 3 7'
	}
	
	def remove() {
		MarbleList ml = new MarbleList()
		(0..22).each { ml+it }
		def removed = ml.remove(7)
		
		expect:
		'0 16 8 17 4 18 (19) 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15' == ml.toString()
		9 == removed		
	}
	
}
