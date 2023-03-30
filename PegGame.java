/* This is My First Project at PLNU!
By Jason Halabo
imports below: */
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;

// creating our class (PegGame) which extends Application, allows us to use JFX
public class PegGame extends Application
{
   // private variables, to be used throughout the code
   private GamePane[][] gameBoard = new GamePane[4][4]; // 4x4 array of gamepanes
   private BorderPane root = new BorderPane(); // our root, borderpane
   private int movesLeft; // int to check potential moves
   private int ballsLeft; // int to check balls left
   private Label label = new Label(); // label to be shown at the top of the screen
   
   // our main code below
   public void start(Stage stage)
   {
      GridPane gridpane = new GridPane(); // creating a gridpane to store our gamepanes
      label.setStyle("-fx-background-color: black;"); // giving the label a black background
      root.setAlignment(label, Pos.CENTER); // setting the label to the center of the screen
      root.setTop(label); // setting the label to the top center of the borderpane
      
      gridpane.setHgap(10); // setting horizontal gap to 10
      gridpane.setVgap(10); // setting vertical gap to 10
      gridpane.setAlignment(Pos.CENTER); // setting the gridpane to the center
      
      // for loop:      
      for(int i=0;i<4;i++) {
         for(int j=0;j<4;j++) {
            GamePane hold = new GamePane(); // creates a new gamepane
            gameBoard[i][j] = hold; // adds it to the board array
            gridpane.add(gameBoard[i][j], i, j); // adds all the elements into my gridpane
            if(i == 0 && j == 2) { // starting the ball at 0,2 as not there
               gameBoard[i][j].setBallVis(false); // setting ball vis to false
            }            
         }
      }
      // checks the moves (adds buttons and the number of balls/moves left
      checkMove(gameBoard);
      // another for loop
      for(int i=0;i<4;i++) {
         for(int j=0;j<4;j++) {
            gameBoard[i][j].draw(); // draws out the gameboard w/ draw method
         }
      }
      
      // sets the gridpane to the center of the borderpane
      root.setCenter(gridpane);
      // creating a 600x600 scene based off the borderpane
      Scene scene = new Scene(root, 600, 600);
      stage.setScene(scene);
      stage.setTitle("The Peg Game"); // title of the game
      stage.show(); // shows the stage
   }
   // launches our args so that the stage pops up
   public static void main(String[] args)
   {
      launch(args);
   }
   
   // Our GamePane class
   public class GamePane extends GridPane // extends gridpane so we can use their class features
   {
      GridPane gp = new GridPane(); // creating a gridpane
      
      // creating all 4 of our buttons
      Button top = new Button();
      Button left = new Button();
      Button right = new Button();
      Button bottom = new Button();
      
      // creating a canvas for the ball
      Canvas canvas = new Canvas(80,80);
      GraphicsContext gc = canvas.getGraphicsContext2D();
      
      // setting visibilities, all ball vis are true and buttons are false at default
      boolean ballVis = true;
      boolean topBVis = false;
      boolean leftBVis = false;      
      boolean rightBVis = false;
      boolean botBVis = false;
      
      // constructor for our class
      public GamePane() {
         super(); // calling super so that we get access to gridpane's features      
         top.setPrefSize(80,20); // setting top button's size
         top.setOnAction(new ButtonListener()); // giving it access to our button listener
         left.setPrefSize(20,80);  // setting left button's size/action
         left.setOnAction(new ButtonListener());
         right.setPrefSize(20,80);  // setting right button's size/action
         right.setOnAction(new ButtonListener());        
         bottom.setPrefSize(80,20);  // setting bottom button's size/action
         bottom.setOnAction(new ButtonListener());  
         gc.fillOval(0, 0, 80, 80);
         
         // adding these elements into the gridpane
         add(top, 1, 0); // top button
         add(left, 0, 1); // left button
         add(right, 2, 1); // right button
         add(bottom, 1, 2); // bottom button
         add(canvas, 1, 1); // the circle
      }
      
