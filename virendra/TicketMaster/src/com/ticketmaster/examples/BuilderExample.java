package com.ticketmaster.examples;

/**
 * Created by root on 2/2/16.
 */

public class BuilderExample {
    public static void main(String[] args) {

        Waiter wObj = new Waiter();

        CappuccinoBuilder cObj = new CappuccinoBuilder();
        EspressoBuilder eObj = new EspressoBuilder();

        wObj.setCoffeeBuilder(cObj);
        wObj.createCoffee();

        Coffee coffeeObj = wObj.getCoffee();

        System.out.println(coffeeObj.getSugar());

        wObj.setCoffeeBuilder(eObj);
        wObj.createCoffee();
        coffeeObj = wObj.getCoffee();
        System.out.println(coffeeObj.getSugar());



    }
}

class Waiter {
    private CoffeeBuilder obj ;


    public void setCoffeeBuilder(CoffeeBuilder obj){
        this.obj = obj;
    }

    public Coffee getCoffee(){
        return obj.getCoffee();
    }

    public void createCoffee(){
        obj.createNewCoffee();
        obj.addSugar();
        obj.coffeeType();

    }
}


class Coffee
{
    private String sugar;
    private String type;

    public void setSugar(String sugar){
        this.sugar = sugar;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getSugar(){
        return this.sugar;
    }
    public String getType(){
        return this.type;
    }
}

//abstract builder
abstract class CoffeeBuilder{
    protected Coffee coffeeObject;

    public Coffee getCoffee(){
        return coffeeObject;
    }

    public void createNewCoffee(){
        coffeeObject = new Coffee();
    }

    abstract public void addSugar();
    abstract public void coffeeType();
}
//cappuccino coffee builder
class CappuccinoBuilder extends CoffeeBuilder{
    @Override
    public void addSugar() {
        coffeeObject.setSugar("brown sugar");
    }

    @Override
    public void coffeeType() {
        coffeeObject.setType("Cappuccino");
    }

}
//espresso coffee builder
class EspressoBuilder extends CoffeeBuilder{

    @Override
    public void addSugar() {
        coffeeObject.setSugar("white sugar");
    }

    @Override
    public void coffeeType() {
        coffeeObject.setType("espresso");
    }
}