package day01

import spock.lang.*

class SumItTest extends Specification {

    def "sums streams" () {
        SumIt si = new SumIt();
        InputStream is = new ByteArrayInputStream('''1
2
3
4'''.getBytes('UTF-8'))
        def sum = si(is)
        expect:
        sum == 10
    }
       
}
