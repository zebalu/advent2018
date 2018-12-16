package day13;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class BoardTest extends Specification {

	def 'example'() {
		Board b = Board.importBoard(getClass().getResourceAsStream('/example.txt'))
		for(int i=0; i<20; ++i) {
			b.tick()
		}
		
		expect:
		b.crashHistory[0].first==7
		b.crashHistory[0].second==3
	}
	
	def 'input'() {
		Board b = Board.importBoard(getClass().getResourceAsStream('/input25.txt'))
		for(int i=0; i<200000 && b.carts.size()>1; ++i) {
			b.tick()
		}
		expect:
		b.carts[0].x==73
		b.carts[0].y==33
	}
	
}
