package day23

class Robots {

	List<Nanobot> bots = []

	Long minX=Long.MAX_VALUE
	Long maxX=Long.MIN_VALUE
	Long minY=Long.MAX_VALUE
	Long maxY=Long.MIN_VALUE
	Long minZ=Long.MAX_VALUE
	Long maxZ=Long.MIN_VALUE

	void readStream(InputStream is) {
		is.withReader { r ->
			r.eachLine { l ->
				Nanobot n =new Nanobot(l)
				bots.add(n)
				minX = Math.min(minX, n.x)
				minY = Math.min(minY, n.y)
				minZ = Math.min(minZ, n.z)
				maxX = Math.max(maxX, n.x)
				maxY = Math.max(maxY, n.y)
				maxZ = Math.max(maxZ, n.z)
			}
		}
	}

	long largestRadious () {
		bots.max { a,b -> a.r - b.r }.r
	}

	List<Nanobot> nanobotsWithRadious(long r) {
		def res = bots.findAll { it.r == r }
	}

	int countRobotsInRadious(Nanobot n) {
		bots.count { n.inRadious(it) }
	}
}
