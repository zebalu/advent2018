package day10;

import spock.lang.Specification

class MovingPointTest extends Specification {
	
	def readAll() {
		def collected = []
		getClass().getResourceAsStream('/input19.txt').eachLine { collected.add(new MovingPoint(it)) }
		def p1 = collected[0]
				
		expect:
		collected.size() == 394
		p1.x==52672
		p1.y==52690
		p1.vx==-5
		p1.vy==-5
	}

	def "3 seconds with example"() {
		def collected = []
		getClass().getResourceAsStream('/example.txt').eachLine { collected.add(new MovingPoint(it)) }
		Sky s = new Sky(collected)
		s.move()
		s.move()
		s.move()
		
		expect:
		63 == s.area
	}
	
	def findShrinkningRate() {
		def collected = []
		getClass().getResourceAsStream('/example.txt').eachLine { collected.add(new MovingPoint(it)) }
		Sky s = new Sky(collected)
		long area = s.getArea()
		long newArea = area
		int count = -1
		while(newArea <= area) {
			area = newArea
			s.move()
			newArea = s.area
			++count
		}
		s.deMove()
		println "shrinknig $count seconds"
		println s
		
		expect:
		count == 3
		
	}
	/*
	def findShrinkningRateWithInput() {
		def collected = []
		getClass().getResourceAsStream('/input19.txt').eachLine { collected.add(new MovingPoint(it)) }
		Sky s = new Sky(collected)
		long area = s.getArea()
		long newArea = area
		int count = -1
		while(newArea <= area) {
			area = newArea
			s.move()
			newArea = s.area
			++count
		}
		s.deMove()
		println "shrinknig $count seconds"
		println s
		
		expect:
		count == 10511
		
	}
	*/
}
