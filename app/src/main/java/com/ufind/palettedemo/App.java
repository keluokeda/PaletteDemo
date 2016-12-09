package com.ufind.palettedemo;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();
    }

    private void initImageLoader() {
        int memoryCacheSize = (int) Runtime.getRuntime().maxMemory() / 8;
        File file = new File(getCacheDir(), "image");
        file.mkdirs();
        ImageLoaderConfiguration configuration =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .threadPoolSize(20)
                        .memoryCacheSize(memoryCacheSize)
                        .diskCacheSize(50 * 1024 * 1024)
                        .diskCache(new UnlimitedDiskCache(file))
                        .build();
        ImageLoader.getInstance().init(configuration);


//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable throwable) {
//                Logger.d(throwable);
//            }
//        });
    }
}
