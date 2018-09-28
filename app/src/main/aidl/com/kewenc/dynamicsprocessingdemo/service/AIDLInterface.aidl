package com.kewenc.dynamicsprocessingdemo.service;
interface AIDLInterface{
    String getData();
    void setData(String str);
    int getEqCutoffFrequency(int band);
    int getEq2CutoffFrequency(int band);
    void setEqGain(int band, int gain);
    void setEq2Gain(int band, int gain);
    int getEqGain(int band);
    int getEq2Gain(int band);
    void initDb(int id);
    void setEnable(boolean b);
}