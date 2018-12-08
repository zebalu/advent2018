package day08;

import spock.lang.Specification

class NodeTest extends Specification {

    def "example C has value 0"() {
        Tree t = exampleTree()
        
        expect:
        t.nodes[2].value() == 0 
    }
    
    def "example D has value 99"() {
        Tree t = exampleTree()
        
        expect:
        t.nodes[3].value() == 99
    }
    
    def "example A has value 66"() {
        Tree t = exampleTree()
        
        expect:
        t.nodes[0].value() == 66
    }
    
    def "root node value of input"() {
        def list = getClass().getResourceAsStream('/input15.txt').text.split().collect { it as Integer }
        Tree t = Tree.fromList(list)
        expect:
        25981 == t.nodes[0].value()
    }
    
    Tree exampleTree() {
        Tree.fromList([2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2,])
    }

}
