package htg.haitran.deso30;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase DB;
    EditText edtDi,edtDen,edtSL;
    RadioButton rb1c,rb2c;
    CheckBox cbTG;
    Button btnAdd,btnNhapLai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        initData();
        btnNhapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtDi.setText("");
                edtDen.setText("");
                edtSL.setText("");
                rb1c.setChecked(true);
                rb2c.setChecked(false);
                cbTG.setChecked(true);

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noiDi = edtDi.getText().toString();
                String noiDen = edtDen.getText().toString();
                int soLuong = -1;
                int ThanhToan = -1;
                if(rb1c.isChecked()) {
                    ThanhToan = 1;
                }else if(rb2c.isChecked()) {
                    ThanhToan = 2;
                }
                int Hang = 0;
                if(cbTG.isChecked()) {
                    Hang = 1;
                }
                if(TextUtils.isDigitsOnly(edtSL.getText().toString()) && edtSL.getText().toString().length() > 0) {
                    soLuong = Integer.parseInt(edtSL.getText().toString());

                }else {
                    Toast.makeText(MainActivity.this, "Bạn cần nhập số lượng là số", Toast.LENGTH_SHORT).show();
                }

                if(noiDi.length() == 0 || noiDen.length() == 0 || soLuong <= 0 || ThanhToan == -1) {
                    Toast.makeText(MainActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    String sql = "INSERT INTO maybay(NoiDi,NoiDen,SoLuong,ThanhToan,Hang) VALUES('"+noiDi+"','"+noiDen+"',"+soLuong+","+ThanhToan+","+Hang+")";
                    DB.execSQL(sql);
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    void anhxa() {
        edtDi = findViewById(R.id.edt_di);
        edtDen = findViewById(R.id.edt_den);
        edtSL = findViewById(R.id.edt_sl);
        rb1c = findViewById(R.id.rb_1c);
        rb2c = findViewById(R.id.rb_2c);
        cbTG = findViewById(R.id.cb_tg);
        btnAdd = findViewById(R.id.btn_add);
        btnNhapLai = findViewById(R.id.btn_nhaplai);

    }
    void initData() {
        DB = openOrCreateDatabase("maybay.db", MODE_PRIVATE, null);
        String sql = "CREATE TABLE IF NOT EXISTS maybay(" +
                "NoiDi text, " +
                "NoiDen text," +
                "SoLuong INTEGER," +
                "ThanhToan INTEGER," +
                "Hang INTEGER)";
        DB.execSQL(sql);
    }
    void readAll() {
        ArrayList<maybay> maybays = new ArrayList<>();
        Cursor cursor = DB.rawQuery("SELECT * FROM maybay", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String noiDi = cursor.getString(cursor.getColumnIndex("NoiDi"));
                    String NoiDen = cursor.getString(cursor.getColumnIndex("NoiDen"));
                    int SoLuong = cursor.getInt(cursor.getColumnIndex("SoLuong"));
                    int ThanhToan = cursor.getInt(cursor.getColumnIndex("ThanhToan"));
                    int ThuongGia = cursor.getInt(cursor.getColumnIndex("Hang"));
                    maybays.add(new maybay(noiDi,NoiDen,SoLuong,ThanhToan,ThuongGia));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

}