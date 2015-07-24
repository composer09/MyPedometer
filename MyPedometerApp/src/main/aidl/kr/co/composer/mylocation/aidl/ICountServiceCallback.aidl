package kr.co.composer.mylocation.aidl;

interface ICountServiceCallback{
    oneway void stepsChanged(int value);
}