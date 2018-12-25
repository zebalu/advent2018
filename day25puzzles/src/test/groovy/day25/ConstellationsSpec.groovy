package day25;

import spock.lang.Specification

class ConstellationsSpec extends Specification {

	def 'example 1 2 constellations'() {
		Constellations cs = Constellations.fromInputStream(getClass().getResourceAsStream('/example1.txt'))
		println cs
		
		expect:
		cs.getConstellationsCount() == 2
	}
	
	def 'example 1 1 constellations with extra coord'() {
		Constellations cs = Constellations.fromInputStream(getClass().getResourceAsStream('/example1.txt'))
		cs.add(new Coord(6,0,0,0))
		println cs
		
		expect:
		cs.getConstellationsCount() == 1
	}
	
	def 'example 2 4 constellations'() {
		Constellations cs = Constellations.fromInputStream(getClass().getResourceAsStream('/example2.txt'))
		println cs
		
		expect:
		cs.getConstellationsCount() == 4
	}
	
	def 'example 3 3 constellations'() {
		Constellations cs = Constellations.fromInputStream(getClass().getResourceAsStream('/example3.txt'))
		println cs
		
		expect:
		cs.getConstellationsCount() == 3
	}
	
	def 'example 4 8 constellations'() {
		Constellations cs = Constellations.fromInputStream(getClass().getResourceAsStream('/example4.txt'))
		println cs
		
		expect:
		cs.getConstellationsCount() == 8
	}
	
	def 'input 8 constellations'() {
		Constellations cs = Constellations.fromInputStream(getClass().getResourceAsStream('/input.txt'))
		println cs
		
		expect:
		cs.getConstellationsCount() == 422
	}
	
}
