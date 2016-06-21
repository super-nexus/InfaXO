package andrija.infaxo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * Created by Andrija on 4/14/16.
 */
public class FieldFragment extends Fragment implements View.OnClickListener{

    HashMap<RelativeLayout, Integer> relativeLayoutHash = new HashMap<>();
    HashMap<Integer, Integer> xoMap = new HashMap<>();

    private static final String TAG = "myTAG";

    RelativeLayout relativeLayout0, relativeLayout1, relativeLayout2,relativeLayout3,
            relativeLayout4,relativeLayout5,relativeLayout6,relativeLayout7,relativeLayout8;

    RelativeLayout[] relativeLayouts = {relativeLayout0, relativeLayout1, relativeLayout2,relativeLayout3,
            relativeLayout4,relativeLayout5,relativeLayout6,relativeLayout7,relativeLayout8};

    ImageView imageView0, imageView1,imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;

    ImageView[] imgArray = {imageView0, imageView1,imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8};

    int[] relativeLayoutIds = {R.id.relativeLayout0,R.id.relativeLayout1, R.id.relativeLayout2, R.id.relativeLayout3,
            R.id.relativeLayout4, R.id.relativeLayout5, R.id.relativeLayout6,R.id.relativeLayout7, R.id.relativeLayout8};

    int[] imageIds = {R.id.imageView_0,R.id.imageView_1,R.id.imageView_2,R.id.imageView_3,R.id.imageView_4,
            R.id.imageView_5,R.id.imageView_6, R.id.imageView_7,R.id.imageView_8};

    PlayingFieldInterface mCommander;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.playing_field_layout, container, false);

        //InitializeRelativeLayouts
        initializeRelLayouts(rootView);

        //Initialize ImageViews

        initializeImgLayouts(rootView);

        //Initialize array of images

        addItemsToHashMap();

        //Add listeners to layouts

        addListenersToLayouts();

        //Add x and o to xoMap

        addItemsToXoMap();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Initialize RelativeLayouts

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCommander = (PlayingFieldInterface) getActivity();
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    private void addItemsToHashMap(){
        for(int i = 0; i<relativeLayouts.length; i++){
            relativeLayoutHash.put(relativeLayouts[i], i);
        }
    }

    private void addItemsToXoMap(){
        xoMap.put(0, R.drawable.o);
        xoMap.put(1, R.drawable.x);
    }

    private void initializeRelLayouts(View rootView){
        for(int i = 0; i<relativeLayouts.length; i++){
            relativeLayouts[i] = (RelativeLayout) rootView.findViewById(relativeLayoutIds[i]);
        }
        for(RelativeLayout r: relativeLayouts){
            if(r == null){
                Log.i(TAG, "initializeRelLayouts: one or more relative layouts are equal to null");

            }
        }
    }

    private void addListenersToLayouts(){
        for(RelativeLayout r: relativeLayouts){
            if(r != null) {
                r.setOnClickListener(this);
            }
            else{
                Log.i(TAG, "addListenersToLayouts: relativeLayout == null");
            }
        }
    }

    private void initializeImgLayouts(View rootView) {
        for (int i = 0; i < imgArray.length; i++){
            imgArray[i] = (ImageView) rootView.findViewById(imageIds[i]);
        }
    }

    public void setPicture(int index, int item){
        imgArray[index].setImageResource(xoMap.get(item));
    }
    public void setPicture(int index){
        imgArray[index].setImageResource(xoMap.get(0));
    }


    @Override
    public void onClick(View v) {
        if(mCommander != null){

            Log.i(TAG, "onClick: Clicked" + relativeLayoutHash.get((RelativeLayout) v));

            mCommander.onRelativeLayoutCLicked(relativeLayoutHash.get((RelativeLayout) v));
        }
    }
}


interface PlayingFieldInterface{
    void onRelativeLayoutCLicked(int index);
}