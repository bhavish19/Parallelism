/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudscapes;

/**
 *
 * @author bhavm
 */
public class Seq {
    
    public static void main(String[] args){
        float sumx=0;
        float sumy=0;
        CloudData cd = new CloudData();
        cd.readData("input.txt");
        for(int t = 0; t < cd.dimt; t++){
            for (int x=0;x<cd.dimx;x++){
                for(int y = 0; y < cd.dimy; y++){
                    sumx+=cd.advection[t][x][y].x;
                    sumy+=cd.advection[t][x][y].y;
                }
            }
        }
        float avgx =sumx/cd.dim();
        float avgy = sumy/cd.dim();
        System.out.print(avgx+"  "+avgy);
        
    }    
}
