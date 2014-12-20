package com.neotecsoft.woici;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


public class tour extends Activity {
    int t=0;
    ImageView back_tour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(android.os.Build.VERSION.SDK_INT < 11) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        setContentView(R.layout.activity_tour);
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }


        back_tour=(ImageView)findViewById( R.id.back_tour);

        back_tour.setOnClickListener(new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            t++;
            switch (t){
                case 1:
                    back_tour.setBackground(getResources().getDrawable(R.drawable.tour_b));
                    break;
                case 2:
                    back_tour.setBackground(getResources().getDrawable(R.drawable.tour_c));
                    break;
                case 3:
                    startActivity(new Intent(tour.this, buscar.class));
                    break;
            }
        }
    });

    }


}
