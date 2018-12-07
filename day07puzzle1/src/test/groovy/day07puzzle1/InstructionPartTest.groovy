package day07puzzle1;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class InstructionPartTest extends Specification {

    def "block works both ways"() {
        def blocker = new InstructionPart('a')
        def blocked = new InstructionPart('b')
        
        blocker.doBlock(blocked)
        
        expect:
        blocker.blocks.contains(blocked)
        blocked.blockedBy.contains(blocker)
    }
    
    def "unblock works both ways"() {
        def blocker = new InstructionPart('a')
        def blocked = new InstructionPart('b')
        
        blocker.doBlock(blocked)
        
        blocker.unBluck(blocked)
        
        expect:
        !blocker.blocks.contains(blocked)
        !blocked.blockedBy.contains(blocker)
    }
    
    def "unblocked elements can be built"() {
        def blocker = new InstructionPart('a')
        def blocked = new InstructionPart('b')
        
        blocker.doBlock(blocked)
        
        expect:
        blocker.canBeBuilt()
    }
    
    def "blocked elements can not be built"() {
        def blocker = new InstructionPart('a')
        def blocked = new InstructionPart('b')
        
        blocker.doBlock(blocked)
        
        expect:
        !blocked.canBeBuilt()
    }
    
    def "build unblocks blocked"() {
        def blocker = new InstructionPart('a')
        def blocked = new InstructionPart('b')
        
        blocker.doBlock(blocked)
        
        blocker.build()
        
        expect:
        blocked.canBeBuilt()
    }
    
    def "prices are correct"(String id, int price) {
        InstructionPart ip = new InstructionPart(id)
        
        expect:
        ip.price == price
        
        where:
        id | price
        'A'| 1
        'B'| 2
        'C'| 3
        'D'| 4
        'E'| 5
        'F'| 6
        'G'| 7
        'H'| 8
        'I'| 9
        'J'| 10
        'K'| 11
        'L'| 12
        'M'| 13
        'N'| 14
        'O'| 15
        'P'| 16
        'Q'| 17
        'R'| 18
        'S'| 19
        'T'| 20
        'U'| 21
        'V'| 22
        'W'| 23
        'X'| 24
        'Y'| 25
        'Z'| 26
    }
    
}
