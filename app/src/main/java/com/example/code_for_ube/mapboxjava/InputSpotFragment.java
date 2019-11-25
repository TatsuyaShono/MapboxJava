package com.example.code_for_ube.mapboxjava;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.realm.Realm;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class InputSpotFragment extends Fragment {

    private static final String SPOT_ID = "SPOT_ID";
    private static final int REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    private Realm mRealm;
    private long mSpotId;
    private EditText mTitleEdit;
    private EditText mBodyEdit;
    private EditText mAddress;
    private EditText mLatitudeEdit;
    private EditText mLongitudeEdit;
    private ImageView mSpotImage;

    public static InputSpotFragment newInstance(long spotId) {
        InputSpotFragment fragment = new InputSpotFragment();
        Bundle args = new Bundle();
        /**
         * putLongメソッド
         * 機能：Bundleにキーと値のセットを格納します
         * 書式：putLong(String key, int value)
         * 引数：key       値を格納する際のキー
         *      value     格納する値
         */
        args.putLong(SPOT_ID, spotId);
        /**
         * setArgumentsメソッド
         * 機能：フラグメントにデータを保存します
         * 書式：void setArguments(Bundle args)
         * 引数：args  データを格納したBundleインスタンス
         */
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSpotId = getArguments().getLong(SPOT_ID);
        }
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_spot, container, false);
        mTitleEdit = v.findViewById(R.id.title);
        mBodyEdit = v.findViewById(R.id.bodyEditText);
        mAddress = v.findViewById(R.id.addressEditText);
        mLatitudeEdit = v.findViewById(R.id.latitudeEditText);
        mLongitudeEdit = v.findViewById(R.id.longitudeEditText);
        mSpotImage = v.findViewById(R.id.spot_photo);

        mSpotImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                requestReadStorage(view);
            }
        });

        /**
         * addTextChangedListenerメソッド
         * 機能：TextViewの文字が変更されたときに呼び出されるリスナーを設定します
         * 書式：void addTextChangedListener(TextWatcher watcher)
         * 引数：TextWatcherインターフェイスを実装したクラスのインスタンス
         */
        mTitleEdit.addTextChangedListener(new TextWatcher() {
            /**
             * 機能：文字列が変更される前に呼び出されます
             * 書式：void beforeTextChanged(CharSequence s, int start, int count, int after)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param count     ：変更された文字列の数
             * @param after     ：追加された文字列の数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             * 機能：文字が入力される時に呼び出されます
             * 書式：void onTextChanged(CharSequence s, int start, int before, int count)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param before    ：削除される文字列の数
             * @param count     ：追加された文字列の総数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * afterTextChangedメソッド
             * 機能：文字列が変更されたことを通知するために呼び出されます
             * 書式：void afterTextChanged(Editable s)
             * @param s         ：変更後の文字列
             */
            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot
                                = realm.where(Spot.class).equalTo("id", mSpotId).findFirst();
                        spot.title = s.toString();
                    }
                });
            }
        });
        /**
         * addTextChangedListenerメソッド
         * 機能：TextViewの文字が変更されたときに呼び出されるリスナーを設定します
         * 書式：void addTextChangedListener(TextWatcher watcher)
         * 引数：TextWatcherインターフェイスを実装したクラスのインスタンス
         */
        mBodyEdit.addTextChangedListener(new TextWatcher() {
            /**
             * 機能：文字列が変更される前に呼び出されます
             * 書式：void beforeTextChanged(CharSequence s, int start, int count, int after)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param count     ：変更された文字列の数
             * @param after     ：追加された文字列の数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             * 機能：文字が入力される時に呼び出されます
             * 書式：void onTextChanged(CharSequence s, int start, int before, int count)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param before    ：削除される文字列の数
             * @param count     ：追加された文字列の総数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * afterTextChangedメソッド
             * 機能：文字列が変更されたことを通知するために呼び出されます
             * 書式：void afterTextChanged(Editable s)
             * @param s         ：変更後の文字列
             */
            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot
                                = realm.where(Spot.class).equalTo("id", mSpotId).findFirst();
                        spot.bodyText = s.toString();
                    }
                });
            }
        });
        /**
         * addTextChangedListenerメソッド
         * 機能：TextViewの文字が変更されたときに呼び出されるリスナーを設定します
         * 書式：void addTextChangedListener(TextWatcher watcher)
         * 引数：TextWatcherインターフェイスを実装したクラスのインスタンス
         */
        mAddress.addTextChangedListener(new TextWatcher() {
            /**
             * 機能：文字列が変更される前に呼び出されます
             * 書式：void beforeTextChanged(CharSequence s, int start, int count, int after)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param count     ：変更された文字列の数
             * @param after     ：追加された文字列の数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             * 機能：文字が入力される時に呼び出されます
             * 書式：void onTextChanged(CharSequence s, int start, int before, int count)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param before    ：削除される文字列の数
             * @param count     ：追加された文字列の総数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * afterTextChangedメソッド
             * 機能：文字列が変更されたことを通知するために呼び出されます
             * 書式：void afterTextChanged(Editable s)
             * @param s         ：変更後の文字列
             */
            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot
                                = realm.where(Spot.class).equalTo("id", mSpotId).findFirst();
                        spot.address = s.toString();
                    }
                });
            }
        });
        /**
         * addTextChangedListenerメソッド
         * 機能：TextViewの文字が変更されたときに呼び出されるリスナーを設定します
         * 書式：void addTextChangedListener(TextWatcher watcher)
         * 引数：TextWatcherインターフェイスを実装したクラスのインスタンス
         */
        mAddress.addTextChangedListener(new TextWatcher() {
            /**
             * 機能：文字列が変更される前に呼び出されます
             * 書式：void beforeTextChanged(CharSequence s, int start, int count, int after)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param count     ：変更された文字列の数
             * @param after     ：追加された文字列の数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             * 機能：文字が入力される時に呼び出されます
             * 書式：void onTextChanged(CharSequence s, int start, int before, int count)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param before    ：削除される文字列の数
             * @param count     ：追加された文字列の総数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * afterTextChangedメソッド
             * 機能：文字列が変更されたことを通知するために呼び出されます
             * 書式：void afterTextChanged(Editable s)
             * @param s         ：変更後の文字列
             */
            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot
                                = realm.where(Spot.class).equalTo("id", mSpotId).findFirst();
                        spot.address = s.toString();
                    }
                });
            }
        });
        /**
         * addTextChangedListenerメソッド
         * 機能：TextViewの文字が変更されたときに呼び出されるリスナーを設定します
         * 書式：void addTextChangedListener(TextWatcher watcher)
         * 引数：TextWatcherインターフェイスを実装したクラスのインスタンス
         */
        mLatitudeEdit.addTextChangedListener(new TextWatcher() {
            /**
             * 機能：文字列が変更される前に呼び出されます
             * 書式：void beforeTextChanged(CharSequence s, int start, int count, int after)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param count     ：変更された文字列の数
             * @param after     ：追加された文字列の数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             * 機能：文字が入力される時に呼び出されます
             * 書式：void onTextChanged(CharSequence s, int start, int before, int count)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param before    ：削除される文字列の数
             * @param count     ：追加された文字列の総数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * afterTextChangedメソッド
             * 機能：文字列が変更されたことを通知するために呼び出されます
             * 書式：void afterTextChanged(Editable s)
             * @param s         ：変更後の文字列
             */
            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot
                                = realm.where(Spot.class).equalTo("id", mSpotId).findFirst();
                        spot.latitude = s.toString();
                    }
                });
            }
        });
        /**
         * addTextChangedListenerメソッド
         * 機能：TextViewの文字が変更されたときに呼び出されるリスナーを設定します
         * 書式：void addTextChangedListener(TextWatcher watcher)
         * 引数：TextWatcherインターフェイスを実装したクラスのインスタンス
         */
        mLongitudeEdit.addTextChangedListener(new TextWatcher() {
            /**
             * 機能：文字列が変更される前に呼び出されます
             * 書式：void beforeTextChanged(CharSequence s, int start, int count, int after)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param count     ：変更された文字列の数
             * @param after     ：追加された文字列の数
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            /**
             * 機能：文字が入力される時に呼び出されます
             * 書式：void onTextChanged(CharSequence s, int start, int before, int count)
             * @param s         ：入力された文字列
             * @param start     ：入力された文字列の位置
             * @param before    ：削除される文字列の数
             * @param count     ：追加された文字列の総数
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            /**
             * afterTextChangedメソッド
             * 機能：文字列が変更されたことを通知するために呼び出されます
             * 書式：void afterTextChanged(Editable s)
             * @param s         ：変更後の文字列
             */
            @Override
            public void afterTextChanged(Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot
                                = realm.where(Spot.class).equalTo("id", mSpotId).findFirst();
                        spot.longitude = s.toString();
                    }
                });
            }
        });

        return v;
    }

    private void requestReadStorage(View view){
        /**
         * checkSelfPermissionメソッド
         * 機能：パーミッションが許可されているか確認します
         * 書式：int checkSelfPermission(Context context, String permission)
         * 引数：context       コンテキスト
         *      permission    チェックするパーミッション名
         * 戻り値：パーミッションが許可されていればPERMISSION_GRANTED、許可されていなければPERMISSION_DENIEDを返します
         */
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            /**
             * shouldShowRequestPermissionRationaleメソッド
             * 機能：権限の許可を要求する論理的根拠を示すUIを表示するかどうかを取得します
             * 書式：boolean shouldShowRequestPermissionRationale(String permission)
             * 引数：permission    チェックするパーミッション名
             */
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view, R.string.rationale,Snackbar.LENGTH_LONG).show();
            }
            /**
             * requestPermissionsメソッド
             * 機能：アプリが要求された権限を持っていない場合、ユーザーにそれを受け入れるためのUIを提示します
             *      ユーザーが要求されたアクセス許可を受け入れるかもしくは拒否すると、
             *      アクセス許可が付与されているかどうかを通知するコールバックメソッドが呼び出されます
             * 書式：void requestPermissions(String[] permissions, int requestCode)
             * 引数：permissions   要求するパーミッション名の配列
             *      requestCode   一意なコード。これはonRequestPermissionsResultの引数として受け取ります
             */
            requestPermissions(new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
        } else {
            pickImage();
        }
    }

    private void pickImage(){
        /**
         * Intentクラスのコンストラクタ（暗黙的なインテント）
         * 書式：Intent(String action, Uri uri)
         * 引数：action    インテントに設定するアクション。Intentクラスに定義されている定数で指定します
         *      uri       インテントに設定するデータのURI
         */
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /**
         * setTypeメソッド
         * 機能：インテントにMIMEタイプを指定します
         * 書式：Intent setType(String type)
         * 引数：type  インテントに設定するMIMEタイプ
         */
        intent.setType("image/*");
        /**
         * startActivityForResultメソッド
         * 機能：指定したアクティビティを起動する
         * 書式：void startActivityForResult(Intent intent, int requestCode)
         * 引数：intent        起動する機能、またはアクティビティをセットしたインデント
         *      requestCode   起動したアクティビティを特定するための整数
         */
        /**
         * createChooserメソッド
         * 機能：起動する機能を選択するダイアログを表示します
         * 書式：Intent createChooser(Intent target, CharSequence title)
         * 引数：target    ユーザーが選択した機能(アプリ）が渡されます
         *      title     ダイアログに表示する文字列
         */
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_image)), REQUEST_CODE);
    }

    /**
     * onActivityResultメソッド
     * 機能：呼び出し先のアクティビティが終了したときに呼ばれます
     * 書式：void onActivityResult(int requestCode, int resultCode, Intent data)
     * @param requestCode       ：どの画面から戻ってきたかを判別する整数
     * @param resultCode        ：呼び出し先から渡される結果コード
     * @param data              ：呼び出し先から渡されるインテント
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            Uri uri = (data == null) ? null : data.getData();
            if(uri != null){
                try{
                    Bitmap img = MyUtils.getImageFromStream(getActivity().getContentResolver(), uri);
                    mSpotImage.setImageBitmap(img);
                } catch (IOException e){
                    e.printStackTrace();
                }
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Spot spot = realm.where(Spot.class)
                                .equalTo("id", mSpotId)
                                /**
                                 * findFirstメソッド
                                 * 機能：クエリ条件を満たす最初のレコードを検索します
                                 * 書式：E findFirst()
                                 * 戻り値：クエリ条件を満たす最初のモデルオブジェクト
                                 */
                                .findFirst();
                        BitmapDrawable bitmap = (BitmapDrawable) mSpotImage.getDrawable();
                        byte[] bytes = MyUtils.getByteFromImage(bitmap.getBitmap());
                        if(bytes != null && bytes.length > 0){
                            spot.image = bytes;
                        }
                    }
                });
            }
        }
    }

    /**
     * onRequestPermissionsResultメソッド
     * 機能；ユーザーがパーミッションの許可/不許可を選択した時に呼ばれます
     * 書式：void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
     * @param requestCode   ：requestPermissionsの第2引数に渡したコード
     * @param permissions   ：requestPermissionsの第1引数に渡したパーミッションの文字列
     * @param grantResults  ：第2引数permissionsに対応する権限付与結果。
     *                      　「許可」の場合、PERMISSION_GRANTEDとなり、
     *                      　「不許可」の場合、PERMISSION_DENIEDとなる
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Snackbar.make(mSpotImage, R.string.permission_deny, Snackbar.LENGTH_LONG).show();
            } else {
                pickImage();
            }
        }
    }
}
