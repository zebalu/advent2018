package day04;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class GuardLogTest extends Specification {

    def static final STRING_LOG='''[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up'''
    
    def 'parsed data builds correct log'() {
        GuardLog gl = createFromLog()
        
        expect:
        2 == gl.log.size()
        gl.log.containsKey(10)
        gl.log.containsKey(99)
    }
    
    def 'most sleepy guard is found'() {
        GuardLog gl = createFromLog()
        Tuple2<Integer, Integer> guardIdTime = gl.mostSleepingGuard()
        
        
        expect:
        10 == guardIdTime.first
        50 == guardIdTime.second
    }
    
    def 'most frequently slept minute is found'() {
        GuardLog gl = createFromLog()
        Tuple2<Integer, Integer> guardIdTime = gl.mostFrequentlySleptMinuteByGuard()
        
        
        expect:
        99 == guardIdTime.first
        45 == guardIdTime.second
    }
    
    def 'find most sleepy guard with real Data'() {
        GuardLog gl = GuardLog.fromStream(getClass().getResourceAsStream('/input7.txt'))
        Tuple2<Integer, Integer> guardIdTime = gl.mostSleepingGuard()
        
        expect:
        118599 == guardIdTime.first * gl.log[guardIdTime.first].mostFrequentSleepingMinute().first
    }
    
    def 'find most sleept minute with real Data'() {
        GuardLog gl = GuardLog.fromStream(getClass().getResourceAsStream('/input7.txt'))
        Tuple2<Integer, Integer> guardIdTime = gl.mostFrequentlySleptMinuteByGuard()
        
        expect:
        33949 == guardIdTime.first * guardIdTime.second
    }

    private GuardLog createFromLog() {
        GuardLog gl = GuardLog.fromStream(new ByteArrayInputStream(STRING_LOG.getBytes('UTF-8')))
        return gl
    }
    
}
