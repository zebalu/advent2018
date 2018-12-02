package day01;

class FindFirstFrequencyRepeate {

    static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(args[0]);
        FindIt findIt = new FindIt();
        System.out.println("repetition frequency found: ${findIt(fis)}");
    }
    
}
