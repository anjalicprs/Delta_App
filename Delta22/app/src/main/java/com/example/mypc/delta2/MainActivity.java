package com.example.mypc.delta2;

import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.speech.RecognizerIntent;
import android.support.v4.app.NavUtils;
import android.widget.Toast;
import android.graphics.RectF;

public class MainActivity extends Activity implements OnClickListener {

    public ListView mList;
    public Button speakButton;
    public View g;
    private int width=800, height=800;
    private float x=463,y=743,r=30;
    private Canvas c;
    private Paint paint;
    private ImageView imageview;

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        speakButton = (Button) findViewById(R.id.button1);
        g = (View) findViewById(R.id.view);
        speakButton.setOnClickListener(this);
        Bitmap b = Bitmap.createBitmap(width,
                height,Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
        c.drawColor(Color.WHITE);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        imageview =(ImageView) findViewById(R.id.view);
        imageview.setImageBitmap(b);
        paint.setColor(Color.RED);
        c.drawCircle(x, y, r, paint);
        voiceinputbuttons();
    }

    public void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }

    public void voiceinputbuttons() {
        speakButton = (Button) findViewById(R.id.button1);

    }

    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        startVoiceRecognitionActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);



            if (matches.contains("left")) {
              update(-10,0);
            }
            if (matches.contains("right")) {
                update(10,0);
            }
            if (matches.contains("down")) {
               update(0,10);
            }
            if (matches.contains("up")) {
               update(0,-10);
            }
            if (matches.contains("zoom")) {
               changerad();
            }
            if (matches.contains("shrink")) {
                changerad2();
            }

        }


    }
    public void update(int vx,int vy){
        paint.setColor(Color.WHITE);
        c.drawCircle(x, y, r, paint);
        x=x+vx;
        y=y+vy;
        if(x+r>=width)vx=-vx;
        if(x-r<=0)vx=-vx;
        if(y+r>=height)vy=-vy;
        if(y-r<=0)vy=-vy;
        paint.setColor(Color.RED);
        c.drawCircle(x, y, r, paint);
        imageview.invalidate();
    }
    public void changerad()
    {
        paint.setColor(Color.WHITE);
        c.drawCircle(x, y, r, paint);
        if(r<400)
        r=r+10;
        paint.setColor(Color.RED);
        c.drawCircle(x, y, r, paint);
        imageview.invalidate();

    }
    public void changerad2()
    {
        paint.setColor(Color.WHITE);
        c.drawCircle(x, y, r, paint);
        if(r>10)
            r=r-10;
        paint.setColor(Color.RED);
        c.drawCircle(x, y, r, paint);
        imageview.invalidate();

    }

   /* private void setposition(int myNewX, int myNewY) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) g.getLayoutParams();
        lp.setMargins(10, myNewY, 10, 0);
    }*/
   // RelativeLayout rl = (RelativeLayout) findViewById(R.id.y);

}