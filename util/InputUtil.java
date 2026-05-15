package util;

import java.util.*;

public class InputUtil {
    private static final Scanner sc=new Scanner(System.in);

    public static String nextLine(){
        return sc.nextLine().trim();
    }

    public static int nextInt(){
        while(true){
            try{
                return Integer.parseInt(sc.nextLine().trim());
            }catch(Exception e){
                System.out.println("Invalid choice");
            }
        }
    }
}