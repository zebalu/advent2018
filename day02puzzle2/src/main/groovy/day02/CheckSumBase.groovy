package day02

import groovy.transform.Canonical

@Canonical
class CheckSumBase {

    final String id
    
    CheckSumBase(String id) {
        this.id=id
    }
    
    int size() {
        return id.size()
    }
    
    def minus(CheckSumBase other) {
        StringBuilder sb = new StringBuilder()
        int maxLenght = Math.min(size(), other.size())
        for(int i=0; i<maxLenght; ++i) {
            if(id[i] == other.id[i]) {
                sb.append(id[i])
            }
        }
        return new CheckSumBase(sb.toString())
    }
    
}
