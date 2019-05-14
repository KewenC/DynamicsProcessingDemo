package com.kewenc.dynamicsprocessingdemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.DynamicsProcessing;
import android.media.audiofx.PresetReverb;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.kewenc.dynamicsprocessingdemo.service.AIDLInterface;

public class AIDLService extends Service {

//    private int[][] new_values = {
//            {0,  0,  0,  0,  0,  0, -6, -6, -6, -7},// 古典 Classical
//            {8,  6,  2,  0,  0, -4, -6, -6,  0,  0},// 舞曲 Dance
//            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0},// 平直 Flat
//            {-2,  6,  9,  0,  9,  8,  7,  0,  0,  0},// 爵士 Jazz
//            {-2,  2,  5,  7,  5, -2, -4, -4, -4, -4},// 流行 Pop
//            {5,  2, -3, -6, -3,  3,  6,  8,  8,  8},// 摇滚 Rock
//            {-4,  0,  5,  6,  7,  6,  3,  2,  1,  0},// 现场 On site
//            {0,  0,  2,  6,  6,  6,  2,  0,  0,  0},// 俱乐部 Club
//            {12, 11, 10,  4,  0, -4, -6, -8, -9, -9},// 低音 Bass
//            {9,  9,  9,  5,  0,  4, 11, 11, 11, 11},// 高音 Treble
//            {-4,  6,  6,  6, -4, -4, -4, -4, -4, -4},// 声乐 Vocal music
//            {10, 10,  5, -5, -3,  2,  8, 10, 11, 12},// 强劲 Strong
//            {2,  0, -2, -4, -2,  2,  5,  7,  8, 9},// 轻柔 Gentle
//            {6,  6,  0,  0,  0,  0,  0,  0,  6,  6},// 聚会 Gather
//    };

//    public static final int[][] new_values = {
//            {0,  0,  0,  0,  0,  0, -6, -6, -6, -7},// 古典 Classical
//            {8,  6,  2,  0,  0, -4, -6, -6,  0,  0},// 舞曲 Dance
//            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0},// 平直 Flat
////			    {-2,  6,  9,  0,  9,  8,  7,  0,  0,  0},// 爵士 Jazz
//            {3,  3,  1,  2,  -1,  -1,  0,  1,  2,  4},// 爵士 Jazz
////				{-2,  2,  5,  7,  5, -2, -4, -4, -4, -4},// 流行 Pop
//            {-1,  0,  0,  1,  4, 3, 1, 0, -1, 1},// 流行 Pop
//            {5,  2, -3, -6, -3,  3,  6,  8,  8,  8},// 摇滚 Rock
//            {-4,  0,  5,  6,  7,  6,  3,  2,  1,  0},// 现场 On site
//            {0,  0,  2,  6,  6,  6,  2,  0,  0,  0},// 俱乐部 Club
////			    {12, 11, 10,  4,  0, -4, -6, -8, -9, -9},// 低音 Bass
//            {6, 4, 6,  2,  0, 0, 0, 0, 0, 0},// 低音 Bass
//            {9,  9,  9,  5,  0,  4, 11, 11, 11, 11},// 高音 Treble
////		    	{-4,  6,  6,  6, -4, -4, -4, -4, -4, -4},// 声乐 Vocal music
//            {-2,  5,  4,  -2, -2, -1, 2, 3, 1, 4},// 重金属
//            {10, 10,  5, -5, -3,  2,  8, 10, 11, 12},// 强劲 Strong
//            {2,  0, -2, -4, -2,  2,  5,  7,  8, 9},// 轻柔 Gentle
//            {2,  6, 4, 0, -2,  -1,  2,  2,  1, 3},// 蓝调
//            {0,  3, 0, 0, 1,  4,  5,  3,  0, 1},// 民谣
//            {6,  6,  0,  0,  0,  0,  0,  0,  6,  6},// 聚会 Gather
//    };
public static final int[][] new_values = {
        {0,  0,  0,  0,  0,  0, -6, -6, -6, -7},// 古典 Classical
        {8,  6,  2,  0,  0, -4, -6, -6,  0,  0},// 舞曲 Dance
        {0,  0,  0,  0,  0,  0,  0,  0,  0,  0},// 平直 Flat
//			    {-2,  6,  9,  0,  9,  8,  7,  0,  0,  0},// 爵士 Jazz
        {3,  3,  1,  2,  -1,  -1,  0,  1,  2,  4},// 爵士 Jazz
//				{-2,  2,  5,  7,  5, -2, -4, -4, -4, -4},// 流行 Pop
        {-1,  0,  0,  1,  4, 3, 1, 0, -1, 1},// 流行 Pop
        {5,  2, -3, -6, -3,  3,  6,  8,  8,  8},// 摇滚 Rock
        {-4,  0,  5,  6,  7,  6,  3,  2,  1,  0},// 现场 On site
        {0,  0,  2,  6,  6,  6,  2,  0,  0,  0},// 俱乐部 Club
//			    {12, 11, 10,  4,  0, -4, -6, -8, -9, -9},// 低音 Bass
        {6, 4, 6,  2,  0, 0, 0, 0, 0, 0},// 低音 Bass
        {9,  9,  9,  5,  0,  4, 11, 11, 11, 11},// 高音 Treble
//		    	{-4,  6,  6,  6, -4, -4, -4, -4, -4, -4},// 声乐 Vocal music
        {-2,  5,  4,  -2, -2, -1, 2, 3, 1, 4},// 重金属
        {10, 10,  5, -5, -3,  2,  8, 10, 11, 12},// 强劲 Strong
        {2,  0, -2, -4, -2,  2,  5,  7,  8, 9},// 轻柔 Gentle
        {2,  6, 4, 0, -2,  -1,  2,  2,  1, 3},// 蓝调
        {0,  3, 0, 0, 1,  4,  5,  3,  0, 1},// 民谣
        {6,  6,  0,  0,  0,  0,  0,  0,  6,  6},// 聚会 Gather
};
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
//    private PresetReverb reverb;

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
            if (Build.VERSION.SDK_INT >= 28 && eq != null) {
                return (int)eq.getBand(band).getCutoffFrequency();
            }
            return 0;
        }

        @Override
        public int getEq2CutoffFrequency(int band) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28 && eq2 != null) {
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
//                    dp = new DynamicsProcessing(id);

                DynamicsProcessing.Config.Builder builder = new DynamicsProcessing.Config.Builder(
                        0,
                        1,
                        true,
                        10 ,
                        true,
                        10,
                        true,
                        10,
                        true);

                    dp = new DynamicsProcessing(0, id, builder.build());

                    dp.setEnabled(true);

                eq = new DynamicsProcessing.Eq(true, true, maxBandCount/2);
                eq2 = new DynamicsProcessing.Eq(true, true, maxBandCount/2);
Log.e("TAGF",(dp==null)+"_"+(eq==null));
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
//                reverb = new PresetReverb(0, id);
//                reverb.setEnabled(true);
//                reverb.setPreset((short)0);
            }
        }

        @Override
        public void setEnable(boolean b) throws RemoteException {
            if (Build.VERSION.SDK_INT >= 28) {
                if (!b){
                    stopSelf();
                }
//                dp.setEnabled(b);
            }
        }

        @Override
        public void setPresetReverR(int b) throws RemoteException {
//            reverb.setPreset((short)b);
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
