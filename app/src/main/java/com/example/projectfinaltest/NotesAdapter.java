package com.example.projectfinaltest;

import android.content.Context;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

    private ListNote danhsach;
    private Context context;

    public NotesAdapter(Context context,ListNote danhsach) {
        this.context = context;
        this.danhsach = danhsach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesAdapter.ViewHolder holder, int position) {
        myNote p= danhsach.get(position);
        if(p==null)
            return;

        holder.txtXenote.setText(p.getXeNote());
        holder.txtGioNote.setText(p.getGioNote());
        holder.txtNgayNote.setText(p.getNgayNote());
        holder.txtKmNote.setText(p.getKmNote());
        holder.txtDiChiNote.setText(p.getDiaDiemNote());
        holder.txtLitNote.setText(p.getLitNote());
        holder.txtTienNote.setText(p.getSoTienNote());
        holder.txtNoteNote.setText(p.getNoteNote());
        holder.txtLoaiNote.setText(p.getLoaiNote());
    }

    public void release(){
        context=null;
    }



    @Override
    public int getItemCount() {
        if( danhsach!=null)
            return danhsach.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView txtLoaiNote,txtXenote, txtGioNote, txtNgayNote, txtKmNote, txtDiChiNote, txtLitNote,txtTienNote,txtNoteNote;
        View layout;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
                txtXenote= itemView.findViewById(R.id.txtXeNote);
                txtGioNote= itemView.findViewById(R.id.txtGioNote);
                txtNgayNote= itemView.findViewById(R.id.txtNgayNote);
                txtKmNote= itemView.findViewById(R.id.txtKmNote);
                txtDiChiNote= itemView.findViewById(R.id.txtDiaDiemNote);
                txtLitNote= itemView.findViewById(R.id.txtLitNote);
                txtTienNote= itemView.findViewById(R.id.txtGiaTien);
                txtNoteNote=itemView.findViewById(R.id.txtNote);
                txtLoaiNote= itemView.findViewById(R.id.txtLoaiNote);
                layout= itemView.findViewById(R.id.layoutNoteRow);
                itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("LỰA CHỌN CỦA BẠN");
            menu.add(this.getAdapterPosition(),123,0,"Xem");
            menu.add(this.getAdapterPosition(),456,1,"Xóa");



        }


    }
    public void  removeItem(int positon){
        danhsach.removeAt(positon);
        notifyDataSetChanged();
    }

}
