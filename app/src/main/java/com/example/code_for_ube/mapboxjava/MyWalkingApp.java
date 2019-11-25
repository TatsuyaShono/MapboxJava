package com.example.code_for_ube.mapboxjava;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * データベースの設定処理
 */
public class MyWalkingApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
