package com.example.code_for_ube.mapboxjava;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class MyUtils {

    /**
     * getImageFromByteメソッド
     * 機能：引数にDBに格納してあるバイト配列を受け取って、
     *      Android SDKで利用できるようにBitmapクラスのインスタンスに変換して返します
     * @param bytes
     * @return
     */
    public static Bitmap getImageFromByte(byte[] bytes) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        /**
         * decodeByteArrayメソッド
         * 機能：指定されたバイト配列からBitmapクラスのインスタンスを作成します
         * 書式：Bitmap decodeByteArray(byte[] data, int offset, int length, BitmapFactory.Options opts)
         * 引数：data      圧縮画像データのバイト配列
         *      offset    デコーダが解析を開始する場所のimageDataへのオフセット
         *      length    解析を行うバイト数
         *      opts      縮小率を制御するオプションと、画像を完全にデコードするかどうか、またはサイズを返すのみにするかを制御するオプション
         */
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
        int bitmapSIze = 1;
        if((opt.outHeight * opt.outWidth) > 500000){
            double outSize = (double)(opt.outHeight * opt.outWidth) / 500000;
            bitmapSIze = (int)(Math.sqrt(outSize) + 1);
        }

        opt.inJustDecodeBounds = false;
        opt.inSampleSize = bitmapSIze;
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
        return bmp;
    }

    public static byte[] getByteFromImage(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        /**
         * compressメソッド
         * 機能：Bitmapを指定した画像フォーマットに変換して指定したストリームへ渡します
         * 書式：boolean compress(Bitmap.CompressFormat format, int quality, OutputStream stream)
         * 引数：format    画像フォーマットをJPEG, PNG, WEBPのいずれかで指定します
         *      quality   画質を0~100の整数で指定します
         *      stream    出力先ストリーム
         */
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static  Bitmap getImageFromStream(ContentResolver resolver, Uri uri)
            throws IOException{
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        /**
         * openInputStreamメソッド
         * 機能：URIよりストリームをオープンします
         * 書式：InputStream openInputStream(Uri uri)
         * 引数：uri   ストリームの場所を表すURI
         */
        InputStream is = resolver.openInputStream(uri);
        /**
         * decodeStreamメソッド
         * 機能：ストリームからビットマップを生成します
         * 書式：Bitmap decodeStream(InputStream is, Rect outPadding, BitmapFactory.Options opts)
         * 引数：is            入力ストリーム
         *      outPadding    null以外を渡すとビットマップのパディングを指定する
         *      opts          縮小率を制御するオプションと、画像を完全にデコードするかどうか、
         *                    またはサイズが返すのみにするかを制御するオプション
         */
        BitmapFactory.decodeStream(is, null, opt);
        is.close();
        int bitmapSize = 1;
        if((opt.outHeight * opt.outWidth) > 500000) {
            double outSize = (double)(opt.outHeight * opt.outWidth) / 500000;
            bitmapSize = (int)(Math.sqrt(outSize) + 1);
        }

        opt.inJustDecodeBounds = false;
        opt.inSampleSize = bitmapSize;
        is = resolver.openInputStream(uri);
        Bitmap bmp = BitmapFactory.decodeStream(is, null, opt);
        is.close();
        return bmp;
    }

    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color){
        Drawable normalDrawable = item.getIcon();
        /**
         * wrapメソッド
         * 機能：tint(色付け)ができるようにDrawableをラップします
         * 書式：Drawable wrap(Drawable drawable)
         * 引数：drawable  ：対象となるDrawable
         * 戻り値：すべてのAPIレベルでtint(色付け)が可能なDrawableオブジェクト
         */
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        /**
         * setTintメソッド
         * 機能：Drawableに着色する色を指定します
         * 書式：void setTint(Drawable drawable, int tint)
         * 引数：drawable  ：対象となるDrawable
         *      tint      ：このDrawableを塗りつぶすために使用する色
         */
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(context, color));
        item.setIcon(wrapDrawable);
    }
}
