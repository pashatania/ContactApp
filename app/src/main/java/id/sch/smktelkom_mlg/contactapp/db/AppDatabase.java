package id.sch.smktelkom_mlg.contactapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.sch.smktelkom_mlg.contactapp.model.Contact;

//entities = daftar entity dipisahkan dengan koma
//version = setiap perubahan dll versi naik
@Database(entities = {Contact.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    //mendaftarkan DAO
    public abstract ContactDao contactDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    AppDatabase() {
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class,
                "contact.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}

