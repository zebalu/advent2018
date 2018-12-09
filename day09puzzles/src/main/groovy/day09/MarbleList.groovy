package day09

class MarbleList {
	
	MarbleListNode current = null
	MarbleListNode firstInserted = null
	int marbleCount = 0

	void add(int marble) {
		if(marbleCount == 0) {
			MarbleListNode mln = new MarbleListNode(marble)
			mln.next = mln
			mln.prev = mln
			current = mln
			firstInserted = mln
		} else {
			current = current.next
			MarbleListNode mln = new MarbleListNode(marble)
			MarbleListNode nextNode = current.next
			mln.next=nextNode
			mln.prev=current
			current.next=mln
			nextNode.prev=mln
			current = mln
		}
		++marbleCount	
	}
	
	def plus(int marble) {
		add(marble)
		return this
	}
	
	int remove(minusNth) {
		int steps = 0; 
		while(steps < minusNth) {
			current=current.prev
			++steps
		}
		def next = current.next
		current.prev.next=current.next
		current.next.prev=current.prev
		int result = current.value
		if(current == firstInserted) {
			firstInserted = current
		}
		current=next
		--marbleCount
		
		return result
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(' ')
		MarbleListNode iterator = firstInserted
		int steps = 0
		while(steps<marbleCount) {
			if(iterator == current) {
				sj.add("(${iterator.value})")
			} else {
				sj.add("${iterator.value}")
			}
			iterator = iterator.next
			++steps
		}
		return sj.toString()
	}
}
