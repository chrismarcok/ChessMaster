/* Last Edited: by ATHER on
 * JAN 04 2017
 */

import java.awt.*;

public abstract class Piece
{
  private boolean clickedOn = false;

  public abstract void paint(Graphics2D g2d);
  
  public abstract void move(int x, int y);
  
  public abstract int[] getCoords();
  
  public abstract List[] getPossibleSpaces(int startX);
  public abstract List[] getPossibleSpaces(int startX, boolean a, boolean b, boolean c, boolean d);
  
  public abstract void setHasMoved(boolean b);
  
  public abstract boolean getHasMoved();
  
  public abstract boolean getIsPawn();
  
  public abstract boolean getIsWhite();
  
  public abstract int getPieceType();
  
  
  
  public void toggleClickedOn(){
    clickedOn = !clickedOn;
  }
  
  public boolean getClickedOn(){
    return clickedOn;
  }
}