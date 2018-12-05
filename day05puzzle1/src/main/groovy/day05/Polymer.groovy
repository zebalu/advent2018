package day05

import groovy.transform.Canonical

@Canonical
class Polymer {

    String chain
    
    Polymer(String chain) {
        this.chain = chain
        if(chain.endsWith('\n')) {
            this.chain = chain.substring(0, chain.length()-1)
        }
    }
    
    void react() {
       int startChainLength = chain.size()+1
       while(startChainLength != chain.size()) {
           startChainLength = chain.size()
           reactStep()
       }
    }
    
    Polymer bestReduction() {
        Polymer best = new Polymer(chain)
        ('a'..'z').each { chr ->
            Polymer p = Polymer.deduceFrom(this, chr)
            p.react()
            if(p.chain.size() < best.chain.size()) {
                best = p
            }
        }
        return best;
    }
    
    private void reactStep() {
        for(int i=0; i<chain.size()-1; ++i) {
            if(chain[i].toLowerCase() == chain[i+1].toLowerCase() && chain[i] != chain[i+1]) {
                StringBuilder sb = new StringBuilder()
                if(i>0) {
                    sb.append(chain.substring(0, i))
                }
                if(i+2<chain.size()) {
                    sb.append(chain.substring(i+2, chain.length()))
                }
                chain=sb.toString()
                --i
                if(i>0) {
                    --i
                }
            }
        }
    }
    
    static Polymer deduceFrom(Polymer p, String character) {
        StringBuilder sb = new StringBuilder()
        def c = character.toLowerCase()
        def C = character.toUpperCase()
        for(int i=0; i<p.chain.size(); ++i) {
            def pci = p.chain[i]
            if(pci != c && pci != C) {
                sb.append(pci)
            }
        }
        return new Polymer(sb.toString())
    }
    
}
