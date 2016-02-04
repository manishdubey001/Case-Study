package com.ticketmaster.examples;

/**
 * Created by root on 3/2/16.
 */
public class FactoryPatternExample {

    public static void main(String[] args) {

        //creating new status class
        System.out.println(EntityFactory.getEntityObject("status", "in-progress").getName());
        System.out.println(EntityFactory.getEntityObject("priority").getName());
        System.out.println(EntityFactory.getEntityObject("tickettype","returning").getName());



    }
}

class EntityFactory{

    public static EntityBase getEntityObject(String type, String name){
        EntityBase base = null;
        if (type.toLowerCase().equals("status")){ base = new Status();  base.setName(name); }
        else if (type.toLowerCase().equals("priority")){ base = new Priority(); base.setName(name); }
        else if (type.toLowerCase().equals("tickettype")){ base = new TicketType(); base.setName(name); }
        return base;
    }

    public static EntityBase getEntityObject(String type){
        EntityBase base = null;
        if (type.toLowerCase().equals("status")){ base = new Status(); }
        else if (type.toLowerCase().equals("priority")){ base = new Priority(); }
        else if (type.toLowerCase().equals("tickettype")){ base = new TicketType(); }
        return base;
    }

}

interface EntityBase{

    String getName();
    void setName(String name);

}

class Status implements EntityBase{

    private String name="open";

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }
    public int hashCode(){
        return getName().hashCode();
    }
}

class Priority implements EntityBase{
    private String name = "medium";
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }
}

class TicketType implements EntityBase{
    private String name = "";

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }
}
