package day21;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class ComputerTest extends Specification {

	def "input with groovy"() {
		def specks = ReadProgram.read(getClass().getResourceAsStream('/input.txt'))
		GComputer c = new GComputer()
		c.ip = specks.first
		
		int first = c.execute(specks.second)
		
		expect:
		first==10780777
	}
	/*
	def "input with java"() {
		def specks = ReadProgram.read(getClass().getResourceAsStream('/input.txt'))
		JComputer c = new JComputer()
		c.ip = specks.first
		
		int last = c.execute(specks.second)
		
		expect:
		last==13599657
	}
	*/
}
