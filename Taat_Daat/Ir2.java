/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ir2;

import Ir2.InvertedIndex;
import Ir2.LinkedList;
import Ir2.Input;
import Ir2.Output;
import java.util.Map;
import Ir2.Daat;
import Ir2.TermPostings;
import Ir2.Taat;


/**
 *
 * @author sanja
 */
public class Ir2 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        InvertedIndex invertedindex = new InvertedIndex(args[0]);
        Map<String,LinkedList> postingsListDict = invertedindex.getPostingsList();
        //System.out.println(postingsListDict.toString());
        
        Input input = new Input();
        if(!Output.OutputFile(args[1])){             
            System.out.println("Error : Output File could not be created");
        }
        if(input.readFile(args[2])){
            for(int index : input.queryLength()){
                String[] queryTerms = input.queryTerms(index);
                TermPostings postings = new TermPostings(queryTerms,postingsListDict);
                Taat termAtATime = new Taat(queryTerms,postingsListDict);
                Daat documentAtATime = new Daat(queryTerms,postingsListDict);
            }
            System.out.println("Output ready");
        }
        else {
            System.out.println("Error : Incorrect Input File Path");
        }
        Output.closeFile();
    }
    
}
