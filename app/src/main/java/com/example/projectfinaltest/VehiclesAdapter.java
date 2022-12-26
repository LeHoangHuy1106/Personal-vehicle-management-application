package com.example.projectfinaltest;

import android.content.Context;
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

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.ViewHolder>{

    private ListVehicles danhsach;
    private Context context;

    public VehiclesAdapter(Context context,ListVehicles danhsach) {
        this.context = context;
        this.danhsach = danhsach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }
    public void release(){
        context=null;
    }
    @Override
    public void onBindViewHolder( VehiclesAdapter.ViewHolder holder, int position) {
        myVehicles p= danhsach.get(position);
        if(p==null)
            return;
        holder.txtName.setText(p.getTenXe());
        holder.txtBienSo.setText(p.getBienSo());
        holder.txtKm.setText(p.getKmHienTai()+" km");
        if( p.getLoaiXe().equals("Ô tô"))
        {
            holder.imgLoai.setImageResource(R.drawable.iconcar);
        }
        else
        {
            holder.imgLoai.setImageResource(R.drawable.iconbike);
        }


    }


    @Override
    public int getItemCount() {
        if( danhsach!=null)
            return danhsach.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView txtName, txtBienSo, txtKm;
        ImageView imgLoai;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNameXe);
            txtKm= itemView.findViewById(R.id.txtKm);
            txtBienSo = itemView.findViewById(R.id.txtBienSo);
            imgLoai= itemView.findViewById(R.id.imgLoai);
            txtName.setOnCreateContextMenuListener(this);
            txtKm.setOnCreateContextMenuListener(this);
            txtBienSo.setOnCreateContextMenuListener(this);
            imgLoai.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("LỰA CHỌN CỦA BẠN");
            menu.add(this.getAdapterPosition(),123,0,"Xem thông tin");
            menu.add(this.getAdapterPosition(),789,1,"Lịch thay nhớt");
            menu.add(this.getAdapterPosition(),456,1,"Xóa");



        }


    }
    public void  removeItem(int positon){
        danhsach.removeAt(positon);
        notifyDataSetChanged();
    }

}
