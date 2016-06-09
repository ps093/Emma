/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

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

        String file_name = "C:\\Users\\Patrick\\Documents\\Emma.txt";

        InputStream sentencesIn = null;
        InputStream tokenIn = null;
        InputStream personIn = null;

        //nur ab hier coden
        try {
            EmmaParser file = new EmmaParser(file_name);
            String aryLines = file.OpenFile();

            int i = 0;
            int j = 0;

            sentencesIn = new FileInputStream("en-sent.bin");
            SentenceModel sentence = new SentenceModel(sentencesIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentence);
            String sentences[] = sentenceDetector.sentDetect(aryLines);

            tokenIn = new FileInputStream("en-token.bin");
            TokenizerModel token = new TokenizerModel(tokenIn);
            Tokenizer tokenizer = new TokenizerME(token);

            personIn = new FileInputStream("en-ner-person.bin");
            TokenNameFinderModel person = new TokenNameFinderModel(personIn);
            NameFinderME nameFinder = new NameFinderME(person);

            for (i = 0; i < sentences.length; i++) {
                String tokens[] = tokenizer.tokenize(sentences[i]);

                Span nameSpans[] = nameFinder.find(tokens);
                
                 

                for (j = 0; j < nameSpans.length; j++) {
                    for (int k = nameSpans[j].getStart(); k < nameSpans[j].getEnd(); k++) {
                        System.out.println(tokens[k]);
                    }
                }
            }

            //nur bis hier coden      
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

}
