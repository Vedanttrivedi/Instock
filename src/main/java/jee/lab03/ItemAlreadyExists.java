package jee.lab03;

public class ItemAlreadyExists extends Exception{
    ItemAlreadyExists(String msg){
        super(msg);
    }
}
