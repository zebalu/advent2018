package day08;

import spock.lang.Specification

class TreeTest extends Specification {

    def "example calculated correctly"() {
        Tree t = Tree.fromList([2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2,])
        expect:
        138 == t.sumMeta()
    }
    
    def "calculated on input"() {
        def list = getClass().getResourceAsStream('/input15.txt').text.split().collect { it as Integer }
        Tree t = Tree.fromList(list)
        expect:
        48260 == t.sumMeta()
    }
    
}
