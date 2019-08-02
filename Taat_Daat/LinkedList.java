/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ir2;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 *
 * @author sanja
 */
public class LinkedList {
    private int length;
    private Iterator begin,end;
    
    public LinkedList(Iterator first){
        this.begin = first;
        this.end = first;
        this.length = 1;
    }
    public Integer getLength(Boolean check){
        return this.length;
    }
    public Iterator getFirst(){
        return this.begin;
    }
    public void appendPostings(Iterator posting){
        this.end.setNext(posting); 
        this.end = posting;
        this.length += 1;
    }
    public void printPostings(){
        Iterator posting = this.begin;
        while(posting!=null){
            Output.writeOutput(posting.getDocID()+" ");
            posting = posting.getNext();
        }
    }
    public void setSkipPointers(){
        int count = 1;
        Iterator current = this.begin;
        Iterator nextPosting = this.begin.getNext();
        int skipLength = (int) round(sqrt(this.length));
        if(skipLength>1){
            while(nextPosting.getNext()!=null){
                nextPosting = nextPosting.getNext();
                count++;
                count = count % skipLength;
                if(count==0){
                    current.setSkipPointer(nextPosting);
                    current = nextPosting;
                }
            }
            if(count>1){
                current.setSkipPointer(nextPosting);
            }
        }
    }
}
