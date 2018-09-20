package com.example.furyou.omisechallenge;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

public class AmountWatcher implements TextWatcher{
    // Change this to what you want... ' ', '-' etc..
    private static final char space = ' ';

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 1) {
            char c = s.charAt(0);
            s.insert(0, "0");
            s.insert(1, ".");
            s.insert(2, "0");
            s.insert(3, String.valueOf(c));
        }/*
        // Insert char where needed.
        if (s.length() > 0 && (s.length() % 5) == 0) {
            char c = s.charAt(s.length() - 1);
            // Only if its a digit where there should be a space we insert a space
            if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }*/
    }
}
