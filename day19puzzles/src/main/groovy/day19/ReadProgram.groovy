package day19

class ReadProgram {

	private static final String HASHTAG_IP_SPACE = "#ip "

	public static Tuple2<Integer, List<Tuple4<String, Integer, Integer, Integer>>> read(InputStream is) {
		def int ip = -1
		def resultList = []
		is.withReader { r ->
			r.eachLine { l ->
				if(l.startsWith(HASHTAG_IP_SPACE)) {
					ip = l.substring(HASHTAG_IP_SPACE.size()) as Integer
				} else {
					def parts = l.split()
					resultList.add(new Tuple4(parts[0], parts[1] as Integer, parts[2] as Integer, parts[3] as Integer))
				}
			}
		}
		return new Tuple2(ip, resultList)
	}
	
}
