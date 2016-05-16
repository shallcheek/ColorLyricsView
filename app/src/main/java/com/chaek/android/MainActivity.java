package com.chaek.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup group = (RadioGroup) findViewById(R.id.radiogroup);
        final RadioButton r1 = (RadioButton) findViewById(R.id.radioButton1);
        final RadioButton r2 = (RadioButton) findViewById(R.id.radioButton2);
        final RadioButton r3 = (RadioButton) findViewById(R.id.radioButton3);
        final RadioButton r4 = (RadioButton) findViewById(R.id.radioButton4);
        final ColorLyricsView colorLoadingTextView = (ColorLyricsView) findViewById(R.id.a);
        colorLoadingTextView.setText("测试测试测试测试测试");
        if (group != null) {
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radioButton1) {
                        colorLoadingTextView.setDirectionType(ColorLyricsView.LEFT);
                    } else if (checkedId == R.id.radioButton2) {
                        colorLoadingTextView.setDirectionType(ColorLyricsView.TOP);
                    } else if (checkedId == R.id.radioButton3) {
                        colorLoadingTextView.setDirectionType(ColorLyricsView.BOTTOM);
                    } else if (checkedId == R.id.radioButton4) {
                        colorLoadingTextView.setDirectionType(ColorLyricsView.RIGHT);
                    }
                }
            });
        }
        SeekBar progressBar = (SeekBar) findViewById(R.id.seekBar);
        if (progressBar != null) {
            progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    colorLoadingTextView.setProgress(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
