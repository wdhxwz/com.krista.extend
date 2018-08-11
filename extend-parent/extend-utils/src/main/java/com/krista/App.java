package com.krista;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        System.out.println(list.contains(1L));

        System.out.println(new Long[]{1L,2L});

        System.out.println(ArrayUtils.toString(new Long[]{1L,2L},""));



        System.out.println( "Hello World!" );
    }
}
