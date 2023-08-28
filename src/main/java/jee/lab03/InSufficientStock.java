package jee.lab03;

public class InSufficientStock extends Exception{
    InSufficientStock(String msg){
        super(msg);
    }
}
