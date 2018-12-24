package day24puzzles

import static day24puzzles.Side.IMMUNE_SYSTEM
import static day24puzzles.Side.INFECTION

class Body {

	List<Group> groups = []
	Set<Group> targeted = new HashSet<>()
	List<Tuple2<Group, Group>> attackPlan = []
	List<Group> deadunits = []

	void doARound() {
		groups.sort()
		resetStates()
		selectPhase()
		attackPhase()
		removeDeadUnits()
	}

	int unitCount () {
		groups.sum { it.unitCount }
	}

	private void resetStates() {
		targeted.clear()
		attackPlan.clear()
		deadunits.clear()
	}
	private void selectPhase() {
		groups.each {
			List<Group> attackable = getAttackableEnemiesOf(it)
			Group toAttack = it.getGroupToAttack(attackable)
			if(toAttack) {
				attackPlan.add(new Tuple2(it, toAttack))
				targeted.add(toAttack)
			}
		}
		if(attackPlan.isEmpty()) {
			println "it looks like nobody can attack anybody :/"
			throw new IllegalStateException("nobody can hurt anybody. this war will neved end")
		}
	}
	private void attackPhase() {
		def countsBefore = unitSums
		List<Group> initiative = new ArrayList<>(groups)
		initiative.sort({a,b -> b.initiative - a.initiative}).each { Group g ->
			Group e = getToAttackGroup(g)
			if(g.isAlive() && e != null && e.isAlive()) {
				g.attack(e)
				if(!e.isAlive()) {
					deadunits.add(e)
				}
			}
		}
		def countsAfter = unitSums
		if(countsBefore == countsAfter) {
			throw new IllegalStateException("nobody could hurt anybody. this war can never end.")
		}
	}
	private void removeDeadUnits() {
		deadunits.each {  groups.remove(it) }
	}

	boolean isWarOver() {
		def counts = getUnitCounts()
		return isWarOverByCount(counts)
	}

	void boostImmuneSystem(int boost) {
		groups.findAll { it.side == IMMUNE_SYSTEM }.each { it.attackStrength+=boost }
	}

	private boolean isWarOverByCount(counts) {
		return counts.first == 0 || counts.second == 0
	}

	Side getWinningSide() {
		def counts = getUnitCounts()
		if(isWarOverByCount(counts)) {
			if(counts.first >0) {
				return IMMUNE_SYSTEM
			} else if(counts.second > 0) {
				return INFECTION
			} else {
				throw new IllegalStateException("It should be over, but: $counts")
			}
		}
		throw new IllegalStateException("We don't know yet! Current IMMUNE vs Infection count: $counts")
	}

	private Tuple2 getUnitCounts() {
		int immuneSystemCount = 0
		int infectionCount = 0
		groups.each {
			if(it.side==IMMUNE_SYSTEM) {
				immuneSystemCount++
			} else {
				infectionCount++
			}
		}
		return new Tuple2(immuneSystemCount, infectionCount)
	}

	private Tuple2 getUnitSums() {
		int immuneSystemSum = 0
		int infectionSum = 0
		groups.each {
			if(it.unitCount>0) {
				if(it.side==IMMUNE_SYSTEM) {
					immuneSystemSum+=it.unitCount
				} else {
					infectionSum+=it.unitCount
				}
			}
		}
		return new Tuple2(immuneSystemSum, infectionSum)
	}

	private Group getToAttackGroup(Group attacker) {
		return attackPlan.find { it.first == attacker }?.second
	}

	private List<Group> getAttackableEnemiesOf(Group g) {
		groups.findAll{ it.side != g.side && !targeted.contains(it) }
	}

	void addGroup(Group g) {
		groups.add(g)
		Collections.sort(groups)
	}

	static Body importFromStream(InputStream is) {
		Body body = new Body()
		Side buildingFor = null
		is.withReader { r ->
			r.eachLine { l ->
				if(l == 'Immune System:') {
					buildingFor = IMMUNE_SYSTEM
				} else if (l == 'Infection:') {
					buildingFor = INFECTION
				} else if(!l.isEmpty()) {
					Group g = Group.fromString(l)
					g.side=buildingFor
					body.addGroup(g)
				}
			}
		}
		return body
	}
}
