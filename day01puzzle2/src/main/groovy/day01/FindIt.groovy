package day01

import java.io.InputStream

class FindIt {

    def call(InputStream it) {
        def sum = 0
        def sums = [0]
        def nums = [];
        BigInteger result = null
        it.withReader { r ->
            r.eachLine {line ->
                int i = line as Integer
                sum += i
                if(sums.contains(sum)) {
                    result = sum
                    return
                }
                sums<<sum
                nums<<i
            }
        }
        while(result == null) {
            for(int i = 0; i<nums.size() && result == null; ++i) {
                int n = nums[i]
                sum += n
                if(sums.contains(sum)) {
                    result = sum
                } else {
                    sums<<sum
                }
            }
        }
        return result
    }
}