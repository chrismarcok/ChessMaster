/* Last Edited: by ATHER on
 * JAN 04 2017
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
// knights have to be done differently, only restriction is if theres a piece in the spot it wants to go to
public class Knight extends Piece{
  private int X = 0;
  private int Y = 0;
  private boolean isWhite = false;
  private List [] directions = new List[8];
  private boolean hasMoved = false;
  private boolean isPawn = false;
  private int pieceType =3;
  
  public Knight (int x, int y, boolean white){
    this.X = x;
    this.Y = y;
    this.isWhite = white;
    directions[0] = new List();
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
  
  public void setHasMoved(boolean b){
    hasMoved = b;
  }
  
  public boolean getIsPawn(){
    return isPawn;
  }
  
  public void paint(Graphics2D g2d){
    BufferedImage knight = null;
    if (isWhite){
      try {
        knight = ImageIO.read(new File("White Knight.png"));
      } catch (IOException e) {
      }
    }
    else {
      try {
        knight = ImageIO.read(new File("Black Knight.png"));
      } catch (IOException e) {
      }
    }
    g2d.drawImage(knight, X, Y, null);
  }
  
  public void move(int x, int y){
    this.X = x;
    this.Y = y;
  }
  public int [] getCoords (){
    int [] coords = {X,Y};
    return coords;
  }
  public List [] getPossibleSpaces (int startX){
    directions[0] = new List();
    directions[1] = new List();
    directions[2] = new List();
    directions[3] = new List();
    directions[4] = new List();
    directions[5] = new List();
    directions[6] = new List();
    directions[7] = new List();  
//FURTHER VERTICALLY
    //BOTTOM RIGHT    
    if (X + 50 <= 350+startX && Y + 100 <= 380 ){
      
      directions[0].addToFront(X+50, Y+100);
      
    }
    //BOTTOM LEFT
    if (X - 50 >= startX && Y + 100 <= 380){
      
      directions[1].addToFront(X-50, Y+100);
      
    }
    //TOP RIGHT
    if (X + 50 <= 350+startX && Y - 100 >= 30){
      
      directions[2].addToFront(X+50, Y-100);
      
    }
    //TOP LEFT
    if (X - 50 >= startX && Y - 100 >= 30){
      
      directions[3].addToFront(X-50, Y-100);
      
    }
    
    //FURTHER HORIZONTALLY
    //BOTTOM RIGHT 
    if (X + 100 <= 350+startX){
      if (Y + 50 <= 380){
        directions[4].addToFront(X+100, Y+50);
      }
    }
    
    //BOTTOM LEFT
    if (X - 100 >= startX){
      if (Y + 50 <= 380){
        directions[5].addToFront(X-100, Y+50);
      }
    }
    
    //TOP RIGHT
    if (X + 100 <= 350+startX){
      if (Y - 50 >= 30){
        directions[6].addToFront(X+100, Y-50);
      }
    }
    
    //TOP LEFT
    if (X - 100 >= startX){
      if (Y - 50 >= 30){
        directions[7].addToFront(X-100, Y-50);
      }
    }
    return directions;
  }
  public List[] getPossibleSpaces(int startX, boolean a, boolean b, boolean c, boolean d){
    return directions;
    }
}