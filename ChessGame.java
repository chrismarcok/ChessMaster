/* Chess Game by Ather Hassan, Chris Marcok, Sarin Shrestha
 * 
 * Last Edited 1/19/2017
 * 
 * ACS STUDIOS
 * 
 * ICS4U1 ISU
 * 
 * For Mr Donald
 * 
 */

import sun.audio.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class ChessGame extends JPanel {
  private static final long serialVersionUID = 0; // stop compiler warning
  
  private Piece[] b1BlackPieces = new Piece[16];
  private Piece[] b1WhitePieces = new Piece[16];
  private Piece[] b2BlackPieces = new Piece[16];
  private Piece[] b2WhitePieces = new Piece[16];
  private int[] mouseCoords = new int[2];
  private int[] specificMouseCoords = new int[2]; // array of {x,y} ,
  private int [] graveYardPieceCoords = new int [2];
  // coordinates of
  // where the mouse has clicked
  private int turn = 0; // (BOARD 1) 0 is white (bot), 1 is black top) (BOARD
  // 2) 2 is white , 3 is black (bot)
  
  private ChessBoard board1 = new ChessBoard(30, 30, false);
  private ChessBoard board2 = new ChessBoard(630, 30, false);
  
  private boolean highlighting = false;
  private List possibleMovements = new List();
  private List enemyPossibleMovements = new List();
  private boolean b1InCheckMate = false;
  private boolean b2InCheckMate = false;
  private boolean mousePressedAgain= false;
  private boolean whitewin1 = false;
  private boolean whitewin2 = false;
  private boolean blackwin1 = false;
  private boolean blackwin2 = false;
  
  
  // private boolean musicNotPlaying = true;
  
  private Graveyard grave1 = new Graveyard(30, 530, 1);
  private Graveyard grave2 = new Graveyard(630, 530, 2);
  private boolean pawnInEndzone = false;
  
  public ChessGame() { // constructor
    addMouseListener(new mouseListener());
    b1BlackPieces = createSet(b1BlackPieces, 30, true, false);
    b1WhitePieces = createSet(b1WhitePieces, 30, false, true);
    b2BlackPieces = createSet(b2BlackPieces, board2.getX(), true, false);
    b2WhitePieces = createSet(b2WhitePieces, board2.getX(), false, true);
    mouseCoords[1] = -1;
    mouseCoords[0] = -1;
 
    
    
    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
      }
      
      @Override
      public void keyPressed(KeyEvent e) {
        // example
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          //System.out.println("con grats u can press the space bar");
          
        }
      }
    });
    setFocusable(true);
  }
  
  public static Piece[] createSet(Piece[] pArray, int x, boolean top, boolean isWhite) {
    
    if (top) { // top means that non-pawns are on top, PAWNS ARE ON BOTTOM
      for (int i = 0; i < 16; i++) {
        if (i > 7)
          pArray[i] = new Pawn(x + 50 * (i - 8), 80, isWhite);
        else if (i == 0 || i == 7)
          pArray[i] = new Rook(x + 50 * i, 30, isWhite);
        else if (i == 1 || i == 6)
          pArray[i] = new Knight(x + 50 * i, 30, isWhite);
        else if (i == 2 || i == 5)
          pArray[i] = new Bishop(x + 50 * i, 30, isWhite);
        else if (i == 3)
          pArray[i] = new Queen(x + 50 * i, 30, isWhite);
        else
          pArray[i] = new King(x + 50 * i, 30, isWhite);
      }
    }
    
    else { // pawns are on top, NON PAWNS ON BOTTOM
      for (int i = 0; i < 16; i++) {
        if (i < 8)
          pArray[i] = new Pawn(x + 50 * i, 330, isWhite);
        else if (i == 8 || i == 15)
          pArray[i] = new Rook(x + 50 * (i - 8), 380, isWhite);
        else if (i == 9 || i == 14)
          pArray[i] = new Knight(x + 50 * (i - 8), 380, isWhite);
        else if (i == 10 || i == 13)
          pArray[i] = new Bishop(x + 50 * (i - 8), 380, isWhite);
        else if (i == 11)
          pArray[i] = new Queen(x + 50 * (i - 8), 380, isWhite);
        else
          pArray[i] = new King(x + 50 * (i - 8), 380, isWhite);
      }
    }
    return pArray;
  }
  
  private class mouseListener extends MouseAdapter {
    
    public void mouseClicked(MouseEvent e) { // when you press and release
      // quickly
    }
    
    public void mousePressed(MouseEvent e) {
      // get coordinates of mouse click
      int mx = e.getX();
      int my = e.getY();
      int [] oldMouseCoords = {specificMouseCoords[0],specificMouseCoords[1]};
      specificMouseCoords[0] = ((int) Math.floor(((double) (mx) - 30) / 50.0)) * 50 + 30;
      specificMouseCoords[1] = ((int) Math.floor(((double) (my) - 30) / 50.0)) * 50 + 30;
      if (oldMouseCoords[0] == specificMouseCoords[0] && oldMouseCoords[1] == specificMouseCoords[1])
        mousePressedAgain = true;
      else
        mousePressedAgain = false;
      if ((turn == 0 || turn == 1) && !b1InCheckMate) {
        mouseCoords[0] = board1.getXSpace(mx);
        mouseCoords[1] = board1.getYSpace(my);
      } else if ((turn == 2 || turn == 3) || ((turn == 0 || turn == 1) && b1InCheckMate)) {
        mouseCoords[0] = board2.getXSpace(mx);
        mouseCoords[1] = board2.getYSpace(my);
      }
      if (specificMouseCoords[1] < 480) {
        
        if (turn == 0 && !b1InCheckMate) {
          pieceClickedOn(b1WhitePieces, 1, board1, b1BlackPieces, 2, b2WhitePieces);
        } else if (turn == 1 && !b1InCheckMate) {
          pieceClickedOn(b1BlackPieces, 2, board1, b1WhitePieces, 1, b2BlackPieces);
        } else if (turn == 2 || (turn == 0 && b1InCheckMate)) {
          pieceClickedOn(b2BlackPieces, 2, board2, b2WhitePieces, 1, b1BlackPieces);
        } else if (turn == 3 || (turn == 1 && b1InCheckMate)) {
          pieceClickedOn(b2WhitePieces, 1, board2, b2BlackPieces, 2, b1WhitePieces);
        }
      }

      else {
        if (turn ==0 && !b1InCheckMate)
        for (Piece p : b2WhitePieces) {

          if (specificMouseCoords[0] == p.getCoords()[0] && specificMouseCoords[1] == p.getCoords()[1] && p.getPieceType() != 6 && p.getIsWhite() && p.getCoords()[1] > 480) {
            // if piece is in graveyard and isn't a  king
            for (Piece s : b2WhitePieces) {
              if (s.getClickedOn()) {
                s.toggleClickedOn();
              }
            }
            p.toggleClickedOn();
            replaceGraveyardPiece(b1WhitePieces, 1, board1, b2WhitePieces);
            break;
          }
          
        }
        if (turn == 1 && !b1InCheckMate)
        for (Piece p : b2BlackPieces) {

          if (specificMouseCoords[0] == p.getCoords()[0] && specificMouseCoords[1] == p.getCoords()[1] && p.getPieceType() != 6 && !p.getIsWhite()  && p.getCoords()[1] > 480) {
            // if piece is in graveyard and isn't a  king
            for (Piece s : b2BlackPieces) {
              if (s.getClickedOn()) {
                s.toggleClickedOn();
              }
            }
            p.toggleClickedOn();
            replaceGraveyardPiece(b1BlackPieces, 2, board1, b2BlackPieces);
            break;
          }
        }
        if (turn == 2 || (turn == 0 && b1InCheckMate))
        for (Piece p : b1BlackPieces) {

          if ( specificMouseCoords[0] == p.getCoords()[0] && specificMouseCoords[1] == p.getCoords()[1] && p.getPieceType() != 6 && !p.getIsWhite() && p.getCoords()[1] > 480) {
            // if piece is in graveyard and isn't a king
            for (Piece s : b1BlackPieces) {
              if (s.getClickedOn()) {
                s.toggleClickedOn();
              }
            }
            p.toggleClickedOn();
            replaceGraveyardPiece(b2BlackPieces, 2, board2, b1BlackPieces);
            break;
            
          }
        }
        if (turn == 3 || (turn == 1 && b1InCheckMate))
        for (Piece p : b1WhitePieces) {

          if ( specificMouseCoords[0] == p.getCoords()[0] && specificMouseCoords[1] == p.getCoords()[1] && p.getPieceType() != 6 && p.getIsWhite() && p.getCoords()[1] > 480) {
            for (Piece s : b1WhitePieces) {
              if (s.getClickedOn()) {
                s.toggleClickedOn();
              }
            }
            p.toggleClickedOn();
            replaceGraveyardPiece(b2WhitePieces, 1, board2, b1WhitePieces);
            break;
          }
          
        }
        
      }
    }
    
    public void replaceGraveyardPiece(Piece[] pieceArray, int colour, ChessBoard board, Piece[] enemySet) {
      possibleMovements.deleteList();
      for (Piece z : pieceArray) {
        if (z.getCoords()[1] < 430 && z.getPieceType() != 6) {
          
          possibleMovements.addToFront(board.getXSpace(z.getCoords()[0]), board.getYSpace(z.getCoords()[1]));
          
          
        }
      }
      if (!highlighting)
      highlighting = !highlighting;
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) { // when the mouse enters
      // the area
      
    }
    
    public void mouseExited(MouseEvent e) { // when mouse exits the area
    }
  }
  
  
  
  
  
  public boolean movingGraveYardPiece ( Piece [] sameColourSet){
    for (Piece p : sameColourSet){
      if(p.getClickedOn())
        return true;
    }
    return false;
  }
  public void pieceClickedOn(Piece[] pieceArray, int colour, ChessBoard board, Piece[] enemySet, int enemyColour, Piece [] sameColourSet) {
    // anything when a piece is clicked on. calls compileSquares to compile
    // all locations a piece can go to

    if (!movingGraveYardPiece(sameColourSet)){
      for (Piece p : pieceArray) {

        
        
        
        if (mouseCoords[0] == board.getXSpace(p.getCoords()[0])
              && mouseCoords[1] == board.getYSpace(p.getCoords()[1])) {
          for (Piece s : pieceArray) {
            if (s.getClickedOn()) {
              s.toggleClickedOn();
            }
          }
          p.toggleClickedOn();
          
          if (!p.getIsPawn() && p.getPieceType() != 6)
            compileSquares(p.getPossibleSpaces(board.getX()), colour, enemySet, enemyColour, p, board, pieceArray);
          
          //if p = king
          else if (p.getPieceType() == 6){
            //if king hasnt moved
            if (!p.getHasMoved()){
              boolean queenSideCastlePossible = false; //castle on left side
              boolean kingSideCastlePossible = false; //castle on right side
              boolean UNUSED = false;
              boolean UNUSED2 = false;
              
              int kingXCoord = p.getCoords()[0];
              int kingYSpace = board.getYSpace(p.getCoords()[1]);
              
              if (board.availability(board.getXSpace(kingXCoord - 50), kingYSpace) == 0 && board.availability(board.getXSpace(kingXCoord - 100), kingYSpace) == 0 
                    && board.availability(board.getXSpace(kingXCoord - 150), kingYSpace) == 0 && board.availability(board.getXSpace(kingXCoord - 200), kingYSpace) == colour){
                
                
                for (Piece z : pieceArray){
                  if (z.getPieceType() == 2 && z.getHasMoved() == false && (z.getCoords()[0] == kingXCoord - 200) && (board.getYSpace(z.getCoords()[1]) == kingYSpace)){
                    queenSideCastlePossible = true;

                    break;
                  }
                }
              }
              if (board.availability(board.getXSpace(kingXCoord + 50), kingYSpace) == 0 && board.availability(board.getXSpace(kingXCoord + 100), kingYSpace) == 0 && board.availability(board.getXSpace(kingXCoord + 150), kingYSpace) == colour){
                for (Piece z : pieceArray){
                  if (z.getPieceType()==2){
                  
                    
                    
                  }
                  if (z.getPieceType()==2 && z.getHasMoved()==false && (z.getCoords()[0] == kingXCoord+150) && (board.getYSpace(z.getCoords()[1]) == kingYSpace)){

                    kingSideCastlePossible = true;
                    break;
                  }
                }
              }
              compileSquares(p.getPossibleSpaces(board.getX(), queenSideCastlePossible, kingSideCastlePossible, UNUSED, UNUSED2), colour, enemySet, enemyColour, p, board, pieceArray);

            }
            else{
              compileSquares(p.getPossibleSpaces(board.getX()), colour, enemySet, enemyColour, p, board, pieceArray);
            }
            
          }
          
          else {
            boolean tempLeft = false;
            boolean tempRight = false;
            boolean tempMid = false;
            boolean tempTwoInfront = false;
            if (colour == 1) {
              if (p.getCoords()[1] > 30) {
                if (p.getCoords()[0] > board.getX())
                  tempLeft = board.availability(board.getXSpace(p.getCoords()[0] - 50),
                                                board.getYSpace(p.getCoords()[1] - 50)) == 2;
              
                tempMid = board.availability(board.getXSpace(p.getCoords()[0]),
                                             board.getYSpace(p.getCoords()[1] - 50)) != 0;
                if (!p.getHasMoved())
                  tempTwoInfront = board.availability(board.getXSpace(p.getCoords()[0]),
                                                      board.getYSpace(p.getCoords()[1] - 100)) == 0;
                if (p.getCoords()[0] < board.getX() + 350)
                  tempRight = board.availability(board.getXSpace(p.getCoords()[0] + 50),
                                                 board.getYSpace(p.getCoords()[1] - 50)) == 2;
             
              }
            } else {
              if (p.getCoords()[1] < 380) {
                if (p.getCoords()[0] > board.getX())
                  tempRight = board.availability(board.getXSpace(p.getCoords()[0] - 50),
                                                 board.getYSpace(p.getCoords()[1] + 50)) == 1;
                tempMid = board.availability(board.getXSpace(p.getCoords()[0]),
                                             board.getYSpace(p.getCoords()[1] + 50)) != 0;
                if (!p.getHasMoved())
                  tempTwoInfront = board.availability(board.getXSpace(p.getCoords()[0]),
                                                      board.getYSpace(p.getCoords()[1] + 100)) == 0;
                if (p.getCoords()[0] < board.getX() + 350)
                  tempLeft = board.availability(board.getXSpace(p.getCoords()[0] + 50),
                                                board.getYSpace(p.getCoords()[1] + 50)) == 1;
              }
              
            }
            
            compileSquares(p.getPossibleSpaces(board.getX(), tempLeft, tempMid, tempRight, tempTwoInfront),
                           colour, enemySet, enemyColour, p, board, pieceArray);
          }
          highlighting = !highlighting;
          break;
        }
        
      }
    }
  }
  
  public void doesMoveKillKingMath(Piece[] enemySet, int enemyColour, Piece p, int possibleX, int possibleY,
                                   ChessBoard board) {
    // compile a list of all possible squares the piece can travel to
    
    List temp = new List();
    enemyPossibleMovements.deleteList();
    // holds a set of coordinates
    int[] holder = new int[2];
    int[] originalSpot = { p.getCoords()[0], p.getCoords()[1] };
    int possibleXYAvailability = board.availability(possibleX, possibleY);
    List[] lArray = new List[2];
   
    board.changeAvailability(board.getXSpace(originalSpot[0]), board.getYSpace(originalSpot[1]), possibleX,
                             possibleY);
    
    for (Piece e : enemySet) {
      temp.deleteList();
      // doesnt run if e coords are the coordinates the original piece wants to move to
      if (!(e.getCoords()[0] == (possibleX * 50 + board.getX()) && e.getCoords()[1] == (possibleY * 50 + 30))
            && e.getCoords()[1] <= 380) {
        // checks if the piece is a pawn, and calls the overloaded method if it is
        if (e.getIsPawn()) {
          boolean tempLeft = false;
          boolean tempRight = false;
          boolean tempMid = false;
          boolean tempTwoInfront = false;
          if (enemyColour == 1) {
            if (e.getCoords()[1] > 30 && e.getCoords()[1] < 380) {
              if (e.getCoords()[0] > board.getX()) {

                tempLeft = board.availability(board.getXSpace(e.getCoords()[0] - 50),
                                              board.getYSpace(e.getCoords()[1] - 50)) == 2;
              }
              tempMid = board.availability(board.getXSpace(e.getCoords()[0]),
                                           board.getYSpace(e.getCoords()[1] - 50)) != 0;
              if (!e.getHasMoved())
                tempTwoInfront = board.availability(board.getXSpace(e.getCoords()[0]),
                                                    board.getYSpace(e.getCoords()[1] - 100)) == 0;
              if (e.getCoords()[0] < board.getX() + 350)
                tempRight = board.availability(board.getXSpace(e.getCoords()[0] + 50),
                                               board.getYSpace(e.getCoords()[1] - 50)) == 2;
            }
          } else {
            if (e.getCoords()[1] < 380) {
              if (e.getCoords()[0] > board.getX())
                tempRight = board.availability(board.getXSpace(e.getCoords()[0] - 50),
                                               board.getYSpace(e.getCoords()[1] + 50)) == 1;
              tempMid = board.availability(board.getXSpace(e.getCoords()[0]),
                                           board.getYSpace(e.getCoords()[1] + 50)) != 0;
              
              if (!e.getHasMoved())
                tempTwoInfront = board.availability(board.getXSpace(e.getCoords()[0]),
                                                    board.getYSpace(e.getCoords()[1] + 100)) == 0;
              if (e.getCoords()[0] < board.getX() + 350)
                tempLeft = board.availability(board.getXSpace(e.getCoords()[0] + 50),
                                              board.getYSpace(e.getCoords()[1] + 50)) == 1;
            }
            
          }
          lArray = e.getPossibleSpaces(board.getX(), tempLeft, tempMid, tempRight, tempTwoInfront);
        } else {
          lArray = e.getPossibleSpaces(board.getX());
        }
        // travels through the array
        for (int i = 0; i < lArray.length; i++) {
          // travels through the list
          
          for (int y = lArray[i].length() - 1; y >= 0; y--) {
            
            holder = lArray[i].returnValue(y);
            // converts the coordinates from the list into board space coordinates
            holder[0] = board.getXSpace(holder[0]);
            holder[1] = board.getYSpace(holder[1]);
           
            if (!(holder[0] > 7 || holder[1] > 7 || holder[0] < 0 || holder[1] < 0)) {
              
              // deletes invalid parts of the list
             
              if (board.availability(holder[0], holder[1]) != 0) {
                if (board.availability(holder[0], holder[1]) == enemyColour) {
                  lArray[i].deleteStuff(holder[0], holder[1], true);
                  
                  break;
                } else {
                  lArray[i].deleteStuff(holder[0], holder[1], false);
                  
                  if (!enemyPossibleMovements.isAPartOfList(holder))
                    temp.addToFront(holder[0], holder[1]);
                  break;
                  
                }
              } else {
                
                if (!enemyPossibleMovements.isAPartOfList(holder))
                  temp.addToFront(holder[0], holder[1]);
                
              }
              
            }
          }
        }
      }

      enemyPossibleMovements.addListToFront(temp);
    }
    board.changeAvailability(possibleX, possibleY, board.getXSpace(originalSpot[0]),
                             board.getYSpace(originalSpot[1]));
    board.setAvailability(possibleX, possibleY, possibleXYAvailability);
  
    
  }
  
  public void compileSquares(List[] lArray, int colour, Piece[] enemySet, int enemyColour, Piece p, ChessBoard board,
                             Piece[] set) {
    possibleMovements.deleteList();
  
    int[] kingCoords = new int[2];
    // the white king is the 13th piece in its array, black king is the 5th
    if (colour == 1)
      kingCoords = set[12].getCoords();
    else
      kingCoords = set[4].getCoords();
    kingCoords[0] = board.getXSpace(kingCoords[0]);
    kingCoords[1] = board.getYSpace(kingCoords[1]);
  
    List temp = new List();
    // holds a set of coordinates
    int[] holder = new int[2];
    
    // travels through the array
    for (int i = 0; i < lArray.length; i++) {
      // travels through the list
      for (int y = lArray[i].length() - 1; y >= 0; y--) {
        holder = lArray[i].returnValue(y);
        // converts the coordinates from the list into board space coordinates
        holder[0] = board.getXSpace(holder[0]);
        holder[1] = board.getYSpace(holder[1]);
   
        
        // deletes invalid parts of the list
        if (holder[0] < 8 && holder[1] < 8) {
          if (board.availability(holder[0], holder[1]) != 0) {
            // so pawns cant eat vertically
            if (board.availability(holder[0], holder[1]) == colour
                  || (p.getIsPawn() && p.getCoords()[0] == holder[0])) {
              lArray[i].deleteStuff(holder[0], holder[1], true);
              break;
            } else {
              lArray[i].deleteStuff(holder[0], holder[1], false);
              // Compiles a list of all possible enemy movements
              doesMoveKillKingMath(enemySet, enemyColour, p, holder[0], holder[1], board);
              // checks if it is the king moving
              if (board.getXSpace(p.getCoords()[0]) == kingCoords[0]
                    && board.getYSpace(p.getCoords()[1]) == kingCoords[1]) {
                if (!enemyPossibleMovements.isAPartOfList(holder))
                  temp.addToFront(holder[0], holder[1]);
                break;
              } else {
                
                if (!enemyPossibleMovements.isAPartOfList(kingCoords))
                  temp.addToFront(holder[0], holder[1]);
                break;
              }
            }
          } else {
            doesMoveKillKingMath(enemySet, enemyColour, p, holder[0], holder[1], board);

            if (board.getXSpace(p.getCoords()[0]) == kingCoords[0]
                  && board.getYSpace(p.getCoords()[1]) == kingCoords[1]) {
              
              if (!enemyPossibleMovements.isAPartOfList(holder)) {
                temp.addToFront(holder[0], holder[1]);
              }
            } else {
              
              if (!enemyPossibleMovements.isAPartOfList(kingCoords))
                temp.addToFront(holder[0], holder[1]);
              
            }
          }
        }
      }
    }
    possibleMovements = temp;
  }
  
  public boolean isMoveValid() { // check if where the mouse clicked is a
    // valid spot to move the Piece
    
    for (int i = 0; i < possibleMovements.length(); i++) {
      
      if (mouseCoords[0] == possibleMovements.returnValue(i)[0]
            && mouseCoords[1] == possibleMovements.returnValue(i)[1])
        return true;
    }
    return false;
  }
  public void castle(Piece[] pieceArray, int i, ChessBoard board, int colour){
    if (specificMouseCoords[0] > pieceArray[i].getCoords()[0]) //KINGSIDE
    for (Piece p : pieceArray){
      if (p.getCoords()[0] > board.getX() && p.getPieceType() == 2){
        //change original availability to 0
        board.setAvailability(board.getXSpace(p.getCoords()[0]), board.getYSpace(p.getCoords()[1]), 0);
        //change availability of the destination to colour
        board.setAvailability(board.getXSpace(p.getCoords()[0] - 100), board.getYSpace(p.getCoords()[1]), colour);
        
        
        //move the rook to its new spot
        p.move(p.getCoords()[0] - 100, p.getCoords()[1]); 
      }
    }
    else{ // QUEENSIDE
      for (Piece p : pieceArray){
        if (p.getCoords()[0] == board.getX() && p.getPieceType() == 2){
          
          //change original availability to 0
          board.setAvailability(board.getXSpace(p.getCoords()[0]), board.getYSpace(p.getCoords()[1]), 0);
          //change availability of the destination to colour
          board.setAvailability(board.getXSpace(p.getCoords()[0] + 150), board.getYSpace(p.getCoords()[1]), colour);
          
          //move the rook to its new spot
          p.move(p.getCoords()[0] + 150, p.getCoords()[1]); 
          
        }
      }
    }
  }
  
  
  
  public void move() {
    // chessboard 1
    if (turn == 0 && !b1InCheckMate) {
      for (int i = 0; i < b1WhitePieces.length; i++) {
        
        if (b1WhitePieces[i].getClickedOn() && isMoveValid()) {
          b1WhitePieces[i].toggleClickedOn();
          
          board1.changeAvailability(board1.getXSpace(b1WhitePieces[i].getCoords()[0]), board1.getYSpace(b1WhitePieces[i].getCoords()[1]), mouseCoords[0], mouseCoords[1]);
          
          for (Piece s : b1BlackPieces) {
            if (board1.getXSpace(s.getCoords()[0]) == mouseCoords[0]
                  && board1.getYSpace(s.getCoords()[1]) == mouseCoords[1]) {
              int[] temp = grave2.returnSpace(false);
              s.move(temp[0], temp[1]);
               playSoundEffect();
            }
          }
          
          
          System.out.println(Math.abs(b1WhitePieces[i].getCoords()[0]-specificMouseCoords[0]));
          if (b1WhitePieces[i].getPieceType() == 6 && Math.abs(b1WhitePieces[i].getCoords()[0]-specificMouseCoords[0]) == 100){
            castle(b1WhitePieces, i, board1, 1);
          }
          
          b1WhitePieces[i].move(mouseCoords[0] * 50 + board1.getX(), mouseCoords[1] * 50 + board1.getY());
          
          // pawn in endzone
          if (b1WhitePieces[i].getIsPawn() && ((mouseCoords[1] * 50 + board1.getY() == 30)
                                                 || mouseCoords[1] * 50 + board1.getY() == 380)) {
            int[] tempMouseHolder = { mouseCoords[0], mouseCoords[1] };
            
            b1WhitePieces[i] = chooseNewPiece(true, b1WhitePieces[i].getCoords());
          }
          highlighting = !highlighting;
          b1WhitePieces[i].setHasMoved(true);
          

          if (checkMate(b1BlackPieces, board1, 2, b1WhitePieces, 1)) {
            b1InCheckMate = checkMate(b1BlackPieces, board1, 2, b1WhitePieces, 1);
            for (Piece g : b1BlackPieces) {
              
              if ((g.getCoords()[1] < 430)) {
                int[] temp = grave2.returnSpace(true);
                g.move(temp[0], temp[1]);
                whitewin1 = true;
              }
            }
            turn = 0;
            break;
          }
          
          turn++;
        }
        else
          swapGraveyardPieces(i, board1, b2WhitePieces, b1WhitePieces);
      }
    }
    
    if (turn == 1 && !b1InCheckMate) {
      for (int i = 0; i < b1BlackPieces.length; i++) {
        
        if (b1BlackPieces[i].getClickedOn() && isMoveValid()) {

          b1BlackPieces[i].toggleClickedOn();
          board1.changeAvailability(board1.getXSpace(b1BlackPieces[i].getCoords()[0]),
                                    board1.getYSpace(b1BlackPieces[i].getCoords()[1]), mouseCoords[0], mouseCoords[1]);
          for (Piece s : b1WhitePieces) {
            if (board1.getXSpace(s.getCoords()[0]) == mouseCoords[0]
                  && board1.getYSpace(s.getCoords()[1]) == mouseCoords[1]) {
              int[] temp = grave1.returnSpace(true);
              s.move(temp[0], temp[1]);
               playSoundEffect();
            }
          }
          
          if (b1BlackPieces[i].getPieceType() == 6 && Math.abs(b1BlackPieces[i].getCoords()[0]-specificMouseCoords[0]) == 100){
            castle(b1BlackPieces, i, board1, 2);
          }
          
          b1BlackPieces[i].move(mouseCoords[0] * 50 + board1.getX(), mouseCoords[1] * 50 + board1.getY());
          
          // pawn in endzone
          if (b1BlackPieces[i].getIsPawn() && ((mouseCoords[1] * 50 + board1.getY() == 30)
                                                 || mouseCoords[1] * 50 + board1.getY() == 380)) {
            b1BlackPieces[i] = chooseNewPiece(false, b1BlackPieces[i].getCoords());
          }
          
          highlighting = !highlighting;
          b1BlackPieces[i].setHasMoved(true);
          
          if (checkMate(b1WhitePieces, board1, 1, b1BlackPieces, 2)) {
            b1InCheckMate = checkMate(b1WhitePieces, board1, 1, b1BlackPieces, 2);
            for (Piece g : b1WhitePieces) {
              
              if ((g.getCoords()[1] < 430)) {
                int[] temp = grave1.returnSpace(true);
                g.move(temp[0], temp[1]);
                blackwin1 = true;
              }
            }
            turn = 0;
            break;
          }
          
          turn++;
          
        }
        else
          swapGraveyardPieces(i, board1, b2BlackPieces, b1BlackPieces);
        
      }
    }
    
    
    // board 2
    if (turn == 2 || (turn == 0 && b1InCheckMate)) {
      for (int i = 0; i < b2BlackPieces.length; i++) {
        
        if (b2BlackPieces[i].getClickedOn() && isMoveValid()) {

          b2BlackPieces[i].toggleClickedOn();
          board2.changeAvailability(board2.getXSpace(b2BlackPieces[i].getCoords()[0]),
                                    board2.getYSpace(b2BlackPieces[i].getCoords()[1]), mouseCoords[0], mouseCoords[1]);
          for (Piece s : b2WhitePieces) {
            if (board2.getXSpace(s.getCoords()[0]) == mouseCoords[0]
                  && board2.getYSpace(s.getCoords()[1]) == mouseCoords[1]) {
              int[] temp = grave2.returnSpace(true);
              s.move(temp[0], temp[1]);
               playSoundEffect();
            }
          }
          
          if (b2BlackPieces[i].getPieceType() == 6 && Math.abs(b2BlackPieces[i].getCoords()[0]-specificMouseCoords[0]) == 100){
            castle(b2BlackPieces, i, board2, 2);
          }
          
          b2BlackPieces[i].move(mouseCoords[0] * 50 + board2.getX(), mouseCoords[1] * 50 + board2.getY());
          
          // pawn in endzone
          if (b2BlackPieces[i].getIsPawn() && ((mouseCoords[1] * 50 + board2.getY() == 30)
                                                 || mouseCoords[1] * 50 + board2.getY() == 380)) {
            b2BlackPieces[i] = chooseNewPiece(false, b2BlackPieces[i].getCoords());
          }
          
          highlighting = !highlighting;
          b2BlackPieces[i].setHasMoved(true);
          
          if (checkMate(b2WhitePieces, board2, 1, b2BlackPieces, 2)) {
            b2InCheckMate = checkMate(b2WhitePieces, board2, 1, b2BlackPieces, 2);
            for (Piece g : b2WhitePieces) {
              
              if ((g.getCoords()[1] < 430)) {
                int[] temp = grave2.returnSpace(true);
                g.move(temp[0], temp[1]);
                blackwin2 = true;
              }
            }
            turn = 0;
            break;
          }
          
          turn++;
        }
        else
          swapGraveyardPieces(i, board2, b1BlackPieces, b2BlackPieces);
      }
    }
    if (turn == 3 || (turn == 1 && b1InCheckMate)) {
      for (int i = 0; i < b1WhitePieces.length; i++) {
        
        if (b2WhitePieces[i].getClickedOn() && isMoveValid()) {
          b2WhitePieces[i].toggleClickedOn();
          board2.changeAvailability(board2.getXSpace(b2WhitePieces[i].getCoords()[0]),
                                    board2.getYSpace(b2WhitePieces[i].getCoords()[1]), mouseCoords[0], mouseCoords[1]);
          
          for (Piece s : b2BlackPieces) {
            if (board2.getXSpace(s.getCoords()[0]) == mouseCoords[0]
                  && board2.getYSpace(s.getCoords()[1]) == mouseCoords[1]) {
              int[] temp = grave1.returnSpace(false);
              s.move(temp[0], temp[1]);
               playSoundEffect();
            }
          }
          
          if (b2WhitePieces[i].getPieceType() == 6 && Math.abs(b2WhitePieces[i].getCoords()[0]-specificMouseCoords[0]) == 100){
            castle(b2WhitePieces, i, board2, 1);
          }
          
          
          b2WhitePieces[i].move(mouseCoords[0] * 50 + board2.getX(), mouseCoords[1] * 50 + board2.getY());
          
          // pawn in endzone
          if (b2WhitePieces[i].getIsPawn() && ((mouseCoords[1] * 50 + board2.getY() == 30)
                                                 || mouseCoords[1] * 50 + board2.getY() == 380)) {
            int[] tempMouseHolder = { mouseCoords[0], mouseCoords[1] };
            
            b2WhitePieces[i] = chooseNewPiece(true, b2WhitePieces[i].getCoords());
          }
          highlighting = !highlighting;
          b2WhitePieces[i].setHasMoved(true);

          if (checkMate(b2BlackPieces, board2, 2, b2WhitePieces, 1)) {
            b2InCheckMate = checkMate(b2BlackPieces, board2, 2, b2WhitePieces, 1);
            for (Piece g : b2BlackPieces) {
              
              if ((g.getCoords()[1] < 430)) {
                int[] temp = grave1.returnSpace(true);
                g.move(temp[0], temp[1]);
                whitewin2 = true;
              }
            }
            turn = 0;
            break;
          }
          
          turn++;
        }
        else
          swapGraveyardPieces(i, board2, b1WhitePieces, b2WhitePieces);
        
      }
    }
    if (turn > 3 || (turn > 1 && (b1InCheckMate || b2InCheckMate)))
      turn = 0;
  }
  
  public void swapGraveyardPieces(int i, ChessBoard board, Piece[] sameColorSet, Piece[] yourSet) {
    if (sameColorSet[i].getClickedOn()){
      for (Piece v : yourSet)
        if (v.getClickedOn())
        v.toggleClickedOn();
     
      if (mousePressedAgain && mouseCoords[0]== board.getXSpace(graveYardPieceCoords[0]) && (mouseCoords[1]== board.getYSpace(graveYardPieceCoords[1])))
      {
        
        possibleMovements.deleteList();
        sameColorSet[i].toggleClickedOn();
        if (highlighting)
        highlighting = false;
        
      }
       graveYardPieceCoords = sameColorSet[i].getCoords();
      if (mouseCoords[0]!= board.getXSpace(graveYardPieceCoords[0]) || (mouseCoords[1]!= board.getYSpace(graveYardPieceCoords[1])) ) {
        
        
        if (isMoveValid()) {
          for (int y = 0; y < yourSet.length; y++) {
            if (board.getXSpace(yourSet[y].getCoords()[0]) == mouseCoords[0] && board.getYSpace(yourSet[y].getCoords()[1]) == mouseCoords[1]) {
              int[] tempCoords = { sameColorSet[i].getCoords()[0], sameColorSet[i].getCoords()[1] };
              Piece tempPiece = sameColorSet[i];
              sameColorSet[i].move(yourSet[y].getCoords()[0], yourSet[y].getCoords()[1]);
              yourSet[y].move(tempCoords[0], tempCoords[1]);
              sameColorSet[i] = yourSet[y];
              yourSet[y] = tempPiece;
              yourSet[y].toggleClickedOn();
              turn++; 
              break;
            }
          }
          
          highlighting = !highlighting;
          sameColorSet[i].toggleClickedOn();
          possibleMovements.deleteList();
        }
        
        
        
        
        else{
          possibleMovements.deleteList();
          sameColorSet[i].toggleClickedOn();
          highlighting = !highlighting;
        }
      }
      
    }
  }
  
  
  private Piece chooseNewPiece(boolean isWhite, int[] coords) {
    pawnInEndzone = true;
    Piece rook = new Rook(coords[0], coords[1], isWhite);
    Piece knight = new Knight(coords[0], coords[1], isWhite);
    Piece bishop = new Bishop(coords[0], coords[1], isWhite);
    Piece queen = new Queen(coords[0], coords[1], isWhite);
    int mouseX = specificMouseCoords[0];
    int mouseY = specificMouseCoords[1];
    int counter = 0;
    // runs loop until the mouseclick is within certain bounds
    while (!(mouseX >= 430 && mouseX <= 630 && mouseY >= 180 && mouseY <= 230) && counter == 0) {
      repaint();
      mouseX = specificMouseCoords[0];
      mouseY = specificMouseCoords[1];
      if (mouseX == 430 && mouseY == 180) {
        pawnInEndzone = false;
        return rook;
      } else if (mouseX == 480 && mouseY == 180) {
        pawnInEndzone = false;
        return knight;
      } else if (mouseX == 530 && mouseY == 180) {
        pawnInEndzone = false;
        return bishop;
      } else if (mouseX == 580 && mouseY == 180) {
        pawnInEndzone = false;
        return queen;
      }
      if ((mouseX >= 430 && mouseX <= 630 && mouseY >= 180 && mouseY <= 230))
        counter++;
    }
    pawnInEndzone = false;
    return queen;
  }
  
  public boolean checkMate(Piece[] pieces, ChessBoard board, int colour, Piece[] enemySet, int enemyColour) {
    for (Piece p : pieces) {
      if (p.getCoords()[1] < 430) {
        if (!p.getIsPawn())
          compileSquares(p.getPossibleSpaces(board.getX()), colour, enemySet, enemyColour, p, board, pieces);
        else {
          boolean tempLeft = false;
          boolean tempRight = false;
          boolean tempMid = false;
          boolean tempTwoInfront = false;
          if (colour == 1) {
            if (p.getCoords()[1] > 30) {
              if (p.getCoords()[0] > board.getX() ) {
               
                tempLeft = board.availability(board.getXSpace(p.getCoords()[0] - 50),
                                              board.getYSpace(p.getCoords()[1] - 50)) == 2;
                
              }
              tempMid = board.availability(board.getXSpace(p.getCoords()[0]),
                                           board.getYSpace(p.getCoords()[1] - 50)) != 0;
              if (!p.getHasMoved() && p.getCoords()[0] > board.getX()
                    && p.getCoords()[0] < board.getX() + 350)
                tempTwoInfront = board.availability(board.getXSpace(p.getCoords()[0]),
                                                    board.getYSpace(p.getCoords()[1] - 100)) == 0;
              if (p.getCoords()[0] < board.getX() + 350 && p.getCoords()[0] > board.getX())
                tempRight = board.availability(board.getXSpace(p.getCoords()[0] + 50),
                                               board.getYSpace(p.getCoords()[1] - 50)) == 2;
            }
          } else {
            if (p.getCoords()[1] < 380) {
              if (p.getCoords()[0] > board.getX())
                tempRight = board.availability(board.getXSpace(p.getCoords()[0] - 50),
                                               board.getYSpace(p.getCoords()[1] + 50)) == 1;
              tempMid = board.availability(board.getXSpace(p.getCoords()[0]),
                                           board.getYSpace(p.getCoords()[1] + 50)) != 0;
              if (!p.getHasMoved())
                tempTwoInfront = board.availability(board.getXSpace(p.getCoords()[0]),
                                                    board.getYSpace(p.getCoords()[1] + 100)) == 0;
              if (p.getCoords()[0] < board.getX() + 350)
                tempLeft = board.availability(board.getXSpace(p.getCoords()[0] + 50),
                                              board.getYSpace(p.getCoords()[1] + 50)) == 1;
            }
            
          }
          
          compileSquares(p.getPossibleSpaces(board.getX(), tempLeft, tempMid, tempRight, tempTwoInfront),
                         colour, enemySet, enemyColour, p, board, pieces);
        }
        if (possibleMovements.length() > 0) {
          possibleMovements.deleteList();
          return false;
        }
        
        else {
          possibleMovements.deleteList();
          
        }
        
      }
    }
    return true;
  }
  public static void playMusic() {
    AudioPlayer MGP = AudioPlayer.player;
    AudioStream BGM;
    AudioData MD;
    
    ContinuousAudioDataStream loop = null;
    
    try {
      InputStream test = new FileInputStream("Billy Jean.wav");
      BGM = new AudioStream(test);
      AudioPlayer.player.start(BGM);
      
    } catch (FileNotFoundException e) {
      System.out.print(e.toString());
    } catch (IOException error) {
      System.out.print(error.toString());
    }
    MGP.start(loop);
  }
  
  public static void playSoundEffect() {
    AudioPlayer MGP = AudioPlayer.player;
    AudioStream BGM;
    AudioData MD;
    
    ContinuousAudioDataStream loop = null;
    
    try {
      InputStream test = new FileInputStream("eating.wav");
      BGM = new AudioStream(test);
      AudioPlayer.player.start(BGM);
      
    } catch (FileNotFoundException e) {
      System.out.print(e.toString());
    } catch (IOException error) {
      System.out.print(error.toString());
    }
    MGP.start(loop);
  }
  
  public void paint(Graphics g) {
       BufferedImage win = null;
    try {
      win = ImageIO.read(new File("winscreen.png"));
    } catch (IOException e) {
    }
BufferedImage screen = null;
    try {
      screen = ImageIO.read(new File("bg.jpg"));
    } catch (IOException e) {
    }
    
    Graphics2D g2d = (Graphics2D) g;
    super.paint(g);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
     g.drawImage(screen,0,0,null);
    
    // paint boards
    board1.paint(g2d);
    if ((turn == 0 || turn == 1) && !b1InCheckMate)
      board1.highlightPossibleMovement(possibleMovements, g2d, highlighting);
    board2.paint(g2d);
    if (turn == 2 || turn == 3 || ((turn == 0 || turn == 1) && b1InCheckMate))
      board2.highlightPossibleMovement(possibleMovements, g2d, highlighting);
    
    // current turn
    g2d.setFont(new Font("Times New Roman", Font.PLAIN, 30));
    g2d.setColor(Color.BLUE);
    String str = "Current Turn:";
    g2d.drawString(str, 450, 200);
    BufferedImage arrow = null;
    try {
      arrow = ImageIO.read(new File("arrow.png"));
    } catch (IOException e) {
    }
    if (turn == 0 && !b1InCheckMate) {
      str = "Board 1 White";
      g2d.setColor(Color.BLACK);
      g2d.drawImage(arrow, 560, 425, -arrow.getWidth() / 5, -arrow.getHeight() / 5, null);
    } else if (turn == 1 && !b1InCheckMate) {
      str = "Board 1 Black";
      g2d.setColor(Color.BLACK);
      g2d.drawImage(arrow, 560, 125, -arrow.getWidth() / 5, -arrow.getHeight() / 5, null);
    } else if (turn == 2 || (turn == 0 && b1InCheckMate)) {
      str = "Board 2 Black";
      g2d.setColor(Color.BLACK);
      g2d.drawImage(arrow, 500, 40, arrow.getWidth() / 5, arrow.getHeight() / 5, null);
    } else if (turn == 3 || (turn == 1 && b1InCheckMate)) {
      str = "Board 2 White";
      g2d.setColor(Color.BLACK);
      g2d.drawImage(arrow, 500, 325, arrow.getWidth() / 5, arrow.getHeight() / 5, null);
    }
    
    g2d.drawString(str, 450, 250);
    g2d.setFont(new Font("Times New Roman", Font.BOLD, 25));
    g2d.setColor(Color.RED);
    g2d.drawString("Team 1", 190, 25);
    g2d.drawString("Team 1", 790, 455);
    g2d.setColor(Color.BLUE);
    g2d.drawString("Team 2", 790, 25);
    g2d.drawString("Team 2", 190, 455);
    
    
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Times New Roman", Font.PLAIN, 30));
    // graveyards
    grave1.paint(g2d);
    grave2.paint(g2d);
    
    
    BufferedImage thinking = null;
    try {
      thinking = ImageIO.read(new File("thinking.png"));
      
    } catch (IOException e) {
    }
    //g2d.drawImage(thinking, 430, 500, null);
    // Pieces
    for (Piece p : b1BlackPieces) {
      p.paint(g2d);
    }
    
    for (Piece p : b1WhitePieces) {
      p.paint(g2d);
    }
    for (Piece p : b2BlackPieces) {
      p.paint(g2d);
    }
    
    for (Piece p : b2WhitePieces) {
      p.paint(g2d);
    }
     if(whitewin1 == true)
    {
      
      String name = "CHECKMATE: WHITE WINS";
      g.setFont(new Font(name, 5, 30));
      g.setColor(Color.WHITE);
      g.drawString(name, 40, 485);
      
    }
    if(whitewin2 == true)
    {
      
      String name = "CHECKMATE: WHITE WINS";
      g.setFont(new Font(name, 5, 30));
      g.setColor(Color.WHITE);
      g.drawString(name, 635, 485);
    }
    if(blackwin1 == true)
    {
      
      String name = "CHECKMATE: BLACK WINS";
      g.setFont(new Font(name, 5, 30));
      g.setColor(Color.WHITE);
      g.drawString(name, 40, 485);
    }
    if(blackwin2 == true)
    {
      
      String name = "CHECKMATE: BLACK WINS";
      g.setFont(new Font(name, 5, 30));
      g.setColor(Color.WHITE);
      g.drawString(name, 635, 485);
    }
    

    
    if (pawnInEndzone) {
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.fillRect(400, 150, 250, 100);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(400, 150, 250, 100);
      g2d.drawString("Choose a New Piece", 402, 180);
      
      BufferedImage whiteKnight = null;
      BufferedImage whiteRook = null;
      BufferedImage whiteBishop = null;
      BufferedImage whiteQueen = null;
      BufferedImage blackKnight = null;
      BufferedImage blackRook = null;
      BufferedImage blackBishop = null;
      BufferedImage blackQueen = null;
      
      try {
        whiteRook = ImageIO.read(new File("White Rook.png"));
        whiteKnight = ImageIO.read(new File("White Knight.png"));
        whiteBishop = ImageIO.read(new File("White Bishop.png"));
        whiteQueen = ImageIO.read(new File("White Queen.png"));
        blackRook = ImageIO.read(new File("Black Rook.png"));
        blackKnight = ImageIO.read(new File("Black Knight.png"));
        blackBishop = ImageIO.read(new File("Black Bishop.png"));
        blackQueen = ImageIO.read(new File("Black Queen.png"));
      } catch (IOException e) {
      }
      if (turn == 0) {
        g2d.drawImage(whiteRook, 430, 180, null);
        g2d.drawImage(whiteKnight, 480, 180, null);
        g2d.drawImage(whiteBishop, 530, 180, null);
        g2d.drawImage(whiteQueen, 580, 180, null);
      } else {
        g2d.drawImage(blackRook, 430, 180, null);
        g2d.drawImage(blackKnight, 480, 180, null);
        g2d.drawImage(blackBishop, 530, 180, null);
        g2d.drawImage(blackQueen, 580, 180, null);
      }
      
    }
    if(whitewin1 && blackwin2)
    {
      
      g.drawImage(win,0,0,null);
      
      String name = "GAME OVER: TEAM 2 WINS";
      g.setFont(new Font(name, 60, 60));
      g.setColor(Color.WHITE);
      g.drawString(name, 150, 300);
    }
    if(whitewin2 && blackwin1)
    {
      
      g.drawImage(win,0,0,null);
      String name = "GAME OVER: TEAM 1 WINS";
      g.setFont(new Font(name, 60, 60));
      g.setColor(Color.WHITE);
      g.drawString(name, 150, 300);
    }
    if(whitewin1 && whitewin2)
    {
      
      g.drawImage(win,0,0,null);
      String name = "GAME OVER: TIE";
      g.setFont(new Font(name, 60, 60));
      g.setColor(Color.WHITE);
      g.drawString(name, 280, 300);
    }
    if(blackwin1 && blackwin2)
    {
      
      g.drawImage(win,0,0,null);
      
      String name = "GAME OVER: TIE";
      g.setFont(new Font(name, 60, 60));
      g.setColor(Color.WHITE);
      g.drawString(name, 280, 300);
    }

  }
  
  
  private void run() throws InterruptedException { // always running method
    while (true) {
      move();
      repaint();
      Thread.sleep(10);
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    TitleScreen a = new TitleScreen();
    if (!a.getStart())
      a.run();
    ChessGame cg = new ChessGame();
    JFrame frame = new JFrame("Chess Master Express XD");
    frame.setSize(1080, 800);
    frame.setLocationRelativeTo(null);
    frame.add(cg);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     playMusic();
    cg.run(); // invokes method "run", which is a method that will be
    // continuously running while the program is on
    
  }
  
}