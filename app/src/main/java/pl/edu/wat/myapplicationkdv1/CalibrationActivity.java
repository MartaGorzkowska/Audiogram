package pl.edu.wat.myapplicationkdv1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;


public class CalibrationActivity extends ActionBarActivity {

    ToneGenerator toneGenerator;
    SeekBar seekBar;
    int MAX = 10000;
    int DIV = 200000;

    public void onCreate() {


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       toneGenerator = new ToneGenerator();
        toneGenerator.playCalibration();
        toneGenerator.setCalibrationVolume((float) MAX / DIV);
        setContentView(R.layout.activity_calibration);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(MAX);
        seekBar.setProgress(MAX);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean
                    fromUser) {
                toneGenerator.setCalibrationVolume((float) progress/ DIV);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }


        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        toneGenerator.stop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calibration, menu);
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

    public void onSaveClick(View view) {
        int progress = seekBar.getProgress();
        toneGenerator.calibration = ((float) progress/ DIV);
        toneGenerator.setCalibrationVolume((float) progress/ DIV);

    }

    }


