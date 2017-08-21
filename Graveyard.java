import java.awt.*; 
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Graveyard{
  
  private int X;
  private int Y;
  private int width = 400;
  private int height = 200;
  private int teamNo;
  private boolean[][] isWhite;;
  private int counterX;
  private int counterY;
  
  public Graveyard (int x, int y, int teamNo)
  {
    this.X = x;
    this.Y = y;
    this.counterX = x;
    this.counterY = y;
    this.teamNo = teamNo;
    isWhite = new boolean[8][4];
  }
  
  public void paint (Graphics2D g2d)
  {
   Color lightBlue = new Color(52,152,219);
Color lightRed = new Color(231,76,60);
    if (teamNo == 2)
    g2d.setColor(lightBlue);
    else
     g2d.setColor(lightRed); 
    g2d.fillRect(X, Y, width, height);
    
    g2d.drawRect(X, Y, width, height);
    g2d.setFont(new Font("Times New Roman", Font.BOLD, 30)); 

    String str = "Team " + teamNo + "'s Graveyard";
    g2d.drawString(str, X , Y - 10); 
    g2d.setColor(Color.BLACK);
    g2d.drawRect(X, Y, width, height);
    
  }
  
  public int[] returnSpace(boolean isPieceWhite){ //1 means it has a white piece, and 2 means it has a black piece
    isWhite[(counterX - X)/50][(counterY - Y)/50] = isPieceWhite;
    
    int[] space = {counterX, counterY};
    counterX+=50;
    
    if (counterX > X + 350){
      counterY+=50;
      counterX = X;
    }
    return space;
  }
  
  
  
  
}