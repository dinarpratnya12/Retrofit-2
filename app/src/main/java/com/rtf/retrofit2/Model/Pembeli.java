package com.rtf.retrofit2.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pembeli {
    @SerializedName("id_pembeli")
    private String idPembeli;
    @SerializedName("nama")
    private String nama;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("telpn")
    private String telpn;
    @SerializedName("photo_id")
    private String photoId; private String action;

    public Pembeli() {

    }
    public Pembeli(String idPembeli, String nama, String alamat, String telpn, String photoId, String action) {
        this.idPembeli = idPembeli;
        this.nama = nama;
        this.alamat = alamat;
        this.telpn = telpn;
        this.photoId = photoId;
        this.action = action;
    }
    public String getIdPembeli() {
        return idPembeli;
    }
    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String getTelpn() {
        return telpn;
    }
    public void setTelpn(String telpn) {
        this.telpn = telpn;
    }
    public String getPhotoId() {
        return photoId;
    }
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
}