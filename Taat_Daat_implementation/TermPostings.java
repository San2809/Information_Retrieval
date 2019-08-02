/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ir2;

import Ir2.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sanja
 */
public class TermPostings {
    private String[] terms;
    public TermPostings(String[] query,Map<String,LinkedList> LinkedList){
        this.terms = query;
        for(String term : query){
            Output.writeOutput("GetPostings\n"+term+"\nPostings list: ");
            if(LinkedList.get(term)!=null)
                LinkedList.get(term).printPostings();
            Output.writeOutput("\n");
        }   
    }
}