      // creating the draw method
      public void draw() {
         if(ballVis) { // if the ball is visible (true)
            gc.fillOval(0,0,80,80); // the ball is drawn
         } else { // if not, the ball is erased/invisible
            gc.clearRect(0,0,80,80);
         }
         
         if(topBVis) { // if the top button is visible
            top.setVisible(true); // button is placed
         } else { // if not, it's invisible
            top.setVisible(false);
         }
         
         if(leftBVis) { // if the left button is visible
            left.setVisible(true); // button is placed
         } else { // if not, it's invisible
            left.setVisible(false);
         }
         
         if(rightBVis) { // if the right button is visible
            right.setVisible(true); // button is placed
         } else { // if not, it's invisible
            right.setVisible(false);
         }
         
         if(botBVis) { // if the bottom button is visible
            bottom.setVisible(true); // button is placed
         } else { // if not, it's invisible
            bottom.setVisible(false);
         }
         
         // checker to update the label
         if(ballsLeft == 1) { // checks if the person has won (1 ball remaining)
            label.setTextFill(Color.LIME); // sets the label text to green
            // sets the background to green
            root.setBackground(new Background(new BackgroundFill(new Color(0,1,0,0.7), CornerRadii.EMPTY, Insets.EMPTY)));
            label.setText("You Win!"); // changes text to you win
         } else if(movesLeft == 0 && ballsLeft != 1) { // checks if the person has lost
            label.setTextFill(Color.RED); // sets the label to red text
            // background is red
            root.setBackground(new Background(new BackgroundFill(new Color(1,0,0,0.7), CornerRadii.EMPTY, Insets.EMPTY)));
            label.setText("You Lose!"); // prints you lose
         } else { // if you haven't won/lost yet
            label.setTextFill(Color.CYAN); // text fill is cyan
            // tells you the amount of balls and moves that are left
            label.setText("Balls Left: "+ballsLeft+" \t\t\t\t\t Moves Left: "+movesLeft);
         }
      }
      
      // accessors to get ball and button visibility, and to set those values if needed are below
      public boolean getBallVis() {
         return ballVis;
      }
      
      public void setBallVis(boolean ballVis) {
         this.ballVis = ballVis;
      }
      
      public void setTopBVis(boolean topBVis) {
         this.topBVis = topBVis;
      }
      
      public void setLeftBVis(boolean leftBVis) {
         this.leftBVis = leftBVis;
      }
      
      public void setRightBVis(boolean rightBVis) {
         this.rightBVis = rightBVis;
      }
      
      public void setBotBVis(boolean botBVis) {
         this.botBVis = botBVis;
      }
      
