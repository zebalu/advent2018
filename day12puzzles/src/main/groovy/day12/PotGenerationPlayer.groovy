package day12

class PotGenerationPlayer {

	Map<List<Boolean>, Boolean> rules = [:]
	Map<Long, Boolean> plants = [:]

	long min = 0;
	long max = 0;

	void addRule(String rule) {
		def pattern = [rule[0] == "#", rule[1] == "#", rule[2] == "#", rule[3] == "#", rule[4] == "#"]
		rules[pattern] = rule[9] == "#"
	}

	boolean getPlant(long i) {
		return getPlant(i, plants)
	}

	boolean getPlant(long i, Map<Integer, Boolean> plants) {
		return plants.computeIfAbsent(i, {false});
	}

	boolean addPlant(long i) {
		if(i<min) {
			min=i
		}
		if(i>max) {
			max=i
		}
		plants[i] = true
	}

	boolean killPlant(long i) {
		plants[i] = false
	}

	List<Boolean> plantArea(long i) {
		return plantArea(i, plants)
	}

	List<Boolean> plantArea(long i, Map<Integer, Boolean> plants) {
		return [getPlant(i-2, plants), getPlant(i-1, plants), getPlant(i, plants), getPlant(i+1, plants), getPlant(i+2, plants)]
	}

	void playAGeneration() {
		def keys = new HashSet<>(plants.keySet())
		def plantCopy = new HashMap<>(plants)
		(min..max).each { k ->
			def area = plantArea(k, plantCopy)
			if(rules[area]) {
				addPlant(k)
			} else {
				killPlant(k)
			}
		}
		plantCopy.keySet().each {
			if(!plants.containsKey(it)) {
				plants[it]=plantCopy[it]
				if(it<min) {
					min=it
				} 
				if(max<it) {
					max=it
				}
			}
		}
		TreeSet<Long> ordered = new TreeSet<>(plants.keySet())
		boolean leftUsefulFound = false
		for(long i=ordered.first(); i<=ordered.last()-2 && !leftUsefulFound; ++i) {
			leftUsefulFound=plants[i]
			if(leftUsefulFound) {
				for(long j=ordered.first(); j<i-2; ++j) {
					plants.remove(j)
				}
				min = i-2
			}
		}
		boolean rightUsefulFound = false
		for(long i=ordered.last(); i>=ordered.first()+2 && !rightUsefulFound; --i) {
			rightUsefulFound=plants[i]
			if(rightUsefulFound) {
				for(long j=ordered.last(); j>i+2; --j) {
					plants.remove(j)
				}
				max = i+2
			}
		}
	}

	def sumPotIndeces() {
		BigInteger sum = 0
		plants.entrySet().stream().sorted{a,b -> (int)(a.key-b.key)}.filter{
			 it.value}.map{ it.key}.forEach{ sum+=it}
		return sum
	}
	
	String pattern() {
		StringBuilder sb =new StringBuilder()
		plants.entrySet().stream().sorted{a,b -> (int)(a.key-b.key)}.each{
			if(it.value) {
				sb.append("#")
			} else {
				sb.append('.')
			}
		}
		return sb.toString()
	}

	static PotGenerationPlayer read(InputStream is) {
		PotGenerationPlayer pgp = new PotGenerationPlayer()
		def lines = is.readLines()
		def input = lines[0].substring('initial state: '.length())
		pgp.min=-2
		pgp.max=input.size()+2
		input.eachWithIndex { v,i ->
			if(v == '#') {
				pgp.addPlant(i)
			} else {
				pgp.killPlant(i)
			}
		}
		for(int i=2; i<lines.size(); ++i) {
			pgp.addRule(lines[i])
		}
		return pgp;
	}
}
