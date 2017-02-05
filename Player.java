//Player class that controls all aspects of the character
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.File;


public class Player {
	//Character Info
	private int x,y,vy,prevX, prevY, newX, newY, health;//coordinates of the player and health
	private final int G = 1;							//Gravity value
	private final int T_V = 20;							//Terminal Velocity (max speed player can fall)
	private Animation animation = new Animation();		//Animation class that will handle frames of the character
	private String colour = "";							//colour of the character
	private int direction; 								//current direction the player is facing
	private Projectile arrow; 							//projectile of the character 
	private boolean alive = true;						//flag to check if you are alive
	private static Map tileMap;							//The tileMap the player can interact with
	private boolean jump = false;						//flag to check if you have jumped yet
	private boolean livearrow = false;					//flag to check if you have fired a projectile yet
	private Rectangle hitBox, hitBoxMelee;				//hit boxes for your character and your weapon
	//Timers                                            
	private long arrowTime;								//Timer for arrow ammo	
	private long stunTime;								//Timer for amount of time stunned
	private long deathTime;								//Timer for death animation before the game resets
	
	//Animation flags					
	private boolean shootableArrow = true;				//Flag to show you can shoot an arrow
	private boolean attackAnimation = false;			//Flag to show the character is currently in attacking animation
	private boolean bowAnimation = false;				//Flag to show the character is currently in bow animation
	private boolean hitStunAnimation = false;			//Flag to show the character is currently in hitStun animation
	private boolean deathAnimation = false;				//Flag to show the character is currently in dying animation
	private int currentTileX, currentTileX2; 			//currentTileX left side of player, currentTileX2 right side of player
	private int currentTileY, currentTileY2; 			//currentTileY top part of player, currentTileY2 bottom side of player
	private boolean movingR = false;					//Flag to show the character is moving to the right
	private boolean movingL = false;					//Flag to show the character is moving to the left
	
