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
        String file_name_LLF = "C:\\Users\\Patrick\\Documents\\LocationListFictional.txt";
        String file_name_LLnF = "C:\\Users\\Patrick\\Documents\\LocationListnonFictional.txt";

        InputStream sentencesIn = null;
        InputStream tokenIn = null;
        InputStream personIn = null;
        InputStream locationIn = null;

        //nur ab hier coden
        try {
            EmmaParser file = new EmmaParser(file_name);
            CharakterList fileCL = new CharakterList(file_name_CL);
            LocationListFictional fileLLF = new LocationListFictional (file_name_LLF);
            LocationListnonFictional fileLLnF = new LocationListnonFictional (file_name_LLnF);
            
            String[] aryLines_LLF = fileLLF.OpenFileLLF();
            String[] aryLines_LLnF = fileLLnF.OpenFileLLnF();
            String aryLines = file.OpenFile();
            String[] aryLines_CL = fileCL.OpenFileCL();

            int b = 0;
            int i = 0;
            int j = 0;
            int l = 0;
            int bnFL=0;
            int bFL=0;

            HashSet<String> names = new HashSet<String>();
            HashSet<String> locations = new HashSet<String>();
               
            System.out.println("Alle erfundenen Orte:\n");
            for (int c = 0; c < aryLines_LLF.length; c++) {
                System.out.println(aryLines_LLF[c]);
                bFL++;
            }
            System.out.println("\nInsgesamt: " + bFL + " Fictional locations\n");       //Anzahl der erfundenen Namen in der Liste
            
            
            System.out.println("Alle echten Orte:\n");            
            for (int c = 0; c < aryLines_LLnF.length; c++) {
                System.out.println(aryLines_LLnF[c]);
                bnFL++;
            }
            System.out.println("\nInsgesamt: " + bnFL + " non-fictional locations\n");    //Anzahl der Echten Orte in der Liste
            
            
            System.out.println("Alle Namen Laut Fan-Wiki:\n"); 
            for (int c = 0; c < aryLines_CL.length; c++) {
                System.out.println(aryLines_CL[c]);
                b++;
            }            
            System.out.println("\nInsgesamt: " + b + " Namen\n");                    //Anzahl der Namen in der Namensliste
                                                        
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

            
             locationIn = new FileInputStream("en-ner-location.bin");                    //Setup des Locationfinders
             TokenNameFinderModel location = new TokenNameFinderModel(locationIn);
             NameFinderME locationFinder = new NameFinderME(location);
             
            String sentences[] = sentenceDetector.sentDetect(aryLines);                 //SentenceDetector

            for (i = 0; i < sentences.length; i++) {                                   //Tokenizer 
                String tokens[] = tokenizer.tokenize(sentences[i]);

                Span nameSpans[] = nameFinder.find(tokens);
                Span locationSpans[] = locationFinder.find(tokens);
                StringBuilder name = new StringBuilder();
                StringBuilder orte = new StringBuilder();

                
                 for (l = 0; l < locationSpans.length; l++) {                                            //Locationfidner
                    for (int m = locationSpans[l].getStart(); m < locationSpans[l].getEnd(); m++) {

                 if (m > locationSpans[l].getStart()) {
                 orte.append(" ");
                 }
                 orte.append(tokens[m]);
                 }
                 locations.add(orte.toString());
                 
                 orte.setLength(0);
                 }
                 
                for (j = 0; j < nameSpans.length; j++) {                                                //Namefinder
                    for (int k = nameSpans[j].getStart(); k < nameSpans[j].getEnd(); k++) {

                        if (k > nameSpans[j].getStart()) {
                            name.append(" ");
                        }
                        name.append(tokens[k]);
                    }
                    
                    names.add(name.toString());                                     //Durch ein HashSet werden doppelte Namen entfernt.

                    name.setLength(0);
                }
            }

            List<String> list = new ArrayList<String>(names);

            StringBuilder matchednames = new StringBuilder();
            StringBuilder notmatchednames = new StringBuilder();

            int t = 0;
            int s = 0;
            
            for (int a = 0; a < b; a++) {

                if (list.indexOf(aryLines_CL[a]) > -1) {                            //Gefundene und nicht gefundene Namen Auflisten
                    matchednames.append(aryLines_CL[a]).append("\n");
                    t++;
                }
                if (list.indexOf(aryLines_CL[a]) == -1) {
                    notmatchednames.append(aryLines_CL[a]).append("\n");
                    s++;
                }
                list.remove(aryLines_CL[a]);                                        //Alle Namen die in der 'Perfekten Namens Liste' sind raus werfen.
            }

            System.out.println("\nDie gefundenen Namen:\n" + matchednames + "\nInsgesamt: " + t + " Namen gefunden");
            System.out.println("\nDie nicht gefundenen Namen:\n" + notmatchednames + "\nInsgesamt: " + s + " Namen nicht gefunden");           
            System.out.println("\nDie Namen die OPENNLP findet und sich nicht zuordnen lassen: " + list);
            System.out.println("Insgesamt: " + list.size() + " Namen");                                        //Namen die erkannt werden aber noch nicht mit anderen zusammen gefast werden konnten

            List<String> locationslist = new ArrayList<String>(locations);
            
            StringBuilder matchedFL = new StringBuilder();
            StringBuilder notmatchedFL = new StringBuilder();

            int tFL = 0;
            int sFL = 0;
            
            for (int a = 0; a < bFL; a++) {

                if (locationslist.indexOf(aryLines_LLF[a]) > -1) {                            //Gefundene und nicht gefundene fictional locations Auflisten
                    matchedFL.append(aryLines_LLF[a]).append("\n");
                    tFL++;
                }
                if (locationslist.indexOf(aryLines_LLF[a]) == -1) {
                    notmatchedFL.append(aryLines_LLF[a]).append("\n");
                    sFL++;
                }
                locationslist.remove(aryLines_LLF[a]);                                        //Alle Namen die in der 'LocationListFictional' sind raus werfen.
            }
            
            
            StringBuilder matchednFL = new StringBuilder();
            StringBuilder notmatchednFL = new StringBuilder();

            int tnFL = 0;
            int snFL = 0;
            
            for (int a = 0; a < bnFL; a++) {

                if (locationslist.indexOf(aryLines_LLnF[a]) > -1) {                            //Gefundene und nicht gefundene echte Orte Auflisten
                    matchednFL.append(aryLines_LLnF[a]).append("\n");
                    tnFL++;
                }
                if (locationslist.indexOf(aryLines_LLnF[a]) == -1) {
                    notmatchednFL.append(aryLines_LLnF[a]).append("\n");
                    snFL++;
                }
                locationslist.remove(aryLines_LLnF[a]);                                        //Alle Namen die in der 'LocationListnonFictional' sind raus werfen.
            }
            
            System.out.println("\nDie gefundenen non fictional locations:\n" + matchednFL + "\nInsgesamt: " + tnFL + " nonfictional locations gefunden");
            System.out.println("\nDie nicht gefundenen non fictional locations:\n" + notmatchednFL + "\nInsgesamt: " + snFL + " nonfictional Locations nicht gefunden");
            System.out.println("\nDie gefundenen fictional locations:\n" + matchedFL + "\nInsgesamt: " + tFL + " fictional locations gefunden");
            System.out.println("\nDie nicht gefundenen fictional locations:\n" + notmatchedFL + "\nInsgesamt: " + sFL + " fictional Locations nicht gefunden");
            System.out.println("\nDie übrigen gefundenen Orte sind: " + locationslist);
            System.out.println("Insgesamt: " + locationslist.size() + " Orte");
            
            /*
             StringBuilder tolhs = new StringBuilder ();
             StringBuilder torhs = new StringBuilder ();
             StringBuilder namevariant = new StringBuilder ();
                       
             for (int a=0; a<b; a++){
             tolhs.append(aryLines_CL[a]);
                            
                            
                            
                            
             for (int c=0; c<list.size(); c++){
             torhs.append(list.get(c));
                                
                                
                                
             if (distance = true){
                                    
             namevariant.append(list.get(c));
             namevariant.append("\n");
                                    
             list.remove(c);
             }
             torhs.setLength(0);
             }
                            
                            
                            
                            
             tolhs.setLength(0);
             }
                        
             System.out.println("\nDie Namensvarianten sind: ");
             */
            


//nur bis hier coden      
        }
        
        catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

}
