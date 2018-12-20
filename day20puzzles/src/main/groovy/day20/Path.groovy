package day20

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

@Canonical
class Path implements Comparable<Path> {

	Room first
	Room last
	Set<Room> rooms = new LinkedHashSet<>()
	
	Path() {
		this([])
	}
	
	Path(Collection<Room> previous) {
		this(previous, null)
	}
	
	Path(Collection<Room> previous, Room next) {
		if(previous) {
			first = previous[0]
		} else {
			first = next
		}
		rooms.addAll(previous)
		if(next != null) {
			rooms.add(next)
			last = next
		}
	}
	
	int getSize() {
		return rooms.size()
	}
	
	int getDoorCount() {
		return size-1
	}
	
	void addRoom(Room room) {
		if(room != null && ! rooms.contains(room)) {
			rooms.add(room)
			last = room
		}
	}
	
	List<Room> toList() {
		new ArrayList(rooms)
	}
	
	@Override
	int hashCode() {
		int hash = 97
		int prime = 103
		hash += hash * prime * last?.hashCode()?:-1
		hash += hash * prime * rooms.size()
		return hash
	}
	
	@Override
	boolean equals(Object o) {
		if (this.is(o)) {
			return true
		}
		if(! (o instanceof Path)) {
			return false
		}
		if(this.last) {
			if(!(this.last.is(o.last))) {
				return false
			}
		}
		if(rooms.size() != o.rooms?.size()) {
			return false
		}
		if(rooms) {
			Iterator<Room> i1 = rooms.iterator()
			Iterator<Room> i2 = o.rooms.iterator()
			if(i1.hasNext() && i2.hasNext()) {
				if(!(i1.next().is(i2.next()))) {
					return false
				}
			} else {
				return false
			}
		}
		return true
	}
	
	@Override
	public int compareTo(Path p) {
		int sizeDiff = size - p.size
		if(sizeDiff!=0) {
			return sizeDiff
		}
		Iterator<Room> i1 = rooms.iterator()
		Iterator<Room> i2 = p.rooms.iterator()
		if(i1.hasNext() && i2.hasNext()) {
			int comp = i1.next().compareTo(i2.next())
			if(comp != 0) {
				return comp
			}
		} else {
			if(i1.hasNext()) {
				return 1
			} else {
				return -1
			}
		}
		return 0
	}
}
