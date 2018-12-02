package day02

import groovy.transform.Canonical

@Canonical
class CheckSumBase {

    final String id
    final boolean hasDoubleLetter
    final boolean hasTripletLetter
    
    CheckSumBase(String id) {
        this.id=id
        hasDoubleLetter=findDoubletts()
        hasTripletLetter=findTripletts()
    }
    
    private boolean findDoubletts() {
        return findLettersRepeating(2)
    }
    
    private boolean findTripletts() {
        return findLettersRepeating(3)
    }
    
    private boolean findLettersRepeating(times) {
        def found = false
        id.each { check ->
            def count = 0
            id.each { letter ->
                if(letter == check) {
                    ++count
                }
            }
            if(count == times) {
                found = true
            }        
        } 
        return found
    }
    
}
