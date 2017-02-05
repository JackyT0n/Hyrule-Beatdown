//GameMap manages the order of which things are drawn and the main image of the game
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class GameMap {
	private Player P1, P2;
	private int winner;
	
	private Animation animation = new Animation();
	private Map tileMap;
	
	//Flags
	public boolean menu = true; 
	public boolean gameOver = false; 
	public boolean controls = false; //Blits the menu screen if in menu and true
	
	//Buttons
	private Rectangle [] buttons = new Rectangle []{ //Buttons used to go through various screens
									new Rectangle(560, 266, 45, 45), //Game Start
									new Rectangle(644, 266, 45, 45), //Show Controls
									new Rectangle(564, 723, 45, 45), //Play Again
									new Rectangle(659, 723, 45, 45), //Main Menu 
									};
	//Images
	private Image backgroundIMG = new ImageIcon("tiles/background.png").getImage();
	private Image gameOverIMG = new ImageIcon("tiles/gameover.png").getImage();
	private Image controlsIMG = new ImageIcon("tiles/controls.png").getImage();
	private Image menuIMG = new ImageIcon("tiles/menu.jpg").getImage();
	private Image arrowIMG = new ImageIcon("PlayerSprites/arrow.png").getImage();
	private Image triforceIMG = new ImageIcon("PlayerSprites/Triforce.png").getImage();
	private Image[] health = {new ImageIcon("tiles/1hp.png").getImage(),new ImageIcon("tiles/2hp.png").getImage(),
	new ImageIcon("tiles/3hp.png").getImage(),
	new ImageIcon("tiles/4hp.png").getImage(),
	};
	
	//In-Game Snow
	private long snowTime = System.nanoTime();
	public ArrayList<snowFlake> snowFlakes = new ArrayList<snowFlake>();
	public boolean snowLoaded = false;
	
	//Menu Screen Flickering Effect
	public ArrayList<snowFlake> fireFlies = new ArrayList<snowFlake>();
	public boolean fireLoaded = false;
	//GameMap Constructor
    public GameMap() {
			tileMap = new Map();
			P1 = new Player(1100,850,tileMap,1); //Creates P1 on right side of screen
			P2 = new Player(150,850,tileMap,2);	//Creates P2 on left side of screen
    }
    //Switches the control flag 
    public void controlsSwitch(){
    	if(controls){
    		controls = false;
    	}
    	else{
    		controls = true;
    	}
    }
    
    //Restarts the game by re-creating the players
    public void restart(){
    	P1 = new Player(1100,850,tileMap,1);
    	P2 = new Player(150,850,tileMap,2);
    }
    
    //draws all the snowflakes in the snowFlakes arrayList
    public void drawSnowFlakes(Graphics g, JPanel panel){
    	if (snowLoaded==false){//If snow has not been loaded, fill the snow ArrayList
    		for(int i=0; i<300; i++){
    			snowFlake tmp = new snowFlake();
    			snowFlakes.add(tmp);
    			snowLoaded = true;
    		}
    	}
    	updateSnow();
    	for(snowFlake flake : snowFlakes){ //Draws each snowflake in snowFlakes
    		g.setColor(Color.white);
    		g.fillOval(flake.getX(), flake.getY(), flake.getRad(), flake.getRad());
    	}
    }
    //Updates the position of each snowFlake in snowFlakes
    public void updateSnow(){
    	for(snowFlake flake : snowFlakes){
    		flake.updatePos();
    	}
    }
    
    //Works the same as snowFlakes, for menu
    public void drawFireFlies(Graphics g, JPanel panel){
    	if (fireLoaded==false){
    		for(int i=0; i<60; i++){
    			snowFlake tmp = new snowFlake();
    			fireFlies.add(tmp);
    			fireLoaded = true;
    		}
    	}
    	updateFire();
    	for(snowFlake fly : fireFlies){
    		g.setColor(Color.red);
    		if(fly.getRad() == 3){
    			g.setColor(Color.yellow);
    		}
    		if(fly.getRad() == 5){
    			g.setColor(Color.orange);
    		}
    		g.fillOval(fly.getX(), fly.getY(), fly.getRad(), fly.getRad());
    	}
    }
    //works the same as update snow, for menu
    public void updateFire(){
    	for(snowFlake fly : fireFlies){
    		fly.updatePos();
    	}
    }
    
    public void moveProjectiles(){
    	if(P1.livearrow() == true){
    		P1.getarrow().move(P2);
    		if (P1.getarrow().getLive() == false){
    			P1.endarrow();
    		}
    	}
    	if(P2.livearrow() == true){
    		P2.getarrow().move(P1);
    		if (P2.getarrow().getLive() == false){
    			P2.endarrow();
    		}
    	}
    }
    public Rectangle [] buttons(){
    	return buttons;
    }
    //Sprite Animations
    public void spriteAnimation(){
		P1.animationUpdate();
		P2.animationUpdate();
    }
    //Draws the Player projectiles
    public void drawProjectiles(Graphics g,JPanel panel){
    	if(P1.livearrow() == true){
    		moveProjectiles();
    		if(P1.facingLeft()){
    			g.drawImage(arrowIMG, P1.getarrow().getPosx() + 50, P1.getarrow().getPosy(),-50,50, panel);
    		}
    		else{
    			g.drawImage(arrowIMG, P1.getarrow().getPosx(), P1.getarrow().getPosy(), panel);
    		}
    	}
    	if(P2.livearrow() == true){
    		moveProjectiles();
    		if(P1.facingLeft()){
    			g.drawImage(arrowIMG, P2.getarrow().getPosx() + 50, P2.getarrow().getPosy(),-50,50, panel);
    		}
    		else{
    			g.drawImage(arrowIMG, P2.getarrow().getPosx(), P2.getarrow().getPosy(), panel);
    		}
    	}
    }
    //Draw Player 1
    public void drawPlayer1(Graphics g,JPanel panel){
   		P1.draw(g,panel);
    	if(P1.getHealth() > 0){
   			g.drawImage(health[P1.getHealth()/25-1], P1.getX()-20, P1.getY() - 30, panel);
   		}
    }
    //Draw Player 2
    public void drawPlayer2(Graphics g,JPanel panel){
    	P2.draw(g,panel);
    	if(P2.getHealth() > 0){
   			g.drawImage(health[P2.getHealth()/25-1], P2.getX()-20, P2.getY() - 30, panel);
   		}
    }
    //Draw the background
    public void drawBackground(Graphics g,JPanel panel){
    	g.drawImage(backgroundIMG, 0,0,panel);
    	snowTile(snowTime);
		for (int x=0; x<25; x++){ //iterate through the tileMap
			for (int y=0; y<19; y++){
				if(tileMap.getMapVal(x,y) != 0){ //if a number is found draw the tile accordingly
					g.drawImage(tileMap.getTileImage(x,y),  x*50, y*50, panel);	
				}
			}
		}
    }
    public void drawGameover(Graphics g,JPanel panel){
    	spriteAnimation();
    	drawBackground(g,panel); //draw the background
    	drawSnowFlakes(g,panel);
    	//g.drawImage(P1.getSprite(), 600, 500, panel); draws the player in the winner box
    	g.drawImage(gameOverIMG, 0,0,panel);
    	if (P1.getAlive() == false){
    		P2.drawWinner(g,panel);
    	}
    	if (P2.getAlive() == false){
    		P1.drawWinner(g,panel);
    	}
    	g.drawImage(triforceIMG, 615,565,panel);

    	
    }
    //Draw the main menu
    public void drawMenu(Graphics g,JPanel panel){
    	g.drawImage(menuIMG, 0,0,panel);
    	drawFireFlies(g, panel);
    	if(controls){
    		g.drawImage(controlsIMG, 0,400,panel);
    	}
    }
    //Move Player 1 to the right
    public void P1MoveR(){
    	P1.moveR();
    }
    //Move Player 1 to the left
    public void P1MoveL(){
    	P1.moveL();
    }
    //Player 1 jump
    public void P1Jump(){
    	P1.jump();
    }
    //Player 1 attack (melee)
    public void P1AttM(){
    	P1.attackMelee(P2);
    }
    //Player 1 attack (ranged)
    public void P1AttR(){
    	P1.attackProj();
    }
    public void setIdle(){
    	P1.setIdle();
    	P2.setIdle();
    }
    //Move Player 2 to the right
    public void P2MoveR(){
    	P2.moveR();
    }
    //Move the Player 2 to the left
    public void P2MoveL(){
    	P2.moveL();
    }
    //Player 2 jump
    public void P2Jump(){
    	P2.jump();
    }
    //Player 2 attack (melee)
    public void P2AttM(){
    	P2.attackMelee(P1);
    }
    //Player 2 attack (ranged)
    public void P2AttR(){
    	P2.attackProj();
    }
    //Gravity (apply gravity too both characters)
    public void gravity(){
    	P1.gravity();
    	P2.gravity();
    }
    //Set the menu to false
    public void endMenu(){
    	menu = false;
    }
    //makes the effect of snow hitting the ground
    public void snowTile(long snowTime){
    	long elapsed = (System.nanoTime() - snowTime) / 1000000;
    	if(elapsed > 100) {
    		switchTile();
			
		}
		
    }
    //Switches a grassy platform to a snow platform
    public void switchTile(){
    	int x = 1 +(int)(Math.random()*23);
    	int y = 1+ (int)(Math.random()*18);
    	if(tileMap.getMapVal(x,y) > 0 && tileMap.getMapVal(x,y) <4){ //adds 14 to the array index which is how far the snowPlatforms are away
    		tileMap.setMapVal(x,y,tileMap.getMapVal(x,y)+14);
    	}
    	snowTime = System.nanoTime();
    }
    public void checkGameOver(){
    	if (P1.getAlive() == false){
    		gameOver = true;
    		winner = 2;
    	}
    	if (P2.getAlive() == false){
    		gameOver = true;
    		winner = 1;
    	}
    	
    }
    //Draw the full game
    public void draw(Graphics g,JPanel panel){
   		
    	if(menu == true){// draw the menu
    		drawMenu(g,panel);
    		
    	}
    	if(menu == false && gameOver == false){
			drawBackground(g,panel); //draw the background
			drawSnowFlakes(g, panel);
			gravity();
			drawPlayer1(g,panel);//draw the P1
			drawPlayer2(g,panel);//draw the P2
			drawProjectiles(g,panel);
			spriteAnimation();
			checkGameOver();
    	}
    	if(gameOver == true){
    		drawGameover(g,panel);
    	}
	}
    
}