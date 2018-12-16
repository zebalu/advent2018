package day16

class OpCodeMapping {

	Map<Integer, String> mapping = [:]
	
	OpCodeMapping(List<Observation> observations) {
		def grouping = observations.groupBy { it.command.first }
		def suspects = grouping.entrySet().collectEntries { 
			def List<List<String>> keyCollection = it.value.collect { it.getMatchingCommandKeys() }
			[(it.key) : intersectionsOf(keyCollection)]
		}
		Map<String, Integer> sureMappings = [:]
		while(sureMappings.size()<suspects.size()) {
			Map<Integer, List<String>> toSave = [:]
			suspects.entrySet().each { 
				if(it.value.size()==1) {
					if(!sureMappings.containsKey(it.value[0])) {
						sureMappings.put(it.value[0], it.key)
					}
				} else {
					def newValues = it.value - sureMappings.keySet()
					toSave[it.key]=newValues
				}
			}
			toSave.entrySet().each { 
				suspects[it.key]=it.value
			}
		}
		sureMappings.entrySet().each { 
			mapping[it.value] = it.key
		}
	}
	
	String getAt(int opCode) {
		mapping[opCode]
	}
	
	List<String> intersectionsOf(List<List<String>> keys) {
		List<String> base = keys[0]
		int next = 0
		while(base.size() > 0 && next<keys.size()-1) {
			++next
			List<String> other = keys[next]
			List<String> baseCopy = new ArrayList<>(base)
			for(String s : baseCopy) {
				if(!other.contains(s)) {
					base.remove(s)
				}
			}
		}
		return base	
	}
	
}
