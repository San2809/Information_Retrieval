package Ir2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.search.DocIdSetIterator;
/**
 *
 * @author sanjay

*/
public class InvertedIndex {

    private String indexPath,outputFilePath,inputFilePath;
        IndexReader reader;
        String INDEX_DIR;
        Map<String,LinkedList> postingsLists;
        
    public InvertedIndex(String indexPath){
        try {

            this.indexPath = indexPath;
                       
                       
                       Directory dir = FSDirectory.open(Paths.get(indexPath));
                       //IndexReader reader = DirectoryReader.open(dir);

            this.reader = DirectoryReader.open(dir);
                        this.postingsLists = new HashMap<>();
                        Object[] fields = MultiFields.getIndexedFields(this.reader).toArray();
                        String indexFields[] = new String[fields.length-1];
                        int index = 0;
                        for (Object field : fields)
                            if (field.toString().contains("text_")){
                                indexFields[index++] = field.toString();
                            }
                        Map dictionary = listTerms(indexFields);
                        for(Object key : dictionary.keySet()){
                            LinkedList termPostings = postingsListCreator((String)key,(ArrayList<String>)dictionary.get(key));
                            this.postingsLists.put((String)key, termPostings);
                        }
                        
        }
        catch (ArrayIndexOutOfBoundsException | IOException e){
            System.out.println(e);
        }
    }
        
    
        private Map listTerms(String[] indexFields) throws IOException{
            Map dictionary = new HashMap();
            for (String field : indexFields) {
                TermsEnum terms = MultiFields.getTerms(this.reader,field).iterator();
                while(terms.next()!=null){
                    String term = terms.term().utf8ToString();
                    if(dictionary.containsKey(term)){
                        ArrayList<String> value = (ArrayList<String>) dictionary.get(term);
                        value.add(field);
                        dictionary.put(term, value);
                    }
                    else {
                        ArrayList<String> arrayList = new ArrayList<>(3);
                        arrayList.add(field);
                        dictionary.put(term, arrayList);
                    }
                }
            }
            return dictionary;
        }
        
        
        private LinkedList postingsListCreator(String term, ArrayList<String> lang) throws IOException{
            Iterator posting = null;
            LinkedList LinkedList = null; 
            byte[] byteTerm = term.getBytes();
            for(String language : lang){
                PostingsEnum postings = MultiFields.getTermDocsEnum(this.reader,language, new BytesRef(byteTerm));
                while(postings.nextDoc()!= PostingsEnum.NO_MORE_DOCS){
                    posting = new Iterator(postings.docID());
                    if(LinkedList == null){
                        LinkedList = new LinkedList(posting);
                    }
                    else {
                        LinkedList.appendPostings(posting);
                    }
                }
            }
            if(LinkedList!=null){
                LinkedList.setSkipPointers();
            }
            return LinkedList;
        }
        public int getDocCount(){
            return reader.numDocs();
        }
        public Map<String,LinkedList> getPostingsList(){
            return this.postingsLists;
        }
}

