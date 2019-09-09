/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sequential;

/**
 *
 * @author bhavm
 */
public class Vector {
    public Float x,y;
    
    Vector(){
        this.x=null;
        this.y=null;
    }
    
    Vector(float x,float y){
        this.x=x;
        this.y=y;
    }
    
    public void setx(float x){
        this.x=x;
    }
    
    public void sety(float y){
        this.y=y;
    }
}
