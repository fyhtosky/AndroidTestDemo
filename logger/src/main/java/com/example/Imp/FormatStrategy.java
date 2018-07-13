package com.example.Imp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 打印消息及保存消息
 * @see PrettyFormatStrategy
 * @see CsvFormatStrategy
 */
public interface FormatStrategy {
    void log(int priority, @Nullable String tag, @NonNull String message);
}
