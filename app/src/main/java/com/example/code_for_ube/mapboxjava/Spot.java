package com.example.code_for_ube.mapboxjava;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Spot extends RealmObject {
    @PrimaryKey

    /**
     * ID
     */
    public long id;

    /**
     * タイトル
     */
    public String title;

    /**
     * 内容
     */
    public String bodyText;

    /**
     * 住所
     */
    public String address;

    /**
     * 営業時間
     */
    public String businessHours;

    /**
     * 休日
     */
    public String holiday;

    /**
     * 緯度
     */
    public String latitude;

    /**
     * 経度
     */
    public String longitude;

    /**
     * 電話番号
     */
    public String phone;

    /**
     * 料金
     */
    public String price;

    /**
     * アクセス
     */
    public String trafficData;

    /**
     * 画像
     */
    public byte[] image;

    /**
     * 日付
     */
    public  String date;
}
