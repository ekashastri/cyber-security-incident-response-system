package service;

import model.*;
import exception.*;
import java.util.*;

public class IncidentService {

    public static List<Incident> load(){
        List<Incident> list=new ArrayList<>();
        for(String s:FileService.read("incidents.txt"))
            list.add(Incident.fromString(s));
        return list;
    }

    public static void save(List<Incident> list){
        List<String> data=new ArrayList<>();
        for(Incident i:list) data.add(i.toString());
        FileService.write("incidents.txt",data);
    }

    public static void checkDuplicate(String id) throws DuplicateIncidentException {
        for(String s:FileService.read("incidents.txt"))
            if(s.startsWith(id+",")) throw new DuplicateIncidentException();
    }
}