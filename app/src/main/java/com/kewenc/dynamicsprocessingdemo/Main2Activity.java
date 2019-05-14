package com.kewenc.dynamicsprocessingdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Virtualizer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.kewenc.dynamicsprocessingdemo.service.AIDLInterface;
import static com.kewenc.dynamicsprocessingdemo.AIDLService.ID;
import static com.kewenc.dynamicsprocessingdemo.AIDLService.maxBandCount;
import static com.kewenc.dynamicsprocessingdemo.AIDLService.maxHalfSeekBar;
import static com.kewenc.dynamicsprocessingdemo.AIDLService.maxSeekBar;

public class Main2Activity extends AppCompatActivity {
    private SeekBar seekBar;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private SeekBar seekBar4;
    private SeekBar seekBar5;
    private SeekBar seekBar6;


    private static AIDLInterface aidlInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aidlInterface = AIDLInterface.Stub.asInterface(iBinder);
            try {
                aidlInterface.setData("Hello AIDL");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                Log.e("TAGF",aidlInterface.getData());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("TAGF","onServiceDisconnected");
        }
    };
    private LinearLayout eqLayout;
    private Equalizer mEqualizer;
    private BassBoost mBassBoost;
    private Virtualizer mVirtualizer;
    private MediaPlayer mMediaPlayer;
    private boolean isInit = true;
    private SeekBar sbPresetReverb;
    private PresetReverb reverb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mMediaPlayer = MediaPlayer.create(this, R.raw.aa);
        ID = mMediaPlayer.getAudioSessionId();
        Log.e("TAGF","onCreate_id = "+ID);
        mMediaPlayer.start();

        Intent intent = new Intent(this, AIDLService.class);
        startService(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eqLayout = findViewById(R.id.eqLayout);
        final CheckBox vb = findViewById(R.id.cb);
        vb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBassBoost.setEnabled(vb.isChecked());
                vb.setText("Status="+(vb.isChecked()?"On":"OFF")+"\n__Enabled="+mBassBoost.getEnabled()+"__Strength="+mBassBoost.getRoundedStrength()+"__Supported="+mBassBoost.getStrengthSupported());
                Log.e("TAGF","mBassBoost = "+mBassBoost.getEnabled());
            }
        });
        final ToggleButton btn = findViewById(R.id.btn);
        btn.setChecked(true);
        btn.setTextOff("EQ OFF");
        btn.setTextOn("EQ ON");
        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (aidlInterface != null){
                    try {
                        aidlInterface.setEnable(isChecked);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
//                mBassBoost.setEnabled(!btn.isChecked());
            }
        });
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

        seekBar = findViewById(R.id.seekBar);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar4 = findViewById(R.id.seekBar4);
        seekBar5 = findViewById(R.id.seekBar5);
        seekBar6 = findViewById(R.id.seekBar6);
        seekBar.setMax(3000);
        seekBar2.setMax(3000);
        seekBar3.setMax(3000);
        seekBar4.setMax(3000);
        seekBar5.setMax(3000);

        seekBar6.setMax(1000);

        seekBar.setProgress(1500);
        seekBar2.setProgress(1500);
        seekBar3.setProgress(1500);
        seekBar4.setProgress(1500);
        seekBar5.setProgress(1500);

        seekBar6.setProgress(0);

        mEqualizer = new Equalizer(0, ID);
        mEqualizer.setEnabled(true);

        mBassBoost = new BassBoost(0, ID);
        mBassBoost.setEnabled(true);
        mBassBoost.setEnabled(false);
        mBassBoost.setEnabled(true);

        mVirtualizer = new Virtualizer(0, ID);
        mVirtualizer.setEnabled(true);

        reverb = new PresetReverb(0,ID);
        reverb.setEnabled(true);
        reverb.setPreset((short)0);
        mMediaPlayer.attachAuxEffect(reverb.getId());
        mMediaPlayer.setAuxEffectSendLevel((float) 1.0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEqualizer.setBandLevel((short) 0, (short)(progress-1500));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEqualizer.setBandLevel((short) 1, (short)(progress-1500));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEqualizer.setBandLevel((short) 2, (short)(progress-1500));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEqualizer.setBandLevel((short) 3, (short)(progress-1500));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mEqualizer.setBandLevel((short) 4, (short)(progress-1500));
                if (aidlInterface != null){
                    try {
                        aidlInterface.initDb(ID);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBassBoost.setStrength((short) progress);
                if (mBassBoost.getRoundedStrength() == 0){
                    if (mBassBoost.getEnabled())
                        mBassBoost.setEnabled(false);
                } else {
                    if (!mBassBoost.getEnabled()){
                        mBassBoost.setEnabled(true);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbPresetReverb = findViewById(R.id.sbPresetReverb);
        sbPresetReverb.setMax(6);
        sbPresetReverb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("TAGF","PresetReverb="+progress);
                Log.e("TAGF","PresetReverb="+mEqualizer.getPresetName((short)progress));
//                try {
//                    aidlInterface.setPresetReverR(progress);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                reverb.setPreset((short)progress);
                mMediaPlayer.attachAuxEffect(reverb.getId());
                mMediaPlayer.setAuxEffectSendLevel( 0.2f);
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
        unbindService(serviceConnection);
        if (mMediaPlayer != null){
            mMediaPlayer.pause();
            mMediaPlayer.release();
        }
//

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isInit){
            isInit = false;

            if (aidlInterface != null){
                try {
                    aidlInterface.initDb(ID);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            for (int i=0; i<maxBandCount; i++){
                SeekBar seekBar = new SeekBar(this);
                seekBar.setMax(maxSeekBar);
                seekBar.setProgress(maxHalfSeekBar);
                seekBar.setTag(i);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Log.e("TAGF","aidlInterface = "+(aidlInterface == null));

                        if (Build.VERSION.SDK_INT >= 28) {
                            if ((Integer) seekBar.getTag() < maxBandCount / 2){
                                if (aidlInterface != null){
                                    try {
                                        aidlInterface.setEqGain((Integer) seekBar.getTag(), progress-maxHalfSeekBar);
                                        ((TextView)((LinearLayout)eqLayout.getChildAt((Integer) seekBar.getTag())).getChildAt(0)).setText(aidlInterface.getEqCutoffFrequency((Integer) seekBar.getTag())+"Hz: "+"DB:"+aidlInterface.getEqGain((Integer) seekBar.getTag()));
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
//                            eq.getBand((Integer) seekBar.getTag()).setGain((short)(progress-maxHalfSeekBar));
//                            dp.setPreEqBandAllChannelsTo((Integer) seekBar.getTag(),eq.getBand((Integer) seekBar.getTag()));
//                            ((TextView)((LinearLayout)eqLayout.getChildAt((Integer) seekBar.getTag())).getChildAt(0)).setText((int)eq.getBand((Integer) seekBar.getTag()).getCutoffFrequency()+"Hz: "+"DB:"+(int)eq.getBand((Integer) seekBar.getTag()).getGain());
                            } else {
                                if (aidlInterface != null){
                                    try {
                                        aidlInterface.setEq2Gain((Integer) seekBar.getTag() - maxBandCount/2, progress-maxHalfSeekBar);
                                        ((TextView)((LinearLayout)eqLayout.getChildAt((Integer) seekBar.getTag())).getChildAt(0)).setText(aidlInterface.getEq2CutoffFrequency((Integer) seekBar.getTag() - maxBandCount/2)+"Hz: "+"DB:"+aidlInterface.getEq2Gain((Integer) seekBar.getTag() - maxBandCount/2));
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
//                            eq2.getBand((Integer) seekBar.getTag() - maxBandCount/2).setGain((short)(progress-maxHalfSeekBar));
//                            dp.setPreEqBandByChannelIndex(CHANNEL_1, (Integer) seekBar.getTag() - maxBandCount/2, eq2.getBand((Integer) seekBar.getTag() - maxBandCount/2));
//                          ((TextView)((LinearLayout)eqLayout.getChildAt((Integer) seekBar.getTag())).getChildAt(0)).setText((int)eq2.getBand((Integer) seekBar.getTag() - maxBandCount/2).getCutoffFrequency()+"Hz: "+"DB:"+(int)eq2.getBand((Integer) seekBar.getTag() - maxBandCount/2).getGain());
                            }
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
                if (i < maxBandCount / 2){
                    if (aidlInterface != null){
                        try {
                            textView.setText(aidlInterface.getEqCutoffFrequency(i)+"Hz: "+"DB:"+0);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
//                textView.setText((int)eq.getBand(i).getCutoffFrequency()+"Hz: "+"DB:"+0);
                } else {
                    if (aidlInterface != null){
                        try {
                            textView.setText(aidlInterface.getEq2CutoffFrequency(i - maxBandCount/2)+"Hz: "+"DB:"+0);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
//                textView.setText((int)eq2.getBand(i - maxBandCount/2).getCutoffFrequency()+"Hz: "+"DB:"+0);
                }
                linearLayout.addView(textView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.topMargin = 50;
                linearLayout.addView(seekBar,params);
                eqLayout.addView(linearLayout);
            }
        }
    }
}
