/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;

import static com.oracle.webservices.internal.api.databinding.DatabindingModeFeature.builder;
import static com.oracle.webservices.internal.api.databinding.ExternalMetadataFeature.builder;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import static java.util.stream.DoubleStream.builder;
import static java.util.stream.IntStream.builder;
import static java.util.stream.LongStream.builder;
import static java.util.stream.Stream.builder;
import static jdk.nashorn.tools.ShellFunctions.input;
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

        String file_name = "C:\\Users\\Patrick\\Documents\\Emmasicherung.txt";
        String file_name_CL = "C:\\Users\\Patrick\\Documents\\Emma Charakter Liste.txt";

        InputStream sentencesIn = null;
        InputStream tokenIn = null;
        InputStream personIn = null;
        //InputStream locationIn = null;

        //nur ab hier coden
        try {
            EmmaParser file = new EmmaParser(file_name);
            CharakterList fileCL = new CharakterList(file_name_CL);
            String aryLines = file.OpenFile();
            String[] aryLines_CL = fileCL.OpenFileCL();
            
            
            int b = 0;
            int i = 0;
            int j = 0;
            // int l = 0;
            
            
            HashSet<String> names = new HashSet<String>();
            
            for (int c = 0; c < aryLines_CL.length; c++) {
                System.out.println(aryLines_CL[c]);
                b++;

            }
                System.out.println(b+1);                                             //Anzahl der Namen in der Namensliste
            aryLines = aryLines.replaceAll("--", " - ");                             //Oft wird "--" ohne Leerzeichen zwischen dem Text als Gedankenstrich benutzt dadurch entstehen falsche Tokens.

            
            sentencesIn = new FileInputStream("en-sent.bin");                           //Setup des SentenceDetector
            SentenceModel sentence = new SentenceModel(sentencesIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentence);

            tokenIn = new FileInputStream("en-token.bin");                              //Setup des Tokenizers
            TokenizerModel token = new TokenizerModel(tokenIn);
            Tokenizer tokenizer = new TokenizerME(token);

            personIn = new FileInputStream("en-ner-person.bin");                        //Setup des Namefinders
            TokenNameFinderModel person = new TokenNameFinderModel(personIn);
            NameFinderME nameFinder = new NameFinderME(person);

            /*
             locationIn = new FileInputStream("en-ner-location.bin");                    //Setup des Locationfinders
             TokenNameFinderModel location = new TokenNameFinderModel(locationIn);
             NameFinderME locationFinder = new NameFinderME(location);
             */
            String sentences[] = sentenceDetector.sentDetect(aryLines);                 //SentenceDetector

            for (i = 0; i < sentences.length; i++) {                                   //Tokenizer 
                String tokens[] = tokenizer.tokenize(sentences[i]);

                Span nameSpans[] = nameFinder.find(tokens);
                //Span locationSpans[] = locationFinder.find(tokens);
                StringBuilder name = new StringBuilder();
                //StringBuilder orte = new StringBuilder();

                /*
                 for (l = 0; l < locationSpans.length; l++) {                                            //Locationfidner
                 for (int m = locationSpans[l].getStart(); m < locationSpans[l].getEnd(); m++) {

                 if (m > locationSpans[l].getStart()) {
                 orte.append(" ");
                 }
                 orte.append(tokens[m]);
                 }
                 System.out.println(orte.toString());
                 }
                 */
                for (j = 0; j < nameSpans.length; j++) {                                                //Namefinder
                    for (int k = nameSpans[j].getStart(); k < nameSpans[j].getEnd(); k++) {

                        if (k > nameSpans[j].getStart()) {
                            name.append(" ");
                        }
                        name.append(tokens[k]);

                    }
                    //System.out.print(".");
                    names.add(name.toString());                                     //Durch ein HashSet werden doppelte Namen entfernt.

                    name.setLength(0);
                }
            }

            //System.out.println("\n\nElements using iterator:" );                                        
                        /*Iterator<String> it = names.iterator();
             while (it.hasNext())
             {
             String names1 = (String) it.next();
             //System.out.println(names1);
             }*/
            List<String> list = new ArrayList<String>(names);   
            
                   
    
    
    
                                                   
   
            
            
            for (int a = 0; a < b; a++) { 
                 
                list.remove(aryLines_CL[a]);                                        //Alle Namen die in der 'Perfekten Namens Liste' sind raus werfen.
            }
            
            
            System.out.println("\nArrayList contains: " + list);
            System.out.println(list.size());                                        //Namen die erkannt werden aber noch nicht mit anderen zusammen gefast werden konnten
                
                       StringBuilder tolhs = new StringBuilder ();
                       StringBuilder torhs = new StringBuilder ();
                        for (int a=0; a<b; a++){
                            tolhs.append(aryLines_CL[a]);
                            for (int c=0; c<list.size(); c++){
                                torhs.append(list.get(c));
                                
                                
                                
                                if (distance = true){
                                    list.remove(c);
                                }
                                torhs.setLength(0);
                            }
                            
                            
                            
                            
                            tolhs.setLength(0);
                        }
               
    
            
            //nur bis hier coden      
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        
        
        

    }

}
