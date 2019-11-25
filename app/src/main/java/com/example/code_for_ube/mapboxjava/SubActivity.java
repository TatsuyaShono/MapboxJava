package com.example.code_for_ube.mapboxjava;

import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;

public class SubActivity extends AppCompatActivity
    implements  SpotListFragment.OnFragmentInteractionListener{

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        //createTestData();
        showSpotList();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    private void createTestData(){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //idフィールドの最大値を取得
                Number maxId = mRealm.where(Spot.class).max("id");
                long nextId = 0;
                if(maxId != null) nextId = maxId.longValue() + 1;
                //createObjectではIDを渡してオブジェクトを作成する
                Spot spot
                        = realm.createObject(Spot.class, new Long(nextId));
                spot.title = "テストタイトル";
                spot.bodyText = "テスト本文です。";
            }
        });
    }

    private void showSpotList(){
        /**
         * getSupportFragmentManagerメソッド
         * 機能：FragmentManagerクラスのインスタンスを取得します
         * 書式：FragmentManager getSupportFragmentManager()
         */
        FragmentManager manager = getSupportFragmentManager();
        /**
         * findFragmentByTagメソッド
         * 機能：トランザクションに追加する時につけた名前でフラグメントを検索し、
         *      見つかった場合はフラグメントを、見つからなかった場合はNullを返します
         * 書式：Fragment findFragmentByTag(String tag)
         * 引数：tag   検索するタグ文字列
         */
        Fragment fragment = manager.findFragmentByTag("SpotListFragment");
        if(fragment == null){
            fragment = new SpotListFragment();
            /**
             * beginTransactionメソッド
             * 機能：フラグメントへの操作を開始します
             * 書式：FragmentTransaction beginTransaction()
             */
            FragmentTransaction transaction = manager.beginTransaction();
            /**
             * addメソッド
             * 機能：アクティビティにフラグメントを追加します
             * 書式：FragmentTransaction add(int containerViewId, Fragment fragment, String tag)
             * 引数：containerViewId   フラグメントで置き換えるビューのID
             *      fragment          追加するフラグメント
             *      tag               設定するタグ文字列
             */
            transaction.add(R.id.content, fragment, "SpotListFragment");
            /**
             * commitメソッド
             * 機能：トランザクションへの変更を反映します
             * 書式：int commit()
             */
            transaction.commit();
        }
    }

    @Override
    public void onAddSpotSelected(){
        mRealm.beginTransaction();
        Number maxId = mRealm.where(Spot.class).max("id");
        long nextId = 0;
        if(maxId != null){
            nextId = maxId.longValue() + 1;
        }
        Spot spot = mRealm.createObject(Spot.class, new Long(nextId));
        spot.date = new SimpleDateFormat("MMM d", Locale.US).format(new Date());
        mRealm.commitTransaction();

        InputSpotFragment inputSpotFragment = InputSpotFragment.newInstance(nextId);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        /**
         * replaceメソッド
         * 機能：アクティビティにフラグメントを追加します。
         *      既に同じcontainerViewIdを持つフラグメントがあれば全て削除してから追加します
         * 書式：FragmentTransaction replace(int containerViewId, Fragment fragment, String tag)
         * 引数：containerViewId   フラグメントで置き換える箇所のIDを渡します
         *      fragment          追加するフラグメント
         *      tag               設定するタグ文字列
         */
        transaction.replace(R.id.content, inputSpotFragment, "InputSpotFragment");
        /**
         * addToBackStackメソッド
         * 機能：トランザクションをバックスタックに追加します
         * 書式：FragmentTransaction addBackStack(String name)
         * 引数：name  ：バックスタックの状態に名前をつけます。もしくはnullを渡します
         */
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
