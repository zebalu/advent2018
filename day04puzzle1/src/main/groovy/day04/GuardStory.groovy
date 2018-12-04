package day04

import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Map.Entry

import groovy.transform.Canonical

@Canonical
class GuardStory {

    List<JournalEntry> story = []
    
    void addEntry(JournalEntry e) {
        story.add(e)
    } 
    
    GuardStory plus(JournalEntry e) {
        story.add(e)
        return this
    }
    
    int minutesAsleep() {
        int sum = 0
        JournalEntry lastAsleep = null 
        story.each { s ->
            if(s.isFallsAsleep()) {
                lastAsleep = s
            } else if (s.isWakesUp()) {
                Duration d = Duration.between(lastAsleep.timeStamp.toInstant(ZoneOffset.UTC), s.timeStamp.toInstant(ZoneOffset.UTC))
                sum += d.toMinutes()
            }
        }
        return sum
    }
    
    Tuple2<Integer, Integer> mostFrequentSleepingMinute() {
        def minuteFrquency = [:]
        JournalEntry lastAsleep = null
        story.each { s ->
            if(s.isFallsAsleep()) {
                lastAsleep = s
            } else if (s.isWakesUp()) {
                Duration d = Duration.between(lastAsleep.timeStamp.toInstant(ZoneOffset.UTC), s.timeStamp.toInstant(ZoneOffset.UTC))
                for(int i = lastAsleep.timeStamp.minute; i<lastAsleep.timeStamp.minute+d.toMinutes(); ++i) {
                    int sum = minuteFrquency.computeIfAbsent(i, {m->0})
                    minuteFrquency[i]=sum+1
                }
            }
        }
        if(minuteFrquency) {
            Entry<Integer, Integer> e = minuteFrquency.entrySet().stream().max { a, b -> a.value-b.value }.orElseThrow{ new NoSuchElementException()}
            return new Tuple2(e.key, e.value)
        } 
        return new Tuple2(-1, 0);
    }
}
