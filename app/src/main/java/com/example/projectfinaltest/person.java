package com.example.projectfinaltest;

import java.io.Serializable;

public class person implements Serializable {
    private String Gmail;
    private String hoTen;
    private String ngaySinh;
    private String sdt;
    private String ngheNghiep;
    public person(){};


    public person(String Gmail, String hoTen, String ngaySinh, String sdt, String ngheNghiep) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.ngheNghiep = ngheNghiep;
        this.Gmail= Gmail;
    }


    public String getGmail() {
        return Gmail;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }
}



