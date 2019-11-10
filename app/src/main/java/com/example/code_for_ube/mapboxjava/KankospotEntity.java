package com.example.code_for_ube.mapboxjava;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

/**
 * 観光スポットを管理するEntityクラス。
 */
@Data
public class KankospotEntity {

    /**
     * ID
     */
    @CsvBindByName(column = "id", required = true)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * タイトル
     */
    @CsvBindByName(column = "title", required = true)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 本文
     */
    @CsvBindByName(column = "text", required = true)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 種別
     */
    @CsvBindByName(column = "categories", required = true)
    private String categories;

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     * タグ
     */
    @CsvBindByName(column = "tags", required = true)
    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * ベースネーム
     */
    @CsvBindByName(column = "basename", required = true)
    private String basename;

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    /**
     * 住所
     */
    @CsvBindByName(column = "address", required = true)
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 写真
     */
    @CsvBindByName(column = "artimg", required = true)
    private String artimg;

    public String getArtimg() {
        return artimg;
    }

    public void setArtimg(String artimg) {
        this.artimg = artimg;
    }

    /**
     * 営業時間
     */
    @CsvBindByName(column = "eigyoujikan", required = true)
    private String eigyoujikan;

    public String getEigyoujikan() {
        return eigyoujikan;
    }

    public void setEigyoujikan(String eigyoujikan) {
        this.eigyoujikan = eigyoujikan;
    }

    /**
     * 休日
     */
    @CsvBindByName(column = "holiday", required = true)
    private String holiday;

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    /**
     * 緯度
     */
    @CsvBindByName(column = "latitude", required = true)
    private Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setHoliday(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 経度
     */
    @CsvBindByName(column = "longitude", required = true)
    private Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 駐車場
     */
    @CsvBindByName(column = "parking", required = true)
    private String parking;

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    /**
     * 電話番号
     */
    @CsvBindByName(column = "phone", required = true)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 値段
     */
    @CsvBindByName(column = "price", required = true)
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * アクセス情報
     */
    @CsvBindByName(column = "trafficdata", required = true)
    private String trafficdata;

    public String getTrafficdata() {
        return trafficdata;
    }

    public void setTrafficdata(String trafficdata) {
        this.trafficdata = trafficdata;
    }

}
