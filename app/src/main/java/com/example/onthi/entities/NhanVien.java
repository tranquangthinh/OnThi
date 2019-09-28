package com.example.onthi.entities;

public class NhanVien {
    private int id;
    private String name;
    private int gioiTinh;

    private PhongBan pb;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public PhongBan getPb() {
        return pb;
    }

    public void setPb(PhongBan pb) {
        this.pb = pb;
    }

    public NhanVien() {
    }

    public NhanVien(int id, String name, int gioiTinh, PhongBan pb) {
        this.id = id;
        this.name = name;
        this.gioiTinh = gioiTinh;
        this.pb = pb;
    }

}
