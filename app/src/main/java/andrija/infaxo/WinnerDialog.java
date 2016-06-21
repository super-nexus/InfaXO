package andrija.infaxo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Andrija on 4/7/16.
 */
public class WinnerDialog extends DialogFragment {


    actionCommander mInterface;
    AlertDialog.Builder builder;


    public static WinnerDialog newInstance(String winner) {

        Bundle args = new Bundle();

        args.putString("winner", winner);

        WinnerDialog fragment = new WinnerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new AlertDialog.Builder(getActivity());
        }
            builder.setTitle("Winner");
            builder.setMessage(getArguments().getString("winner"));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mInterface.setClose();
                }
            });
        return builder.create();
    }

    public interface actionCommander{
        void setClose();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mInterface = (actionCommander) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
