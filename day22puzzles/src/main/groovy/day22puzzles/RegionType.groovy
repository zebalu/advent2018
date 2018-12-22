package day22puzzles

enum RegionType {

	ROCKY('.', 0), WET('=',1), NARROW('|',2)

	private RegionType(String str, int rl) {
		this.representation=str
		this.riskLevel=rl
	}

	final String representation
	final int riskLevel

	@Override
	public String toString() {
		return representation
	}

	static RegionType forInt(int value) {
		switch(value%3) {
			case 0: return ROCKY
			case 1: return WET
			case 2: return NARROW
		}
	}
}
