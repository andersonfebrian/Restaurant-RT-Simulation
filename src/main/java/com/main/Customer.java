package com.main;

import java.util.Random;

public class Customer implements Runnable {

    private final String name;

    public boolean gotFood = false;
    public boolean ordered = false;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void orderFood() {
        Restaurant.orderQueue.add(this); // Add 1 to the LinkedBlockingQueue - Keep this simple to simulate food ordered
        this.ordered = !this.ordered;
        System.out.println(this.name + " has ordered food!");
    }

    @Override
    public void run() {
        
        try {
            Thread.sleep( (new Random().nextInt(15) + 1) * 1000 );
        } catch (InterruptedException e) {}
        
        System.out.println(this.name + " has entered the Restaurant");
        while (true) {

            if (this.ordered == false) {
                // Randomize the sleep duration - Simulate the customer going to the table then order;
                int rand = (new Random().nextInt(5) + 1) * 1000;
                try {
                    Thread.sleep(rand);
                } catch (InterruptedException e) {
                }
                this.orderFood();
            }

            if (this.gotFood) {
                System.out.println(this.name + " got their food, eating...");
                try {
                    Thread.sleep((new Random().nextInt(5) + 1) * 1000);
                } catch (Exception e) {
                }

                System.out.println(this.name + " finished their food, leaving...");
                Restaurant.toServe--;

                break;
            }
            
            try {
                Thread.sleep(1000);
            } catch (Exception e){}

        }

    }
}
