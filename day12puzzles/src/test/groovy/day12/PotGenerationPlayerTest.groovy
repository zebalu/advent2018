package day12;

import static org.junit.Assert.*

import java.time.Duration
import java.time.Instant

import org.junit.Test

import spock.lang.Specification

class PotGenerationPlayerTest extends Specification {

	def 'example' () {
		def is = this.getClass().getResourceAsStream('/example.txt')
		PotGenerationPlayer pgp = PotGenerationPlayer.read(is)
		
		for(int i=0; i<20; ++i) {
			pgp.playAGeneration()
			
		}
		
		expect:
		325 == pgp.sumPotIndeces()
	}
	
	def 'input' () {
		def is = this.getClass().getResourceAsStream('/input21.txt')
		PotGenerationPlayer pgp = PotGenerationPlayer.read(is)
		
		for(int i=0; i<20; ++i) {
			pgp.playAGeneration()
			
		}
		
		expect:
		4110 == pgp.sumPotIndeces()
	}
/*	
	def 'input 50_000_000_000 iteration' () {
		def is = this.getClass().getResourceAsStream('/input21.txt')
		PotGenerationPlayer pgp = PotGenerationPlayer.read(is)
		long cheatSum = 0L
		for(long l = 50_000_000_000-3; l<=50_000_000_098; ++l) {
			cheatSum += l
		}
		println cheatSum
		
		Map<String, Tuple2> patterns = [:]
		String lastPattern = null
		Tuple2 lastCalc = null
		Instant start = Instant.now()
		for(int i=0; i<50_000_000_000; ++i) {
			pgp.playAGeneration()
			lastPattern = pgp.pattern()
			lastCalc = new Tuple2(i, pgp.sumPotIndeces())
			if(patterns.containsKey(lastPattern)) {
				break
			} else {
				patterns[lastPattern] = lastCalc
			}
			def lsumPrev = -1
			if(i%50_000==0) {
				Instant now = Instant.now()
				println "$i $pgp.min $pgp.max (length: ${pgp.max-pgp.min}) ${i*100/50_000_000_000}%"
				println "duration: ${Duration.between(start, now).toSeconds()}"
				long lsum = 0L
				pgp.plants.entrySet().stream().filter{it.value}.map{it.key-i+50_000_000_000}.each{
					lsum+=it
				}
				//println lsum			
				if(i>0) {
					println pgp.sumPotIndeces()-lsumPrev
					println "guess: ${(pgp.sumPotIndeces()-lsumPrev)*(1_000_000)}"
				}
				lsumPrev=pgp.sumPotIndeces()
			}
		}
		
		println "${patterns[lastPattern]} vs $lastCalc"
		def cycleLength = lastCalc.first - patterns[lastPattern].first
		def stepsToDo = (50_000_000_000 -patterns[lastPattern].first) / cycleLength
		def diff = lastCalc.second - patterns[lastPattern].second
		println "cycle: $cycleLength ; steps: $stepsToDo ; diff: $diff"
		println patterns[lastPattern].second + (stepsToDo-1)*diff
		
		
		expect:
		4110 == pgp.sumPotIndeces()
	}
*/	
}
