package day08;

import java.util.stream.Collectors

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString
@TupleConstructor
public class Node {

    int childsCount = 0
    int metaDataCount = 0
    List<Integer> metaData = []
    List<Node> childs = []

    boolean isChildsRead() {
        return childsCount == childs.size()
    }

    boolean isMetaDataRead() {
        return metaDataCount == metaData.size()
    }

    int value() {
        if(childsCount == 0) {
            return metaData.sum()
        }
        def sum = 0
        metaData.stream().filter{ it > 0 && it <= childsCount }.map{childs[it-1].value()}
            .forEach{ sum+=it }
        return sum;
    }
}