	//Player Variables
	private final int RIGHT = 1;						//direction - right 
	private final int LEFT = 2; 						//direction - left
	private final int WIDTH = 25; 						//width of the character hitbox
	private final int LENGTH = 50;						//length of the character hitbox
	private final int spriteWidth = 50;					//Average size of Sprites
	private final int spriteLength = 50;
	
	
	//Player Sprites
	private BufferedImage[] idleSprites;
	private BufferedImage[] walkingSprites;
	private BufferedImage[] jumpSprites;
	private BufferedImage[] jumpingSprites;
	private BufferedImage[] fallSprites;
	private BufferedImage[] attackSprites;
	private BufferedImage[] bowSprites;
	private BufferedImage[] stunSprites;
	private BufferedImage[] deathSprites;
	private int currentSprite;
	//Constructor
    public Player(int x, int y, Map tileMap, int type) {
    	if (type == 1){ //Player 1 is a different colour
    		colour = "Dark";
    	}
    	if (type == 2){
    		colour = "";
    	}
    	this.x = x;
    	this.y = y;
    	this.tileMap = tileMap;
    	prevX = x; //set previous step to initial position
    	prevY = y;
    	direction = RIGHT;
    	health = 100;
    	try {
    		//idle sprites
			idleSprites = new BufferedImage[4];
    		BufferedImage image = ImageIO.read(new File ("PlayerSprites/" +colour+ "LinkIdle.png"));
    		for(int i = 0; i < idleSprites.length; i++){
    			idleSprites[i] = image.getSubimage(
    				i * spriteWidth,
	    			0,
	    			spriteWidth,
	    			spriteLength
	    		);
	    		
	    	}
	    	//walking sprites
	    	walkingSprites = new BufferedImage[8];
	    	BufferedImage image1 = ImageIO.read(new File ("PlayerSprites/" + colour + "LinkWalk.png"));
    		for(int i = 0; i < walkingSprites.length; i++){
    			walkingSprites[i] = image1.getSubimage(
    				i * spriteWidth,
	    			0,
	    			spriteWidth,
	    			spriteLength
	    		);
	    		
	    	}
	    	//jumping sprites
	    	jumpSprites = new BufferedImage[3];
	    	jumpingSprites = new BufferedImage[1];
	    	BufferedImage image2 = ImageIO.read(new File ("PlayerSprites/" + colour + "LinkJump.png"));
    		for(int i = 0; i < jumpSprites.length; i++){
    			jumpSprites[i] = image2.getSubimage(
    				i * spriteWidth,
	    			0,
	    			spriteWidth,
	    			spriteLength
	    		);
	    		
	    	}
	    	jumpingSprites[0] = jumpSprites[2];
	    	//Falling Sprites
	    	fallSprites = new BufferedImage[1];
	    	BufferedImage image3 = ImageIO.read(new File ("PlayerSprites/" + colour + "LinkFall.png"));
	    	fallSprites[0] = image3;
	    	
	    	//Attack Sprite
	    	attackSprites = new BufferedImage[6];
	    	BufferedImage image6 = ImageIO.read(new File ("PlayerSprites/" + colour + "LinkAttack.png"));
	    	attackSprites[0] = image6.getSubimage(0,0,79,63);
	    	attackSprites[1] = image6.getSubimage(100,0,79,63);
	    	attackSprites[2] = image6.getSubimage(200,0,79,63);
	    	attackSprites[3] = image6.getSubimage(300,0,79,63);
	    	attackSprites[4] = image6.getSubimage(400,0,79,63);
	    	attackSprites[5] = image6.getSubimage(500,0,79,63);
	    	//Bow Sprites
	    	bowSprites = new BufferedImage[2];
	    	BufferedImage image7 = ImageIO.read(new File ("PlayerSprites/" + colour + "LinkBow.png"));
	    	bowSprites[1] = image7.getSubimage(0,0,50,50);
	    	bowSprites[0] = image7.getSubimage(0,50,50,50);
	    	
	    	//Stun Sprites
	    	stunSprites = new BufferedImage[4];
	    	BufferedImage image8 = ImageIO.read(new File ("PlayerSprites/" + colour + "LinkStun.png"));
	    	stunSprites[0] = image8.getSubimage(0,0,50,50);
	    	stunSprites[1] = image8.getSubimage(50,0,50,50);
	    	stunSprites[2] = image8.getSubimage(50,0,50,50);
	    	stunSprites[3] = image8.getSubimage(50,0,50,50);
	    	//deathSprites
	    	
	    	deathSprites = new BufferedImage[15];
	    	deathSprites[0] = image8.getSubimage(0,0,50,50);
	    	deathSprites[1] = image8.getSubimage(50,0,50,50);
	    	deathSprites[2] = image8.getSubimage(100,0,50,50);
	    	deathSprites[3] = image8.getSubimage(150,0,50,50);
	    	for(int i = 4; i < 14; i++){
    			deathSprites[i] = image8.getSubimage(150,0,50,50);
	    	}
 
    	}
		catch(Exception e) {
			e.printStackTrace();
		}
		
    }
    //Check if the player is facing the left ( for arrows)
    public boolean facingLeft(){
    	if (direction == LEFT){
    		return true;
    	}
    	return false;
    }
    //Return what  kind of animation the player is currently doing
    public void getAnimation(){ 
    	playerTimer();
    	if(movingL == true || movingR == true) { 
			animation.setFrames(walkingSprites);
			animation.setDelay(100);
		}
		//You are idle if no other animations are set
		if(movingL != true && movingR != true && attackAnimation != true && hitStunAnimation != true && deathAnimation != true) {
			animation.setFrames(idleSprites);
			animation.setDelay(900);
		}
		if(vy < 0) { //if you are going up you are in the jumping animation
			if(!jump){//inital jump animation
				animation.setFrames(jumpSprites);
				animation.setDelay(30);
			}
			else{//Already in the air but still going up
				animation.setFrames(jumpingSprites);
				animation.setDelay(-1);
			}
		}
		if(vy > 0) {// falling
			animation.setFrames(fallSprites);
			animation.setDelay(-1);
		}
		if(attackAnimation == true){
			animation.setFrames(attackSprites);
			animation.setDelay(100);
		}
		if(bowAnimation == true){
			animation.setFrames(bowSprites);
			animation.setDelay(300);
		}
		if(hitStunAnimation == true && deathAnimation != true){
			animation.setFrames(stunSprites);
			animation.setDelay(100);
		}
		if(deathAnimation == true){
			animation.setFrames(deathSprites);
			animation.setDelay(100);
		}
    }
    //Keeps track of the timers of different operations
    public void playerTimer(){
    	arrowCharge(arrowTime);
    	hitStun(stunTime);
    	deathStun(deathTime);
    }
    //creates a hitbox for the character
    public Rectangle hitBox(){			
    	hitBox =  new Rectangle (x,y,WIDTH,LENGTH);
    	return hitBox;
    }
    //returns that the player has died
	public void killed(){
		alive = false;
	}
	//checks if the characer is touching the ground or in the air
    public boolean inAir(){
    	currentTileX = x / 50;
    	currentTileX2 = (x + WIDTH) / 50;
    	currentTileY2 = (y + LENGTH) / 50;
    	//checks if either the bottom right or bottom left is touching a tile
    	//excludes the numbers 8 - 10 so they become background images
    	if (tileMap.getMapVal(currentTileX,currentTileY2) > 0 && tileMap.getMapVal(currentTileX,currentTileY2) < 8 || tileMap.getMapVal(currentTileX,currentTileY2) > 10
    		|| tileMap.getMapVal(currentTileX2,currentTileY2) > 0 && tileMap.getMapVal(currentTileX2,currentTileY2) < 8 || tileMap.getMapVal(currentTileX2,currentTileY2) > 10) {
    		jump = false;
    		return false;
    		
    	}
    	else{ //if the tile isn't touching a tile jump must be true
    		jump = true;
    		return true;
    		
    	}
    		
    }
    	
		
 
