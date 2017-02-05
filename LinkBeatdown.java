//LinkBeatdown.java
//Jacky Ton
//Gavin  Autterson
//Link beat down is an arena based game where both players control a characters named link
//and have the abilty to shoot and swing a sword at each other. Both players objective is too
//kill the other player and both players have 4 health
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class LinkBeatdown extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
	int deltaTime = 10;
		
    public LinkBeatdown() {
		super("Link BeatDown");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1255,978);
		myTimer = new Timer(deltaTime, this);	 // trigger every 10 ms
		game = new GamePanel(this);
		add(game);
		setResizable(false);
		setVisible(true);
    }
	
	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		game.play();
		game.repaint();
	}

    public static void main(String[] arguments) {
		LinkBeatdown frame = new LinkBeatdown();		
    }
}

class GamePanel extends JPanel implements MouseListener, KeyListener {
	private boolean []keys;
	private GameMap game;
	private LinkBeatdown mainFrame;
	private static int spriteTimer;
	public GamePanel(LinkBeatdown m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		game = new GameMap();
		mainFrame = m;
		setSize(1250,950);
        addKeyListener(this);
		addMouseListener(this);

	}

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void move(){
    	//Player 1 Actions
		if(keys[KeyEvent.VK_RIGHT] ){
			game.P1MoveR();
		}
		if(keys[KeyEvent.VK_LEFT] ){
			game.P1MoveL();
		}
		if(keys[KeyEvent.VK_UP] ){
			game.P1Jump();
		}
		if(keys[KeyEvent.VK_DOWN] ){
			game.P1AttM(); // melee attack
		}
		if(keys[KeyEvent.VK_0] ){
			game.P1AttR(); // ranged attack
		}
		//Player 2 Actions
		if(keys[KeyEvent.VK_D] ){
			game.P2MoveR();
		}
		if(keys[KeyEvent.VK_A] ){
			game.P2MoveL();
		}
		if(keys[KeyEvent.VK_W] ){
			game.P2Jump();
		}
		if(keys[KeyEvent.VK_S] ){
			game.P2AttM(); // melee attack
		}
		if(keys[KeyEvent.VK_F] ){
			game.P2AttR(); // ranged attack
		}
		
    }
    
	public void play(){
			move();	
	}
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        game.setIdle(); // if no keys are pressed Players are idle
    }
    public void paintComponent(Graphics g){ 	 
		game.draw(g,this);	
    }
    // ------------ MouseListener ------------------------------------------
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    public void mouseClicked(MouseEvent e){
    	//Menu options and hit boxes for them
    	if(game.menu == true){
    		if(game.buttons()[0].contains(e.getX(), e.getY())){
    			game.menu = false;
    		}
    		if(game.buttons()[1].contains(e.getX(), e.getY())){
  				game.controlsSwitch();
    		}
    	}
    	if(game.gameOver == true){
    		if(game.buttons()[2].contains(e.getX(), e.getY())){
    			game.gameOver = false;
    			game.restart();
    		}
    		if(game.buttons()[3].contains(e.getX(), e.getY())){
  				game.menu=true;
  				game.gameOver = false;
  				game.restart();
    		}
    	}
   	}  
    	 
    public void mousePressed(MouseEvent e){
	} 
    	 
		
}