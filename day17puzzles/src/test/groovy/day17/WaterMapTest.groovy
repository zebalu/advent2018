package day17;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class WaterMapTest extends Specification {

	def exampleReadCorrectly() {
		WaterMap wm = readExample()

		println wm

		expect:
		wm.width==12
		wm.height==13
	}

	def exampleFilled() {
		WaterMap wm = readExample()

		int previousPoints = -1
		previousPoints=wm.valuableDataCount
		wm.flowFrom(new Coord(500,0))

		println wm

		expect:
		wm.getWaterCount() == 57
	}
	
	def exampleRestingCount() {
		WaterMap wm = readExample()

		int previousPoints = -1
		previousPoints=wm.valuableDataCount
		wm.flowFrom(new Coord(500,0))

		println wm

		expect:
		wm.restingWaterCount == 29
	}
	/*
	def inputFilled() {
		WaterMap wm = readInput()
		
		PrintWriter pw = new PrintWriter('he0.txt')
		pw.println(wm.minX +" \t "+wm.maxX)
		pw.println(wm.minY +" \t "+wm.maxY)
		pw.println(wm)
		pw.close()
		
		
		wm.flowFrom(new Coord(500,2))

		//println wm
		pw = new PrintWriter('he1.txt')
		pw.println(wm.minX +" \t "+wm.maxX)
		pw.println(wm.minY +" \t "+wm.maxY)
		pw.println(wm)
		pw.close()

		expect:
		wm.waterCount == 39367
	}
	
	def inputRestingCount() {
		WaterMap wm = readInput()
		
		PrintWriter pw = new PrintWriter('he0.txt')
		pw.println(wm.minX +" \t "+wm.maxX)
		pw.println(wm.minY +" \t "+wm.maxY)
		pw.println(wm)
		pw.close()
		
		
		wm.flowFrom(new Coord(500,2))

		//println wm
		pw = new PrintWriter('he1.txt')
		pw.println(wm.minX +" \t "+wm.maxX)
		pw.println(wm.minY +" \t "+wm.maxY)
		pw.println(wm)
		pw.close()

		expect:
		wm.restingWaterCount == 33061
	}
*/
	def readInput() {
		return readResource('/input.txt')
	}

	def readExample() {
		return readResource('/example.txt')
	}

	def readResource(String resource) {
		return WaterMap.fromStream(getClass().getResourceAsStream(resource))
	}
}
