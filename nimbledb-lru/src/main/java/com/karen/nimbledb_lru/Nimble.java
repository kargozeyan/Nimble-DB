package com.karen.nimbledb_lru;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.LruCache;

public class Nimble {
    private static final String DEF_PATH = "default";

    @SuppressLint("StaticFieldLeak")
    private static Context context = null;

    private static LruCache<String, LruCache<String, NimbleStorage<?>>> lruCache =
            new LruCache<>(Integer.MAX_VALUE);

    public static void initialize(Context _context) {
        context = _context.getApplicationContext();
    }

    public static NimbleSection on() {
        return on(DEF_PATH);
    }

    public static NimbleSection on(String name) {
        if (context == null) {
            throw new RuntimeException("context is null, please call initialize method");
        }

        if (lruCache.get(name) == null) {
            lruCache.put(name, new LruCache<String, NimbleStorage<?>>(Integer.MAX_VALUE));
        }

        return new NimbleSection(name, context, lruCache.get(name));
    }
}
