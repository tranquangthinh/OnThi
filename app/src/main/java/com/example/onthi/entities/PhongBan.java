package com.example.onthi.entities;

import java.util.ArrayList;

public class PhongBan {

    private int id;
    private String name;

    private ArrayList<NhanVien>  listNV;

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

    public ArrayList<NhanVien> getListNV() {
        return listNV;
    }

    public void setListNV(ArrayList<NhanVien> listNV) {
        this.listNV = listNV;
    }

    public PhongBan(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PhongBan() {
    }

    @Override
    public String toString() {
        return name;
    }
}
