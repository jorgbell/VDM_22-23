package com.example.victoria;

public class MyClass2 extends MyAbstractClass{
    public boolean b = true;
    public String s = "bona nit";

    public float[][] getArray() {
        return array;
    }

    public void setArray(float[][] array) {
        this.array = array;
    }

    //arrays
    private float array[][] = new float[10][10];


    @Override
    public void SantEsteve() {
        super.SantEsteve();
        System.out.println("bon nadal! vols comes unes canelons?");
    }
}
