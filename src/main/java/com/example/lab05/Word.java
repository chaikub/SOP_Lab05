package com.example.lab05;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords = new ArrayList<>();
    public ArrayList<String> goodWords = new ArrayList<>();

    public Word() {
        goodWords.add("happy");
        goodWords.add("enjoy");
        goodWords.add("life");
        badWords.add("fuck");
        badWords.add("olo");
    }
}
