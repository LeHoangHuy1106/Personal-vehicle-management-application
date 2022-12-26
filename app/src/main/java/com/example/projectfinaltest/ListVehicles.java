package com.example.projectfinaltest;

import java.io.Serializable;
import java.util.ArrayList;

public class ListVehicles implements Serializable {
    ArrayList<myVehicles> listDL= new ArrayList<myVehicles>();
    public void add(myVehicles dl)
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
    public myVehicles get(int i)
    {
        return listDL.get(i);
    }
    public void set(int i, myVehicles dl)
    {
        listDL.set(i, dl);
    }
    public void clear(){
        listDL.clear();
    }


    public int getNgayHenThayNhotbyXe( String tenxe)
    {

        for ( int i =0; i< listDL.size();i++)
        {
            if(listDL.get(i).getTenXe().equals(tenxe))
            {
                if (listDL.get(i).getNhacTheoNgay().equals("1 tháng") )
                {
                    return 1;
                }
                else if (listDL.get(i).getNhacTheoNgay().equals("2 tháng"))
                {
                    return 2;
                }
                else
                if (listDL.get(i).getNhacTheoNgay().equals("3 tháng"))
                {
                    return 3;
                }
                else
                if (listDL.get(i).getNhacTheoNgay().equals("4 tháng"))
                {
                    return 4;
                }
            }

        }
        return 0;
    }

    public int GetKmThayNhobyXe(String tenXe)
    {
        int km=0;
        for ( int i =0; i< listDL.size();i++)
        {
            if(listDL.get(i).getTenXe().equals(tenXe))
            {
                if (listDL.get(i).getNhacTheoKm().equals("300 km"))
                {
                    km= 300;
                }
                else if  (listDL.get(i).getNhacTheoKm().equals("500 km"))
                {
                    km= 500;
                }
                else if  (listDL.get(i).getNhacTheoKm().equals("700km"))
                {
                    km= 500;
                }
                else if  (listDL.get(i).getNhacTheoKm().equals("1000km"))
                {
                    km= 1000;
                }
            }

        }
        return km;
    }

}
