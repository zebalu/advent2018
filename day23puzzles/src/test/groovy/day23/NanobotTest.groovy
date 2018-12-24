package day23;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class NanobotTest extends Specification {
	
	def praseExampleLine1() {
		Nanobot n = new Nanobot('pos=<-27458727,37082345,47925315>, r=95853959')
		
		expect:
		n.with { 
			x == -27458727
			y == 37082345
			z == 47925315
			r == 95853959
		} 
	}
	
	def distance() {
		Nanobot  n1 = new Nanobot(x:0,y:0,z:0,r:3)
		Nanobot  n2 = new Nanobot(x:1,y:1,z:1,r:3)
		
		expect:
		n1.distance(n2) == 3
	}
	
	def inRadious() {
		Nanobot  n1 = new Nanobot(x:0,y:0,z:0,r:3)
		Nanobot  n2 = new Nanobot(x:1,y:1,z:1,r:3)
		
		expect:
		n1.inRadious(n2)
	}
	
	
	def praseProblematic() {
		Nanobot n = new Nanobot('pos=<-36842257,54634766,-82992136>, r=57455800')
		
		expect:
		n.with {
			x == -36842257
			y == 54634766
			z == -82992136
			r == 57455800
		}
	}

}
