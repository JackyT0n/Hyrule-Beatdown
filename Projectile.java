//Class that controls and manages projectiles in the game
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Projectile extends JPanel{
	private int posx,posy;
	private int direction;
	private boolean hit;
	private boolean live = false;
	private Rectangle hitBox;
	private final int RIGHT = 1;
	private final int LEFT = 2;
	private final int WIDTH = 25;
	private final int LENGTH = 50; 
	private Map tileMap;
//	private static final Image[] ProjectileIMG = new Image[]{new ImageIcon("Sprites/bullet.png").getImage(),
//											  				 new ImageIcon("Sprites/enemybullet.png").getImage()};
    public Projectile(int x,int y,int direction, Map tileMap) {//constructor
    	this.tileMap = tileMap;
    	posx = x;
    	posy = y;
    	live = true;
    	this.direction = direction;
    	if (direction == RIGHT){
			posx = x + WIDTH;
			hitBox =  new Rectangle (posx, posy + 24,50, 3);
    	}
    	if (direction == LEFT){
			posx = x - 50;
			hitBox =  new Rectangle (posx, posy + 24,50, 3);
    	}	
    }
    public void move(Player enemy){ //moving the bullet for player
    	if (direction == RIGHT){
			posx += 5;
			hitWall();
			hitBox =  new Rectangle (posx , posy ,20, 25);
			hitEnemy(enemy);	
    	}
    	if (direction == LEFT){
			posx -= 5;
			hitWall();
			hitBox =  new Rectangle (posx, posy ,20, 25);
			hitEnemy(enemy);

    	}
    }
    public void hitWall(){
    	if(tileMap.getMapVal((posx/50),(posy/50)) > 0 || tileMap.getMapVal(((posx + 20)/50),(posy/50)) > 0 
    		|| tileMap.getMapVal(((posx + 20)/50),((posy + 25)/50)) > 0 || tileMap.getMapVal((posx/50),((posy + 25)/50)) > 0){
				live = false;
			}
    }
    public void hitEnemy(Player enemy){
    	if(hitBox.intersects(enemy.hitBox())){
    		live = false;
    		enemy.playerHit();
    	}
    }
    public Rectangle getRect(){
    	return hitBox;
    }
    public boolean getLive(){return live;}
    public int getPosx(){return posx;}
    public int getPosy(){return posy;}

}