    public int getX(){return x;}
    public int getY(){return y;}
    public boolean getAlive() {return alive;}
    public void setX(int newX){x = newX;}
    public void setY(int newY){y = newY;}
	//Moves the player to the right
    public void moveR(){
    	direction = RIGHT;//set the direction to RIGHT
    	newX = x + 4;//move the character 4 pixels to the right
    	currentTileX2 = (newX + WIDTH) / 50;
    	currentTileY = (y) / 50;
    	currentTileY2 = (y + LENGTH - 1) / 50; // subtract one so you do not intersect the a tile immediatly above Player
    	//if space is occupied return player back to previous x position
    	//excludes the numbers 8 - 10 so they become background images
    	if (tileMap.getMapVal(currentTileX2,currentTileY) > 0 && tileMap.getMapVal(currentTileX2,currentTileY) < 8 || tileMap.getMapVal(currentTileX2,currentTileY) > 10 ||
    		tileMap.getMapVal(currentTileX2,currentTileY2) > 0 && tileMap.getMapVal(currentTileX2,currentTileY2) < 8 || tileMap.getMapVal(currentTileX2,currentTileY2) > 10 ){ 
    		x = prevX;
    	}
    	else{//if space is not occupied update the players x position
    		x = newX;
    		prevX = x;
    	}
    	movingR = true; //set the moving animation to the right
    }
    //Move the player to the left
    public void moveL(){
    	direction = LEFT;
    	newX = x - 4;
    	currentTileX = newX  / 50;
    	currentTileY = (y) / 50;
    	currentTileY2 = (y + LENGTH - 1) / 50;
    	//excludes the numbers 8 - 10 so they become background images
    	//if space is occupied return player back to previous x position
    	if (tileMap.getMapVal(currentTileX,currentTileY) > 0 && tileMap.getMapVal(currentTileX,currentTileY) < 8 || tileMap.getMapVal(currentTileX,currentTileY) > 10 || 
    		tileMap.getMapVal(currentTileX,currentTileY2) > 0 && tileMap.getMapVal(currentTileX,currentTileY2) < 8 || tileMap.getMapVal(currentTileX,currentTileY2) > 10){
    		x = prevX;
    	}
    	else{
    		x = newX;
    		prevX = x;
    	}
    	movingL = true; //set the animation to the left
    }
    //Player jump
    public void jump(){
    	if (jump == false){	//only jump if you are touching the ground
	    	y = y-1;// -1 so you are not touching the ground so inair() will not return true
	    	vy = -19;// the speed and distance player will jump
    	}
    }
    //Checks if the player hits something above it
    public void hitWallV(){
    	currentTileX = x / 50;
    	currentTileX2 = (x + WIDTH) / 50;
    	currentTileY = (y + 1) / 50;
    	//excludes the numbers 8 - 10 so they become background images
    	//checks the top left and right corner if it hits any tiles above it
    	if (tileMap.getMapVal(currentTileX,currentTileY) > 0 && tileMap.getMapVal(currentTileX,currentTileY) < 8 || tileMap.getMapVal(currentTileX,currentTileY) > 10 ||
    		tileMap.getMapVal(currentTileX2,currentTileY) > 0 && tileMap.getMapVal(currentTileX2,currentTileY) < 8 || tileMap.getMapVal(currentTileX2,currentTileY) > 10){
    		y = prevY;
    		vy = 0;
    	}
    	else{
    		prevY = y;
    	}
    }


