package day06

import java.util.stream.Collectors

class Grid {

   Map<Integer, List<Coordinate>> byX = new TreeMap<>()
   Map<Integer, List<Coordinate>> byY = new TreeMap<>()
   List<Coordinate> allCoordinates = []
   
   int minX() {
       ((TreeMap)byX).firstEntry().key
   }
   
   int maxX() {
       ((TreeMap)byX).lastEntry().key
   }
   
   int minY() {
       ((TreeMap)byY).firstEntry().key
   }
   
   int maxY() {
       ((TreeMap)byY).lastEntry().key
   }
   
   int largestArea() {
       Map<Coordinate, Integer> areas = [:]
       allCoordinates.each { 
           if(isInfinit(it)) {
               return
           }
           int sumArea = 1;
           int diff = 0;
           int prevSum = 0
           while(prevSum != sumArea) {
               prevSum = sumArea
               ++diff
               Set<Coordinate> cords = new HashSet<>()
               cords.addAll(getAllPointsBetween(new Coordinate(it.x-diff, it.y), new Coordinate(it.x, it.y-diff)))
               cords.addAll(getAllPointsBetween(new Coordinate(it.x, it.y-diff), new Coordinate(it.x+diff, it.y)))
               cords.addAll(getAllPointsBetween(new Coordinate(it.x+diff, it.y), new Coordinate(it.x, it.y+diff)))
               cords.addAll(getAllPointsBetween(new Coordinate(it.x, it.y+diff), new Coordinate(it.x-diff, it.y)))
               cords.each { c->
                   def closest = closestNeighbour(c)
                   if(closest != null && closest == it) {
                       ++sumArea
                   }
               }
           }
           areas[it]=sumArea
       }
       return areas.entrySet().stream().max{a,b -> 
            a.value - b.value
        }.orElseThrow{new NoSuchElementException()}.value
   }
   
   List<Coordinate> getAllPointsBetween(Coordinate c1, Coordinate c2) {
       def result = []
       result.add(c1)
       int xD = 1
       int yD = 1
       if(c2.x<c1.x) {
           xD = -xD
       }
       if(c2.y<c1.y) {
           yD = -yD
       }
       for(int i = 0; i<Math.abs(c1.x-c2.x)-1; ++i) {
           result.add(new Coordinate(c1.x+xD, c1.y+yD))
       }
       return result
   }
   
   int width () {
       return maxX()-minX()
   }
   
   int height() {
       return maxY-minY()
   }
   
   def isInfinit(Coordinate c) {
       return maxX()<=c.x || c.x <= minX() || maxY() <= c.y || c.y <= minY() || canExpandInfinit(c)
   }
   
   def canExpandInfinit(Coordinate c) {
       return false 
       /*
       return closestNeighbour(new Coordinate(c.x-width(), c.y)) == c ||
              closestNeighbour(new Coordinate(c.x+width(), c.y)) == c ||
              closestNeighbour(new Coordinate(c.x, c.y-height())) == c ||
              closestNeighbour(new Coordinate(c.x, c.y+height())) == c*/
   }
   
   Coordinate closestNeighbour(Coordinate c) {
       def neighboursByDistance = allCoordinates.stream()/*.filter{ n->n!=c}*/.map {n -> new Tuple2(Math.abs(c.x-n.x)+Math.abs(c.y-n.y), n) }.sorted{a,b -> a.first - b.first}.collect (Collectors.toList() )
       if(neighboursByDistance[0].first == neighboursByDistance[1].first) {
           return null
       }
       return neighboursByDistance[0].second
   }
   
   private List<Tuple2<Integer, Integer>> closests(Collection<Integer> vals, int val) {
       vals.collect{ new Tuple2(it, Math.abs(val-it))}.sort {a,b -> a.second - b.second?:b.first-a.first}
   }
   
   static Grid fromStream(InputStream is) {
       Grid result = new Grid()
       is.withReader { r->
           r.eachLine { l->
               Coordinate c = Coordinate.fromString(l)
               result.byX.computeIfAbsent(c.x, {[]}).add(c)
               result.byY.computeIfAbsent(c.y, {[]}).add(c)
               result.allCoordinates.add(c)
           }
       }
       return result
   }
}
