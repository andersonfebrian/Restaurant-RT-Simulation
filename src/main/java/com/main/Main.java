package com.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static ExecutorService es = Executors.newFixedThreadPool(1);
    
    public static final int customerSize = 25;
    
    public static void main(String[] args) throws Exception {
//        Customer[] customers = {
//            new Customer("John"), 
//            new Customer("Mary")
//        };

        Customer[] customers = new Customer[customerSize];

        for(int i = 0; i < customerSize; i++) {
            customers[i] = new Customer(String.valueOf(i));
        }
        
        Chef[] chefs = { 
            new Chef("Ramsey"),
            new Chef("Daniel"),
        };
        
        Waiter[] waiters = {
            new Waiter("Ben"),
            new Waiter("Mary"),
        };
        
        es.submit(new Restaurant(customers, chefs, waiters));
    }
}
