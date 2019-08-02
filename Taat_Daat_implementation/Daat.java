/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ir2;

import Ir2.Iterator;
import Ir2.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.lang.NullPointerException;

/**
 *
 * @author sanja
 */

/*
Description : 
    Document-at-a-time parses through the postings list of the query terms one document at a time. 
*/
public class Daat {
    private String[] queryTerms;
    private Map<String,LinkedList> Postings;
    private Map<Integer,Integer> resultDocList;
    private Iterator[] Pointers;
    public Daat(String[] queryTerms,Map<String,LinkedList> LinkedList){
        this.queryTerms = queryTerms;
        Postings = new HashMap<>();
        Pointers = new Iterator[queryTerms.length];
        int index = 0;
        for(String term : queryTerms){
            Postings.put(term, (LinkedList) LinkedList.get(term));
            if(Postings.get(term)!=null)
                Pointers[index++] = Postings.get(term).getFirst();
        }
        resultDocList = new TreeMap<>();
        this.daatAnd();
        index = 0;
        for(String term : queryTerms){
            Postings.put(term, (LinkedList) LinkedList.get(term));
            if(Postings.get(term)!=null)
                Pointers[index++] = Postings.get(term).getFirst();
        }
        resultDocList.clear();
        this.daatOr();
    }
    private void daatOr(){
        Output.writeOutput("DaatOr\n");
        int comparisons = 0;
        int similarityCheck = 0;
        int pointerIndex = 0;
        for(String term : this.queryTerms){
            Output.writeOutput(term+" ");
        }
        while(true){
            try{
                for(int index = 0; index < this.Pointers.length; index++){                    
                    if(this.Pointers.length==1){
                        while(this.Pointers[0]!=null){
                            this.resultDocList.put(this.Pointers[0].getDocID(), similarityCheck);
                            this.Pointers[0] = this.Pointers[0].getNext();
                        }                                                
                        throw new NullPointerException("OrOperationFinished");
                    }
                    if(index != pointerIndex){
                        if(this.Pointers[index].getDocID()>this.Pointers[pointerIndex].getDocID()){
                            this.resultDocList.put(this.Pointers[pointerIndex].getDocID(), similarityCheck);
                            this.Pointers[pointerIndex] = this.Pointers[pointerIndex].getNext();
                            pointerIndex = index;
                        }
                        else if(this.Pointers[index].getDocID()<this.Pointers[pointerIndex].getDocID()){
                            this.resultDocList.put(this.Pointers[index].getDocID(), similarityCheck);
                            this.Pointers[index] = this.Pointers[index].getNext();
                        }
                        else {
                            similarityCheck++;
                        }
                        comparisons++;
                    }
                }
                if(similarityCheck==this.Pointers.length-1){
                    resultDocList.put(this.Pointers[pointerIndex].getDocID(), similarityCheck);
                    for(int index = 0; index < this.Pointers.length; index++){
                        this.Pointers[index] = this.Pointers[index].getNext();
                    }
                    similarityCheck = 0;
                }
            }
            catch(NullPointerException npe){
                Iterator[] intermediatePointers;
                intermediatePointers = new Iterator[this.Pointers.length-1];
                for(int index = 0,i=0; index<this.Pointers.length; index++){
                    if(this.Pointers[index]!=null)
                        intermediatePointers[i++]=this.Pointers[index];
                }
                pointerIndex = 0;
                this.Pointers = intermediatePointers;
                if(this.Pointers.length==0){
                    break;
                }
            }
        }
        Output.writeOutput("\nResults: ");
        int docCount = 0;
        for(int docID: resultDocList.keySet()){
            Output.writeOutput((docID)+" ");
            docCount++;
        }
        Output.writeOutput("\nNumber of documents in results: "+docCount);
        Output.writeOutput("\nNumber of comparisons: "+comparisons); 
        Output.writeOutput("\n");
    }
    private void daatAnd(){
        Output.writeOutput("DaatAnd\n");
        int comparisons = 0;
        int similarityCheck = 0;
        int pointerIndex = 0;
        for(String term : this.queryTerms){
            Output.writeOutput(term+" ");
        }
        while(true){
            try{
                for(int index = 0; index < this.Pointers.length; index++){                    
                    if(index != pointerIndex){                        
                        if(this.Pointers[index].getDocID()>this.Pointers[pointerIndex].getDocID()){
                            this.Pointers[pointerIndex] = this.Pointers[pointerIndex].getNext();
                            if(this.Pointers[pointerIndex].getDocID()<this.Pointers[index].getDocID())
                                pointerIndex = index;
                        }
                        else if(this.Pointers[index].getDocID()<this.Pointers[pointerIndex].getDocID()){
                            this.Pointers[index] = this.Pointers[index].getNext();
                        }
                        else {
                            similarityCheck++;
                        }
                        comparisons++;
                    }
                }
                if(similarityCheck==this.Pointers.length-1){
                    resultDocList.put(this.Pointers[pointerIndex].getDocID(), similarityCheck);
                    for(int index = 0; index < this.Pointers.length; index++){
                        this.Pointers[index] = this.Pointers[index].getNext();
                    }
                    similarityCheck = 0;
                }
            }
            catch(NullPointerException npe){
                break;
            }
        }
        Output.writeOutput("\nResults: ");
        int docCount = 0;
        if(this.queryTerms.length>1){
            for(int docID: resultDocList.keySet()){
                Output.writeOutput((docID)+" ");
                docCount++;
            }
        }
        Output.writeOutput("\nNumber of documents in results: "+docCount);
        Output.writeOutput("\nNumber of comparisons: "+comparisons);  
        Output.writeOutput("\n");
    }
}
