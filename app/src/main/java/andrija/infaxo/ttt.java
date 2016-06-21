package andrija.infaxo;

import android.util.Log;
import android.widget.ImageView;
import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andrija on 4/6/16.
 */


public class ttt {

    private static String TAG = "myTag";

    boolean playerTurn = true, fieldUnlocked = false, wonX = false, wonY = false, end = false;

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    int turnCounter = 0;
    int oldCounter = 0;

    myDebuggingTools myDebuggingTools = new myDebuggingTools(TAG);

    int[] holder = {2,2,2,2,2,2,2,2,2};
    int[] positions = holder;

    public final int X = 1;
    public final int Y = 0;
    public final int EMPTY = 2;


    int currentPlayer = X;

    String winner;

    dialogAndPicturePublisher mCommander;

    public ttt(dialogAndPicturePublisher mCommander){
        this.mCommander = mCommander;
    }

    /*public void setOnCommanderListener(Commander commander){
        mCommander = commander;
    }
    */

    public boolean getPlayerTurn(){
        return playerTurn;
    }

    public void setPlayerTurn(boolean set){
        playerTurn = set;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public boolean isFieldUnlocked() {
        return fieldUnlocked;
    }

    public boolean isEnd() {
        return end;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public void setFieldUnlocked(boolean fieldUnlocked) {
        this.fieldUnlocked = fieldUnlocked;
    }

    public void setCurrentPlayer() {
        currentPlayer = ((currentPlayer == X)? Y:X);
    }

    public void setCurrentPlayer(int currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void addItemToBoard(int index, int item) {
        if(spotAvailable(index)) {
            positions[index] = item;
            Log.i(TAG, "addItemToBoard: added "+((item == 1)? "X":"Y") + " to " + index);
            mCommander.publishImage(index, item);
            setCurrentPlayer();
        }
    }

    public boolean checkAll(){
        if(hasSomebodyWon() || checkIfTie()){
            return true;
        }

        return false;
    }

    public boolean spotAvailable(int index){

        if(positions[index] == EMPTY){
            return true;
        }
        else{
            if(getAvailableSpots().size() != 0) {
                mCommander.makeToast("You cannot Click on this field", true);
            }
            return false;
        }
    }

    public ArrayList<Integer> getAvailableSpots(){

        ArrayList<Integer> holder = new ArrayList<>();
        for(int i = 0; i<positions.length; i++){
            if(positions[i] == EMPTY){
                holder.add(i);
            }
        }
        myDebuggingTools.printArrayContents(holder, true);
        return holder;
    }

    public String getWinner(){
        return winner;
    }

    public boolean hasSomebodyWon(){
        for (int x = 0; x < winningPositions.length; x++){

            if(turnCounter > oldCounter){
                Log.i(String.valueOf(positions[winningPositions[x][0]]) + String.valueOf(positions[winningPositions[x][1]]) + String.valueOf(positions[winningPositions[x][2]]), TAG);
            }

            if(positions[winningPositions[x][0]] == X && positions[winningPositions[x][1]] == X && positions[winningPositions[x][2]] == X){

                wonX = true;
                winner = "Player X WON!";
            }
            else if (positions[winningPositions[x][0]] == Y && positions[winningPositions[x][1]] == Y && positions[winningPositions[x][2]] == Y){
                wonY = true;
                winner = "Player O WON!";
            }
        }
        if(turnCounter > oldCounter){
            oldCounter++;
        }

        if(wonX || wonY) {return true;}



        else {return false;}
    }

    public boolean checkIfTie(){

        int counter = 0;

        for(int x : positions){
            if(x != EMPTY){
                counter++;
            }
        }
        if(counter == 9){
            winner = "It's a tie!";
            return true;
        }
        return false;
    }

    /*
        Redraw all the images again
     */
    public void redrawImages(){
        for(int x = 0 ; x< positions.length; x++){
            if(positions[x] == X){
                mCommander.publishImage(x, X);
            }
            else if(positions[x] == Y){
                mCommander.publishImage(x, Y);
            }
        }
    }

}
