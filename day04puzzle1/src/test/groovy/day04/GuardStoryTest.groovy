package day04;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class GuardStoryTest extends Specification {

    def private static final LINES = [
        '[1518-11-01 00:00] Guard #10 begins shift',
        '[1518-11-01 00:05] falls asleep',
        '[1518-11-01 00:25] wakes up',
        '[1518-11-01 00:30] falls asleep',
        '[1518-11-01 00:55] wakes up',
        '[1518-11-03 00:05] Guard #10 begins shift',
        '[1518-11-03 00:24] falls asleep',
        '[1518-11-03 00:29] wakes up'
    ]

    def "guard story can be appended"() {
        GuardStory gs = new GuardStory()
        JournalEntry je = new JournalEntry(null, 'message 1')
        JournalEntry je2 = new JournalEntry(null, 'message 2')

        gs.addEntry(je)
        gs.addEntry(je2)

        expect:
        gs.story[0] == je
        gs.story[1] == je2
    }

    def "all sleep minutes are calculated"() {
        GuardStory gs = buildStroyFromLines()

        expect:
        50 == gs.minutesAsleep()
    }
    
    def "most frequent minute is found"() {
        GuardStory gs = buildStroyFromLines()

        expect:
        24 == gs.mostFrequentSleepingMinute().first
    }

    private GuardStory buildStroyFromLines() {
        GuardStory gs = new GuardStory()
        LINES.each{ gs + JournalEntry.fromString(it)}
        return gs
    }
}
