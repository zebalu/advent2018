package day3;

import spock.lang.Specification

class SquareTest extends Specification {

    def "area  works"() {
        Square sq = new Square(0, 0, 10, 8)
        
        expect:
        80 == sq.area()
    }
    
    def 'parsing works' () {
        def result = Square.fromString('#3 @ 12,32: 10x2')
        
        expect:
        result.first == 3 &&
        result.second.topLeftX == 12 &&
        result.second.topLeftY == 32 &&
        result.second.width == 10 &&
        result.second.height == 2 
    }
    
    def "corners work"() {
        Square sq = new Square(1, 2, 10, 8)
        
        expect:
        1 == sq.topLeftX
        2 == sq.topLeftY
        11 == sq.topRightX
        2 == sq.topRightY
        1 == sq.bottomLeftX
        10 == sq.bottomLeftY
        11 == sq.bottomRightX
        10 == sq.bottomRightY
    }
}
