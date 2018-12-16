package day13

class Track {
	
	private static final String TURNRIGHT = '\\'
	private static final String TURNLEFT = '/'
	private static final String UPDOWN = '|'
	private static final String LEFTRIGHT = '-'
	private static final String INTERSECTION = '+'
	
	private static final List<String> VALID_ELEMENTS=[TURNLEFT, TURNRIGHT, UPDOWN, LEFTRIGHT, INTERSECTION]

	Map<Integer, Map<Integer, String>> routes = [:]
	
	boolean isIntersection(int x, int y) {
		return routes[x]?.getAt(y)==INTERSECTION
	}
	
	boolean isTurnLeft(int x, int y) {
		return routes[x]?.getAt(y)==TURNLEFT
	}
	
	boolean isTurnRight(int x, int y) {
		return routes[x]?.getAt(y)==TURNRIGHT
	}
	
	void addRoute(int x, int y, String element) {
		if(!isValidElement(element)) {
			throw new IllegalArgumentException("not valid element: "+element)
		}
		routes.computeIfAbsent(x, {[:]})[y]=element
	}
	
	String cachedElement(String element) {
		def result = VALID_ELEMENTS.find { it == element }
		if(result == null) {
			throw new NoSuchElementException("this is not a valid element: $element")
		}
		return result
	}
	
	boolean isValidElement(String element) {
		return VALID_ELEMENTS.contains(element)
	}
	
	int getWidth() {
		return routes.size()
	}
	
	int getHeight() {
		def max = routes.entrySet().stream().map{it.value.keySet()}.flatMap{it.stream()}.max{a,b -> 
			//println "a: $a , b: $b"
			a-b}
		return max.get()
	}
		
	String getAt(int x, int y) {
		String s = routes[x]?.getAt(y)
		if(s) {
			return s
		}
		return ' '
	}
}
