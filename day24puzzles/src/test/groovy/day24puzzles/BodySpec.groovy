package day24puzzles;

import java.awt.JobAttributes.SidesType

import spock.lang.Specification

class BodySpec extends Specification {

	def 'example fight'() {
		Body b = Body.importFromStream(getClass().getResourceAsStream('/example.txt'))
		while(!b.isWarOver()) {
			b.doARound()
		}

		expect:
		5216 == b.unitCount()
	}

	def 'example with example boost'() {
		Body b = Body.importFromStream(getClass().getResourceAsStream('/example.txt'))
		b.boostImmuneSystem(1570)
		while(!b.isWarOver()) {
			b.doARound()
		}

		expect:
		51 == b.unitCount()
		b.winningSide == Side.IMMUNE_SYSTEM
	}

	def 'example is read correctly'() {
		Body b = Body.importFromStream(getClass().getResourceAsStream('/example.txt'))

		expect:
		b.groups.size() == 4
	}

	def 'input part 1'() {
		Body b = Body.importFromStream(getClass().getResourceAsStream('/input.txt'))
		while(!b.isWarOver()) {
			b.doARound()
		}

		expect:
		22859 == b.unitCount()
	}

	def 'input part 2'() {
		Side winnig = null
		int minBoost = 0
		int maxBoost = 5_000
		int steps = 0
		int immuneSystemUnits = -1
		while(minBoost!=maxBoost) {
			++steps
			int boost= (minBoost+maxBoost).intdiv(2)
			Body b = Body.importFromStream(getClass().getResourceAsStream('/input.txt'))
			b.boostImmuneSystem(boost)
			try {
				int rounds = 0
				while(!b.isWarOver()) {
					b.doARound()
					++rounds
				}
				winnig = b.winningSide
				if(winnig == Side.IMMUNE_SYSTEM) {
					maxBoost=boost
					immuneSystemUnits = b.unitCount()
				} else {
					minBoost=boost
				}
			} catch(IllegalStateException ise) {
				minBoost++
				//ise.printStackTrace()
			}
		}
		expect:
		immuneSystemUnits == 2834
	}
}
