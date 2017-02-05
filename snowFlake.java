//Class that controls the snow effect on screen
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.File;
//Creates a snowflake which travels diaonally left down the screen starting at a random x,y
public class snowFlake {
	private int x,y,vx,vy,rad; //positions, speeds, size
	
	//snowFlake constructor
    public snowFlake() {
    	x = 1 +(int)(Math.random()*1800); //random x 1-1800
    	y = 1 + (int)(Math.random()*920); //random y 1-920
    	vy = 1 + (int)(Math.random()*3); //random vy 1-3 down
    	vx = (1 + (int)(Math.random()*3))*-1; //random vx 1-3 left
    	rad = 3 + (int)(Math.random()*5); //random size 3-5
    }
    
    public void updatePos(){
    	x = x+vx; //apply velocities
    	y = y+vy; //update pos with vel
    	
    	vy = (1 + (int)(Math.random()*3)); //alter velocities slightly to give flake effect
    	vx = (1 + (int)(Math.random()*3))*-1;
    	
    	if(y >= 950){ //if the snow hits the ground
    		x = 0 + (int)(Math.random()*1800); //give it a random x val 
    		y=0;//force the flake back to the top
    	}
    	if(x < 0){ //if the snow goes past the left side of the screen
    		x = 100 + (int)(Math.random()*1800);//give it a random x val 
    		y=0;//force the flake back to the top
    	}
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int getRad(){return rad;}
   
    
}