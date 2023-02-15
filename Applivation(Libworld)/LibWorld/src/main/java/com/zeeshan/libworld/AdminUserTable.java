package com.zeeshan.libworld;

public class AdminUserTable {
    private String uu;
    private String ue;
    AdminUserTable(String uu,String ue){
        this.uu=uu;
        this.ue=ue;
    }
    public String getUu(){
        return uu;
    }
    public String getUe(){
        return ue;
    }
}
