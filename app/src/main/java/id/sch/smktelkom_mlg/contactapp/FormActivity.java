package id.sch.smktelkom_mlg.contactapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import id.sch.smktelkom_mlg.contactapp.db.AppDatabase;
import id.sch.smktelkom_mlg.contactapp.model.Contact;

public class FormActivity extends AppCompatActivity {

    private EditText txtNama, txtEmail, txtTelpon, txtTglLahir;
    private Spinner spPekerjaan;
    private RadioGroup rbJkGroup;
    private RadioButton rbJk, rbL, rbP;
    private int mYear, mMonth, mDay;
    private Button btnSave;
    String tglLahirDb = "";
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //set judul halaman
        setTitle("Tambah/Ubah Kontak");
        txtNama = findViewById(R.id.txt_nama);
        txtEmail = findViewById(R.id.txt_email);
        txtTelpon = findViewById(R.id.txt_telpon);
        txtTglLahir = findViewById(R.id.txt_tgllahir);
        spPekerjaan = findViewById(R.id.sp_pekerjaan);
        rbJkGroup = findViewById(R.id.rb_jk_group);
        rbL = findViewById(R.id.rb_l);
        rbP = findViewById(R.id.rb_p);
        btnSave = findViewById(R.id.btn_save);

        //Spinner
        String[] pekerjaan = {"Mahasiswa", "Dosen", "Karyawan"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FormActivity.this,
                android.R.layout.simple_spinner_dropdown_item, pekerjaan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPekerjaan.setAdapter(adapter);

        //datepicker
        txtTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FormActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                txtTglLahir.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                tglLahirDb = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //data dari Adapter
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");
        if(contact != null){
            txtNama.setText(contact.nama);
            txtEmail.setText(contact.email);
            txtTelpon.setText(contact.telpon);
            txtTglLahir.setText(contact.tgllahir);
            tglLahirDb = contact.tgllahir;
            if (contact.jeniskelamin.equalsIgnoreCase("L")) {
                rbL.setSelected(true);
            } else{
                rbP.setSelected(true);
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validasi
                if (txtNama.getText().toString().equals("")) {
                    txtNama.setError("Nama harus diisi");
                    return;
                }
                if (txtEmail.getText().toString().equals("")) {
                    txtEmail.setError("Email harus diisi");
                    return;
                }

                //null karena halaman ini diakses dari floating action button
                //artinya ingin input data baru
                if (contact == null) {
                    contact = new Contact();
                    rbJk = (RadioButton) findViewById(rbJkGroup.getCheckedRadioButtonId());
                    contact.nama = txtNama.getText().toString();
                    contact.email = txtEmail.getText().toString();
                    contact.jeniskelamin = rbJk.getText().toString();
                    contact.telpon = txtTelpon.getText().toString();
                    String selectedItem = (String) spPekerjaan.getSelectedItem();
                    contact.pekerjaan = selectedItem;
                    contact.tgllahir = tglLahirDb;
                    AppDatabase.getInstance(getApplicationContext()).contactDao().insert(contact);
                    Toast.makeText(FormActivity.this,"data berhasil disimpan",Toast.LENGTH_SHORT).show();
                }else{
                    //tidak null karena halaman ini diakses dari adapter
                    rbJk = (RadioButton) findViewById(rbJkGroup.getCheckedRadioButtonId());
                    contact.nama = txtNama.getText().toString();
                    contact.email = txtEmail.getText().toString();
                    contact.jeniskelamin = rbJk.getText().toString();
                    contact.telpon = txtTelpon.getText().toString();
                    String selectedItem = (String) spPekerjaan.getSelectedItem();
                    contact.pekerjaan = selectedItem;
                    contact.tgllahir = tglLahirDb;
                    AppDatabase.getInstance(getApplicationContext()).contactDao().update(contact);
                    Toast.makeText(FormActivity.this,"data berhasil diubah",Toast.LENGTH_SHORT).show();
                }
                //Pindah halaman
                Intent intent1  = new Intent(FormActivity.this,MainActivity.class);
                startActivity(intent1);

            }
        });
    }

}

