/* Last Edited: by ATHER on
 * JAN 04 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Bishop extends Piece{
  private int X = 0;
  private int Y = 0;
  private boolean isWhite = false;
  private List [] directions = new List [4];
  private boolean hasMoved = false;
  private boolean isPawn = false;
  private int pieceType =4;
  
  
  public Bishop (int x, int y, boolean white){
    this.X = x;
    this.Y = y;
    this.isWhite = white;
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
    directions[3] = new List();
  }
  
  public int getPieceType(){
	  return pieceType;
  }
  
  public boolean getIsWhite(){
	  return isWhite;
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
    BufferedImage bishop = null;
    if (isWhite){
      try {
        bishop = ImageIO.read(new File("White Bishop.png"));
      } catch (IOException e) {
      }
    }
    else {
      try {
        bishop = ImageIO.read(new File("Black Bishop.png"));
      } catch (IOException e) {
      }
    }
    g2d.drawImage(bishop, X, Y, null);
  }
  public int [] getCoords (){
    int [] coordinates = {this.X,this.Y};
    return coordinates;
  }
  
  public void move(int x, int y){
    this.X = x;
    this.Y = y;
  }
  // startX is the starting x of the board, not the piece
    public List [] getPossibleSpaces (int startX){
      directions[0] = new List();
      directions[1] = new List();
      directions[2] = new List();
      directions[3] = new List();
  
    // going northeast

      for (int i = 50; (X+i <= (startX+350) && Y - i >= 30) ; i+=50){
      directions[0].addToFront(X+i, Y-i);
      
   
      }
   
    // going southeast
    
      for (int i = 50; X+i <= (startX+350) && Y + i <= 380 ; i+=50){
      directions[1].addToFront(X+i,Y+i);
      

      }
    // going southwest
      
      for (int i = 50; X-i >= startX && Y + i <= 380 ; i+=50){
      directions[2].addToFront(X-i,Y+i);
      
 
      }
    // going northwest
      
      for (int i = 50; X-i >= startX && Y - i >= 30 ; i+=50){
      directions[3].addToFront(X-i,Y-i);
      

      }
      
    return directions;
    
  }
    public List[] getPossibleSpaces(int startX, boolean a, boolean b, boolean c, boolean d){
    return directions;
    }
}