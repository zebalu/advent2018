package day10

import java.util.regex.Matcher
import java.util.regex.Pattern

import groovy.transform.Canonical

@Canonical
class MovingPoint {
	
	private static final Pattern PATTERN = Pattern.compile(/(position=<) ?(\d+)(.*)(\d+)(> velocity=<) ?(\d+)(.*)(\d+)(.*)/)
	
	int x
	int y
	
	int vx
	int vy

	def move() {
		x+=vx
		y+=vy
	}
	
	def deMove() {
		x-=vx
		y-=vy
	}
	
	MovingPoint(String line) {
		def t = parse(line)
		x=t.first
		y=t.second
		vx=t.third
		vy=t.fourth
	}
	
	private static Tuple4 parse(String line) {
		def parts = line.split('<')
		def coord1 = parts[1].split('>')
		def xy = coord1[0].split(',')
		int x = xy[0].trim() as Integer
		int y = xy[1].trim() as Integer
		
		def coord2 = parts[2].split('>')
		def vxvy = coord2[0].split(',')
		int vx = vxvy[0].trim() as Integer
		int vy = vxvy[1].trim() as Integer
 		return new Tuple4(x, y, vx, vy)
	}
}
