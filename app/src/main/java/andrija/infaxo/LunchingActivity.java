package andrija.infaxo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LunchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunching_layout);

        setTitle("TicTacToe");

    }


    public void launchActivity(View view) {

        Intent intent = new Intent(this, PlayingField.class );
        intent.putExtra("mode", false);
        startActivity(intent);
    }

    public void lunchActivityBot(View view) {

        Intent intent = new Intent(this, PlayingField.class);
        intent.putExtra("mode", true);
        startActivity(intent);
    }


}
