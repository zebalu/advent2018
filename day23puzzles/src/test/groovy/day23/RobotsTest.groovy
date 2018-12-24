package day23;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class RobotsTest extends Specification {

	def exampleRadious () {
		Robots r = new Robots()
		r.readStream(getClass().getResourceAsStream('/example.txt'))
		
		expect:
		r.largestRadious() == 4L
	}
	
	def exampleRobotsWithRadious () {
		Robots r = new Robots()
		r.readStream(getClass().getResourceAsStream('/example.txt'))
		def list = r.nanobotsWithRadious(4)
		
		
		expect:
		list.size() == 1
		list[0].x == 0
		list[0].y == 0
		list[0].z == 0
		list[0].r == 4
		
	}
	
	def exampleRobotsWithinRadious () {
		Robots r = new Robots()
		r.readStream(getClass().getResourceAsStream('/example.txt'))
		Nanobot n = r.nanobotsWithRadious(4)[0]
		int count = r.countRobotsInRadious(n)
		
		expect:
		count == 7
	}
	
	def input() {
		Robots r = new Robots()
		r.readStream(getClass().getResourceAsStream('/input.txt'))
		Nanobot bot = r.nanobotsWithRadious(r.largestRadious())[0]
		int count = r.countRobotsInRadious(bot)
		
		expect:
		count == 497
		
	}
	
	/*
	 * [x = 34574432, z = 23778473, y = 27408638]
	 * 85761543
	 */
}
