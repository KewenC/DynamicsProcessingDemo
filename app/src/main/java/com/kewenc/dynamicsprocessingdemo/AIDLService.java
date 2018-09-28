package com.kewenc.dynamicsprocessingdemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.DynamicsProcessing;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.kewenc.dynamicsprocessingdemo.service.AIDLInterface;

public class AIDLService extends Service {
    public static int ID = 0;
    public static final int CHANNEL_1 = 0;
    public static final int[] bandVal = {31, 62,125, 250,  500, 1000, 2000, 4000, 8000, 16000};//网易云
//    private static final int[] bandVal = {100, 200, 400, 600, 1000, 3000, 6000, 12000, 14000, 16000};
//    private static final int[] bandVal = {34, 62, 125, 250, 500, 1000, 2000, 4000, 8000, 16000};//Poweramp
//    private static final int[] bandVal = {60,230,910,3600,14000, 20000};
    public static final int maxBandCount = bandVal.length;
    //private static final int maxBandCount = 10;
    public static final int maxSeekBar = 30;
    public static final int maxHalfSeekBar = maxSeekBar/2;

    private DynamicsProcessing dp;
    private DynamicsProcessing.Eq eq;
    private DynamicsProcessing.Eq eq2;
//    private MediaPlayer mMediaPlayer;

    public AIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (android.os.Build.VERSION.SDK_INT >= 28) {
//            dp = new DynamicsProcessing(ID);
//            dp.setEnabled(true);
//            eq = new DynamicsProcessing.Eq(true, true, maxBandCount/2);
//            eq2 = new DynamicsProcessing.Eq(true, true, maxBandCount/2);
//            for (int i=0; i<maxBandCount; i++) {
//                if (i < maxBandCount / 2) {
//                    eq.getBand(i).setCutoffFrequency(bandVal[i]);
//                } else {
//                    eq2.getBand(i - maxBandCount / 2).setCutoffFrequency(bandVal[i]);
//                }
//            }
//            dp.setPreEqAllChannelsTo(eq);
////            dp.setPostEqAllChannelsTo(eq);
//            dp.setPreEqByChannelIndex(CHANNEL_1,eq2);
////            dp.setPostEqByChannelIndex(CHANNEL_1,eq2);
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    String data = "AIDLService";
    private Binder binder = new AIDLInterface.Stub(){

        @Override
        public String getData() throws RemoteException {
            return data;
        }

        @Override
        public void setData(String str) throws RemoteException {
            data = str;
        }

        @Override
        public int getEqCutoffFrequency(int band) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                return (int)eq.getBand(band).getCutoffFrequency();
            }
            return 0;
        }

        @Override
        public int getEq2CutoffFrequency(int band) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                return (int)eq2.getBand(band).getCutoffFrequency();
            }
            return 0;
        }

        @Override
        public void setEqGain(int band, int gain) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                eq.getBand(band).setGain((short)gain);
                dp.setPreEqBandAllChannelsTo(band,eq.getBand(band));
            }
        }

        @Override
        public void setEq2Gain(int band, int gain) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                eq2.getBand(band).setGain((short)gain);
                dp.setPreEqBandAllChannelsTo(band,eq2.getBand(band));
            }
        }

        @Override
        public int getEqGain(int band) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                return (int)eq.getBand(band).getGain();
            }
            return 0;
        }

        @Override
        public int getEq2Gain(int band) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                return (int)eq2.getBand(band).getGain();
            }
            return 0;
        }

        @Override
        public void initDb(int id) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
//                if (dp != null){
//                    dp.release();

                Log.e("TAGF","id = "+id);
                    dp = new DynamicsProcessing(id);
                    dp.setEnabled(true);
                    eq = new DynamicsProcessing.Eq(true, true, maxBandCount/2);
                    eq2 = new DynamicsProcessing.Eq(true, true, maxBandCount/2);
                    for (int i=0; i<maxBandCount; i++) {
                        if (i < maxBandCount / 2) {
                            eq.getBand(i).setCutoffFrequency(bandVal[i]);
                        } else {
                            eq2.getBand(i - maxBandCount / 2).setCutoffFrequency(bandVal[i]);
                        }
                    }
                    dp.setPreEqAllChannelsTo(eq);
//            dp.setPostEqAllChannelsTo(eq);
                    dp.setPreEqByChannelIndex(CHANNEL_1,eq2);
//            dp.setPostEqByChannelIndex(CHANNEL_1,eq2);
//                }
            }
        }

        @Override
        public void setEnable(boolean b) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                dp.setEnabled(b);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mMediaPlayer != null){
//            mMediaPlayer.pause();
//            mMediaPlayer.release();
//        }
        if (dp != null){
            dp.setEnabled(false);
            dp.release();
            dp = null;
        }
    }
}
