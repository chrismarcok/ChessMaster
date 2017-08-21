/* Last Edited: by ATHER on
 * JAN 04 2017
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Rook extends Piece{
 private int X = 0;
 private int Y = 0;
 private boolean isWhite = false;
 private List [] directions = new List [4];
 private boolean hasMoved = false;
 private boolean isPawn = false;
 private int pieceType =2; 
		 
  public Rook (int x, int y, boolean white){
    this.X = x;
    this.Y = y;
    this.isWhite = white;
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
    directions[3] = new List();
  }
  
  public boolean getHasMoved(){
    return hasMoved;
  }
  
  public void setHasMoved(boolean b){
    hasMoved = b;
  }
  
  public boolean getIsPawn(){
    return isPawn;
  }
  
  public void paint(Graphics2D g2d){
    BufferedImage rook = null;
    if (isWhite){
      try {
        rook = ImageIO.read(new File("White Rook.png"));
      } catch (IOException e) {
      }
    }
    else {
      try {
        rook = ImageIO.read(new File("Black Rook.png"));
      } catch (IOException e) {
      }
    }
    g2d.drawImage(rook, X, Y, null);
  }
  
  public void move(int x, int y){
    this.X = x;
    this.Y = y;
  }
  
  public int[] getCoords (){
    int[] coords = {X,Y};
    return coords;
  }
  public int getPieceType(){
	  return pieceType;
  }
  
  public boolean getIsWhite(){
	  return isWhite;
  }
    public List [] getPossibleSpaces (int startX){
      directions[0] = new List();
      directions[1] = new List();
      directions[2] = new List();
      directions[3] = new List();
    // going north

      for (int i = Y-50; i >=30 ; i-=50){
        directions[0].addToFront(X,i);

      }
      // going east

      for (int i = X+50; i<=(startX+350); i+=50){
        directions[1].addToFront(i,Y);

      }
      //going south

      for (int i = Y+50; i<430; i+=50){
        directions[2].addToFront(X,i);

      }
      // going west

      for (int i = X-50; i>=startX; i-=50){
        directions[3].addToFront(i,Y);

      }

    return directions;
    
  }
    public List[] getPossibleSpaces(int startX, boolean a, boolean b, boolean c, boolean d){
    return directions;
    }
}