package com.example.lenovo.notes_android;

import android.support.annotation.Nullable;

public class Notes {
    private String title;
    private String description;

    public Notes(String title,@Nullable String description) {
        this.title = title;
        this.description = description;
    }

    public String getHead() {
        return title;
    }

    public String getDesc() {
        return description;
    }
}
