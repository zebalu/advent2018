package day22puzzles

enum Tool {

	TORCH, CLIMBING_GEAR, NEITHER

	boolean isCompatibleWith(RegionType region) {
		switch(this) {
			case TORCH:
				return region != RegionType.WET
			case CLIMBING_GEAR:
				return region != RegionType.NARROW
			case NEITHER:
				return region != RegionType.ROCKY
		}
	}
	
	static Tool forRegions(RegionType r1, RegionType r2) {
		Tool found = null
		values().each { Tool t ->
			if(found == null) {
				if(t.isCompatibleWith(r1) && t.isCompatibleWith(r2)) {
					found = t
				}
			}
		}
		return found
	}
}
