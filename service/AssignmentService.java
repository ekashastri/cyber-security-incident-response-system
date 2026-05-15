package service;

import java.util.*;
import util.InputUtil;

public class AssignmentService {

    public static String chooseAnalyst(){

        List<String> users=FileService.read("users.txt");
        List<String> ids=new ArrayList<>();

        System.out.println("--------------------------------");
        System.out.println("Assign Analyst");
        System.out.println("--------------------------------");

        int i=1;
        for(String s:users){
            String[] p=s.split(",");
            if(p[3].equals("ANALYST")){
                System.out.println(i+". "+p[1]+" ("+p[4]+")");
                ids.add(p[0]); i++;
            }
        }

        System.out.println("--------------------------------");
        System.out.print("Choice: ");
        int ch=InputUtil.nextInt();

        return ids.get(ch-1);
    }
}