package day07puzzle1

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes=['id'])
@ToString
class InstructionPart {

    private static final Map<String, Integer> PRICES = [:]
    
    static {
        int price = 1
        ('A'..'Z').each { PRICES[it] = price++ }
    }
    
    final String id

    private Set<InstructionPart> blocks = new HashSet<>()
    private Set<InstructionPart> blockedBy = new HashSet<>()
    
    InstructionPart(String id) {
        this.id=id
    }
    
    def doBlock(InstructionPart other) {
        blocks.add(other)
        other.blockedBy.add(this)
    }
    
    def unBluck(InstructionPart other) {
        blocks.remove(other)
        other.blockedBy.remove(this)
    }
    
    boolean canBeBuilt() {
        return blockedBy.isEmpty()
    }
    
    void build() {
        if(blockedBy.isEmpty()) {
            blocks.each { it.blockedBy.remove(this) }
            blocks.clear()
        } else {
            throw new IllegalStateException("this ($id) is blocked by $blockedBy so can not be built")
        }
    }
    
    int getPrice() {
        return PRICES[id]
    }
}
