package com.example.Imp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.logger.LogAdapter;

import static com.example.logger.Utils.checkNotNull;

public class DiskLogAdapter implements LogAdapter {
    @NonNull private final FormatStrategy formatStrategy;

    public DiskLogAdapter() {
        formatStrategy = CsvFormatStrategy.newBuilder().build();
    }

    public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}
