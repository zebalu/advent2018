package day24puzzles

import java.util.regex.Matcher
import java.util.regex.Pattern

import groovy.transform.Canonical
import groovy.transform.Sortable
import groovy.transform.ToString

@Canonical
@ToString(includeNames=true)
@Sortable(includes=['effectivePower', 'initiative'],reversed=true)
class Group {
	private static final Pattern PATTERN = Pattern.compile(/^(\d+)( units each with )(\d+)( hit points)( \([^)]*\))?( with an attack that does )(\d+)( )(.*)( damage at initiative )(\d+)/)

	int unitCount
	int hitPointsPerUnit
	int attackStrength
	int initiative
	List<Power> immunities = []
	List<Power> weaknesses = []
	Power power
	Side side

	int getEffectivePower() {
		return attackStrength * unitCount
	}

	Group getGroupToAttack(List<Group> attackable) {
		if(attackable == null || attackable.isEmpty()) {
			return null
		}
	//	def damageable = attackable.findAll { it.getDealableDamage(effectivePower, power) > it.hitPointsPerUnit }
		PriorityQueue pq = new PriorityQueue({Group a, Group b ->
			b.getDealableDamage(this.effectivePower, power) - a.getDealableDamage(effectivePower, power) ?: b.effectivePower - a.effectivePower ?: b.initiative - a.initiative
		})
		pq.addAll(attackable)
		Group toAttack = pq.peek()
		if(toAttack != null && toAttack.getDealableDamage(effectivePower, power) == 0) {
			return null
		}
		return toAttack
	}
	
	boolean isAlive() {
		return unitCount > 0
	}
	
	void attack(Group other) {
		int damage = other.getDealableDamage(getEffectivePower(), power)
		int units = damage.intdiv(other.hitPointsPerUnit)
		other.unitCount-=units
	}
	
	int getDealableDamage(int strength, Power power) {
		if(immunities.contains(power)) {
			return 0
		} else if(weaknesses.contains(power)) {
			return strength * 2
		} else {
			return strength
		}
	}
	
	static Group fromString(String description) {
		Matcher matcher = PATTERN.matcher(description)
		if(matcher.matches()) {
			Group g = new Group()
			g.unitCount=matcher.group(1) as Integer
			g.hitPointsPerUnit = matcher.group(3) as Integer
			def wi = parseWeaknesAndImmunity(matcher.group(5))
			g.weaknesses = wi.first
			g.immunities = wi.second
			g.attackStrength = matcher.group(7) as Integer
			g.power = Power.fromString(matcher.group(9))
			g.initiative = matcher.group(11) as Integer
			return g
		}
		throw new IllegalArgumentException("it is not a valid description: $description")
	}

	private static Tuple2<List<Power>, List<Power>> parseWeaknesAndImmunity(String description) {
		List<Power> weaknes = []
		List<Power> immune  = []
		if(description) {
			String trimmed = description.trim().replaceAll(/[\(\)]/, '')
			String[] parts = trimmed.split('; ')
			for(String iwPart: parts) {
				List<Power> current = (iwPart.startsWith('weak to'))?weaknes:immune
				String[] cutTo = iwPart.split(' to ')
				String[] powers = cutTo[1].split(', ')
				for(String power:powers) {
					current.add(Power.fromString(power))
				}
			}
		}
		return new Tuple2(weaknes, immune)
	}
}
