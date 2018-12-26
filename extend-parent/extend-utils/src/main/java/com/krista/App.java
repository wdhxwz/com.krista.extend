package com.krista;

import org.apache.commons.lang3.ArrayUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        System.out.println(list.contains(1L));

        System.out.println(new Long[]{1L, 2L});

        System.out.println(ArrayUtils.toString(new Long[]{1L, 2L}, ""));


        System.out.println("Hello World!");

        dateFormatTest();
    }

    private static void dateFormatTest() {
        System.out.println(new SimpleDateFormat("yyyy").format(new Date()));
        System.out.println(new SimpleDateFormat("MM").format(new Date()));
        System.out.println(new SimpleDateFormat("dd").format(new Date()));
        System.out.println(new SimpleDateFormat("yyyy-MM").format(new Date()));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));

    }
}
