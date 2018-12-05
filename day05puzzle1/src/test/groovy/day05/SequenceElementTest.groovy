package day05;

import spock.lang.Specification

class SequenceElementTest extends Specification {

    def "a can merge with A"() {
        expect:
        new SequenceElement('a').canMergeWith(new SequenceElement('A'))
    }
    
    def "A can merge with a"() {
        expect:
        new SequenceElement('A').canMergeWith(new SequenceElement('a'))
    }
    
    def "A can not merge with b"() {
        expect:
        !(new SequenceElement('A').canMergeWith(new SequenceElement('b')))
    }
    
    def "factory method caches objects"() {
        expect:
        SequenceElement.fromString('A') is SequenceElement.fromString('A')
    }
    
    def "factory method builds new object on not matching case"() {
        expect:
        !(SequenceElement.fromString('A').is(SequenceElement.fromString('a')))
    }
}
