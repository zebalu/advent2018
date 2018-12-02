package day02;

import spock.lang.Specification

class CheckSumBaseTest extends Specification {

    def "input counts"(String id, boolean hasDoubleLetters, boolean hasTripleLetters) {
        CheckSumBase csb = new CheckSumBase(id)
        
        expect:
        hasDoubleLetters == csb.hasDoubleLetter
        hasTripleLetters == csb.hasTripletLetter
        
        where:
        id      |hasDoubleLetters|hasTripleLetters
        'bababc'|true            |true
        'abbcde'|true            |false
        'abcccd'|false           |true
        'aabcdd'|true            |false
        'abcdee'|true            |false
        'ababab'|false           |true
    }
    
}
