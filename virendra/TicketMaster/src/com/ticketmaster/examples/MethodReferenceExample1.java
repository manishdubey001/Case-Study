package com.ticketmaster.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * MethodReferenceExample1 class
 * Created by Virendra on 4/2/16.
 */
public class MethodReferenceExample1 {

    public static void main(String[] args) {

        List lst = Arrays.asList(2,4,7,1,5,13,4,8,12,23,55,22,554,333,4444, 179, 191);
        List obj = MethodReferenceExample1.primeNumber(lst, MethodReferenceExample1::isPrime);
        System.out.println("Prime numbers are : "+obj);


    }


    public static boolean isPrime(int n){

        if (n == 1) return false;

        for (int i=2;i<n;i++){
            if (n%i == 0)
                return false;

        }

        return true;
    }

    public static List primeNumber(List l, Predicate<Integer> p){

        List l1 = new ArrayList<>();

        l.stream().filter((i)-> ( p.test((Integer)i)) ).forEach(i->l1.add(i));

        return l1;

    }
}
