package day05

import groovy.transform.Canonical

@Canonical
class Polymer {

    List<SequenceElement> transformed

    Polymer(String chain) {
        if(chain.endsWith('\n')) {
            transformed = fromstring(chain.substring(0, chain.length()-1))
        } else {
            transformed = fromstring(chain)
        }
    }
    
    Polymer(List<SequenceElement> transformed) {
        this.transformed = new LinkedList<>(transformed)
    }

    void react() {
        Deque<SequenceElement> to = new LinkedList<>()
        Deque<SequenceElement> from = transformed
        while(!from.isEmpty()) {
            if(to.isEmpty()) {
                to.addLast(from.removeFirst())
            } else {
                SequenceElement toE = to.peekLast()
                SequenceElement fromE = from.pollFirst()
                if(!toE.canMergeWith(fromE)) {
                    to.offerLast(fromE)
                } else {
                    to.removeLast()
                }
            }
        }
        transformed = to
    }

    Polymer bestReduction() {
        Polymer best = new Polymer(transformed)
        ('a'..'z').each { chr ->
            Polymer p = Polymer.deduceFrom(this, chr)
            p.react()
            if(p.transformed.size() < best.transformed.size()) {
                best = p
            }
        }
        return best;
    }
    
    String getChain() {
        return fromElements(transformed)
    }
    
    int size() {
        return transformed.size()
    }

    static Polymer deduceFrom(Polymer p, String character) {
        List<Sequence> reduced = new LinkedList<>()
        def c = new SequenceElement(character.toLowerCase())
        def C = new SequenceElement(character.toUpperCase())
        p.transformed.each {
            if(!it.canMergeWith(c) && !it.canMergeWith(C)) {
                reduced.add(it)
            }
        }
        return new Polymer(reduced)
    }
    
    private static List<SequenceElement> fromstring(String s) {
        if(s.size()== 17) {
            throw new IllegalStateException()
        }
        List<SequenceElement> se = new LinkedList<>()
        s.each { c->se.add(new SequenceElement(c)) }
        return se
    }
    
    private static String fromElements(Collection<SequenceElement> elements) {
        return elements.collect{ it.base }.join('')
    }
}