      // gets the button that is selected
      public Button getButtonSelected(char bName) {
         if(bName == 't') { // if the button's ID is 't'
            return top; // the top button is selected
         } else if(bName == 'l') { // if the button's ID is 'l'
            return left; // left button is selected
         } else if(bName == 'r') { // if the button's ID is 'r'
            return right; // right button is selected
         } else { // we can use else since there aren't any other buttons
            return bottom; // sets it to the bottom
         }
      }
   } 
   // a void to check if one can make a move (not in the GamePane Class now)
   public void checkMove(GamePane[][] board) // brings in a gamepane 2d array to use
   { 
      ballsLeft = 0; // sets the int balls left to 0
      movesLeft = 0; // also w/ movesleft to 0
      // for loop
      for(int i=0;i<4;i++) {
         for(int j=0;j<4;j++) {
            if(board[i][j].getBallVis() == true) { // if the ball is visible
               ballsLeft++; // adds to the ballsleft counter
            }     
            // checks if there is a ball 2 spaces above
            if(j-2 > -1) { // holds a boundary (can use a try/catch statement too)
               if(board[i][j-1].getBallVis() == true && board[i][j-2].getBallVis() == false && board[i][j].getBallVis() == true)
               { // checks 1 space from the ball that it is true, 2 spaces are false, and the ball itself is true
                  board[i][j].setBotBVis(true); // sets the bottom button to true
                  movesLeft++; // adds to the movesleft counter
               } else { // if not
                  board[i][j].setBotBVis(false); // button stays false
               }
            }
            // checks if there is a ball 2 spaces below
            if(j+2 < 4) { // boundary again
               if(board[i][j+1].getBallVis() == true && board[i][j+2].getBallVis() == false && board[i][j].getBallVis() == true)
               { // checks the same as above
                  board[i][j].setTopBVis(true); // sets the top button to true
                  movesLeft++; // adds a potential move
               } else {
                  board[i][j].setTopBVis(false); // keeps button false if false
               }
            }
            // checks to the left
            if(i-2 > -1) { // boundary
               if(board[i-1][j].getBallVis() == true && board[i-2][j].getBallVis() == false && board[i][j].getBallVis() == true)
               { // same as the last 2
                  board[i][j].setRightBVis(true); // right button is true
                  movesLeft++; // adds to a move
               } else {
                  board[i][j].setRightBVis(false); // button is false if the if statement is false
               }
            }
            // checks to the right
            if(i+2 < 4) { // boundary again
               if(board[i+1][j].getBallVis() == true && board[i+2][j].getBallVis() == false && board[i][j].getBallVis() == true)
               { // same concept as the last 3
                  board[i][j].setLeftBVis(true); // sets the left button to true
                  movesLeft++; // adds to the movesleft
               } else {
                  board[i][j].setLeftBVis(false); // if not, button stays false
               }
            }
         }
      }   
   } 
   // Button Listener time
   public class ButtonListener implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent e) // actionevent is e
      {
         // for loop
         for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
               if(e.getSource() == gameBoard[i][j].getButtonSelected('t')) { // checks if the top button was clicked
                  click(i,j,'t'); // adds these parameters to the click method
               } else if(e.getSource() == gameBoard[i][j].getButtonSelected('l')) { // checks if left button was clicked
                  click(i,j,'l'); // same as above
               } else if(e.getSource() == gameBoard[i][j].getButtonSelected('r')) { // checks if right button was clicked
                  click(i,j,'r'); // same as above
               } else if(e.getSource() == gameBoard[i][j].getButtonSelected('b')) { // checks if bottom button was clicked
                  click(i,j,'b'); // same as above
               }
            }
         }
      }
   }
   // final method, the click method
   public void click(int x, int y, char bName) { // takes in the x, y, and button name ('t' for example)
      if(bName == 't') { // if it is the top button
         gameBoard[x][y].setBallVis(false); // makes the ball invisible
         gameBoard[x][y+1].setBallVis(false); // the ball it jumped over invisible
         gameBoard[x][y+2].setBallVis(true); // the ball 2 spaces down becomes true
      } else if(bName == 'l') { // if it is the left button
         gameBoard[x][y].setBallVis(false); // makes the ball invis.
         gameBoard[x+1][y].setBallVis(false); // the ball it jumped over invis.
         gameBoard[x+2][y].setBallVis(true); // the ball 2 spaces to the right become true
      } else if(bName == 'r') { // if it is the right button
         gameBoard[x][y].setBallVis(false); // makes the ball invis.
         gameBoard[x-1][y].setBallVis(false); // the ball it jumped over invis.
         gameBoard[x-2][y].setBallVis(true); // the ball 2 spaces to the left become true
      } else { // if it is the bottom button
         gameBoard[x][y].setBallVis(false); // makes the ball invis.
         gameBoard[x][y-1].setBallVis(false); // the ball it jumped over invis.
         gameBoard[x][y-2].setBallVis(true); // the ball 2 spaces above is becomes true
      }
      // now it recalls the checkmove method to add the new buttons
      checkMove(gameBoard);
      // it also redraws the gameboard with the for loop below
      for(int i=0;i<4;i++) {
         for(int j=0;j<4;j++) {
            gameBoard[i][j].draw();
         }
      }
   }
} // The End