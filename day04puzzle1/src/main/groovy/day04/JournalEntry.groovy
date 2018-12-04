package day04

import java.text.SimpleDateFormat
import java.time.LocalDateTime

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
@ToString(includes=[ "timeStamp", "message"])
class JournalEntry implements Comparable<JournalEntry> {

    private static final String PATTERN = '[yyyy-MM-dd HH:mm] '
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN)

    LocalDateTime timeStamp
    String message

    boolean isShiftStart() {
        return message.contains("begins shift")
    }
    
    boolean isFallsAsleep() {
        return message == 'falls asleep'
    }
    
    boolean isWakesUp() {
        return message == 'wakes up'
    }
    
    int getGuardId() {
        if(isShiftStart()) {
            return message.split(' ')[1].substring(1) as Integer
        }
        throw new IllegalStateException("can not provide id, not shift start")
    }
    
    @Override
    public int compareTo(JournalEntry o) {
        return timeStamp.compareTo(o.timeStamp)
    }

    static JournalEntry fromString(String s) {
        LocalDateTime time = DATE_FORMAT.parse(s.substring(0, PATTERN.length())).toLocalDateTime()
        String message = s.substring(PATTERN.length(), s.length())
        return new JournalEntry(time, message)
    }
}
