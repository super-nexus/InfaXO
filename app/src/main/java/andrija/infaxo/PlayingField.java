package andrija.infaxo;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;


public class PlayingField extends Activity implements WinnerDialog.actionCommander, PlayingFieldInterface, dialogAndPicturePublisher {

    // Winner name holder
    private static final String TAG = "myTAG";

    protected ttt TicTacToe;
    protected tttVSbot TicTacToeBot;

    protected FieldFragment fieldFragment;

    Bundle lastSaved;

    private boolean bot = false;

    Intent intent;

    //ON CREATE METHOD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();

        bot = intent.getBooleanExtra("mode", false);

        setContentView(R.layout.playing_activity);

        fieldFragment = new FieldFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout, fieldFragment);

        fragmentTransaction.commit();

        TicTacToeBot = new tttVSbot(this);

        if(savedInstanceState != null){

            Log.i(TAG, "onCreate: savedInstanceState != null");

            TicTacToeBot.setPositions(savedInstanceState.getIntArray("POSITIONS"));

            TicTacToeBot.setCurrentPlayer(savedInstanceState.getInt("CURRENT_PLAYER"));

            bot = savedInstanceState.getBoolean("BOT");

            TicTacToeBot.redrawImages();

        }

    }

    @Override
    protected void onResume() {


        Log.i(TAG, "onResume: started");
        super.onResume();
        TicTacToeBot.setFieldUnlocked(true);
    }

    private void displayDialog(String winnerText) {

        DialogFragment Dialog = WinnerDialog.newInstance(winnerText);
        Dialog.show(getFragmentManager(), "winnerDialog");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putIntArray("POSITIONS", TicTacToeBot.getPositions());

        outState.putInt("CURRENT_PLAYER", TicTacToeBot.getCurrentPlayer());

        outState.putBoolean("BOT", bot);

    }

    @Override
    public void setClose() {
        finish();
    }

    @Override
    public void onRelativeLayoutCLicked(int index){
        boolean breaker = false;
        if (bot) {
            if (TicTacToeBot.isFieldUnlocked() && !breaker) {
                TicTacToeBot.addItemToBoard(index, 1);
                TicTacToeBot.setFieldUnlocked(false);

                Log.i(TAG, "onRelativeLayoutCLicked: first check: "+ String.valueOf(TicTacToeBot.checkAll()));
                if (TicTacToeBot.checkAll()) {
                    TicTacToeBot.setFieldUnlocked(false);
                    displayDialog(TicTacToeBot.getWinner());
                    breaker = true;
                }
                if(!breaker) {
                    TicTacToeBot.botJudgeMove();
                    TicTacToeBot.setFieldUnlocked(true);
                }
                Log.i(TAG, "onRelativeLayoutCLicked: second check: "+ String.valueOf(TicTacToeBot.checkAll()));
                if (TicTacToeBot.checkAll()) {
                    breaker = true;
                    TicTacToeBot.setFieldUnlocked(false);
                    displayDialog(TicTacToeBot.getWinner());
                }

            }
        } else {

            Log.i(TAG, "onRelativeLayoutCLicked: !bot executed");

            if (TicTacToeBot.isFieldUnlocked() && !breaker) {

                TicTacToeBot.addItemToBoard(index, TicTacToeBot.getCurrentPlayer());

                TicTacToeBot.setFieldUnlocked(false);

                if (TicTacToeBot.checkAll()) {
                    TicTacToeBot.setFieldUnlocked(false);
                    displayDialog(TicTacToeBot.getWinner());
                    breaker = true;
                }
                else{
                    TicTacToeBot.setFieldUnlocked(true);
                }

            }
        }
    }

    @Override
    public void makeToast(String text, boolean Short) {
        if (Short) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void publishImage(int index, int item) {
        fieldFragment.setPicture(index, item);
    }

    @Override
    public void publishDialog(String message) {
        displayDialog(message);
    }

}
