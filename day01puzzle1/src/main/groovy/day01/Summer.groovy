package day01;

class Summer {

    static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(args[0]);
        SumIt sumIt = new SumIt();
        System.out.println("resulting sum: "+sumIt(fis));
    }
    
}
