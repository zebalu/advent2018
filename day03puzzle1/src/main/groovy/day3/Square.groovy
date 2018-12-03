package day3

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includes=['topLeftX', 'topLeftY', 'width', 'height'])
class Square {
    
    int topLeftX
    int topLeftY
    int width
    int height
    
    Square(x,y,w,h) {
        topLeftX=x
        topLeftY=y
        width=w
        height=h
    }
    
    int getTopRightX() {
        return topLeftX+width
    }
    
    int getTopRightY() {
        return topLeftY
    }
    
    int getBottomLeftX() {
        return topLeftX
    }
    
    int getBottomLeftY() {
        return topLeftY+height
    }
    
    int getBottomRightX() {
        return topLeftX+width
    }
    
    int getBottomRightY() {
        return topLeftY+height
    }
    
    int area() {
        return width*height
    }
    
    Square minus(Square other) {
        Square result = new Square(-1, -1, 0, 0)
        if(doOverlap(topLeftX, topRightX, other.topLeftX, other.topRightX) &&
            doOverlap(topLeftY, bottomLeftY, other.topLeftY, other.bottomLeftY)) {
            Tuple2<Integer, Integer> xes = getCommmons(topLeftX, topRightX, other.topLeftX, other.topRightX)
            Tuple2<Integer, Integer> ys = getCommmons(topLeftY, bottomLeftY, other.topLeftY, other.bottomLeftY)
            result.topLeftX=xes.first
            result.width=xes.second-xes.first
            result.topLeftY=ys.first
            result.height=ys.second-ys.first
        }
        return result
    }
    
    boolean contains(x, y) {
        return topLeftX <= x && x < topRightX &&
               topLeftY <= y && y < bottomLeftY
    }
    
    private static boolean doOverlap(a1, a2, b1, b2) {
        return a1<=b1 && b1<a2 || b1<=a1 && a1 < b2
    }
    
    private static Tuple2<Integer, Integer> getCommmons(a1, a2, b1, b2) {
        int start = Math.max(a1, b1)
        int end = Math.min(a2, b2)
        return new Tuple2(start, end)
    }
    
    static Tuple2<Integer, Square> fromString(String idSquare) {
        String[] idParts = idSquare.split(' @ ')
        def id =((idParts[0].substring(1)) as Integer)
        String[] squareParts = idParts[1].split(': ')
        String[] cornerParts = squareParts[0].split(',')
        String[] dimensionParts = squareParts[1].split('x')
        def square = new Square(cornerParts[0] as Integer, cornerParts[1] as Integer, dimensionParts[0] as Integer, dimensionParts[1] as Integer)
        return new Tuple2(id, square)
    }
    
    static Map<Integer, Square> claims(InputStream is) {
        Map<Integer, Square> claims = [:]
        is.withReader { r->
            r.eachLine { l->
                def idS = fromString(l)
                claims[idS.first] = idS.second
            }
        }
        return claims
    }

}
