package com.zeeshan.libworld;

public class CurrentTable {
    private String cu;
    private String cbi;
    private String cbn;
    private String cdta;
    private String cdts;

    public CurrentTable(String cu,String cbi,String cbn,String cdta,String cdts){
        this.cu=cu;
        this.cbi=cbi;
        this.cbn=cbn;
        this.cdta=cdta;
        this.cdts=cdts;
    }
    public String getCu(){
        return cu;
    }
    public String getCbi(){
        return cbi;
    }
    public String getCbn(){
        return cbn;
    }
    public String getCdta(){
        return cdta;
    }
    public String getCdts(){
        return cdts;
    }
}
