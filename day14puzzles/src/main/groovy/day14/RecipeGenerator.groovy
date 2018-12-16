package day14

class RecipeGenerator {

	List<Byte> recipes = [(byte)3,(byte)7]
	int elf1 = 0
	int elf2 = 1
	
	void round() {
		generate()
		step()
	}
	
	void generate() {
		int generated = recipes[elf1]+recipes[elf2]
		if(generated >9) {
			recipes.add((byte)(generated.intdiv(10)))
			recipes.add((byte)(generated%10))
		} else {
			recipes.add((byte)generated)
		}
	}
	
	void generateTillScore(String score) {
		List<Integer> digits = toDigits(score)
		int i=0;
		while(digits.size()>recipes.size()+1||!endsWith(digits)) {
			round()
		}
	}
	
	List<Integer> toDigits(String str) {
		def result = []
		str.each { result+=it as Integer }
		return result
	}
	
	boolean endsWith(List<Integer> digits) {
		return endsWithExactly(digits) || endsWithShifted(digits)
	}
	
	boolean endsWithExactly(List<Integer> digits) {
		for(int i=0; i<digits.size(); ++i) {
			if(!(digits[i] == recipes[i+recipes.size()-digits.size()])) {
				return false
			}
		}
		return true
	}
	
	boolean endsWithShifted(List<Integer> digits) {
		for(int i=0; i<digits.size(); ++i) {
			if(!(digits[i] == recipes[i+recipes.size()-digits.size()-1])) {
				return false
			}
		}
		return true
	}
	
	void step() {
		elf1=stepFrom(elf1)
		elf2=stepFrom(elf2)
	}
	
	int stepFrom(int start) {
		int steps = recipes[start]+1
		int land = start+steps
		return land%recipes.size()
	}
	
	int recipesBefore(String score) {
		def digits = toDigits(score)
		int j=recipes.size()-digits.size()
		boolean found = false
		while(j>=digits.size() && !found) {
			boolean different = false
			for(int i=0; i<digits.size() && !different; ++i) {
				different = recipes[j+i] != digits[i]
			}
			found = !different
			if(!found) {
				--j
			}
		}
		if(found) {
			return j
		}
		throw new NoSuchElementException()
	}
	
	String last10DigitsAfter(int recipeCount) {
		lastXDigitsAfterY(10, recipeCount)
	}
	
	String lastXDigitsAfterY(int x, int y) {
		if(y+x > recipes.size()) {
			throw new IllegalArgumentException("there are too few recepies")
		}
		StringBuilder sb = new StringBuilder()
		for(int i=y; i<y+x; ++i) {
			sb.append(recipes[i])
		}
		return sb.toString()
	}
	
	int recipeCount() {
		return recipes.size()
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(" ")
		for(int i=0; i<recipes.size(); ++i) {
			if(i==elf1 && i == elf2) {
				sj.add("([${recipes[i]}])")
			} else if (i==elf1) {
				sj.add("(${recipes[i]})")
			} else if(i==elf2) {
				sj.add("[${recipes[i]}]")
			} else {
				sj.add(" ${recipes[i]} ")
			}
		}
		return sj.toString();
	}
}
