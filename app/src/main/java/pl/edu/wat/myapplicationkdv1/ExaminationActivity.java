package pl.edu.wat.myapplicationkdv1;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



public class ExaminationActivity extends ActionBarActivity {

    TextView text;
    ToneGenerator playTone;


    private static final int DURATION = 5;
    public static final int[] FREQUENCY = new int[]{125, 500, 1000, 2000, 3000,
            4000, 6000, 8000, 10000};
    public static final int[] AMPLITUDE = new int[]{0, 10, 20, 30, 40, 50, 60,
            70, 80, 90, 100};
    private TextView textExamination;
    private ToneGenerator toneGenerator;
    private final int[] listLeftEar = new int[FREQUENCY.length];
    private final int[] listRightEar = new int[FREQUENCY.length];
    private int mVolumeStep = 0;
    private int mFrequencyStep = 0;
    private boolean rightEar = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        text = (TextView) findViewById(R.id.textView3);
        textExamination = (TextView) findViewById(R.id.textView3);
        toneGenerator = new ToneGenerator();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_examination, menu);
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

    private void nextStep(boolean changeFrequency) {
        toneGenerator.stop();
        if (changeFrequency) {
            mVolumeStep = 10;
        }
        if (mVolumeStep == 10 && mFrequencyStep == FREQUENCY.length - 1 && !rightEar) {
//lewe ucho skończone
//teraz prawe ucho
            mVolumeStep = 0;
            mFrequencyStep = 0;
            rightEar = true;
            playTone();
        } else if (mVolumeStep == 10 && mFrequencyStep == FREQUENCY.length - 1 &&
                rightEar) {
//prawe ucho skończone - koniec
            showResult();
        } else {
//kolejny krok
            if (mVolumeStep == 10) {
//następna częstotliwość
                mFrequencyStep++;
                mVolumeStep = 0;
            } else {
//następna głośność
                mVolumeStep++;
            }
            playTone();
        }


    }
    private void playTone() {

        if (mFrequencyStep < FREQUENCY.length && mVolumeStep < AMPLITUDE.length) {
//odtwarzaj
//ustaw częstotliwość
            int mCurrentFrequency = FREQUENCY[mFrequencyStep];
//ustaw amplitudę
            double mCurrentAmplitude =
                    toneGenerator.amplitude(AMPLITUDE[mVolumeStep]);
//zapisz wynik

            if (rightEar) {
//prawe
                listRightEar[mFrequencyStep] = AMPLITUDE[mVolumeStep];
            } else {
//lewe ucho
                listLeftEar[mFrequencyStep] = AMPLITUDE[mVolumeStep];
            }
//ustaw text
            textExamination.setText(String.format("%dHz %ddB", mCurrentFrequency, 10
                    * mVolumeStep));
//odtwórz
            toneGenerator.stop();
            toneGenerator.playTone(mCurrentFrequency, mCurrentAmplitude, DURATION);
            if (rightEar) {
                toneGenerator.volume(0.0f, 0.1f);
            } else {
                toneGenerator.volume(0.1f, 0.0f);
            }
        }
    }
    private void showResult() {
        Intent intent = new Intent(ExaminationActivity.this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putIntArray("left", listLeftEar);
        bundle.putIntArray("right", listRightEar);

        intent.putExtra("bundle", bundle);

        startActivity(intent);
    }


    public void onDotknij1Click(View view) {
        nextStep(true);
    }

    public void onDotknijClick(View view) {
        nextStep(false);
    }
}
