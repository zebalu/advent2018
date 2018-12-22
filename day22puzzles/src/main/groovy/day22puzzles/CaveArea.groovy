package day22puzzles

import groovy.transform.Canonical
import groovy.transform.Sortable

@Canonical
@Sortable
class CaveArea {

	Coord coord
	RegionType type
	int geologicalIndex
	int erosionLevel
}
