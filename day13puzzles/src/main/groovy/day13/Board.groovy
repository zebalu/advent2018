package day13

class Board {

	Track track = new Track()
	List<Cart> carts = []
	List<Tuple2<Integer, Integer>> crashHistory = []
	
	def tick() {
		def cartsToRemove = []
		carts.sort {a,b -> a.x-b.x ?: a.y-b.y}
		carts.each { c ->
			c.move()
			if(isCartCrashed(c)) {
				println "crash on $c.x , $c.y"
				crashHistory<<new Tuple2<>(c.x, c.y)
				cartsToRemove+=findCartsOn(c.x, c.y)
			}
		}
		carts-=cartsToRemove
	}
	
	boolean isCartCrashed(Cart c) {
		return findCartsOn(c.x, c.y).size()>1
	}
	
	List<Cart> findCartsOn(int x, int y) {
		carts.findAll { it.x==x && it.y==y }
	}
	
	void print() {
		for(int y=0; y<=track.height; ++y) {
			for(int x=0; x<=track.width; ++x) {
				def carts = findCartsOn(x, y);
				if(carts.size()==0) {
					System.out.print(track[x,y])
				} else if(carts.size()==1) {
					def cart =carts[0]
					if(cart.isMoovingDown()) {
						System.out.print('v')
					} else if(cart.isMoovingUp()) {
						System.out.print('^')
					} else if(cart.isMoovingLeft()) {
						System.out.print('<')
					} else if(cart.isMoovingRight()) {
						System.out.print('>')
					}
				} else {
					System.out.print('X')
				}
			}
			System.out.println()
		}
	}
	
	public static Board importBoard(InputStream is) {
		Board b = new Board()
		is.withReader { r ->
			int y=0;
			r.eachLine { line ->
				for(int x=0; x<line.size(); ++x) {
					String element = line[x]
					if(b.track.isValidElement(element)) {
						b.track.addRoute(x, y, b.track.cachedElement(element)) 
					}
					if(Cart.isValidCart(element)) {
						Cart c = new Cart(x,y, element, b.track)
						if(c.isHorizontal()) {
							b.track.addRoute(x, y, b.track.cachedElement('-'))
						} else if(c.isVertical()) {
							b.track.addRoute(x, y, b.track.cachedElement('|'))
						} else {
							throw new IllegalStateException("this cart makes me crazy: $c")
						}
						b.carts.add(c)
					}
				}
				++y
			}
		}
		return b
	}
	
	
}
