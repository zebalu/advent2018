package day11puzzles

import groovy.transform.ToString

@ToString
class PowerGrid {

	List<List<Integer>> grid
	int serial
	
	PowerGrid(int width, int height, int serial) {
		grid = new ArrayList<List<Integer>>(width)
		for(int i=0; i<width; ++i) {
			grid[i] = new ArrayList(height)
			for(int j = 0; j<height; ++j) {
				grid[i][j]=0
			}
		}
		this.serial=serial
		generatePowers()
	}

	private void generatePowers( ) {
		for(int i=0; i<grid.size(); ++i) {
			for(int j=0; j<grid[i].size(); ++j) {
				grid[i][j] = hundrethDigit((((i+1+10)*(j+1))+serial)*(i+1+10))-5
			}
		}
	}
	
	Tuple3<Integer, Integer, Integer> getMaxCorrigatedXY() {
		Tuple3<Integer, Integer, Integer> max = new Tuple3(Integer.MIN_VALUE, -1, -1)
		for(int i=0; i<grid.size()-2; ++i) {
			for(int j=0; j<grid[i].size()-2; ++j) {
				def sum = 0;
				for(int x=0; x<3; ++x) {
					for(int y=0; y<3; ++y) {
						sum += grid[x+i][y+j]
					}
				}
				if(sum > max.first) {
					max = new Tuple3(sum, i+1, j+1)
				}
			}
		}
		return max
	}
	
	Tuple4<Integer, Integer, Integer, Integer> getMaxCorrigatedXY(int gridSize) {
		Tuple4<Integer, Integer, Integer, Integer> max = new Tuple4(Integer.MIN_VALUE, -1, -1, gridSize)
		for(int i=0; i<grid.size()-gridSize; ++i) {
			for(int j=0; j<grid[i].size()-gridSize; ++j) {
				def sum = 0;
				for(int x=0; x<gridSize; ++x) {
					for(int y=0; y<gridSize; ++y) {
						sum += grid[x+i][y+j]
					}
				}
				if(sum > max.first) {
					max = new Tuple4(sum, i+1, j+1, gridSize)
				}
			}
		}
		return max
	}
	
	Tuple4<Integer, Integer, Integer, Integer> getMaxAny() {
		Tuple4<Integer, Integer, Integer, Integer> max = new Tuple4(Integer.MIN_VALUE, -1, -1, -1)
		boolean maximumSumBecomeNegative = false
		for(int i=1; i<=grid.size() && !maximumSumBecomeNegative; ++i) {
			def other = getMaxCorrigatedXY(i)
			if(other.first > max.first) {
				max=other
				println "max: $max"
			} else {
				maximumSumBecomeNegative = other.first<0
			}
		}
		return max
	}
	
	Integer getAt(int i, int j) {
		return grid[i]?.getAt(j)
	}
	
	private int hundrethDigit(int input) {
		String str = "$input"
		if(str.length()>=3) {
			String d = str[-3]
			if(d!='-') {
				return d as Integer
			}
		}
		return 0
	}
		
}
