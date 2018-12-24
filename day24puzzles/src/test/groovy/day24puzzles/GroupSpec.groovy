package day24puzzles;

import static day24puzzles.Power.COLD

import spock.lang.Specification

class GroupSpec extends Specification {

	def 'right group is selected'() {
		Group attacker = new Group(hitPointsPerUnit: 10, unitCount: 5, attackStrength: 2, power: COLD, initiative: 3)
		Group deffender1 = new Group(hitPointsPerUnit: 10, unitCount: 5, attackStrength: 2, initiative: 1)
		Group deffender2 = new Group(hitPointsPerUnit: 10, unitCount: 5, attackStrength: 2, initiative: 2)
		Group deffender3 = new Group(hitPointsPerUnit: 10, unitCount: 5, weaknesses: [COLD], initiative: 0)
		Group deffender4 = new Group(hitPointsPerUnit: 10, unitCount: 5, immunities: [COLD], initiative: 4)
		Group toAttack = attacker.getGroupToAttack([deffender1, deffender2, deffender3, deffender4])
		expect:
		toAttack == deffender3
	}

	def 'right group order is selected'() {
		Group attacker = new Group(hitPointsPerUnit: 10, unitCount: 5, attackStrength: 2, power: COLD, initiative: 3)
		Group deffender1 = new Group(hitPointsPerUnit: 10, unitCount: 5, attackStrength: 2, initiative: 1)
		Group deffender2 = new Group(hitPointsPerUnit: 10, unitCount: 5, attackStrength: 2, initiative: 2)
		Group deffender3 = new Group(hitPointsPerUnit: 10, unitCount: 5, weaknesses: [COLD], initiative: 0)
		Group deffender4 = new Group(hitPointsPerUnit: 10, unitCount: 5, immunities: [COLD], initiative: 4)
		List<Group> toAttack = [deffender1, deffender2, deffender3, deffender4]
		List<Group> attackOrder = []
		int initialSize = toAttack.size()
		for(int i=0; i<initialSize; ++i) {
			Group g = attacker.getGroupToAttack(toAttack)
			if(g!=null) {
				toAttack-=g
				attackOrder+=g
			}
		}
//		toAttack.each { 
//			println it
//		}
//		println '----'
//		attackOrder.each { 
//			println it
//		}
		expect:
		toAttack.size()==1
		toAttack.contains(deffender4)
		attackOrder.size()==3
		attackOrder == [deffender3, deffender2, deffender1]
	}
}
