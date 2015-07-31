package kr.co.composer.pedometer.aidl;

interface ICountServiceCallback{
    oneway void stepsChanged(int value);
}