/* Last Edited: by ATHER on
 * JAN 04 2017
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Pawn extends Piece{
  private int X = 0;
  private int Y = 0;
  private boolean hasMoved = false;
  private boolean isWhite = false;
  private List [] directions = new List[3];
  private boolean isPawn = true;
  private int pieceType =1;
  
  public Pawn (int x, int y, boolean white){
    this.X = x;
    this.Y = y;
    this.isWhite = white;
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
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
  
  public int getPieceType(){
	  return pieceType;
  }
  
  public boolean getIsWhite(){
	  return isWhite;
  }
  
  public void paint(Graphics2D g2d){
    BufferedImage pawn = null;
    if (isWhite){
      try {
        pawn = ImageIO.read(new File("White Pawn.png"));
      } catch (IOException e) {
      }
    }
    else {
      try {
        pawn = ImageIO.read(new File("Black Pawn.png"));
      } catch (IOException e) {
      }
    }
    g2d.drawImage(pawn, X, Y, null);
  }
  
  public void move(int x, int y){
    this.X = x;
    this.Y = y;
  }
  public int [] getCoords (){
    int [] coords = {X,Y};
    return coords;
  }
  @Override
  public List[] getPossibleSpaces (int startX, boolean pieceDiagLeft, boolean pieceInFront, boolean pieceDiagRight, boolean pieceTwoInfront){
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
    int moveDirection;
    if (isWhite)
      moveDirection = 50;
    else
      moveDirection =-50;
    
    if (pieceDiagRight)
      directions[1].addToFront(X+moveDirection, Y-moveDirection);
    if (pieceDiagLeft)
      directions[2].addToFront(X-moveDirection, Y-moveDirection);
      
    //south, black pieces Only
    if (!pieceInFront){
      if(!isWhite){
        if (Y < 380){
          directions[0].addToFront(X, Y+50);
          if (!hasMoved && pieceTwoInfront)
            directions[0].addToFront(X, Y+100);
        }
      }
      else{ //white pieces, north
        if (Y > 30){
          directions[0].addToFront(X, Y-50);
          if (!hasMoved && pieceTwoInfront)
            directions[0].addToFront(X, Y-100);
        }
      }
    }
    return directions;
  }
  
  public List [] getPossibleSpaces (int startX){
    return directions;
  }
}