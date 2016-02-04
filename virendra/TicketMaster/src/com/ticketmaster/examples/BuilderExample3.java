package com.ticketmaster.examples;

/**
 * Created by root on 3/2/16.
 */
public class BuilderExample3 {
    public static void main(String[] args) {
        Cashier cashierObject = new Cashier();
        Meal mealObject;

        mealObject = cashierObject.createMeal("kids");

        mealObject.mealServed();

        mealObject = cashierObject.createMeal("happyhour");
        mealObject.mealServed();

        mealObject = cashierObject.createMeal("general");
        mealObject.mealServed();

    }

}



/**
 * Meal class
 * creates different type of meal
 */
class Meal{

    private String drink;
    private String addon;
    private String mealType;

    //setter methods
    public void setDrink(String drink){
        this.drink = drink;
    }
    public void setAddon(String addon){
        this.addon =  addon;
    }
    public void setMealType(String mealType){
        this.mealType =  mealType;
    }
    //getter methods
    public String getDrink(){
        return this.drink;
    }
    public String getAddon(){
        return this.addon;
    }
    public String getMealType(){
        return this.mealType;
    }

    public String mealServed(){
        StringBuilder builder = new StringBuilder();
        builder.append("Hello Customer!\n\t Please review Your Order and items received\n");
        builder.append("\tMeal Type\t:\t"+getMealType());
        builder.append("\tDrink\t:\t"+getDrink());
        builder.append("\tAdd-on\t:\t"+getAddon());
        return builder.toString();
    }


}

class Lunch{

}


/**
 * Cashier class
 * This is director class. This helps in creating the meal base on the order received
 */
class Cashier {
    private MealBuilder builderObject;

    public Meal getMeal(){

        return builderObject.getMeal();

    }

    public Meal createMeal(String type){

        switch (type){
            case "kids":
                builderObject = new KidsMealBuilder();
                break;
            case "happyhour":
                break;
            case "general":default:
                break;

        }
        return null;
    }

}

//abstract builder
abstract class MealBuilder{

    protected Meal meal;

    public Meal getMeal(){
        return this.meal;
    }

    public abstract void addCoke();
    public abstract void addFrenchFries();
    public abstract void addGoodies();
    public abstract void setBuilder();

}

//concrete builder
class HappyHourMeal extends MealBuilder{

    @Override
    public void addCoke() {

    }

    @Override
    public void addFrenchFries() {

    }

    @Override
    public void addGoodies() {

    }

    @Override
    public void setBuilder(){

    }

    public void createNewMeal(){
        meal = new Meal();
    }

}
//concrete builder
class KidsMealBuilder extends MealBuilder{

    @Override
    public void addCoke() {

    }

    @Override
    public void addFrenchFries() {

    }

    @Override
    public void addGoodies() {

    }

    @Override
    public void setBuilder(){

    }

    public void createNewMeal(){
        meal = new Meal();
    }

    public void createNewMeal(Meal meal){
        this.meal = meal;
    }
}


