package kr.co.composer.mylocation.aidl;

import kr.co.composer.mylocation.aidl.ICountServiceCallback;

interface ICountService
{
    boolean isRunning();
    void setSensitivity(int sens);

    void registerCountCallback(ICountServiceCallback callback);
    void unregisterCountCallback(ICountServiceCallback callback);
}