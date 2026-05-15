package service;

import java.io.*;
import java.util.*;

public class FileService {

    public static List<String> read(String file) {
        List<String> list = new ArrayList<>();
        try {
            File f = new File(file);
            if(!f.exists()) return list;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while((line=br.readLine())!=null) list.add(line);
            br.close();
        } catch(Exception e){}
        return list;
    }

    public static void write(String file, List<String> data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(String s:data){ bw.write(s); bw.newLine(); }
            bw.close();
        } catch(Exception e){}
    }

    public static void append(String file, String data) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            bw.write(data); bw.newLine(); bw.close();
        } catch(Exception e){}
    }
}