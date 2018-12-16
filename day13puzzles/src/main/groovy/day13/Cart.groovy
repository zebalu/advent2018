package day13

import groovy.transform.Canonical

@Canonical
class Cart {

	private static final String LEFT = 'LEFT'
	private static final String STRAIGHT = 'STRAIGHT'
	private static final String RIGHT = 'RIGHT'
	
	private static final String LEFT_CART = "<"
	private static final String RIGHT_CART = ">"
	private static final String UP_CART = "^"
	private static final String DOWN_CART = "v"
	
	private static final List<String> VALID_CARTS = [LEFT_CART, RIGHT_CART, UP_CART, DOWN_CART]
	
	int x
	int y
	
	int vx
	int vy
	
	Track track
	
	Deque<String> intersectionStrategy = new LinkedList<>([LEFT, STRAIGHT, RIGHT])
	
	Cart(int x, int y, String cart, Track track) {
		if(!isValidCart(cart)) {
			throw new IllegalArgumentException("This is not a cart: $cart")
		}
		this.x=x
		this.y=y
		if(LEFT_CART == cart) {
			vx=-1
			vy=0
		} else if(RIGHT_CART == cart) {
			vx=1
			vy=0
		} else if(UP_CART == cart) {
			vy = -1
			vx=0
		} else if(DOWN_CART == cart) {
			vy=+1
			vx=0
		} else {
			throw new IllegalArgumentException("I don't know what to do with this: $cart")
		}
		this.track=track
	}
		
	void move() {
		x+=vx
		y+=vy
		if(track[x,y] == ' ' ) {
			println "cart has wandered off the grid: $this"
		}
		if(track.isIntersection(x,y)) {
			String next = nextIntersectionStep()
			if(next == LEFT) {
				if(isHorizontal()) {
					turnLeft()
				} else {
					turnRight()
				}
			} else if(next == RIGHT) {
				if(isHorizontal()) {
					turnRight()
				} else {
					turnLeft()
				}
			}
		}
		if(track.isTurnLeft(x, y)) {
			turnLeft()
		} else if(track.isTurnRight(x, y)) {
			turnRight()
		}
	}
	
	void turnLeft() {
		if(isMoovingRight()) {
			vy=-1
			vx=0
		} else if(isMoovingLeft()) {
			vx=0
			vy=1
		} else if(isMoovingDown()) {
			vx=-1
			vy=0
		} else if(isMoovingUp()) {
			vx=1
			vy=0
		}
	}
	
	String nextIntersectionStep() {
		String next = intersectionStrategy.poll()
		intersectionStrategy.offer(next)
		return next
	}
	
	void turnRight() {
		if(isMoovingRight()) {
			vy=1
			vx=0
		} else if(isMoovingLeft()) {
			vx=0
			vy=-1
		} else if(isMoovingDown()) {
			vx=1
			vy=0
		} else if(isMoovingUp()) {
			vx=-1
			vy=0
		}
	}
	
	boolean isMoovingRight() {
		return vx>0 && vy == 0
	}
	
	boolean isMoovingLeft() {
		return vx<0 && vy == 0
	}
	
	boolean isMoovingUp() {
		return vx==0 && vy < 0
	}
	
	boolean isMoovingDown() {
		return vx==0 && vy > 0
	}
	
	boolean isHorizontal() {
		return vy==0 && vx!=0
	}
	
	boolean isVertical() {
		return vy!=0 && vx == 0
	}
	
	static boolean isValidCart(String cart) {
		return VALID_CARTS.contains(cart)
	}
}
