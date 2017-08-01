package peterloos.de.bouncingballs;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    private BouncingBallsView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Globals.setDisplaySize(size);

        Context context = this.getApplicationContext();
        this.view = new BouncingBallsView(context);
        this.setContentView(this.view);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.view.resume();
    }
}

