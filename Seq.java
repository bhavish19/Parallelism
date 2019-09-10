/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Scanner;

/**
 *
 * @author bhavm
 */
public class Seq {
    
    public static void main(String[] args){
        float sumx=0;
        float sumy=0;
        Scanner in = new Scanner(System.in);
        System.out.println("Sequential programming:");
        System.out.print("Enter 'input_file_name.txt' and 'output_file_name.txt' (separated by a space): ");
        String input = in.next();
        String output = in.next();
        Vector wind = new Vector();
        CloudData cd = new CloudData();
        cd.readData(input);
        System.gc();
        long start = System.currentTimeMillis();
        for(int t = 0; t < cd.dimt; t++){
            for (int x=0;x<cd.dimx;x++){
                for(int y = 0; y < cd.dimy; y++){
                    sumx+=cd.advection[t][x][y].x;
                    sumy+=cd.advection[t][x][y].y;
                    float localtotx=0;
                    float localtoty=0;
                    int neighbour=0;
                    for (int i =Math.max(0, x-1);i<Math.min(cd.dimx, x+2);i++){
                        for (int j=Math.max(0,y-1);j<Math.min(cd.dimy,y+2);j++){
                            localtotx+=cd.advection[t][i][j].x;
                            localtoty+=cd.advection[t][i][j].y;
                            neighbour++;
                        }             
                    }
                    float localavgx = localtotx/neighbour;
                    float localavgy = localtoty/neighbour;
                    float average = (float)Math.sqrt(Math.pow(localavgx, 2)+Math.pow(localavgy,2));
                    if (average>0.2 && Math.abs(cd.convection[t][x][y])<=average)
                        cd.classification[t][x][y]=1;
                    else if (average<Math.abs(cd.convection[t][x][y]))
                        cd.classification[t][x][y]=0;
                    else
                        cd.classification[t][x][y]=2;
                }
            }
        }
        float avgx =sumx/cd.dim();
        float avgy = sumy/cd.dim();
        wind.setx(avgx);
        wind.sety(avgy);
        long diff = System.currentTimeMillis() - start;
        System.out.println("Runtime: " + diff + " milliseconds");
        cd.writeData(output, wind);
        
    }    
}
