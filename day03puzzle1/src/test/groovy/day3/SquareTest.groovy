package day3;

import java.util.Map.Entry

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

    def "non overlaping has 0 area"() {
        Square s1 = new Square(0, 0, 3, 3)
        Square s2 = new Square(3, 3, 2, 2)

        expect:
        0 == (s1 - s2).area()
    }

    def "overlaping calculated correctly"() {
        Square s1 = new Square(0, 0, 4, 4)
        Square s2 = new Square(2, 2, 3, 3)

        expect:
        new Square(2, 2, 2, 2) == s1 - s2
    }
    /*
     def "get common area of input"() {
     int areaSum = 0
     List<Square> squares = []
     getClass().getResourceAsStream("/input5.txt").withReader { r->
     r.eachLine { 
     squares += Square.fromString(it).second
     }
     }
     for(int x=0; x<1000; x+=1) {
     for(int y=0; y<1000; y+=1) {
     int inSquares = 0
     for(int i=0; i<squares.size() && inSquares < 2; ++i) {
     inSquares += squares[i].contains(x, y)?1:0
     if(squares[i].contains(x, y) && inSquares > 1) {
     println "$x, $y is in: $i= ${squares[i]}, was found in: $inSquares"
     }
     }
     if(inSquares > 1) {
     ++areaSum
     println "area: $areaSum"
     }
     }
     }
     expect:
     areaSum == 111630
     }*/

    def "points found"(int x, int y, boolean contains) {
        Square s = new Square(0, 0, 2, 2)

        expect:
        contains == s.contains(x, y)

        where:
        x| y| contains
        0| 0| true
        0| 2| false
        1| 1| true
        3| 1| false
        2| 3| false
    }

    def "get not overlaping square"() {
        Map<Integer, Square> claims = Square.claims(getClass().getResourceAsStream("/input5.txt"))
        List<Entry<Integer, Square>> entries = new ArrayList<>(claims.entrySet())
        Integer id = null;
        for(int i=0; i<entries.size() && id == null; ++i) {
            def overlapFound = false
            for(int j=0; j<entries.size() && !overlapFound; ++j) {
                if(i!=j) {
                    overlapFound = ((entries[i].value - entries[j].value).area() > 0) || ((entries[j].value - entries[i].value).area() > 0)
                }
            }
            if(!overlapFound) {
                id = entries[i].key
            }
        }

        expect:
        id == 724
    }
}
