package com.example.projectfinaltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder>{

    private ListNote danhsach;
    private Context context;

    public StatisticAdapter(Context context,ListNote danhsach) {
        this.context = context;
        this.danhsach = danhsach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistic_row,parent,false);
        StatisticAdapter.ViewHolder holder = new StatisticAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StatisticAdapter.ViewHolder holder, int position) {
        myNote p= danhsach.get(position);
        if(p==null)
            return;

        holder.tvST_stt.setText(position+1+"");
        holder.tvST_tenxe.setText(p.getXeNote());
        holder.tvST_ngay.setText(p.getNgayNote());
        holder.tvST_km.setText(p.getKmNote());
        holder.tvST_tien.setText(p.getSoTienNote());
        holder.tvST_LoaiNote.setText(p.getLoaiNote());


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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvST_stt, tvST_tenxe, tvST_LoaiNote, tvST_ngay, tvST_km, tvST_tien;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvST_stt= itemView.findViewById(R.id.txtstatistic_stt);
            tvST_tenxe= itemView.findViewById(R.id.txtstatistic_TenXe);
            tvST_LoaiNote= itemView.findViewById(R.id.txtstatistic_loaiNote);
            tvST_ngay= itemView.findViewById(R.id.txtstatistic_NgayNote);
            tvST_km=  itemView.findViewById(R.id.txtstatistic_km);
            tvST_tien= itemView.findViewById(R.id.txtstatistic_tien);

        }



    }



}
