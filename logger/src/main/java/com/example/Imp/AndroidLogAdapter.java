package com.example.Imp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.logger.LogAdapter;

import static com.example.logger.Utils.checkNotNull;

/**
 * Android的日主库参数配置类
 */
public class AndroidLogAdapter  implements LogAdapter{
    @NonNull private final FormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();;
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }
}
