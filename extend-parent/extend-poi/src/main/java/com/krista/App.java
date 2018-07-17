package com.krista;

import com.krista.extend.poi.POITool;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        // POITool poiTool = new POITool().config();

        String filePath = "C:\\Users\\Administrator\\Desktop\\aa.txt";
        OutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write("王德华".getBytes());
        outputStream.flush();

        System.out.println( "Hello World!" );
    }
}
