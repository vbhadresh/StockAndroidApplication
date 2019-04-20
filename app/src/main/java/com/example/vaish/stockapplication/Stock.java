package com.example.vaish.stockapplication;

import java.util.Comparator;

/**
 * Created by vaish on 4/29/2018.
 */

public class Stock implements Comparable<Stock>{
    private String Stock_Sys;
    private String Companys_Name;
    private String Price;
    private String Pricechange;
    private String Percentage_Changes;
    private String PreviousClose;
    double d;

    public void setPercentage_Changes(String percentage_Changes) {
        Percentage_Changes = percentage_Changes;
    }

    public void setPricechange(String pricechange) {
        Pricechange = pricechange;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setCompanys_Name(String companys_Name) {
        Companys_Name = companys_Name;
    }

    public void setStock_Sys(String stock_Sys) {
        Stock_Sys = stock_Sys;
    }

    public void setPreviousClose(String previousClose){PreviousClose = previousClose;}

    public String getStock_Sys() {
        return Stock_Sys;
    }

    public String getCompanys_Name() {
        return Companys_Name;
    }

    public String getPrice() {
        return Price;
    }

    public String getPricechange() {
        return Pricechange;
    }

    public String getPreviousClose(){
        return PreviousClose;
    }

    public String getPercentage_Changes() {
        return Percentage_Changes;
    }
    public String toString(){
        return Stock_Sys + Companys_Name +Price+Pricechange+ Percentage_Changes;
    }

    @Override
    public int compareTo(Stock o) {
        return 0;
    }
    public static Comparator<Stock> sortit
            = new Comparator<Stock>() {

        @Override
        public int compare(Stock o1, Stock o2) {
            String s=o1.getStock_Sys().toUpperCase();
            String s1=o2.getStock_Sys().toUpperCase();
            return s.compareTo(s1);
        }

    };
}
