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

/**
 *
 * @author sanja
 */


public class Taat {
    private String[] queryTerms;
    private Map<String,LinkedList> Postings;
    private Map<Integer,Integer> resultDocList;
    public Taat(String[] queryTerms,Map<String,LinkedList> LinkedList){
        this.queryTerms = queryTerms;
        Postings = new HashMap<>();
        for(String term : queryTerms){
            Postings.put(term, (LinkedList) LinkedList.get(term));
        }   
        resultDocList = new TreeMap<>();
        this.and();
        resultDocList.clear();
        this.or();
    }
    private void or(){
        Output.writeOutput("TaatOr\n");
        for(String term : queryTerms){
            Iterator postings = null;
            if(Postings.get(term)!=null)
                postings = Postings.get(term).getFirst();
            while(postings!=null){
                if(resultDocList.containsKey(postings.getDocID()))
                    resultDocList.put(postings.getDocID(),resultDocList.get(postings.getDocID())+1);
                else
                    resultDocList.put(postings.getDocID(),1);
                postings = postings.getNext();
            }
            Output.writeOutput(term+" ");
        }
        Output.writeOutput("\nResults: ");
        int docCount = 0, comparisions = 0;
        for(int docID: resultDocList.keySet()){
            if(resultDocList.get(docID)>0){
                Output.writeOutput((docID)+" ");
                docCount++;
            }
            if(resultDocList.get(docID)>0)
                comparisions += resultDocList.get(docID);
        }
        Output.writeOutput("\nNumber of documents in results: "+docCount);
        Output.writeOutput("\nNumber of comparisons: "+comparisions);
        Output.writeOutput("\n");
    }
    private void and(){
        Output.writeOutput("Taat And\n");
        for(String term : queryTerms){
            Iterator postings = null;
            if(Postings.get(term)!=null)
                postings = Postings.get(term).getFirst();
            while(postings!=null){
                if(resultDocList.containsKey(postings.getDocID()))
                    resultDocList.put(postings.getDocID(),resultDocList.get(postings.getDocID())+1);
                else
                    resultDocList.put(postings.getDocID(),1);
                postings = postings.getNext();
            }
            Output.writeOutput(term+" ");
        }
        Output.writeOutput("\nResults: ");
        int docCount = 0, comparisions = 0;
        if(this.queryTerms.length>1){
            for(int docID : resultDocList.keySet()){
                if(resultDocList.get(docID)==queryTerms.length){
                    Output.writeOutput((docID)+" ");
                    docCount++;
                }
                if(resultDocList.get(docID)>0)
                    comparisions += resultDocList.get(docID);
            }
        }
        Output.writeOutput("\nNumber of documents in results: "+docCount);
        Output.writeOutput("\nNumber of comparisons: "+comparisions);
        Output.writeOutput("\n");
        
        
    }
}
