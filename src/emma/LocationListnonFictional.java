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
public class LocationListnonFictional {
public String pathLLnF;
    
    public LocationListnonFictional(String file_pathLLnF) {
        pathLLnF = file_pathLLnF;
    }
    
     public String [] OpenFileLLnF() throws IOException {

        FileReader frLLnF = new FileReader(pathLLnF);
        BufferedReader textReaderLLnF = new BufferedReader(frLLnF);

        int numberOfLinesLLnF = readLinesLLnF();

        String[] textDataLLnF = new String[numberOfLinesLLnF];

        int i;

        for (i = 0; i < numberOfLinesLLnF; i++) {
            textDataLLnF[i] = textReaderLLnF.readLine();

        }
        textReaderLLnF.close();
        return textDataLLnF;
    }

    int readLinesLLnF() throws IOException {

        FileReader file_to_readLLnF = new FileReader(pathLLnF);
        BufferedReader bfLLnF = new BufferedReader(file_to_readLLnF);

        String aLineLLnF;

        int numberOfLinesLLnF = 0;

        while ((aLineLLnF = bfLLnF.readLine()) != null) {
            numberOfLinesLLnF++;
            
            
        }
        bfLLnF.close();
        return numberOfLinesLLnF;
    }
    
}
