package id.sch.smktelkom_mlg.contactapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.sch.smktelkom_mlg.contactapp.FormActivity;
import id.sch.smktelkom_mlg.contactapp.MainActivity;
import id.sch.smktelkom_mlg.contactapp.R;
import id.sch.smktelkom_mlg.contactapp.db.AppDatabase;
import id.sch.smktelkom_mlg.contactapp.model.Contact;

//penghubung antara data dan row_contact.xml
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    //link ke activity
    Context context;

    //penampung data
    List<Contact> contacts;

    //constructor
    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    //untuk link ke row_contact.xml
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(view);
    }

    private void delete(Contact contact){
        AppDatabase.getInstance(context).contactDao().delete(contact);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    //mengisi data ke masing2 id
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.txtNama.setText(contact.nama);
        holder.txtEmail.setText(contact.email);
        holder.txtTgllahir.setText(contact.tgllahir);
        holder.txtTelpon.setText(contact.telpon);
        holder.txtJk.setText(contact.jeniskelamin);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormActivity.class);
                //pindah activity dengan membawa object
                intent.putExtra("contact",contact);
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Hapus data?");
                builder.setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                delete(contact);
                            }
                        });
                builder.setNegativeButton("Batal",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    //mengetahui berapa jumlah data
    @Override
    public int getItemCount() {
        return (contacts != null) ? contacts.size() : 0;

    }

    //innerClass sbg penghubung antara id dan row_contact.xml
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtEmail, txtTgllahir, txtTelpon, txtJk;
        LinearLayout layout;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama);
            txtEmail = itemView.findViewById(R.id.txt_email);
            txtTgllahir = itemView.findViewById(R.id.txt_tgllahir);
            txtTelpon = itemView.findViewById(R.id.txt_telpon);
            txtJk = itemView.findViewById(R.id.txt_jk);
            layout = itemView.findViewById(R.id.layout);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
