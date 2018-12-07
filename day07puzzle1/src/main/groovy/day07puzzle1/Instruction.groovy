package day07puzzle1

import java.util.regex.Pattern
import java.util.stream.Collectors

class Instruction {

    private static final Pattern PATTERN = Pattern.compile('(Step )([A-Z])( must be finished before step )([A-Z])( can begin\\.)')

    final Map<String, InstructionPart> instructions = new HashMap<>()

    private Instruction() {}

    boolean isItBuilt() {
        return instructions.isEmpty()
    }

    String whatToBuildNext() {
        instructions.entrySet().stream().filter{it.value.canBeBuilt()}.min{a,b -> a.key.compareTo(b.key)}.orElseThrow{new NoSuchElementException()}.key
    }
    
    List<String> canBeBuitNext() {
        instructions.entrySet().stream().filter{it.value.canBeBuilt()}.sorted{a,b -> a.key.compareTo(b.key)}.map{it.key}.collect(Collectors.toList())
    }
    
    boolean hasBuildable() {
        !instructions.entrySet().stream().filter{it.value.canBeBuilt()}.collect{Collectors.toList()}.isEmpty()
    }
    
    void build(String id) {
        instructions.remove(id).build()
    }
    
    String buildAll() {
        StringBuilder sb= new StringBuilder()
        while(!isItBuilt()) {
            String nextPart = whatToBuildNext()
            build(nextPart)
            sb.append(nextPart)
        }
        return sb.toString()
    }
    
    static Instruction fromStringInstructions(String text) {
        Instruction result = new Instruction()
        text.eachLine {
            def matcher = PATTERN.matcher(it);
            if(matcher.matches()) {
                String p = matcher.group(2)
                String b = matcher.group(4)
                InstructionPart ip = result.instructions.computeIfAbsent(p, {new InstructionPart(p)})
                InstructionPart ib = result.instructions.computeIfAbsent(b, {new InstructionPart(b)})
                ip.doBlock(ib);
            }
        }
        return result
    }
}
