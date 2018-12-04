package day04

import java.util.Map.Entry

import groovy.transform.Canonical

@Canonical
class GuardLog {

    Map<Integer, GuardStory> log = [:]

    GuardLog(List<JournalEntry> entries) {
        Collections.sort(entries)
        GuardStory latest = null
        entries.each { e ->
            if(e.isShiftStart()) {
                latest = log.computeIfAbsent(e.getGuardId(), {id -> new GuardStory()})
            }
            latest+e
        }
    }

    Tuple2<Integer, Integer> mostSleepingGuard() {
        return log.entrySet().stream().map{ Entry<Integer, GuardStory> e ->
            new Tuple2(e.key, e.value.minutesAsleep())
        }.max { a, b ->
            a.second - b.second
        }.orElseThrow{ new NoSuchElementException() }
    }
    
    Tuple2<Integer, Integer> mostFrequentlySleptMinuteByGuard() {
        def found = log.entrySet().stream().map{ Entry<Integer, GuardStory> e ->
            new Tuple2(e.key, e.value.mostFrequentSleepingMinute())
        }.max { a, b ->
            a.second.second - b.second.second
        }.orElseThrow{ new NoSuchElementException() }
        return new Tuple2(found.first, found.second.first)
    }

    static GuardLog fromStream(InputStream is) {
        def entries = []
        is.withReader { r ->
            r.eachLine { l->
                entries += JournalEntry.fromString(l)
            }
        }
        return new GuardLog(entries)
    }
}
