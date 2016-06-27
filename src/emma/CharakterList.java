/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emma;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
/**
 *
 * @author Patrick
 */


public class CharakterList {
    
    public String pathCL;
    
    
    
    public CharakterList(String file_pathCL) {
        pathCL = file_pathCL;
    }

    public String [] OpenFileCL() throws IOException {

        FileReader frCL = new FileReader(pathCL);
        BufferedReader textReaderCL = new BufferedReader(frCL);

        int numberOfLinesCL = readLinesCL();

        String[] textDataCL = new String[numberOfLinesCL];

        int i;

        for (i = 0; i < numberOfLinesCL; i++) {
            textDataCL[i] = textReaderCL.readLine();

        }
        textReaderCL.close();
        return textDataCL;
    }

    int readLinesCL() throws IOException {

        FileReader file_to_readCL = new FileReader(pathCL);
        BufferedReader bfCL = new BufferedReader(file_to_readCL);

        String aLineCL;

        int numberOfLinesCL = 0;

        while ((aLineCL = bfCL.readLine()) != null) {
            numberOfLinesCL++;
            
            
        }
        bfCL.close();
        return numberOfLinesCL;
    }
    
}
