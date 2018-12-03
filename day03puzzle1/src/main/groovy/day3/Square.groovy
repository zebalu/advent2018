package day3

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
        if(doOverlap(topLeftX, topRightX, other.topLeftX, other.topRightX)) {
            
        }
        return result
    }
    
    private static boolean doOverlap(a1, a2, b1, b2) {
        return a1<b1 && a2>b1 || b1<a1 && b2 > a1
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

}
