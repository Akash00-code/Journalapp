package net.engineeringdigest.journalapp.Service;

public class LoopTimeout {
    static void runLoopTimeout(){
        int counter=0;
        for(int i=0;i<10000000;i++){
            counter++;
        }
        System.out.println(counter);
    }
    static int Square(int x){
        return x*x;
    }
}
