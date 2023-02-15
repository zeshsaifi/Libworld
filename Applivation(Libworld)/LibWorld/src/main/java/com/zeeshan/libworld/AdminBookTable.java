package com.zeeshan.libworld;

public class AdminBookTable {
    private String abi;
    private String abn;
    private String aq;
    private String aa;
    AdminBookTable(String abi,String abn,String aq,String aa){
        this.abi=abi;
        this.abn=abn;
        this.aq=aq;
        this.aa=aa;
    }
    public String getAbi(){
        return abi;
    }
    public String getAbn(){
        return abn;
    }
    public String getAq(){
        return aq;
    }
    public String getAa(){
        return aa;
    }
}
