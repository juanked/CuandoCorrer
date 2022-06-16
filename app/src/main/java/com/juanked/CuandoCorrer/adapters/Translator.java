package com.juanked.CuandoCorrer.adapters;

import com.example.CuandoCorrer.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Translator {
    private String input;


    public Translator(String input) {
        this.input = input;
    }

    private static Map<String, Integer> dictionary = null;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("Thunderstorm",R.string.thunderstorm);
        aMap.put("Drizzle",R.string.drizzle);
        aMap.put("Rain",R.string.rain);
        aMap.put("Snow",R.string.snow);
        aMap.put("Mist",R.string.mist);
        aMap.put("Smoke",R.string.smoke);
        aMap.put("Haze",R.string.haze);
        aMap.put("Dust",R.string.dust);
        aMap.put("Fog",R.string.fog);
        aMap.put("Sand",R.string.sand);
        aMap.put("Ash",R.string.ash);
        aMap.put("Squall",R.string.squall);
        aMap.put("Tornado",R.string.tornado);
        aMap.put("Clear",R.string.clear);
        aMap.put("Clouds",R.string.clouds);
        dictionary = Collections.unmodifiableMap(aMap);
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public static int translate (String input){
        if(dictionary.get(input)!=null)
            return dictionary.get(input);
        else
            return -1;
    }

}
