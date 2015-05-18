package pl.edu.wat.myapplicationkdv1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ResultActivity extends ActionBarActivity {

    private TextView wyniki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int  []left = getIntent().getBundleExtra("bundle").getIntArray("left");
        int [] right = getIntent().getBundleExtra("bundle").getIntArray("right");


        Number[] x = new Number[10];
        for (int i = 1; i < 10; i++) {
            x[i] = left[i-1];

        }
        Number[] y = new Number[10];
        for (int i = 1; i < 10; i++) {
            y[i] = right[i-1];

        }

        int wyn,wyn1,ubytek = 0;

        //lewe ucho
        //wyjtek1

        if (Math.abs(left[1]- left[3]) > 40)
        {
            wyn = (left[1] + left[2] + left[3] + left[5]) / 4; }
               else if (Math.abs(left[3] - left[1]) > 40)
            {
                    wyn = (left[1] + left[2] + left[3] + left[5]) / 4;
            }

        //wyjatek2
        else if (Math.abs(left[1]-left[3])<=40 && left[5]>left[3]) {
            wyn = (left[1]+left[2]+left[5])/3;
        }
        //normalnie bez wyjatkow
      else {
            wyn = (left[1] + left[2] + left[3]) / 3;
        }

//prawe ucho
        if (Math.abs(right[1]- right[3]) > 40)
        {
            wyn1 = (right[1] + right[2] + right[3] + right[5]) / 4; }
        else if (Math.abs(right[3] - right[1]) > 40)
        {
            wyn1 = (right[1] + right[2] + right[3] + right[5]) / 4;
        }

        //wyjatek2
        else if (Math.abs(right[1]-right[3])<=40 && right[5]>right[3]) {
            wyn1 = (right[1]+right[2]+right[5])/3;
        }
        //normalnie bez wyjatkow
        else {
            wyn1 = (right[1] + right[2] + right[3]) / 3;
        }

        wyniki = (TextView) findViewById(R.id.textView11);
        wyniki.setText(String.format("LEWE UCHO: %d dB", wyn));

        wyniki = (TextView) findViewById(R.id.textView12);
        wyniki.setText(String.format("PRAWE UCHO: %d dB", wyn1));



        if (wyn < wyn1 && Math.abs(wyn1 - wyn) <= 25)
        {
            ubytek = wyn;
        }
        else if (wyn1 < wyn && Math.abs(wyn - wyn1) <= 25 )
        {
            ubytek = wyn1;
        }
        else if (wyn < wyn1 && Math.abs(wyn1 - wyn) > 25 )
        {
            ubytek = wyn + 5;
        }
        else if (wyn > wyn1 && Math.abs(wyn - wyn1) > 25 )
        {
            ubytek = wyn1 + 5;
        }


        if ( ubytek <= 20)
        {
            wyniki = (TextView) findViewById(R.id.textView13);
            wyniki.setText(String.format("%d dB - norma",  ubytek));
        }
        else if ( ubytek > 20 &&  ubytek <= 40)
        {
            wyniki = (TextView) findViewById(R.id.textView13);
            wyniki.setText(String.format("%d dB - lekkie uszkodzenie słuchu",  ubytek));
        }
        else if ( ubytek > 40 &&  ubytek <= 70)
        {
            wyniki = (TextView) findViewById(R.id.textView13);
            wyniki.setText(String.format("%d dB - umiarkowane uszkodzenie słuchu",  ubytek));
        }
        else if ( ubytek > 70 &&  ubytek <= 90)
        {
            wyniki = (TextView) findViewById(R.id.textView13);
            wyniki.setText(String.format("%d dB - znaczne zniszczenie słuchu",  ubytek));
        }
        else if ( ubytek > 90 &&  ubytek <= 120)
        {
            wyniki= (TextView) findViewById(R.id.textView13);
            wyniki.setText(String.format("%d dB - głębokie uszkodzenie słuchu",  ubytek));
        }
        else if ( ubytek > 120 )
        {
            wyniki = (TextView) findViewById(R.id.textView13);
            wyniki.setText(String.format("%d dB - całowita głuchota",  ubytek));
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartClick(View view)
    {
        Intent intent = new Intent(ResultActivity.this,
               pl.edu.wat.myapplicationkdv1.DrawActivity.class);

        int[] left = getIntent().getBundleExtra("bundle").getIntArray("left");
        int[] right = getIntent().getBundleExtra("bundle").getIntArray("right");

        Bundle bundle= new Bundle();
        bundle.putIntArray("lewe", left);
        bundle.putIntArray("prawe", right);
        intent.putExtra("bundle", bundle);

        startActivity(intent);

    }
}
