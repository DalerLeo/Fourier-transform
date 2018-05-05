/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest1;

/**
 *
 * @author dalerleo
 */
public class DFT {
    final double[]  FE;
    final double[] FO;
    final double OMEGA;
    double[] magnitude;
    final int N; 
    DFT(byte[] bytes) {
        int len = bytes.length;
        N = len;
        OMEGA = (2*Math.PI)/N;
        FE = new double [len];
        FO = new double [len];
        magnitude = new double[len];
    }
    
    
    
public double[] makeDft(byte[] bytes) {
        for (int k=0; k<N; k++)
        {
           for (int n=0; n<N; n++)
           {
               FE[k] += bytes[n]*Math.cos(OMEGA*n*k);
               FO[k] += -(bytes[n]*Math.sin(OMEGA*n*k));
           }
           
           magnitude[k]= Math.sqrt(Math.pow(FE[k],2)+Math.pow(FO[k], 2));
        }
        
        return magnitude;
    }
    
}
