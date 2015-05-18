package pl.edu.wat.myapplicationkdv1;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * Created by student on 02.04.15.
 */
public class ToneGenerator {



    private final static int SAMPLE_RATE = 48000;
    public static float calibration = 0;
    private AudioTrack audioTrack;
    //konstruktor

    public ToneGenerator() {

    }

    //odtwarzenie tonów
    public void playCalibration() {
        short[] generatedCalibration = generateCalibration();
        play(generatedCalibration, true);
    }



    public void playTone(double frequency, double amplitude, int duration) {
        short[] generatedTone = generateTone(frequency, amplitude, duration);
        play(generatedTone, true);
    }



    private void play(short[] sample, boolean loop) {

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sample.length * 2,
                AudioTrack.MODE_STATIC);
        audioTrack.write(sample, 0, sample.length);


        if (loop) {
            audioTrack.setLoopPoints(0, sample.length, -1);
        }
        audioTrack.play();
    }
    public void stop() {
        try {
            audioTrack.stop();
            audioTrack.release();
        } catch (Exception e) {
            Log.d("ToneGenerator", "exception captured");
        }
    }
    //generowanie tonów
    private short[] generateTone(double frequency, double amplitude, int
            duration) {
        int numSamples = duration * SAMPLE_RATE;
// tablica przechowująca próbki (short zajmuje 16 bitów)
        short sample[] = new short[numSamples];
// tworzenie próbek
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = (short) (amplitude * Math.sin(2 * Math.PI * i /
                    (SAMPLE_RATE / frequency)) * Short.MAX_VALUE);

//!!! wygeneruj próbki o zadanych parametrach
        }
            return sample;



    }

    private short[] generateCalibration() {
//tworzymy ton 1000Hz
        short firstTon[] = generateTone(1000, gain(0), 1);
//tworzymy drugą część tonu 1000Hz o 5dB cichszy
        short secondTon[] = generateTone(1000, gain(-5), 1);
//łączymy te tony w nową tablicę
        short sound[] = new short[firstTon.length + secondTon.length];
//kopiujemy do nowej tablicy

        System.arraycopy(firstTon, 0, sound, 0, firstTon.length);
        System.arraycopy(secondTon, 0, sound, firstTon.length,
                secondTon.length);
        return sound;
    }
    //Sterowanie poziomem głośności
    public void setCalibrationVolume(float gain) {
        audioTrack.setStereoVolume(gain, gain);
    }
    public void volume(float leftVolume, float rightVolume) {
        audioTrack.setStereoVolume(leftVolume, rightVolume);
    }


// metody pomocnicze
    /**
     * Zamiana dB w wartość liniową
     * @param db wartość w dB
     * @return wartość liniowa
     */

    public double gain(double db) {
        double b = db/20;
        return // zaimplementuj wzór zamieniający dB na skalę liniową 10^(db/20)
        Math.pow(10,b);
    }

    /**
     * Wylicza skalibrowaną amplitudę z podanej wartości
     * @param db wzmocnienie w dB
     * @return amplituda
     */
    public double amplitude(int db) {
        return calibration * gain(db);
    }




}
