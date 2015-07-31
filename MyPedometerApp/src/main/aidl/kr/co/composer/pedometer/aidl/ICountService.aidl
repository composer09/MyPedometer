package kr.co.composer.pedometer.aidl;

import kr.co.composer.pedometer.aidl.ICountServiceCallback;

interface ICountService
{
    boolean isRunning();
    void setSensitivity(int sens);

    void registerCountCallback(ICountServiceCallback callback);
    void unregisterCountCallback(ICountServiceCallback callback);
}