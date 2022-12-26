package com.example.projectfinaltest;

import java.io.Serializable;

public class myVehicles implements Serializable {
    String TenXe;
    String LoaiXe;
    String HangXe;
    String bienSo;
    String ngaySoHuu;
    int  kmBanDau;
    int kmHienTai;
    int SoLanThaiNho;
    String nhacTheoNgay;
    String nhacTheoKm;
    int stt;

    public myVehicles(String tenXe, String loaiXe, String hangXe, String bienSo, String ngaySoHuu, int kmBanDau, int kmHienTai, int soLanThaiNho, String nhacTheoNgay, String nhacTheoKm, int stt) {
        this.TenXe = tenXe;
        this.LoaiXe = loaiXe;
        this.HangXe = hangXe;
        this.bienSo = bienSo;
        this.ngaySoHuu = ngaySoHuu;
        this.kmBanDau = kmBanDau;
        this.kmHienTai = kmHienTai;
        this.SoLanThaiNho = soLanThaiNho;
        this.nhacTheoNgay = nhacTheoNgay;
        this.nhacTheoKm = nhacTheoKm;
        this.stt = stt;
    }

    public String getTenXe() {
        return TenXe;
    }

    public void setTenXe(String tenXe) {
        TenXe = tenXe;
    }

    public String getLoaiXe() {
        return LoaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        LoaiXe = loaiXe;
    }

    public String getHangXe() {
        return HangXe;
    }

    public void setHangXe(String hangXe) {
        HangXe = hangXe;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getNgaySoHuu() {
        return ngaySoHuu;
    }

    public void setNgaySoHuu(String ngaySoHuu) {
        this.ngaySoHuu = ngaySoHuu;
    }

    public int getKmBanDau() {
        return kmBanDau;
    }

    public void setKmBanDau(int kmBanDau) {
        this.kmBanDau = kmBanDau;
    }

    public int getKmHienTai() {
        return kmHienTai;
    }

    public void setKmHienTai(int kmHienTai) {
        this.kmHienTai = kmHienTai;
    }

    public int getSoLanThaiNho() {
        return SoLanThaiNho;
    }

    public void setSoLanThaiNho(int soLanThaiNho) {
        SoLanThaiNho = soLanThaiNho;
    }

    public String getNhacTheoNgay() {
        return nhacTheoNgay;
    }

    public void setNhacTheoNgay(String nhacTheoNgay) {
        this.nhacTheoNgay = nhacTheoNgay;
    }

    public String getNhacTheoKm() {
        return nhacTheoKm;
    }

    public void setNhacTheoKm(String nhacTheoKm) {
        this.nhacTheoKm = nhacTheoKm;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public myVehicles(){};


}