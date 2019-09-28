package com.example.onthi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onthi.Database.QuanLyNhanVIen;
import com.example.onthi.entities.NhanVien;
import com.example.onthi.entities.PhongBan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Spinner spPhongBan;
    private Button btnThem,btnXoa;

    private ArrayList<PhongBan> pblist = new ArrayList<>();
    private NhanVienAdapter nvAdapter;
    private String DB_NAME = "QLNV.sqlite";
    private QuanLyNhanVIen qlnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qlnv = new QuanLyNhanVIen(this,DB_NAME,null,1);

        createTBPB();
        createTBNV();

        System.out.println("Tới đây nè");
        listView = (ListView) findViewById(R.id.listView);
        spPhongBan = (Spinner) findViewById(R.id.spPhongBan);

        PhongBan pb = new PhongBan(1,"Kế Toán");
//        NhanVien nv = new NhanVien(1,"Trần Quang Thịnh",1,pb);
//        NhanVien nv1 = new NhanVien(2,"Trần Lý",1,pb);
//        NhanVien nv2 = new NhanVien(3,"Trần Lu",0,pb);
//        NhanVien nv3 = new NhanVien(4,"Trần Ngu",0,pb);
//        NhanVien nv4 = new NhanVien(5,"Trần Đù",1,pb);
//        NhanVien nvl[] = new NhanVien[]{nv,nv1,nv2,nv3,nv4};
//        pb.setListNV(new ArrayList<NhanVien>(Arrays.asList(nvl)));
        PhongBan pb1 = new PhongBan(2,"Quản Lý");
//        NhanVien nv5 = new NhanVien(6,"Phan Thịnh",0,pb1);
//        NhanVien nv6 = new NhanVien(7,"Phan Lý",0,pb1);
//        NhanVien nvk[] = new NhanVien[]{nv5,nv6};
//        pb1.setListNV(new ArrayList<NhanVien>(Arrays.asList(nvk)));
        PhongBan pb2 = new PhongBan(3,"Nhân Sự");
//        pblist.add(pb);
//        pblist.add(pb1);
//        pblist.add(pb2);
//        addPhongBan(pb);
//        addPhongBan(pb1);
//        addPhongBan(pb2);
//        System.out.println("Tới đây rồi nè nè");

        ArrayAdapter<PhongBan> pBAdapter = new ArrayAdapter<PhongBan>(this,android.R.layout.simple_spinner_item,getAllPhongBan());
        spPhongBan.setAdapter(pBAdapter);
        System.out.println("Size :"+getAllPhongBan().size());
        spPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                PhongBan pb = (PhongBan) adapterView.getSelectedItem();
                docDLLenListView(pb);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnThem = (Button) findViewById(R.id.btnThemNV);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goiFormThem();
            }
        });
        btnXoa = (Button) findViewById(R.id.btnXoaNV);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlterDialog();
            }
        });



    }

    public void docDLLenListView(PhongBan pb){
        ArrayList<NhanVien> list = getAllNhanVien(pb);
        nvAdapter =  new NhanVienAdapter(this,list);
        listView.setAdapter(nvAdapter);
    }

    private void displayNhanVienData(NhanVien nv){
        int id = nv.getId();
        String ten = nv.getName();
        int gioiTinh = nv.getGioiTinh();
        PhongBan pb = nv.getPb();
    }
    public void showAlterDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa nhân viên");
        builder.setMessage("Bạn chắc chắn muốn xóa ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                PhongBan pb =(PhongBan) spPhongBan.getSelectedItem();
                ArrayList<NhanVien> list = getAllNhanVien(pb);
                for(int i=listView.getChildCount()-1;i>=0;i--){
                    CheckBox cb = (CheckBox) listView.getChildAt(i).findViewById(R.id.cbXoa);
                    if(cb.isChecked()){
                        deleteNhanVien(list.get(i).getId());
                    }
                }
                docDLLenListView(pb);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Nhận giá trị trả về từ Activity ThêmNV
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PhongBan pb = (PhongBan)spPhongBan.getSelectedItem();
        docDLLenListView(pb);
    }

    //Gọi sang Activity thêmNV
    public void goiFormThem(){
        Intent intent = new Intent(this,ThemNVActivity.class);

        PhongBan pb = (PhongBan)spPhongBan.getSelectedItem();
        intent.putExtra("id",pb.getId()+"");

        //Gửi không cần phản hồi
        this.startActivityForResult(intent,9999);
    }

    public void createTBPB(){
        qlnv.queryData("Create Table if not Exists PhongBan(Id Integer Primary Key,Name Varchar)");
    }

    public void createTBNV(){
            qlnv.queryData("Create Table if not Exists NhanVien(Id Integer Primary Key,Name Varchar,Gia Float,ID_PhongBan Integer)");
    }
    public void addPhongBan(PhongBan pb){
        String sql = "Insert Into PhongBan  Values("+pb.getId() +",'"+pb.getName().trim() +"')";
        qlnv.queryData(sql);
    }
    public  void deleteNhanVien(int id){
        String sql = "Delete From NhanVien where id="+id;
        qlnv.queryData(sql);
    }

    public ArrayList<PhongBan> getAllPhongBan(){
        ArrayList<PhongBan> pblist = new ArrayList<>();
        String sql ="Select * from PhongBan";
        Cursor cursor = qlnv.getData(sql);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name =  cursor.getString(1);

            PhongBan pb = new PhongBan(id,name);
            pblist.add(pb);
        }
        return pblist;
    }

    public ArrayList<NhanVien> getAllNhanVien(PhongBan pb){
        ArrayList<NhanVien> nvlist = new ArrayList<>();
        String sql ="Select * from NhanVien where id_PhongBan="+pb.getId();
        Cursor cursor = qlnv.getData(sql);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name =  cursor.getString(1);
            int gioiTinh = cursor.getInt(2);

            NhanVien nv = new NhanVien(id,name,gioiTinh,pb);
            nvlist.add(nv);

        }
        return nvlist;
    }



}

