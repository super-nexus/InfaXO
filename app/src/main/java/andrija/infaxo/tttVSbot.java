package andrija.infaxo;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andrija on 4/9/16.
 */
public class tttVSbot extends ttt {

    Random randInt = new Random();

    private static final String TAG = "myTAG";

    public final int NEUTRAL = 0;
    public final int POSITIVE = 1;
    public final int NEGATIVE = -1;
    public final int MIDDLE =  4;

    public int currentWantedPosition = 404;

    public final int[] corners = {0, 2, 6, 8};

    public tttVSbot(dialogAndPicturePublisher dialogAndPicturePublisher) {
        super(dialogAndPicturePublisher);
    }

    public void botMove(int index){
        if(spotAvailable(index)){
            positions[index] = Y;
            mCommander.publishImage(index, Y);
        }
    }

    public boolean middleAvailable(){
        if(positions[MIDDLE] == 2 ){
            return true;
        }
        return false;
    }

    public int[] Judge(){

        int[] decider = {NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL,NEUTRAL};

        for(int i = 0; i<winningPositions.length;i++){

            if(positions[winningPositions[i][0]] == X && positions[winningPositions[i][1]] == X && positions[winningPositions[i][2]] == EMPTY){
                decider[winningPositions[i][2]] = NEGATIVE;
            }
            else if(positions[winningPositions[i][0]] == X && positions[winningPositions[i][1]] == EMPTY && positions[winningPositions[i][2]] == X){
                decider[winningPositions[i][1]] = NEGATIVE;
            }
            else if(positions[winningPositions[i][0]] == EMPTY&& positions[winningPositions[i][1]] == X && positions[winningPositions[i][2]] == X){
                decider[winningPositions[i][0]] = NEGATIVE;
            }
            if(positions[winningPositions[i][0]] == Y && positions[winningPositions[i][1]] == Y && positions[winningPositions[i][2]] == EMPTY){
                decider[winningPositions[i][2]] = POSITIVE;
            }
            else if(positions[winningPositions[i][0]] == Y && positions[winningPositions[i][1]] == EMPTY && positions[winningPositions[i][2]] == Y){
                decider[winningPositions[i][1]] = POSITIVE;
            }
            else if(positions[winningPositions[i][0]] == EMPTY && positions[winningPositions[i][1]] == Y && positions[winningPositions[i][2]] == Y){
                decider[winningPositions[i][0]] = POSITIVE;
            }
        }

        myDebuggingTools.printArrayContents(true, decider);

        return decider;
    }

    public ArrayList<Integer> getAvailableCorners(){
        ArrayList<Integer> cornerHolder = new ArrayList<>();

        for(int x : corners){
            if(positions[x] == EMPTY){
                cornerHolder.add(x);
            }
        }

        return cornerHolder;
    }

    public int cornerMove(){

        ArrayList<Integer> holder = getAvailableCorners();

        int randIntHolder = randInt.nextInt(holder.size());

        return holder.get(randIntHolder);

    }

    public boolean cornerAvailable(){
        for(int x : corners){
           if(positions[x] == EMPTY){
               return true;
           }
        }
        return false;
    }

    public int randMove(){
        int randIntHolder = 404;
        ArrayList<Integer> positionsHolder = new ArrayList<>();

        try {


             positionsHolder = getAvailableSpots();

            if (positionsHolder.size() > 0) {

                randIntHolder = randInt.nextInt(positionsHolder.size());

                return positionsHolder.get(randIntHolder);

            } else {


                Log.i(TAG, "randMove: Error size == 0");

            }
        }catch (IndexOutOfBoundsException e){
            checkAll();
            mCommander.publishDialog(getWinner());
        }

        return 0;
    }

    public int getCurrentWantedPosition() {
        return currentWantedPosition;
    }

    public void setCurrentWantedPosition(int currentWantedPosition) {
        this.currentWantedPosition = currentWantedPosition;
    }

    public void botJudgeMove(){

        int[] decider = Judge();

        int position = 404;

        boolean breaker = false;

        if(getCurrentPlayer() == Y) {

            Log.i(TAG, "botJudgeMove: started after !getPlayerTurn");

            for (int i = 0; i < decider.length; i++) {
                if (decider[i] == POSITIVE) {
                    Log.i(TAG, "botJudgeMove: positiveMove");
                    botMove(i);
                    position = i;
                    breaker = true;
                    break;
                }
            }
            if (!breaker) {
                for (int i = 0; i < decider.length; i++) {
                    if (decider[i] == NEGATIVE) {
                        Log.i(TAG, "botJudgeMove: negativeMove");
                        botMove(i);
                        position = i;
                        breaker = true;
                        break;
                    }
                }
            }
            if (!breaker) {
                if (cornerAvailable()) {
                    Log.i(TAG, "botJudgeMove: cornerMove");
                    position = cornerMove();
                    botMove(cornerMove());
                    breaker = true;
                }
            }

            if (!breaker) {
                for (int i = 0; i < decider.length; i++) {
                    if (middleAvailable()) {
                        Log.i(TAG, "botJudgeMove: middleMove");
                        botMove(MIDDLE);
                        position = MIDDLE;
                        breaker = true;
                        break;
                    }
                }
            }


            if (!breaker) {
                Log.i(TAG, "botJudgeMove:  randMove");
                position = randMove();
                botMove(position);
            }
        }

            setCurrentPlayer();
        }

}