    public void gravity(){
    	hitWallV();
    	if (inAir()){//only apply graivty if you are in the air
    		vy = vy + G; //Effect of gravity
    		if(vy >= T_V){vy = T_V;} //Terminal Velocity
    		y = y + vy; //Update y pos with y vel
			hitWallV();
    	}
    	if (!inAir()){
    		vy = 0;
    		y = (currentTileY * 50) ;//ensures you are perfectly on top of a tile and not in it
    	}		
    }
    //Checks if you have hit an enemy with a melee attack
    public boolean attackMelee(Player enemy){
    	attackAnimation = true;
    	if (direction == RIGHT){ // checks the direction you  are facing to make accurate hitbox
    		hitBoxMelee =  new Rectangle (x + WIDTH, y + (LENGTH / 2) - 10,35, 25);
    		if (hitBoxMelee.intersects(enemy.hitBox())){
    			enemy.playerHit();
    			return true;
    		}
    	}
    	if (direction == LEFT){
    		hitBoxMelee =  new Rectangle (x - 35, y + (LENGTH / 2) - 10,35,25);
    		if (hitBoxMelee.intersects(enemy.hitBox())){
    			enemy.playerHit();
    			return true;
    		}
    	}
    	return false;
    }
    //creates an arrow to shoot
    public void attackProj(){ 
    	if(shootableArrow == true){
    		bowAnimation = true; //sets you into bow animation
    		livearrow = true; // make an arrow live 
    		shootableArrow = false; // sets the arrow to unshootable
    		arrow = new Projectile(x,y,direction,tileMap);	
    		arrowTime = System.nanoTime();//reset reference time 
    	}
    	else{}
    }
  	public boolean livearrow(){
  		return livearrow;
  	}
  	public Projectile getarrow(){
  		return arrow;
  	}
  	public void endarrow(){
  		livearrow = false;
  	}
  	//allows the player to get hit
    public void playerHit(){
    	if(!hitStunAnimation){ // checks if the character is in hit stun animation or not before applying damage
    		health -= 25;
    		stunTime = System.nanoTime();
    		hitStunAnimation = true;
    	}
    	if (health <= 0){ // if you die play death animation
    		deathTime = System.nanoTime();
    		deathAnimation = true;
    	}
    	if(hitStunAnimation){}
    }
    //death pause (for dramatic purposes)
    public void deathStun(long deathTime){
    	long elapsed = (System.nanoTime() - deathTime) / 1000000;
    	if(elapsed > 900) {
    		if (health <= 0){
				deathAnimation = false;
				alive = false;
    		}
			
		}
    }
    //Timer for hitStun
    public void hitStun(long stunTime){
    	long elapsed = (System.nanoTime() - stunTime) / 1000000;
    	if(elapsed > 400) {
			hitStunAnimation = false;
		}
		
    }
    //Timer for arrow recharge
    public void arrowCharge(long arrowTime){
    	long elapsed = (System.nanoTime() - arrowTime) / 1000000;
    	if(elapsed > 1000) {
			shootableArrow = true;
		}
		
    }
    public int getHealth(){
    	return health;
    }
    //set the character animation to idle sets all other animtations to false
	public void setIdle(){
		movingR = false;
		movingL = false;
		attackAnimation = false;
		bowAnimation = false;
	}
	//update the frames in animation
	public void animationUpdate(){
		animation.update();
	}
	//draw the winner in the winning screen
	public void drawWinner(Graphics g,JPanel panel){
		animation.setFrames(idleSprites);
		animation.setDelay(700);
		g.drawImage(animation.getImage(), 625 , 505 ,panel);
	}
	//Draw the player
	public void draw(Graphics g,JPanel panel){
		getAnimation();
		if(direction == LEFT){ // offset for putting pictures to the left
			if(attackAnimation == true){
				g.drawImage(animation.getImage(), x + 40, y - 6 , -78,63,panel);
			}
			else{
				g.drawImage(animation.getImage(), x + 25, y , -50,50,panel);
			}
		}
		else{// if direction is right
			if(attackAnimation == true){
				g.drawImage(animation.getImage(), x - 14, y - 6,panel);
			}
			else{
				g.drawImage(animation.getImage(), x , y ,panel);
			}
	
		}

	}

    
    
}