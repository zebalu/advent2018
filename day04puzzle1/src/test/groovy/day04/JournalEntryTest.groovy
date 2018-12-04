package day04;

import static org.junit.Assert.*

import java.time.Month
import java.time.temporal.ChronoField

import org.junit.Test

import spock.lang.Specification

class JournalEntryTest extends Specification {

    def "can be parsed"() {
        JournalEntry je = JournalEntry.fromString("[1518-11-01 00:00] Guard #10 begins shift")
        expect:
        je.timeStamp.getYear() == 1518
        je.timeStamp.getMonth() == Month.NOVEMBER
        je.timeStamp.getDayOfMonth() == 1
        je.timeStamp.getHour() == 0
        je.timeStamp.getMinute() == 0
        je.message == 'Guard #10 begins shift'
    }
    
    def "siftstart is registered"() {
        JournalEntry je = JournalEntry.fromString("[1518-11-01 00:00] Guard #10 begins shift")
        expect:
        je.isShiftStart()
        10 == je.getGuardId()
    }
    
    def "falls asleep registered"() {
        JournalEntry je = JournalEntry.fromString("[1518-11-01 00:05] falls asleep")
        expect:
        je.isFallsAsleep()
    }
    
    def "wakes up registered"() {
        JournalEntry je = JournalEntry.fromString("[1518-11-01 00:25] wakes up")
        expect:
        je.isWakesUp()
    }
    
    def "can be sorted"() {
        def entries = [JournalEntry.fromString("[1518-11-01 00:05] falls asleep"), JournalEntry.fromString("[1518-11-01 00:25] wakes up"), JournalEntry.fromString("[1518-11-01 00:00] Guard #10 begins shift")]

        Collections.sort(entries)
        
        expect:
        entries[0].isShiftStart()
        entries[1].isFallsAsleep()
        entries[2].isWakesUp()
    }
    
    
    
}
