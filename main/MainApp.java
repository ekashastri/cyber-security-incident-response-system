package main;

import model.*;
import service.*;
import util.InputUtil;
import exception.*;

import java.util.*;

public class MainApp {

    public static void main(String as[])
	{
	LogService.log("Program Started"); 

        System.out.println("==============================================");
        System.out.println("      CYBER INCIDENT MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        while(true){

            boolean empty = FileService.read("users.txt").isEmpty();

            System.out.println("----------------------------------------------");
            if(empty) System.out.println("(No user created)");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("----------------------------------------------");
            System.out.print("Choice: ");

            int ch = InputUtil.nextInt();

            if(ch == 1){ register(); System.out.println("Registered"); }
            else if(ch == 2) login();
            else return;
        }
    }

    static void register(){

        System.out.println("----------------------------------------------");
        System.out.println("REGISTER");
        System.out.println("----------------------------------------------");

        System.out.print("Enter User ID: ");
        String id = InputUtil.nextLine();

        System.out.print("Enter Name: ");
        String name = InputUtil.nextLine();

        System.out.print("Enter Password: ");
        String pass = InputUtil.nextLine();

        System.out.println("1. Admin\n2. Analyst");
        System.out.print("Choice: ");
        int role = InputUtil.nextInt();

        if(role == 1){
            AuthService.register(new Admin(id,name,pass),"-");
        } else {
            System.out.println("1. Junior\n2. Senior");
            System.out.print("Choice: ");
            int lvl = InputUtil.nextInt();
            String level = (lvl==1)?"JUNIOR":"SENIOR";
            AuthService.register(new SecurityAnalyst(id,name,pass,level),level);
        }
    }

    static void login(){

        System.out.print("Enter User ID: ");
        String id = InputUtil.nextLine();

        System.out.print("Enter Password: ");
        String pass = InputUtil.nextLine();

        User u = AuthService.login(id,pass);
        if(u == null) return;

        System.out.println("Welcome " + u.getName());

        if(u instanceof Admin) adminPanel();
        else analystPanel((SecurityAnalyst)u);
    }

    static void adminPanel(){

        while(true){

            List<Incident> list = IncidentService.load();

            System.out.println("----------------------------------------------");
            System.out.println("Admin Panel");
            System.out.println("----------------------------------------------");
            System.out.println("1. Create Incident");
            System.out.println("2. Assign Incident");
            System.out.println("3. View Incidents");
            System.out.println("4. Dashboard");
            System.out.println("5. Logout");
            System.out.println("----------------------------------------------");
            System.out.print("Choice: ");

            int ch = InputUtil.nextInt();

            if(ch == 1){
                try{
                    System.out.println("----------------------------------------------");
                    System.out.println("Create Incident");
                    System.out.println("----------------------------------------------");

                    System.out.print("Enter Incident ID: ");
                    String id = InputUtil.nextLine();

                    IncidentService.checkDuplicate(id);

                    System.out.print("Enter Type: ");
                    String type = InputUtil.nextLine();

                    System.out.println("Select Severity");
                    System.out.println("1. Low\n2. Medium\n3. High\n4. Critical");
                    System.out.print("Choice: ");

                    int s = InputUtil.nextInt();
                    String sev = (s==1)?"LOW":(s==2)?"MEDIUM":(s==3)?"HIGH":"CRITICAL";

                    System.out.print("Enter Description: ");
                    String desc = InputUtil.nextLine();

                    list.add(new Incident(id,type,sev,desc));
                    IncidentService.save(list);

                }catch(DuplicateIncidentException e){
                    System.out.println(e.getMessage());
                }
            }

            else if(ch == 2){
                System.out.print("Enter Incident ID: ");
                String id = InputUtil.nextLine();

                for(Incident i:list){
                    if(i.getId().equals(id)){
                        System.out.println("Assign To");
                        String a = AssignmentService.chooseAnalyst();
                        i.setAssignedTo(a);
                    }
                }
                IncidentService.save(list);
            }

            else if(ch == 3){

                for(Incident i:list){
                    System.out.println(i.getId()+" | "+i.getStatus()+" | Created: "+i.getCreatedDateFormatted());
                }
            }

            else if(ch == 4){

                long total = list.size();
                long resolved = list.stream().filter(i->i.isResolved()).count();

                System.out.println("----------------------------------------------");
                System.out.println("Dashboard");
                System.out.println("----------------------------------------------");
                System.out.println("Total Incidents: "+total);
                System.out.println("Resolved: "+resolved);
                System.out.println("Pending: "+(total-resolved));
                System.out.println("----------------------------------------------");
            }

            else return;
        }
    }

    static void analystPanel(SecurityAnalyst user){

        while(true){

            List<Incident> list = IncidentService.load();

            System.out.println("----------------------------------------------");
            System.out.println("Analyst Panel");
            System.out.println("----------------------------------------------");
            System.out.println("1. View Assigned Incidents");
            System.out.println("2. Begin Investigation");
            System.out.println("3. Add Note");
            System.out.println("4. Mark as Resolved");
            System.out.println("5. View Details");
            System.out.println("6. Logout");
            System.out.println("----------------------------------------------");
            System.out.print("Choice: ");

            int ch = InputUtil.nextInt();

            if(ch == 1){
                for(Incident i:list)
                    if(i.getAssignedTo().equals(user.getId()))
                        System.out.println(i.getId()+" | "+i.getStatus()+" | "+i.getCreatedDateFormatted());
            }

            else if(ch == 2){
                System.out.print("Enter Incident ID: ");
                String id = InputUtil.nextLine();

                for(Incident i:list){
                    if(i.getId().equals(id)&&i.getAssignedTo().equals(user.getId())){
                        if(i.canInvestigate()){
                            i.beginInvestigation();
                            System.out.println("1. Suspicious Activity\n2. Malware\n3. Unauthorized Access");
                            int f = InputUtil.nextInt();
                            i.addNote((f==1)?"Suspicious Activity":(f==2)?"Malware":"Unauthorized Access");
                        }
                    }
                }
                IncidentService.save(list);
            }

            else if(ch == 3){
                System.out.print("Enter Incident ID: ");
                String id = InputUtil.nextLine();

                System.out.print("Enter Note (; separated): ");
                String note = InputUtil.nextLine();

                for(Incident i:list)
                    if(i.getId().equals(id)&&i.getAssignedTo().equals(user.getId()))
                        i.addNote(note);

                IncidentService.save(list);
            }

            else if(ch == 4){
                System.out.print("Enter Incident ID: ");
                String id = InputUtil.nextLine();

                for(Incident i:list)
                    if(i.getId().equals(id)&&i.getAssignedTo().equals(user.getId()))
                        i.resolve();

                IncidentService.save(list);
            }

            else if(ch == 5){
                System.out.print("Enter Incident ID: ");
                String id = InputUtil.nextLine();

                for(Incident i:list)
                    if(i.getId().equals(id)&&i.getAssignedTo().equals(user.getId()))
                        i.display();
            }

            else return;
        }
    }
}