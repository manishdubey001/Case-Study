package com.ticketmaster.examples;

import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 3/2/16.
 */
public class FactoryExample2 {

    public static void main(String[] args) {
        final List<String> lst = Arrays.asList("India","Australia","USA");
        final List<String> charge = Arrays.asList("3.0","0.8","1.2");

        for (String s: lst){
            System.out.println("If person moves to "+s
                    +". Then his/her transactions must be in: "
                    + CurrencyFactory.newInstance(s).currencyCode());
        }

        for (int i=0;i<lst.size();i++){

            CurrencyBase temp = CurrencyFactory.newInstance(lst.get(i),charge.get(i));

            System.out.println("If person moves to "+lst.get(i)
                    +". Then his/her transactions must be in: "
                    + temp.currencyCode()+"\n Currency Conversion Charge: "+temp.getRate());
        }
    }
}

class CurrencyFactory {

    public static CurrencyBase newInstance(String s){
        CurrencyBase obj = null;
        switch (s.toLowerCase()){
            case "india":
                obj = new IndianRupee();
                break;
            case "usa":
                obj = new USDollars();
                break;
            case "australia":
                obj = new AUSDollars();
                break;
            default: throw new NumberFormatException("Invalid currency");

        }

        return obj;

    }

    public static CurrencyBase newInstance(String s, String charge){
        CurrencyBase obj = newInstance(s);
        obj.setRate(charge);
        return obj;
    }
}
interface CurrencyBase{
    String currencyCode();
    String getRate();
    void setRate(String rate);
}

class IndianRupee implements CurrencyBase{
    private final String code  = "Rs";
    private String rate ;

    @Override
    public String currencyCode(){ return "Rs"; }
    @Override
    public String getRate(){ return this.code+this.rate; }
    @Override
    public void setRate(String rate){ this.rate = rate; }
}

class USDollars implements CurrencyBase{
    private final String code  = "$";
    private String rate ;
    @Override
    public String currencyCode(){ return "Dollars"; }
    @Override
    public String getRate(){ return this.code+this.rate; }
    @Override
    public void setRate(String rate){ this.rate = rate; }
}

class AUSDollars implements CurrencyBase{
    private final String code  = "AUS$";
    private String rate ;
    @Override
    public String currencyCode(){ return "Australian Dollars"; }
    @Override
    public String getRate(){ return this.code+this.rate; }
    @Override
    public void setRate(String rate){ this.rate = rate; }
}


