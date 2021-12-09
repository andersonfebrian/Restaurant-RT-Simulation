package com.main;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class Waiter implements Runnable{
    private String name;
    
    public int customerServed = 0;
    
    public Waiter(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    private void passOrderToKitchen(LinkedBlockingQueue<Customer> orderQ, LinkedBlockingQueue<Customer> kitchenQ) {
        if(orderQ.peek() != null) {
            try {
                kitchenQ.add(orderQ.take());
                System.out.println(this.name + " passed an order to the kitchen!");
            } catch (InterruptedException e) {}
        }
    }
    
    private void passFoodToCustomer(LinkedBlockingQueue<Customer> serveQ) {
        if(serveQ.peek() != null) {
            try {
                Customer c = serveQ.take();
                c.gotFood = true;
                customerServed++;
                System.out.println(this.name + " served food to " + c.getName());
            } catch(InterruptedException e){}
        }
    }
    
    private void randomizeTask(int id) {
        switch(id) {
            case 0: 
                passOrderToKitchen(Restaurant.orderQueue, Restaurant.kitchenQueue);
                break;
            case 1:
                passFoodToCustomer(Restaurant.serveQueue);
                break;
        }
    }
    
    @Override
    public void run() {
        System.out.println(this.name + " ready to serve food.");
        
        while(true) {
            
            int rand = (new Random().nextInt(2));
            this.randomizeTask(rand);

            try {
                Thread.sleep(500);
            } catch(Exception e){};
        }
    }
}
