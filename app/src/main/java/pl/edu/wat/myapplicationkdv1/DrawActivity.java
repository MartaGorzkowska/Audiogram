package pl.edu.wat.myapplicationkdv1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


public class DrawActivity extends ActionBarActivity {

    private RelativeLayout mainLayout;
    private LineChart draw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        //PRZECHWYCENIE DANYCH

        int[] left = getIntent().getBundleExtra("bundle").getIntArray("lewe");
        int[] right = getIntent().getBundleExtra("bundle").getIntArray("prawe");

        Number[] x = new Number[10];
        for (int i = 1; i < 10; i++) {
            x[i] = left[i-1];

        }
        Number[] y = new Number[10];
        for (int i = 1; i < 10; i++) {
            y[i] = right[i-1];

        }



        LineChart chart = (LineChart) findViewById(R.id.mainLayout);
        draw = new LineChart(this);
        mainLayout.addView(draw);
        draw.setBackgroundColor(Color.WHITE);
        draw.setNoDataTextDescription("BRAK DANYCH!");

        /*
        draw.setDescription("");
        draw.setNoDataTextDescription("BRAK DANYCH!");
        draw.setHighlightEnabled(true);
        draw.setTouchEnabled(true);
        draw.setDragEnabled(true);
        draw.setScaleEnabled(true);
        draw.setDrawGridBackground(false);
        draw.setPinchZoom(true);
        draw.setBackgroundColor(Color.WHITE);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        draw.setData(data);
        Legend l = draw.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.BLACK);

        XAxis d1 = draw.getXAxis();
        d1.setTextColor(Color.BLACK);
        d1.setDrawGridLines(false);
        d1.setAvoidFirstLastClipping(true);

        YAxis g1 = draw.getAxisLeft();
        g1.setTextColor(Color.BLACK);
        g1.setAxisMaxValue(120f);
        g1.setDrawGridLines(true);

        YAxis g12 = draw.getAxisRight();
        g12.setEnabled(false);

*/
        ArrayList<Entry> leftVals = new ArrayList<>();
        ArrayList<Entry> rightVals = new ArrayList<>();

        for (int i = 0; i < left.length; i++) {
            leftVals.add(new Entry(left[i], i));
            rightVals.add(new Entry(right[i], i));
        }

        LineDataSet setComp1 = new LineDataSet(leftVals, "LEWE UCHO");
        setComp1.setFillColor(Color.BLUE);
        setComp1.setColor(Color.BLUE);
        setComp1.setCircleColor(Color.BLUE);
        LineDataSet setComp2 = new LineDataSet(rightVals, "PRAWE UCHO");
        setComp2.setFillColor(Color.RED);
        setComp2.setColor(Color.RED);
        setComp2.setCircleColor(Color.RED);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < ExaminationActivity.FREQUENCY.length; i++) {
            xVals.add(String.valueOf(ExaminationActivity.FREQUENCY[i]));
        }

        LineData data1 = new LineData(xVals, dataSets);

        YAxis leftAxis = draw.getAxisLeft();
        leftAxis.setAxisMaxValue(100);
        leftAxis.setAxisMinValue(-10);
        leftAxis.setStartAtZero(false);
        leftAxis.setInverted(true);

        draw.setDescription("dB");
        draw.setPinchZoom(true);

        draw.setData(data1);
        draw.invalidate();

//-------------KONIEC WYKRESU----------------------------------------

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_char, menu);
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
}