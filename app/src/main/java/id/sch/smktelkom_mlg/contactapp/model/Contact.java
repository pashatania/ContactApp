package id.sch.smktelkom_mlg.contactapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//tabel COntact
//implements Serializable supaya object dapat dikirim antar activity
@Entity
public class Contact implements Serializable {

    //id auto-increment
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long id;
    public String nama;
    public String tgllahir;
    public String pekerjaan;
    public String jeniskelamin;
    public String email;
    public String telpon;
}

