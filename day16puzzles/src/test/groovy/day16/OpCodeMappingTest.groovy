package day16;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class OpCodeMappingTest extends Specification {

	def "build from input"() {
		OpCodeMapping ocm = new OpCodeMapping(Observation.observationsFromInput)
		println ocm
		
		expect:
		ocm != null
	}

	@Test
	public void testOpCodeMapping() throws Exception {
		throw new RuntimeException("not yet implemented");
	}
	
}
