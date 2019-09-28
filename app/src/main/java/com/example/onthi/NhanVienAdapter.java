package com.example.onthi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.onthi.entities.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter extends BaseAdapter {

    private ArrayList<NhanVien> listNV = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;


    public NhanVienAdapter(Context context,ArrayList<NhanVien> listNV) {
        this.listNV = listNV;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listNV.size();
    }

    @Override
    public Object getItem(int i) {
        return listNV.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listNV.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       ViewHolder viewHolder;

       if(view == null){
            view = layoutInflater.inflate(R.layout.nhanvien_view,null);
            viewHolder = new ViewHolder();
            viewHolder.imgView = (ImageView) view.findViewById(R.id.img);
            viewHolder.ten = (TextView) view.findViewById(R.id.lblTen);
            viewHolder.cb = (CheckBox) view.findViewById(R.id.cbXoa);
            view.setTag(viewHolder);
       }else viewHolder = (ViewHolder) view.getTag();
        NhanVien nv = (NhanVien) getItem(i);
        if(nv.getGioiTinh()==0){
            Drawable img;
            img = AppCompatResources.getDrawable(viewGroup.getContext(),R.drawable.girl);
            viewHolder.imgView.setImageDrawable(img);
        }else {
            Drawable img;
            img = AppCompatResources.getDrawable(viewGroup.getContext(),R.drawable.boy);
            viewHolder.imgView.setImageDrawable(img);
        }
        viewHolder.ten.setText(nv.getName());

        return view;
    }
    static class ViewHolder{
        ImageView imgView;
        TextView ten;
        CheckBox cb;
    }
}
