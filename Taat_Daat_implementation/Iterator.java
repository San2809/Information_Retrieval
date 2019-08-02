/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ir2;

/**
 *
 * @author sanja
 */
public class Iterator {
    
    private int docID;
    private Iterator next,skip;
    
    public Iterator(int docID){
        this.docID = docID;
        this.skip = null;
        this.next = null;
    }
    public Iterator getNext(){
        return this.next;
    }
    public Iterator getSkipPointer(){
        return this.skip;
    }
    public int getDocID(){
        return this.docID;
    }
    public void setNext(Iterator next){
        this.next = next;
        
    }
    public void setSkipPointer(Iterator skip){
        this.skip = skip;
    }
}
