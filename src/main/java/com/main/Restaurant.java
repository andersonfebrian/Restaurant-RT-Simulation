package com.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant implements Runnable {
    public ExecutorService ses;
    public ExecutorService wes;
    public ExecutorService ces;
    
    public Customer[] customers;
    public Chef[] chefs;
    public Waiter[] waiters;
    
    public static int toServe;
    
    public static int foodReady = 0;
    
    public static LinkedBlockingQueue<Customer> orderQueue, kitchenQueue, serveQueue;
    
    public Restaurant(Customer[] customers, Chef[] chefs, Waiter[] waiters) {
        this.customers = customers;
        this.chefs = chefs;
        this.waiters = waiters;
        
        this.ses = Executors.newFixedThreadPool(chefs.length);
        this.wes = Executors.newFixedThreadPool(waiters.length);
        this.ces = Executors.newCachedThreadPool();
        
        this.orderQueue = new LinkedBlockingQueue();
        this.kitchenQueue = new LinkedBlockingQueue();
        this.serveQueue = new LinkedBlockingQueue();
        
        this.toServe = this.customers.length;
    }
    
    @Override
    public void run () {
        System.out.println("Restaurant In Service!");
        
        for (Chef chef : this.chefs) {
            ses.submit(chef);
        }
        
        for (Waiter waiter : this.waiters) {
            wes.submit(waiter);
        }
        
        for (Customer customer : this.customers) {
            ces.submit(customer);
        }
        
        while(true) {
            if(this.toServe == 0) {
                
                System.out.println("Closing Down Restaurant, Come back tomorrow!");
                
                for(Waiter waiter : this.waiters) {
                    System.out.println(waiter.getName() + " is leaving work... | Customer Served: " + waiter.customerServed);
                }
                
                for(Chef chef : this.chefs) {
                    System.out.println(chef.name + " is leaving work... | Food Cooked: " + chef.foodCooked);
                }
                
                boolean flag = this.shutdownExecutors();
                
                System.out.println("Executors shutdown correctly: " + flag);
                
                System.exit(0);
            }
            
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {}
        }
    }
    
    private boolean shutdownExecutors() {
        ses.shutdown();
        ces.shutdown();
        wes.shutdown();
        return ses.isShutdown() && ces.isShutdown() && wes.isShutdown();
    }
}
