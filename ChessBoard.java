/* Last Edited: by ATHER on
 * JAN 04 2017
 */


import java.awt.*; 
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class ChessBoard
{ // 2d array of all the squares, holds what kind of piece is on square
  private int squares [] [];
  // 2d array of the colours
  private int squareColour [] [];
  // hold the x and y values of the whole board, starts from at top left
  private int x;
  private int y;
  // checks if the top are white pieces
  
  public ChessBoard (int x, int y, boolean topWhite)
  {
    this.x=x;
    this.y=y;
    squares = new int [8][8];
    squareColour = new int [8][8];
    boolean whiteSquare = true;
    
    // initializes 2d array. 0 in the array means it is an empty square, 1 means it has a white piece, and 2 means it has a black piece
    for (int j = 0; j < 8 ; j++){
      for (int i = 0; i < 8 ; i++){
        // initializes based on where the pieces start and if the top army is white or black
        if ((i<2 && topWhite)|| (i>5 && !topWhite))
          squares [j][i] = 1;
        if((i <2 && !topWhite)|| (i>5 && topWhite))
          squares [j][i] = 2;
        if (i >= 2 && i <= 5)
          squares[j][i] = 0;
        // initializes the colours of each square on board, 1 is white 2 is black
        if ((j%2 != 0 && i%2 == 0)|| (j%2 == 0 && i%2 != 0)){
          squareColour[j][i] = 2;
          whiteSquare = false;}
        else{
          squareColour[j][i] = 1;
          whiteSquare = true;
        }
      }
    }
  }
  
  public void changeAvailability (int x1, int y1, int x2, int y2){
    int temp = squares [x1][y1];
    squares[x1][y1] = 0;
    squares[x2][y2] = temp;
  }
  
  public void highlightPossibleMovement(List x, Graphics2D g2d, boolean highlighting){
    if (highlighting){
      for (int i =0; i < x.length(); i++)
      {
        BufferedImage highlightSquare = null;
        try {
          highlightSquare = ImageIO.read(new File("Highlight Square.png"));
        } catch (IOException e) {
        }
        if (highlighting)
        g2d.drawImage(highlightSquare, x.returnValue(i)[0]*50+this.x,x.returnValue(i)[1]*50+this.y, null);
      }
    }
  }
  
  public void setAvailability(int x, int y, int value){
	  squares[x][y] = value;
  }
  
  public int availability(int x, int y){
    return squares[x][y];
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public int getXSpace(int mX){
    return (int) Math.floor(((double)(mX) - x)/50.0);
  }
  
  public int getYSpace(int mY){
    return (int) Math.floor(((double)(mY) - y)/50.0);
  }
  
  public void paint (Graphics2D g2d)
  {
    for (int j = y; j < y+400; j += 50){
      for (int i = x; i < x+400; i += 50){
        if (squareColour[(j-y)/50][(i-x)/50] == 1)
          g2d.setColor(Color.WHITE);
        else
          g2d.setColor(Color.GRAY);
        g2d.fillRect(i,j,50,50);
      }
    }
    g2d.setColor(Color.BLACK);
    g2d.drawRect(x,y,400,400);
  }
}