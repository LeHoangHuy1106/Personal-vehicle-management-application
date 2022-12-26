package com.example.projectfinaltest;

import java.io.Serializable;

public class myNote implements Serializable {
    String loaiNote;
    String xeNote;
    String gioNote;
    String ngayNote;
    String kmNote;
    String diaDiemNote;
    String litNote;
    String noteNote;
    String soTienNote;
    int stt;

    public myNote(){}

    public String getLoaiNote() {
        return loaiNote;
    }

    public void setLoaiNote(String loaiNote) {
        this.loaiNote = loaiNote;
    }

    public String getXeNote() {
        return xeNote;
    }

    public void setXeNote(String xeNote) {
        this.xeNote = xeNote;
    }

    public String getGioNote() {
        return gioNote;
    }

    public void setGioNote(String gioNote) {
        this.gioNote = gioNote;
    }

    public String getNgayNote() {
        return ngayNote;
    }

    public void setNgayNote(String ngayNote) {
        this.ngayNote = ngayNote;
    }

    public String getKmNote() {
        return kmNote;
    }

    public void setKmNote(String kmNote) {
        this.kmNote = kmNote;
    }

    public String getDiaDiemNote() {
        return diaDiemNote;
    }

    public void setDiaDiemNote(String diaDiemNote) {
        this.diaDiemNote = diaDiemNote;
    }

    public String getLitNote() {
        return litNote;
    }

    public void setLitNote(String litNote) {
        this.litNote = litNote;
    }

    public String getNoteNote() {
        return noteNote;
    }

    public void setNoteNote(String noteNote) {
        this.noteNote = noteNote;
    }

    public String getSoTienNote() {
        return soTienNote;
    }

    public void setSoTienNote(String soTienNote) {
        this.soTienNote = soTienNote;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public myNote(String loaiNote, String xeNote, String gioNote, String ngayNote, String kmNote, String diaDiemNote, String litNote, String noteNote, String soTienNote, int stt) {
        this.loaiNote = loaiNote;
        this.xeNote = xeNote;
        this.gioNote = gioNote;
        this.ngayNote = ngayNote;
        this.kmNote = kmNote;
        this.diaDiemNote = diaDiemNote;
        this.litNote = litNote;
        this.noteNote = noteNote;
        this.soTienNote = soTienNote;
        this.stt = stt;
    }
}
