package com.meituan.crawler.parse.youdaodict;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;


public class FileWords {

    public static void main(String[] args) {

        try {
            // read file content from file
            StringBuffer sb = new StringBuffer("");
            FileReader reader = new FileReader("f://test11.txt");
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            FileWriter writer = new FileWriter("f://test12.txt");
            BufferedWriter bw = new BufferedWriter(writer);
            while ((str = br.readLine()) != null) {
                sb.append(str + "\\r\\n");
                String separator = System.getProperty("line.separator");
                if (str.trim()==separator) {
                    continue;
                }
                str = str.replaceAll("；", "").trim();
                System.out.println(str);
                bw.write(str);
                bw.newLine();
            }
            br.close();
            reader.close();
            // write string to file

            bw.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
