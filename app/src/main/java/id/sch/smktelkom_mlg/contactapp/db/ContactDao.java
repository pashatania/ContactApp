package id.sch.smktelkom_mlg.contactapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.sch.smktelkom_mlg.contactapp.model.Contact;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE id=:id")
    Contact getById(Long id);

    @Insert
    void insert(Contact Contact);

    @Update
    void update(Contact Contact);

    @Delete
    void delete(Contact Contact);

    @Query("SELECT COUNT(*) from Contact")
    Integer count();

}

