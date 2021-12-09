package com.main;

import java.util.concurrent.LinkedBlockingQueue;

public class Chef implements Runnable {
    public String name;
    
    public int foodCooked = 0;
    
    public Chef(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    private void simulateCooking() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        passKitchenToServe(Restaurant.kitchenQueue, Restaurant.serveQueue);
    }
    
    private void passKitchenToServe(LinkedBlockingQueue<Customer> kitchenQ, LinkedBlockingQueue<Customer> serveQ) {
        if (kitchenQ.peek() != null) {
            try {
                
                serveQ.add(kitchenQ.take());
                System.out.println(this.name + " has cooked food and moved to Serving Queue");
                this.foodCooked++;
            } catch (InterruptedException e) {}
        }
    }
    
    @Override
    public void run () {
        System.out.println(this.name + " ready to cook!");
        while(true) {
            
            simulateCooking();
            
            try {
                Thread.sleep(500);
            } catch(Exception e){}
        }
//        this.simulateCooking();
    }
}
