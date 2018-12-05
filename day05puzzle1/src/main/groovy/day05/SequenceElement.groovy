package day05

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class SequenceElement {
    
    private static final Map<String, SequenceElement> STORE = [:]

    static {
        ('a'..'z').each{fromString(it)}
        ('A'..'Z').each{fromString(it)}
    }
    
    final String base
    final int codePoint
    final int lowCodePoint
    
    SequenceElement(String base) {
        if(base.length()!=1) {
            throw new IllegalArgumentException("Only 1 character long strings are accepted")
        }
        this.base = base
        codePoint = Character.codePointAt(base, 0)
        lowCodePoint = Character.codePointAt(base.toLowerCase(), 0) 
    }
    
    boolean canMergeWith (SequenceElement other) {
        return codePoint != other.codePoint && lowCodePoint == other.lowCodePoint
    }
    
    public static SequenceElement fromString(String base) {
        return STORE.computeIfAbsent(base, {s -> new SequenceElement(s)})
    }
    
}
