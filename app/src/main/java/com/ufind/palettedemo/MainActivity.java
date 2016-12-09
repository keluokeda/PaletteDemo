package com.ufind.palettedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.iv_light)
    ImageView mIvLight;
    @BindView(R.id.fm_light)
    FrameLayout mFmLight;
    @BindView(R.id.iv_light_vibrant)
    ImageView mIvLightVibrant;
    @BindView(R.id.fm_light_vibrant)
    FrameLayout mFmLightVibrant;
    @BindView(R.id.iv_light_muted)
    ImageView mIvLightMuted;
    @BindView(R.id.fm_light_muted)
    FrameLayout mFmLightMuted;
    @BindView(R.id.iv_dark)
    ImageView mIvDark;
    @BindView(R.id.fm_dark)
    FrameLayout mFmDark;
    @BindView(R.id.iv_dark_vibrant)
    ImageView mIvDarkVibrant;
    @BindView(R.id.fm_dark_vibrant)
    FrameLayout mFmDarkVibrant;
    @BindView(R.id.iv_dark_muted)
    ImageView mIvDarkMuted;
    @BindView(R.id.fm_dark_muted)
    FrameLayout mFmDarkMuted;
    private ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        imageViews = new ArrayList<>(6);
        imageViews.add(mIvDark);
        imageViews.add(mIvDarkMuted);
        imageViews.add(mIvDarkVibrant);
        imageViews.add(mIvLight);
        imageViews.add(mIvLightMuted);
        imageViews.add(mIvLightVibrant);

    }

    @OnClick(R.id.button)
    public void onClick() {

        Intent intent = new Intent(this, MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        startActivityForResult(intent, 3333);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 3333) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            String imagePath = "file://" + path.get(0);
            ImageLoader.getInstance().displayImage(imagePath, mIvDark, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    onComplete(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }
    }

    private void onComplete(Bitmap bitmap) {
        for (ImageView view : imageViews) {
            view.setImageBitmap(bitmap);
        }
        Palette palette = Palette.from(bitmap).generate();
        mFmDark.setBackgroundColor(palette.getMutedColor(Color.BLACK));
        mFmDarkMuted.setBackgroundColor(palette.getDarkMutedColor(Color.BLACK));
        mFmDarkVibrant.setBackgroundColor(palette.getDarkVibrantColor(Color.BLACK));

        mFmLight.setBackgroundColor(palette.getVibrantColor(Color.BLACK));
        mFmLightMuted.setBackgroundColor(palette.getLightMutedColor(Color.BLACK));
        mFmLightVibrant.setBackgroundColor(palette.getLightVibrantColor(Color.BLACK));

    }
}
