package com.example.code_for_ube.mapboxjava;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.palette.graphics.Palette;
import io.realm.Realm;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowSpotActivity extends AppCompatActivity {

    public static final String SPOT_ID = "SPOT_ID";
    private static final long ERR_CD = -1;

    private Realm mRealm;
    private Bitmap mBitmap;
    private String mBodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_spot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                /**
                 * putExtraメソッド
                 * 機能：暗黙的なインテントに追加情報を設定します
                 * 書式：putExtra(String name, Bundle value)
                 * 引数：name      Intentクラスに定義された定数
                 *      value     追加情報として送るデータ
                 */
                shareIntent.putExtra(Intent.EXTRA_TEXT, mBodyText);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRealm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        final long spotId = intent.getLongExtra(SPOT_ID, ERR_CD);

        TextView body = findViewById(R.id.body);
        ImageView imageView = findViewById(R.id.toolbar_image);
        NestedScrollView scrollView = findViewById(R.id.scroll_view);

        Spot spot = mRealm.where(Spot.class).equalTo("id", spotId).findFirst();
        CollapsingToolbarLayout layout = findViewById(R.id.toolbar_layout);
        layout.setTitle(spot.title);

        mBodyText = spot.bodyText;
        body.setText(mBodyText);

        byte[] bytes = spot.image;
        if(bytes != null && bytes.length > 0){
            mBitmap = MyUtils.getImageFromByte(bytes);
            imageView.setImageBitmap(mBitmap);
            /**
             * fromメソッド
             * 機能：BitmapからPaletteの生成を行うPalette.Builderクラスのインスタンスを返します
             * 書式：Palette.Builder from(Bitmap bitmap)
             * 引数：bitmap    対象となる画像
             */
            /**
             * generateメソッド
             * 機能：Paletteクラスのインスタンスを生成します
             * 書式：Palette generate()
             */
            Palette palette = Palette.from(mBitmap).generate();
            /**
             * getLightVibrantColorメソッド
             * 機能：パレットから鮮やかな色(明るい)を取得して返します
             * 書式：int getLightVibrantColor(int defaultColor)
             * 引数：defaultColor  画像から取得できなかった時に返すデフォルトの色
             */
            int titleColor = palette.getLightVibrantColor(Color.WHITE);
            int bodyColor = palette.getDarkMutedColor(Color.BLACK);
            int scrimColor = palette.getMutedColor(Color.DKGRAY);
            int iconColor = palette.getLightMutedColor(Color.LTGRAY);

            //折りたたみ可能タイトル部分の文字列を指定します
            layout.setExpandedTitleColor(titleColor);
            //コンテンツのスクリム色を指定します
            layout.setContentScrimColor(scrimColor);
            //ビューの背景色を設定します
            scrollView.setBackgroundColor(bodyColor);
            //文字色を設定します
            body.setTextColor(titleColor);
            /**
             * setBackgroundTintListメソッド
             * 機能：背景を着色する色を適用します
             * 書式：void setBackgroundTintList(ColorStateList tint)
             * 引数：tint  適用するColorStateList
             */
            /**
             * valueOfメソッド
             * 機能：単色からなるColorStateListを生成します
             * 書式：ColorStateList ValueOf(int color)
             * 引数：color 設定する色
             */
            fab.setBackgroundTintList(ColorStateList.valueOf(iconColor));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
