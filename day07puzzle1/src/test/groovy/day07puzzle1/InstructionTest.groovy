package day07puzzle1;

import spock.lang.Specification

class InstructionTest extends Specification {

    def "build order"() {
        Instruction inst = Instruction.fromStringInstructions(getClass().getResourceAsStream('/input13.txt').text)
        
        expect:
        'BGKDMJCNEQRSTUZWHYLPAFIVXO'==inst.buildAll()
    }
}
