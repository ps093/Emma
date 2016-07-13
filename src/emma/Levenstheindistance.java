/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
package emma;

/**
 *
 * @author Patrick
 
public class Levenstheindistance {

     
    
                                                   
    private static int minimum(int f, int g, int h) {                            
        return Math.min(Math.min(f, g), h);                                      
    }                       

  
     
    
                                                                                 
    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int d = 0; d <= lhs.length(); d++)                                 
            distance[d][0] = d;                                                  
        for (int e = 1; e <= rhs.length(); e++)                                 
            distance[0][e] = e;                                                  
                                                                                 
        for (int d = 1; d <= lhs.length(); d++)                                 
            for (int e = 1; e <= rhs.length(); e++)                             
                distance[d][e] = minimum(                                        
                        distance[d - 1][e] + 1,                                  
                        distance[d][e - 1] + 1,                                  
                        distance[d - 1][e - 1] + ((lhs.charAt(d - 1) == rhs.charAt(e - 1)) ? 0 : 1));
                                                                                 
        return distance[lhs.length()][rhs.length()];                           
    }                                                                            

    
    
}
*/