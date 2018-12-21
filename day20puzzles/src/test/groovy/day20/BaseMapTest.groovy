package day20;

import spock.lang.Specification

class BaseMapTest extends Specification {

	def readExample1() {
		InputStream is = new StringInputStream('^ENWWW(NEEE|SSE(EE|N))$')
		BaseMap bm = BaseMap.fromStream(is)
		println bm
		expect:
		bm != null
	}
	
	def readExampleLongestPath() {
		InputStream is = new StringInputStream('^ENWWW(NEEE|SSE(EE|N))$')
		BaseMap bm = BaseMap.fromStream(is)
		Path p = bm.longestPath()
		println bm.toStringWithPath(p)
		expect:
		p.doorCount == 10
	}
	
	def readExample2LongestPath() {
		InputStream is = new StringInputStream('^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$')
		BaseMap bm = BaseMap.fromStream(is)
		Path p = bm.longestPath()
		println bm.toStringWithPath(p)
		expect:
		p.doorCount == 18
	}
	
	def readExample3LongestPath() {
		InputStream is = new StringInputStream('^WNE$')
		BaseMap bm = BaseMap.fromStream(is)
		Path p = bm.longestPath()
		println bm.toStringWithPath(p)
		expect:
		p.doorCount == 3
	}
	
	def readExample4LongestPath() {
		InputStream is = new StringInputStream('^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$')
		BaseMap bm = BaseMap.fromStream(is)
		Path p = bm.longestPath()
		println bm.toStringWithPath(p)
		expect:
		p.doorCount == 23
	}

	def readExample5LongestPath() {
		InputStream is = new StringInputStream('^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$')
		BaseMap bm = BaseMap.fromStream(is)
		Path p = bm.longestPath()
		println bm.toStringWithPath(p)
		expect:
		p.doorCount == 31
	}
	/*
	def readInputLongestPath() {
		BaseMap bm = BaseMap.fromStream(getClass().getResourceAsStream('/input.txt'))
		println bm
		Path p = bm.longestPath()
		println bm.toStringWithPath(p)
		expect:
		p.doorCount == 3930
	}
	
	def readInputCountPathLonger1000() {
		BaseMap bm = BaseMap.fromStream(getClass().getResourceAsStream('/input.txt'))
		println bm
		int count = bm.countPathsBiggerThan(1000)
		expect:
		count == 8240
	}
	*/
	
	private static class StringInputStream extends InputStream {
		
		private final ByteArrayInputStream bais = null;
		
		public StringInputStream(String str) {
			bais = new ByteArrayInputStream(str.getBytes('UTF-8'))
		}

		@Override
		public int read() throws IOException {
			return bais.read()
		}
		
		
	}
}
