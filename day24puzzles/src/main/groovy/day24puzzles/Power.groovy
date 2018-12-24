package day24puzzles

enum Power {
	RADIATION, FIRE, SLASHING, BLUDGEONING, COLD
	
	public static Power fromString(String str) {
		for (Power p: values()) {
			if(p.toString().equalsIgnoreCase(str)) {
				return p
			}
		}
		throw new IllegalArgumentException("unknown power: $str")
	}
}
