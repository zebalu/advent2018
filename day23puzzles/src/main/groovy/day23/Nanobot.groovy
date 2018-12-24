package day23

import java.util.regex.Matcher
import java.util.regex.Pattern

import groovy.transform.Canonical
import groovy.transform.MapConstructor
import groovy.transform.Sortable

@Canonical
@Sortable
@MapConstructor
class Nanobot {

	private static final Pattern PATTERN = Pattern.compile(/.*(\d+),(\d+),(\d+).*r=(\d+)/)

	long x
	long y
	long z
	long r

	Nanobot(String description) {
		Tuple4<Long, Long, Long, Long> t = parseDescription(description)
		x=t.first
		y=t.second
		z=t.third
		r=t.fourth
	}

	boolean inRadious(Nanobot other) {
		//println "$other is ${distance(other)} away so it is ${distance(other) <= r ?'':'not '} in radious"
		return distance(other)	<= r
	}
	
	boolean contains(x, y, z) {
		return Math.abs(this.x-x)+Math.abs(this.y-y)+Math.abs(this.z-z) < r
	}

	long distance(Nanobot other) {
		return Math.abs(x-other.x)+Math.abs(y-other.y)+Math.abs(z-other.z)
	}

	private static Tuple4<Long, Long, Long, Long> parseDescription(String description) {
		String minusPos = description.substring('pos=<'.size())
		String[] coordsAndR = minusPos.split('>, r=')
		String[] coords = coordsAndR[0].split(',')
		long x = coords[0] as Long
		long y = coords[1] as Long
		long z = coords[2] as Long
		long r = coordsAndR[1] as Long
		return new Tuple4<>(x,y,z,r)
	}
}
