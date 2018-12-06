package day06

import groovy.transform.Canonical

@Canonical
class Coordinate {
    
    int x
    int y
    
    static Coordinate fromString(String s) {
        def cs = s.split(', ')
        return new Coordinate(cs[0] as Integer, cs[1] as Integer)
    }
    
}
