/* Last Edited: by ATHER on
 * JAN 04 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class King extends Piece{
  private int X = 0;
  private int Y = 0;
  private boolean isWhite = false;
  private List [] directions = new List [8];
  private boolean hasMoved = false;
  private boolean isPawn = false;
  private int pieceType =6;
  
  public King (int x, int y, boolean white){
    this.X = x;
    this.Y = y;
    this.isWhite = white;
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
    directions[3] = new List();
    directions[4] = new List();
    directions[5] = new List();
    directions[6] = new List();
    directions[7] = new List();
  }
  
  public boolean getHasMoved(){
    return hasMoved;
  }
  public boolean getIsPawn(){
    return isPawn;
  }
  
  public void setHasMoved(boolean b){
    hasMoved = b;
  }
  
  public void paint(Graphics2D g2d){
    BufferedImage king = null;
    if (isWhite){
      try {
        king = ImageIO.read(new File("White King.png"));
      } catch (IOException e) {
      }
    }
    else {
      try {
        king = ImageIO.read(new File("Black King.png"));
      } catch (IOException e) {
      }
    }
    g2d.drawImage(king, X, Y, null);
  }
  
  public void move(int x, int y){
    this.X = x;
    this.Y = y;
  }
  public int [] getCoords (){
    int [] coords = {X,Y};
    return coords;
  }
  
  public int getPieceType(){
	  return pieceType;
  }
  
  public boolean getIsWhite(){
	  return isWhite;
  }
  // king cant move to a spot that puts him in check
  public List [] getPossibleSpaces (int startX){
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
    directions[3] = new List();
    directions[4] = new List();
    directions[5] = new List();
    directions[6] = new List();
    directions[7] = new List();
    
    // going north
    if (Y>30)
      directions[0].addToFront(X,Y-50);
    // going northeast
    if (Y>30 && X<startX+350)
      directions[1].addToFront(X+50,Y-50);
    // going east
    if ( X<startX+350)
      directions[2].addToFront(X+50,Y);
        
    // going southeast
    if (Y<380 && X<startX+350)
      directions[3].addToFront(X+50,Y+50);
    //going south
    if (Y<380)
      directions[4].addToFront(X,Y+50);
    // going southwest
    if (Y<380 && X>startX)
      directions[5].addToFront(X-50,Y+50);
    // going west
    if (X>startX)
      directions[6].addToFront(X-50,Y);
    // going northwest
    if (Y>30 && X>startX)
      directions[7].addToFront(X-50,Y-50);
    return directions;
    
  }
  public List[] getPossibleSpaces(int startX, boolean queenSideCastlePossible, boolean kingSideCastlePossible, boolean UNUSED, boolean UNUSED2){
	    directions[0] = new List();
	    directions[1] = new List();
	    directions[2] = new List();
	    directions[3] = new List();
	    directions[4] = new List();
	    directions[5] = new List();
	    directions[6] = new List();
	    directions[7] = new List();
	    
	    // going north
	    if (Y>30)
	      directions[0].addToFront(X,Y-50);
	    // going northeast
	    if (Y>30 && X<startX+350)
	      directions[1].addToFront(X+50,Y-50);
	    // going east
	    if ( X<startX+350)
	      directions[2].addToFront(X+50,Y);
	    
	    if (kingSideCastlePossible)
	    directions[2].addToFront(X+100,Y);
	    
	        
	    // going southeast
	    if (Y<380 && X<startX+350)
	      directions[3].addToFront(X+50,Y+50);
	    //going south
	    if (Y<380)
	      directions[4].addToFront(X,Y+50);
	    // going southwest
	    if (Y<380 && X>startX)
	      directions[5].addToFront(X-50,Y+50);
	    // going west
	    if (X>startX)
	      directions[6].addToFront(X-50,Y);
	    
	    if(queenSideCastlePossible)
	    directions[6].addToFront(X-100,Y);
	    
	    // going northwest
	    if (Y>30 && X>startX)
	      directions[7].addToFront(X-50,Y-50);
	    return directions;
    }
}