/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 *
 * @author Patrick
 */
public class Emma {

    private static SentenceModel model;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        String file_name = "C:\\Users\\Patrick\\Documents\\Emma_Chap_I_Jane_Austen.txt";

        InputStream sentenceIn = null;
        InputStream tokenIn = null;
        InputStream locationIn = null;
        
        //nur ab hier coden
        try {
            EmmaParser file = new EmmaParser(file_name);
            String aryLines = file.OpenFile();

            int i = 0;
            int j = 0;
            
                
            
            sentenceIn = new FileInputStream("en-sent.bin");
            SentenceModel sentence = new SentenceModel(sentenceIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentence);
            String sentences[] = sentenceDetector.sentDetect(aryLines);
            
            tokenIn = new FileInputStream("en-token.bin");
            TokenizerModel token = new TokenizerModel(tokenIn);
            Tokenizer tokenizer = new TokenizerME(token);
            
            locationIn = new FileInputStream("en-ner-location.bin");
            TokenNameFinderModel location = new TokenNameFinderModel(locationIn);
            NameFinderME nameFinder = new NameFinderME(location);
            
            for (i = 0; i < sentences.length; i++) {
            String tokens[] = tokenizer.tokenize(sentences[i]);
            
            for (j = 0; j < tokens.length; j++) {
                System.out.println(tokens[j]);
                }
            
            }
            
            
            
                    
            
      //nur bis hier coden      
        } catch (IOException e) {
            throw new RuntimeException(e);
            
            

        } 

    }

}
