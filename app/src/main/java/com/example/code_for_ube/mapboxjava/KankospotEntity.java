package com.example.code_for_ube.mapboxjava;

import com.univocity.parsers.annotations.*;
import lombok.Data;

/**
 * 観光スポットを管理するEntityクラス。
 */
@Data
public class KankospotEntity {

    /**
     * ID
     */
    @Parsed(field = "id")
    public Integer id = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * タイトル
     */
    @Parsed(field = "title")
    public String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 本文
     */
    @Parsed(field = "text")
    public String text = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 種別
     */
    @Parsed(field = "categories")
    public String categories = "";

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     * タグ
     */
    @Parsed(field = "tags")
    public String tags = "";

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * ベースネーム
     */
    @Parsed(field = "basename")
    public String basename = "";

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    /**
     * 住所
     */
    @Parsed(field = "address")
    public String address = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 写真
     */
    @Parsed(field = "artimg")
    public String artimg = "";

    public String getArtimg() {
        return artimg;
    }

    public void setArtimg(String artimg) {
        this.artimg = artimg;
    }

    /**
     * 営業時間
     */
    @Parsed(field = "eigyoujikan")
    public String eigyoujikan = "";

    public String getEigyoujikan() {
        return eigyoujikan;
    }

    public void setEigyoujikan(String eigyoujikan) {
        this.eigyoujikan = eigyoujikan;
    }

    /**
     * 休日
     */
    @Parsed(field = "holiday")
    public String holiday = "";

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    /**
     * 緯度
     */
    @Parsed(field = "latitude")
    public Double latitude = 0d;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 経度
     */
    @Parsed(field = "longitude")
    public Double longitude = 0d;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 駐車場
     */
    @Parsed(field = "parking")
    public String parking = "";

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    /**
     * 電話番号
     */
    @Parsed(field = "phone")
    public String phone = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 値段
     */
    @Parsed(field = "price")
    public Integer price = 0;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * アクセス情報
     */
    @Parsed(field = "trafficdata")
    public String trafficdata = "";

    public String getTrafficdata() {
        return trafficdata;
    }

    public void setTrafficdata(String trafficdata) {
        this.trafficdata = trafficdata;
    }

}
