package ThreadExample;

public class Task implements Runnable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("ThreadExample slept 1000ms");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Executing : "+ getName());
    }

    public Task(String Data){
       this.setName(Data);
    }

    /*public static void main(String[] args) {
        System.out.println("Start");
        new ThreadExample(new Task("Evans")).start();
        new ThreadExample(new Task("Leroy")).start();
        System.out.println("Stop");
    }*/
}
