package com.cryptonita.app.core.controllers;

import java.util.ArrayList;
import java.util.List;

public class testCristian {

    private void test() {
        List<String> test = new ArrayList<>();
        List<String> test2 = new ArrayList<>();

        mainLoop:
        for (String s : test) {
            for (String s1 : test2) {
                if (true)
                    break mainLoop;
            }
        }

    }



}
