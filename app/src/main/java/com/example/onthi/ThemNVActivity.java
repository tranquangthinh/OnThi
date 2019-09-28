package com.example.onthi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onthi.Database.QuanLyNhanVIen;
import com.example.onthi.entities.NhanVien;
import com.example.onthi.entities.PhongBan;

import java.util.ArrayList;

public class ThemNVActivity extends AppCompatActivity {

    EditText txtMa,txtTen;
    RadioButton rbtnNam,rbtnNu;
    TextView lblPB;
    Button btnThem,btnTroVe;

    private String DB_NAME = "QLNV.sqlite";
    private QuanLyNhanVIen qlnv;
    PhongBan pbDuocChon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themnhanvien_activity);

        qlnv = new QuanLyNhanVIen(this,DB_NAME,null,1);
        txtMa = (EditText) findViewById(R.id.txtMa);
        txtTen = (EditText) findViewById(R.id.txtTen);

        rbtnNam = (RadioButton) findViewById(R.id.rbtnNam);
        rbtnNu = (RadioButton) findViewById(R.id.rbtnNu);

        lblPB = (TextView) findViewById(R.id.lblPB);

        btnThem = (Button) findViewById(R.id.btnThem);
        btnTroVe = (Button) findViewById(R.id.btnTroVe);

        Intent intent = this.getIntent();
        int idpb = Integer.parseInt(intent.getStringExtra("id"));
        pbDuocChon = getPhongBan(idpb);
        lblPB.setText(pbDuocChon.getName().trim());
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backClicked(view);
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_String = txtMa.getText().toString().trim();
                String name = txtTen.getText().toString().trim();
                if(id_String.equals("")||name.equals("")){
                    Toast.makeText(ThemNVActivity.this,"Nhập đủ thông tin!!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                int id_Int = Integer.parseInt(id_String);
                int gt=0;
                if(rbtnNam.isChecked()){
                    gt=1;
                }
                NhanVien nv = new NhanVien(id_Int,name,gt,pbDuocChon);

                if(addNhanVien(nv)){
                    finish();
                }

            }
        });

    }

    @Override
    public void finish() {
        // Chuẩn bị dữ liệu Intent.
        Intent data = new Intent();
        data.putExtra("feedback", "Hi!");

        // Activity đã hoàn thành OK, trả về dữ liệu.
        this.setResult(RESULT_OK, data);
        super.finish();
    }

    // Phương thức được gọi khi người dùng nhấn vào nút trở về
    public void backClicked(View view)  {
        // Gọi phương thức onBackPressed().
        this.onBackPressed();
    }
    public boolean addNhanVien(NhanVien nv){
        for(NhanVien nvtam : getAllNhanVien()){
            if(nv.getId()==nvtam.getId()){
                Toast.makeText(this,"Trùng mã",Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        String sql = "Insert Into NhanVien  Values("+nv.getId()
                +",'"+nv.getName().trim()
                +"',"+nv.getGioiTinh()
                +"," +nv.getPb().getId()+")";
        qlnv.queryData(sql);
        return true;
    }
    public NhanVien getNhanVien(String idNV){
        String sql ="Select * from NhanVien where id="+idNV;
        Cursor cursor = qlnv.getData(sql);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name =  cursor.getString(1);
            int gioiTinh = cursor.getInt(2);
            int id_PB = cursor.getInt(3);
            PhongBan pb = getPhongBan(id_PB);
            if(pb==null){
                Toast.makeText(this,"Lỡi Xuất",Toast.LENGTH_LONG).show();
            }else{
                NhanVien nv = new NhanVien(id,name,gioiTinh,pb);
                return nv;
            }
        }
        return null;
    }
    public PhongBan getPhongBan(int idPB){
        String sql ="Select * from PhongBan where id="+idPB;
        Cursor cursor = qlnv.getData(sql);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name =  cursor.getString(1);

            PhongBan pb = new PhongBan(id,name);
            return pb;
        }
        return null;
    }

    public ArrayList<NhanVien> getAllNhanVien(){
        ArrayList<NhanVien> nvlist = new ArrayList<>();
        String sql ="Select * from NhanVien";
        Cursor cursor = qlnv.getData(sql);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name =  cursor.getString(1);
            int gioiTinh = cursor.getInt(2);
            int id_PB  = cursor.getInt(3);
            PhongBan pb = getPhongBan(id_PB);
            if(pb==null){
                Toast.makeText(this,"Lỡi Xuất",Toast.LENGTH_LONG).show();
            }else{
                NhanVien nv = new NhanVien(id,name,gioiTinh,pb);
                nvlist.add(nv);
            }


        }
        return nvlist;
    }
}
