package andrija.infaxo;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andrija on 5/4/16.
 */
public class myDebuggingTools {

    String TAG = "";

    public myDebuggingTools(String TAG){
        this.TAG = TAG;
    }

    public myDebuggingTools(){}

    public <T> void printArrayContents(ArrayList<T> givenArray, boolean android){

        String stringToLog = "{";

        if(android){

            for(int i = 0; i< givenArray.size(); i++){

                stringToLog += givenArray.get(i);

            }
            stringToLog+= "}";
            Log.i(TAG, "printArrayContents: "+ stringToLog);
        }
        else{

            for(int i = 0; i< givenArray.size(); i++){

                stringToLog += givenArray.get(i);

            }
            stringToLog+= "}";

            System.out.println(stringToLog);
        }
    }



    @SafeVarargs
    public final <T> void printArrayContents(boolean android, T... givenArray){

        String stringToLog = "{";

        if(android) {

            for (T x : givenArray) {

                stringToLog+= x;

            }
            stringToLog+= "}";
            Log.i(TAG, "printArrayContents: "+ stringToLog);
        }
        else{

            for (T x : givenArray) {

                stringToLog+= x;

            }
            stringToLog+= "}";
            System.out.println(stringToLog);
        }
    }

}
