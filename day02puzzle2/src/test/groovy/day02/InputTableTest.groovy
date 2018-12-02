package day02;

import spock.lang.Specification

class InputTableTest extends Specification {

    def "adding checksums works"() {
        InputTable it = new InputTable()
        CheckSumBase csb = new CheckSumBase("test")
        it+csb
        
        expect:
        it.chekSums.contains(csb)
    }
    
    def "chaining checksums add works"() {
        InputTable it = new InputTable()
        CheckSumBase csb = new CheckSumBase("test")
        CheckSumBase csb2 = new CheckSumBase("test2")
        it+csb+csb2
        
        expect:
        it.chekSums.contains(csb) && it.chekSums.contains(csb2)
    }
    
    def 'from creates inputTable with multiple input'() {
        def inpT = InputTable.fromStream(toStream(['alpha', 'beta', 'gamma']))
        
        expect:
        inpT.chekSums.size() == 3 &&
        inpT.chekSums.contains(new CheckSumBase('alpha')) &&
        inpT.chekSums.contains(new CheckSumBase('beta')) &&
        inpT.chekSums.contains(new CheckSumBase('gamma'))
    }
    
    def exampleCommonLetters () {
        def inputTable = InputTable.fromStream(toStream(['abcde','fghij','klmno','pqrst','fguij','axcye','wvxyz']))
        
        expect:
        'fgij' == inputTable.firstCommonLettersWithOneDistance()
    }
    
    def exampleCommonLetters2 () {
        def inputTable = InputTable.fromStream(this.getClass().getResourceAsStream('/input4.txt'))
        
        expect:
        'fonbwmjquwtapeyzikghtvdxl' == inputTable.firstCommonLettersWithOneDistance()
    }
    
    private InputStream toStream(list) {
        StringJoiner sj = new StringJoiner("\n");
        list.each { e ->
            sj.add(e)
        }
        return new ByteArrayInputStream(sj.toString().getBytes('UTF-8'))
    }
}
