package com.example.lalthanpuia.trafficpoliceswipe4;

import android.widget.Space;

public class Spaceship {
    private String name;
  /*  private String date;
    private String message;*/


    public Spaceship(String name) {
        this.name = name;
 /*       this.date = date;
        this.message = message;*/
    }

    @Override
    public String toString() {
        return name;
    }

}
