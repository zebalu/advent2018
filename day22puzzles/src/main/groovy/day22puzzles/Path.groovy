package day22puzzles

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

@Canonical
@EqualsAndHashCode(includes=['position', 'tool', 'price'])
class Path {

	Coord position
	Tool tool
	int price = -1
	Set<Coord> way = new HashSet<>()
	CaveArea currentArea
	List<String> history = []
	Map<Coord, Tool> used = [:]

	boolean canEnter(CaveArea area) {
		return tool.isCompatibleWith(area.type)
	}
	
	Path enter(CaveArea area) {
		Set<Coord> newWay = new HashSet<>(way)
		newWay.add(area.coord)
		int newPrice = price+1
		List<String> newHistory = new ArrayList<>(history)
		newHistory << "enter ${area.type} area at ( $area.coord.x , $area.coord.y ) minutes ellasped: $newPrice"
		Map<Coord, Tool> newUsed = new HashMap(used)
		newUsed[area.coord] = tool
		Path r = new Path(position: area.coord, tool: tool, way: newWay, price: newPrice, currentArea:area, history: newHistory, used: newUsed)
		r.price=newPrice
		return r // new Path(position: area.coord, tool: tool, way: newWay, price: newPrice, currentArea:area, history: newHistory)
	}
	
	Path switchFor(CaveArea area) {
		Tool newTool = Tool.forRegions(currentArea.type, area.type)
		int newPrice = price+7
		List<String> newHistory = new ArrayList<>(history)
		newHistory << "switch to $newTool from ${tool} to enter ( $area.coord.x , $area.coord.y ) which is ${area.type} (and I am in a ${currentArea.type}) minutes ellasped: $newPrice"
		Map<Coord, Tool> newUsed = new HashMap(used)
		newUsed[area.coord] = newTool
		return new Path(position: position, tool: newTool, way: way, price: newPrice, currentArea: currentArea, history: newHistory, used: newUsed)
	}
	
	Path switchFor(Tool tool) {
		int newPrice = price+7
		List<String> newHistory = new ArrayList<>(history)
		newHistory << "switch to $tool for searching minutes ellasped: $newPrice"
		Map<Coord, Tool> newUsed = new HashMap(used)
		newUsed[position] = tool
		return new Path(position: position, tool: tool, way: way, price: newPrice, currentArea: currentArea, history: newHistory, used: newUsed)
	}
	
	boolean hasBeen(CaveArea area) {
		return way.contains(area.coord)
	}
	
	String asHistory() {
		StringJoiner sj = new StringJoiner('\n')
		history.each { sj.add(it) }
		return sj.toString()
	}
}
