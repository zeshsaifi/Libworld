package com.zeeshan.libworld;

public class HistoryTable {
    private String hu;
    private String hbi;
    private String hbn;
    private String hdta;
    private String hdts;
    public HistoryTable(String hu,String hbi,String hbn,String hdta,String hdts){
        this.hu=hu;
        this.hbi=hbi;
        this.hbn=hbn;
        this.hdta=hdta;
        this.hdts=hdts;
    }
    public String getHu(){
        return hu;
    }
    public String getHbi(){
        return hbi;
    }
    public String getHbn(){
        return hbn;
    }
    public String getHdta(){
        return hdta;
    }
    public String getHdts(){
        return hdts;
    }
}
