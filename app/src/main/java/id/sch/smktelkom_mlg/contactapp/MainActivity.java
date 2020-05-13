package id.sch.smktelkom_mlg.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.sch.smktelkom_mlg.contactapp.adapter.ContactAdapter;
import id.sch.smktelkom_mlg.contactapp.db.AppDatabase;
import id.sch.smktelkom_mlg.contactapp.model.Contact;

public class MainActivity extends AppCompatActivity {

    //deklarasi variabel
    FloatingActionButton fab;
    RecyclerView list;
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //memanggil layout
        setContentView(R.layout.activity_main);

        //inisialisasi/dapatkan id nya
        fab = findViewById(R.id.fab);
        list = findViewById(R.id.list);

        //membuat data dummy
        list.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        //dummy data
        /*
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        contact.email = "test@email.com";
        contact.id = 1L;
        contact.jeniskelamin = "L";
        contact.nama = "Denny Chandra";
        contact.pekerjaan = "swasta";
        contact.telpon = "12345";
        contact.tgllahir = "21-11-1990";
        contacts.add(contact);

        //adapter
        // Array lapor mapping ke row_layout
        adapter = new ContactAdapter(this,contacts);
        list.setAdapter(adapter);
        */

        List<Contact> contacts = AppDatabase.getInstance(this).contactDao().getAll();
        adapter = new ContactAdapter(this,contacts);
        list.setAdapter(adapter);

        //event onclick
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        FormActivity.class);
                startActivity(intent);
            }
        });
    }
}



