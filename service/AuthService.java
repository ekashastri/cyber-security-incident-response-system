package service;

import model.*;
import java.util.*;

public class AuthService {

    public static boolean exists(String id){
        for(String s:FileService.read("users.txt"))
            if(s.split(",")[0].equals(id)) return true;
        return false;
    }

    public static void register(User u,String extra){
        if(exists(u.getId())){
            System.out.println("Duplicate User ID");
            return;
        }
        FileService.append("users.txt",
            u.getId()+","+u.getName()+","+u.getPassword()+","+u.getRole()+","+extra);
    }

    public static User login(String id,String pass){

        List<String> users=FileService.read("users.txt");
        if(users.isEmpty()){
            System.out.println("No users found. Please register first.");
            return null;
        }

        for(String s:users){
            String[] p=s.split(",");
            if(p[0].equals(id)&&p[2].equals(pass)){
                if(p[3].equals("ADMIN"))
                    return new Admin(p[0],p[1],p[2]);
                else
                    return new SecurityAnalyst(p[0],p[1],p[2],p[4]);
            }
        }

        System.out.println("Invalid credentials");
        return null;
    }
}