
import java.util.concurrent.RecursiveTask;

public class MakeParallel extends RecursiveTask<Vector>  {
	  int lo; // arguments
	  int hi;
          CloudData cd;
	  int[] arr;
	  static final int SEQUENTIAL_CUTOFF=100;

	  	    
	  MakeParallel(int l, int h, CloudData d) { 
	    lo=l; hi=h;
            this.cd=d;
            this.arr=new int[3];
	  }


	  protected Vector compute(){// return answer - instead of run
		  if((hi-lo) < SEQUENTIAL_CUTOFF) {
			  float sumx = 0;
                          float sumy = 0;
		      for(int i=lo; i < hi; i++){
                          cd.locate(i, arr);
                          sumx+=cd.advection[arr[0]][arr[1]][arr[2]].x;
                          //System.out.print(cd.advection[arr[0]][arr[1]][arr[2]].x+ " ");
                          sumy+=cd.advection[arr[0]][arr[1]][arr[2]].y;
                          float localtotx=0;
                          float localtoty=0;         
                          int neighbour=0;
                          for (int row=Math.max(0,arr[1]-1);row<Math.min(cd.dimx,arr[1]+2);row++){
                              for (int col=Math.max(0,arr[2]-1);col<Math.min(cd.dimy,arr[2]+2);col++){
                                  localtotx+=cd.advection[arr[0]][row][col].x;
                                  localtoty+=cd.advection[arr[0]][row][col].y;
                                  neighbour++;
                              }
                          }
                          float localavgx = localtotx/neighbour;
                          float localavgy = localtoty/neighbour;
                          float average = (float)Math.sqrt(Math.pow(localavgx,2)+Math.pow(localavgy,2));
                         if (average>0.2 && Math.abs(cd.convection[arr[0]][arr[1]][arr[2]])<=average)
                             cd.classification[arr[0]][arr[1]][arr[2]]=1;
                         else if (average < Math.abs(cd.convection[arr[0]][arr[1]][arr[2]]))
                             cd.classification[arr[0]][arr[1]][arr[2]]=0;
                         else
                             cd.classification[arr[0]][arr[1]][arr[2]]=2;
                          //System.out.print(localavgx+"  ");
                      }
		      float avgx = sumx/cd.dim();
                      float avgy = sumy/cd.dim();
                      
		      return new Vector(avgx,avgy);
		  }
		  else {
			  MakeParallel left = new MakeParallel(lo,(hi+lo)/2, cd);
			  MakeParallel right= new MakeParallel((hi+lo)/2,hi, cd);
			  
			  // order of next 4 lines
			  // essential – why?
			  left.fork();
			  Vector rightAns = right.compute();
			  Vector leftAns  = left.join();
                          Vector v = new Vector();
                          v.x=leftAns.x + rightAns.x;
                          v.y=leftAns.y + rightAns.y;
			  return v;     
		  }
	 }
}


