/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


/**
 *
 * @author Patrick
 */
public class LocationListFictional {
    
    public String pathLLF;
    
    public LocationListFictional(String file_pathLLF) {
        pathLLF = file_pathLLF;
    }
    
     public String [] OpenFileLLF() throws IOException {

        FileReader frLLF = new FileReader(pathLLF);
        BufferedReader textReaderLLF = new BufferedReader(frLLF);

        int numberOfLinesLLF = readLinesLLF();

        String[] textDataLLF = new String[numberOfLinesLLF];

        int i;

        for (i = 0; i < numberOfLinesLLF; i++) {
            textDataLLF[i] = textReaderLLF.readLine();

        }
        textReaderLLF.close();
        return textDataLLF;
    }

    int readLinesLLF() throws IOException {

        FileReader file_to_readLLF = new FileReader(pathLLF);
        BufferedReader bfLLF = new BufferedReader(file_to_readLLF);

        String aLineLLF;

        int numberOfLinesLLF = 0;

        while ((aLineLLF = bfLLF.readLine()) != null) {
            numberOfLinesLLF++;
            
            
        }
        bfLLF.close();
        return numberOfLinesLLF;
    }
    
}
