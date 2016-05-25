/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import opennlp.tools.sentdetect.SentenceModel;


/**
 *
 * @author Patrick
 */
public class Emma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws IOException {
        // TODO code application logic here

           String file_name = "C:\\Users\\Patrick\\Documents\\Emma_Chap_I_Jane_Austen.txt";      
    
    try {
    EmmaParser file = new EmmaParser( file_name );
    String[ ] aryLines = file.OpenFile( );
    
        int i;
        for ( i=0; i < aryLines.length; i++ ) {
        System.out.println( aryLines[ i ] ) ;
            }
    }
    
    catch(IOException e){
        System.out.println( e.getMessage() );
        }   
   
    InputStream modelIn = new FileInputStream("en-sent.bin");

try {
  SentenceModel model = new SentenceModel(modelIn);
}
catch (IOException e) {
  e.printStackTrace();
}
finally {
  if (modelIn != null) {
    try {
      modelIn.close();
    }
    catch (IOException e) {
    }
  }
}
    
    }
    
}
