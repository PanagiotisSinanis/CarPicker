package com.example.carpicker;

import android.provider.BaseColumns;

public final class SelectionLoggerContract {
    private SelectionLoggerContract(){};

    public static class LoggerEntry implements BaseColumns{
        public static final String TABLE_NAME = "selections";
        public static final String COLUMN_NAME_MODEL = "model";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
    }
}
