/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

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

        InputStream modelIn = null;
        
        
        //nur ab hier coden
        try {
            EmmaParser file = new EmmaParser(file_name);
            String aryLines = file.OpenFile();

            int i;
            
                System.out.println(aryLines);
            
            modelIn = new FileInputStream("en-sent.bin");
            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
            String sentences[] = sentenceDetector.sentDetect(aryLines);

            
            for (i = 0; i < sentences.length; i++) {
                System.out.println(sentences[i]);
            }
      //nur bis hier coden      
        } catch (IOException e) {
            throw new RuntimeException(e);
            
            

        } finally {

            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                }
            }
        }

    }

}
