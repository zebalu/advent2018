package day02

class InputTable {
    
    List<CheckSumBase> chekSums = []
    
    def plus(CheckSumBase csb) {
        chekSums<<csb
        return this
    }
    
    static InputTable fromStream(InputStream is) {
        InputTable inputTable = new InputTable()
        is.withReader { r ->
            r.eachLine { l->
                CheckSumBase csb = new CheckSumBase(l)
                inputTable+csb
            }
        }
        return inputTable
    }
    
    int checkSum() {
        int two = 0
        int three = 0
        chekSums.each { cs ->
            if(cs.hasDoubleLetter) {
                ++two
            }
            if(cs.hasTripletLetter) {
                ++three
            }
        }
        return two*three
    }

}
