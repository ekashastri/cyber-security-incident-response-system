package service;

import java.time.LocalDateTime;

public class LogService {
    public static void log(String msg) {
        FileService.append("logs.txt","["+LocalDateTime.now()+"] "+msg);
    }
}