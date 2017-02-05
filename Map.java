//Class that holds the tileMap that the game is based on
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;
//Creates a 2D array of integers that correspond to an array of Images which are used to create the games map
public class Map {
   private static Image[] tileImage = new Image[]{ //Holds various 50x50 images used in creating the map
   							new ImageIcon("tiles/backTile.png").getImage(),
   							new ImageIcon("tiles/platLeft.png").getImage(),
  			 				new ImageIcon("tiles/platMid.png").getImage(),
  			 				new ImageIcon("tiles/platRight.png").getImage(),
  			 				new ImageIcon("tiles/leftWall.png").getImage(),
  			 				new ImageIcon("tiles/rightWall.png").getImage(),
  			 				new ImageIcon("tiles/backTile.png").getImage(),
  			 				new ImageIcon("tiles/roof.png").getImage(),
  			 				new ImageIcon("tiles/redFlower.png").getImage(),
  			 				new ImageIcon("tiles/blueFlower.png").getImage(),
  			 				new ImageIcon("tiles/darkBush.png").getImage(),
  			 				new ImageIcon("tiles/tiki1.png").getImage(),
  			 				new ImageIcon("tiles/tiki2.png").getImage(),
  			 				new ImageIcon("tiles/tiki3.png").getImage(),
  			 				new ImageIcon("tiles/tiki4.png").getImage(),
  			 				new ImageIcon("tiles/platLeft2.png").getImage(),
  			 				new ImageIcon("tiles/platMid2.png").getImage(),
  			 				new ImageIcon("tiles/platRight2.png").getImage(),};
  /**
	*negative numbers for decorative sprites -> hitable objects > 0
	*0 - empty
	*1 - platform left end
	*2 - platform middle
	*3 - platform right end
	*4 - left wall
	*5 - right wall
	*6 - square tile
	*7 - roof
	*8 - redFlower
	*9 - blueFlower
	*10 - darkBush
	*11 - tiki1
	*12 - tiki2
	*13 - tiki3
	*14 - tiki4
	*15 - snowy platform left end
	*16 - snowy platform middle
	*17 - snowy platform right end
	**/
	
  int[][] map = new int[][]{ //Each number is called in gameMap to draw a 50x50 piece of the map
 		 { 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 9, 9, 0, 0, 0, 0, 0, 8, 8, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 10, 0, 0, 0, 0, 0, 1, 11, 12, 0, 0, 6, 0, 0, 11, 12, 3, 0, 0, 0, 0, 0, 10, 5},
 		 { 4, 3, 0, 0, 0, 0, 0, 0, 13, 14, 0, 0, 0, 0, 0, 13, 14, 0, 0, 0, 0, 0, 0, 3, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 0, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 0, 5},
 		 { 4, 10, 0, 0, 0, 0, 0, 1, 3, 0, 0, 0, 6, 0, 0, 0, 1, 3, 0, 0, 0, 0, 0, 10, 5},
 		 { 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 6, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 9, 0, 9, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 8, 0, 8, 5},
 		 { 4, 2, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 6, 9, 6, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 5},
 		 { 4, 10, 10, 0, 0, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 0, 0, 0, 0, 10, 5},
 		 { 4, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 5},
 		 { 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
		};
		
    public Map(){
    }
    //returns the Image @ x,y on the map
    public Image getTileImage(int x, int y){ 
        return tileImage[map[y][x]];
    }
    //returns the int @ x,y on the map
    public int getMapVal(int x, int y){return map[y][x];}
    
    //sets the int value @ x,y on the map
    public void setMapVal(int x, int y, int newTile){map[y][x] = newTile;}
}