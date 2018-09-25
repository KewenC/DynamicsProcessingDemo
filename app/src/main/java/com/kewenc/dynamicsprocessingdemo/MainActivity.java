package com.kewenc.dynamicsprocessingdemo;

import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.DynamicsProcessing;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private Equalizer mEqualizer;
    private BassBoost mBassBoost;
    private Virtualizer mVirtualizer;
    private DynamicsProcessing dp;
    private DynamicsProcessing.Eq eq;
    private int ID;
    private MediaPlayer mMediaPlayer;
    private SeekBar seekBar;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private SeekBar seekBar4;
    private SeekBar seekBar5;


    private static final int[] bandVal = {100, 200, 400, 600, 1000, 3000, 6000, 12000, 14000, 16000};
//    private static final int[] bandVal = {16000, 14000, 12000, 6000, 3000, 1000, 600, 400, 200, 100};
//    private static final int[] bandVal = {34, 62, 125, 250, 500, 1000, 2000, 4000, 8000, 16000};
//    private static final int[] bandVal = {16000, 8000, 4000, 2000, 1000, 500, 250, 125, 62, 34};
//    private static final int[] bandVal = {60,230,910,3600,14000};
private static final int maxBandCount = bandVal.length;
private static final int maxSeekBar = 30;
private static final int maxHalfSeekBar = maxSeekBar/2;
    private LinearLayout eqLayout;
    private ToggleButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setChecked(true);
        btn.setTextOff("OFF");
        btn.setTextOn("ON");
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dp.setEnabled(isChecked);
            }
        });

        eqLayout = findViewById(R.id.eqLayout);

        mMediaPlayer = MediaPlayer.create(this, R.raw.aa);
        ID = mMediaPlayer.getAudioSessionId();

//        seekBar = findViewById(R.id.seekBar);
//        seekBar2 = findViewById(R.id.seekBar2);
//        seekBar3 = findViewById(R.id.seekBar3);
//        seekBar4 = findViewById(R.id.seekBar4);
//        seekBar5 = findViewById(R.id.seekBar5);
//        seekBar.setMax(3000);
//        seekBar2.setMax(3000);
//        seekBar3.setMax(3000);
//        seekBar4.setMax(3000);
//        seekBar5.setMax(3000);
//        seekBar.setProgress(1500);
//        seekBar2.setProgress(1500);
//        seekBar3.setProgress(1500);
//        seekBar4.setProgress(1500);
//        seekBar5.setProgress(1500);



//        mEqualizer = new Equalizer(0, ID);
//        mEqualizer.setEnabled(true);
//        mBassBoost = new BassBoost(0, ID);
//        mBassBoost.setEnabled(true);
//        mVirtualizer = new Virtualizer(0, ID);
//        mVirtualizer.setEnabled(true);


        if (android.os.Build.VERSION.SDK_INT >= 28) {
            dp = new DynamicsProcessing(ID);
            eq = new DynamicsProcessing.Eq(true, true, maxBandCount);
//            dp.setPreEqAllChannelsTo(eq);
            dp.setEnabled(true);
            for (int i=0;i<maxBandCount;i++){
                DynamicsProcessing.EqBand eqBand= new DynamicsProcessing.EqBand(true,bandVal[i],0);
                eq.setBand(i, eqBand);
                dp.setPreEqAllChannelsTo(eq);
                dp.setPostEqAllChannelsTo(eq);

                SeekBar seekBar = new SeekBar(this);
                seekBar.setMax(maxSeekBar);
                seekBar.setProgress(maxHalfSeekBar);
                seekBar.setTag(i);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            Log.e("TAGF","A__"+(Integer) seekBar.getTag()+"_"+((short)(progress-maxHalfSeekBar)));
//                            eq.getBand((Integer) seekBar.getTag()).setGain((short)(progress-1500));
                            eq.getBand((Integer) seekBar.getTag()).setGain((short)(progress-maxHalfSeekBar));
                            dp.setPreEqBandAllChannelsTo((Integer) seekBar.getTag(),eq.getBand((Integer) seekBar.getTag()));
                            dp.setPostEqBandAllChannelsTo((Integer) seekBar.getTag(),eq.getBand((Integer) seekBar.getTag()));
                            ((TextView)((LinearLayout)eqLayout.getChildAt((Integer) seekBar.getTag())).getChildAt(0)).setText(bandVal[(Integer) seekBar.getTag()]+"Hz: "+"DB:"+(short)(progress-maxHalfSeekBar));
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView textView = new TextView(this);
                textView.setText(bandVal[i]+"Hz: "+"DB:"+0);
                linearLayout.addView(textView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.topMargin = 50;
                linearLayout.addView(seekBar,params);
                eqLayout.addView(linearLayout);
            }
        }

        mMediaPlayer.start();
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mEqualizer.setBandLevel((short) 0, (short)(progress-1500));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mEqualizer.setBandLevel((short) 1, (short)(progress-1500));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mEqualizer.setBandLevel((short) 2, (short)(progress-1500));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mEqualizer.setBandLevel((short) 3, (short)(progress-1500));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mEqualizer.setBandLevel((short) 4, (short)(progress-1500));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();        if (mMediaPlayer != null){
            mMediaPlayer.pause();
            mMediaPlayer.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
