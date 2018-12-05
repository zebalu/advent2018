package day05;

import spock.lang.Specification

class PolymerTest extends Specification {

    def "aA is destroyed" () {
        Polymer p = new Polymer('aA')
        p.react()
        
        expect:
        p.chain == ''
    }
    
    def "abBA is destroyed" () {
        Polymer p = new Polymer('abBA')
        p.react()
        
        expect:
        p.chain == ''
    }
    
    def "abAB is left unchanged" () {
        Polymer p = new Polymer('abAB')
        p.react()
        
        expect:
        p.chain == 'abAB'
    }
    
    def "aabAAB is left unchanged" () {
        Polymer p = new Polymer('aabAAB')
        p.react()
        
        expect:
        p.chain == 'aabAAB'
    }
    
    def "dabAcCaCBAcCcaDA is shrinked correctly" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA')
        p.react()
        
        expect:
        p.chain == 'dabCBAcaDA'
    }
    
    def "new line is cut from input" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA\n')
        p.react()
        
        expect:
        p.chain == 'dabCBAcaDA'
    }
    
    def "dabAcCaCBAcCcaDA deduced ith Aa is shrinked correctly" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA')
        p = Polymer.deduceFrom(p, 'a')
        p.react()
        
        expect:
        p.chain == 'dbCBcD'
    }
    
    def "dabAcCaCBAcCcaDA deduced ith bB is shrinked correctly" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA')
        p = Polymer.deduceFrom(p, 'b')
        p.react()
        
        expect:
        p.chain == 'daCAcaDA'
    }
    
    def "dabAcCaCBAcCcaDA deduced ith cC is shrinked correctly" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA')
        p = Polymer.deduceFrom(p, 'C')
        p.react()
        
        expect:
        p.chain == 'daDA'
    }
    
    def "dabAcCaCBAcCcaDA deduced ith dD is shrinked correctly" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA')
        p = Polymer.deduceFrom(p, 'D')
        p.react()
        
        expect:
        p.chain == 'abCBAc'
    }
    
    def "best reduction for dabAcCaCBAcCcaDA is found" () {
        Polymer p = new Polymer('dabAcCaCBAcCcaDA')
        p = p.bestReduction()
        
        expect:
        p.chain == 'daDA'
    }
 /*   
    def 'shrink input'() {
        File f = new File(this.getClass().getResource('/input9.txt').file)
        Polymer p = new Polymer(f.getText('UTF-8'))
        
        p.react()
        
        expect:
        9060==p.size()
    }
    
    def 'best deduction found with shrink input'() {
        File f = new File(this.getClass().getResource('/input9.txt').file)
        Polymer p = new Polymer(f.getText('UTF-8'))
        p = p.bestReduction()
        
        expect:
        6310==p.size()
    }
 */   
}
