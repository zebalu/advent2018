package day01

import java.io.InputStream

class SumIt {

    def call(InputStream it) {
        def sum = 0
        it.withReader { r ->
            r.eachLine {line ->
                int i = line as Integer
                sum += i
            }
        }
        return sum
    }
}