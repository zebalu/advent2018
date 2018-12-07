package day07puzzle1;

import static org.junit.Assert.*

import org.junit.Test

class ParallelInstructionConsumerTest {

    @Test
    void parallelConsume() {
        Instruction instruction = Instruction.fromStringInstructions(this.getClass().getResourceAsStream('/input13.txt').text)
        ParallelInstructionConsumer pic = new ParallelInstructionConsumer(instruction, 5)
        
        assertEquals(941, pic.workOnIt())
    }
    
}
