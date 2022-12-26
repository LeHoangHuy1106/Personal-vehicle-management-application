package com.example.projectfinaltest;

import java.io.Serializable;
import java.util.ArrayList;

public class ListNote implements Serializable {
    ArrayList<myNote> listDL= new ArrayList<myNote>();
    public void add(myNote dl)
    {
        listDL.add(0,dl);
    }
    public void removeAt(int i)
    {
        listDL.remove(i);
    }
    public int size(){
        return listDL.size();
    }
    public myNote get(int i)
    {
        return listDL.get(i);
    }
    public void set(int i, myNote dl)
    {
        listDL.set(i, dl);
    }
    public void clear(){
        listDL.clear();
    }
    public int SumMoney()
    {
        int sum=0;
        for( int i=0; i< listDL.size();i++)
        {
            sum= sum+ Integer.parseInt( listDL.get(i).getSoTienNote());
        }
        return sum;
    }
    public int countLoai(String loai)
    {
        int count =0;
        for( int i=0; i< listDL.size();i++)
        {
            if(listDL.get(i).getLoaiNote().equals(loai)) {
                count = count + 1;
            }
        }
        return count;
    }
    public int SoLanthayNhot(String tenxe)
    {
        int count =0;
        for( int i=0; i< listDL.size();i++)
        {
            if(listDL.get(i).getXeNote().equals(tenxe) &&listDL.get(i).getLoaiNote().equals("Thay nhớt") ) {
                count = count + 1;
            }
        }
        return count;
    }

    public int kmThayNhoGanNhat(String xe)
    {
        int km=0;
        for( int i =0; i< listDL.size();i++)
        {
            if(listDL.get(i).getLoaiNote().equals("thay nhớt") &&listDL.get(i).getXeNote().equals(xe))
            {
                return Integer.parseInt(listDL.get(i).getKmNote());
            }
        }
        return km;
    }
    public String NgayThayNhoGanNhat(String xe)
    {
        String  month= "";
        for( int i =0; i< listDL.size();i++)
        {
            if(listDL.get(i).getLoaiNote().equals("thay nhớt") &&listDL.get(i).getXeNote().equals(xe))
            {
                month= listDL.get(i).getNgayNote();
            }
        }
        return month;
    }

}
