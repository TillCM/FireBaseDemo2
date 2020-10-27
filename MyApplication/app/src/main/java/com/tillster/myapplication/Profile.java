package com.tillster.myapplication;

import android.renderscript.ScriptIntrinsicYuvToRGB;

public class Profile
{

    public Profile(String name, String address, double weight, double length) {
        this.name = name;
        this.address = address;
        this.weight = weight;
        this.length = length;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    String name;
    String address;
    double weight;
    double length;

}
