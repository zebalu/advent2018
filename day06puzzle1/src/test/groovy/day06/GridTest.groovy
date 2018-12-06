package day06;

import spock.lang.Specification

class GridTest extends Specification {

    private static final def STR = '''1, 1
1, 6
8, 3
3, 4
5, 5
8, 9'''
    
    def "example min max found"() {
        Grid g = Grid.fromStream(inputStr())
        
        g.largestArea()
        
        expect:
        1 == g.minX()
        1 == g.minY()
        8 == g.maxX()
        9 == g.maxY()
    }
    
    def "infinit coords are found"(int x, int y, boolean infinit) {
        Grid g = Grid.fromStream(inputStr())
        
        expect:
        infinit == g.isInfinit(new Coordinate(x,y))
        
        where:
        x | y | infinit
        1 | 1 | true
        1 | 6 | true
        3 | 4 | false
        5 | 5 | false
        8 | 3 | true
        8 | 9 | true
    }
 
    private static inputStr() {
        return new ByteArrayInputStream(STR.bytes)
    }   
}
