package main.hospital;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import main.persons.Patient;
import main.rooms.Room;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Statistics extends Thread {
    private Hospital hospital;

    public Statistics(Hospital hospital){
        this.hospital = hospital;
    }

    @Override
    public void run() {
        while (true){
            printStatistics();
            printStatisticsJSON();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private synchronized void printStatistics(){
        File f = new File("status at"+LocalDateTime.now()+".txt");
        try(PrintStream ps = new PrintStream(f)){
            ps.println("=============== STATISTICS ===============");
            for(Map.Entry<String, CopyOnWriteArrayList<Room>> dep: hospital.getDepartments().entrySet()){
                ps.println("Department of "+dep.getKey());
                for(Room r: dep.getValue()){
                    ps.println("\t List of patients at room "+r.getRoomId());
                    for(Patient p:r.getPatientsInRoom()){
                        ps.println("\t\t "+p.getNames()+" Doctor: "+p.getDoctor().getNames());
                    }
                }
            }
            ps.println("=============== END OF STATISTICS ===============");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized void printStatisticsJSON(){
        File f = new File("status at"+LocalDateTime.now()+".json");
        JsonObject jo = new JsonObject();
        Gson gson = new Gson();
        for(Map.Entry<String, CopyOnWriteArrayList<Room>> dep: hospital.getDepartments().entrySet()){
            jo.addProperty("Department of ",dep.getKey());
            for(Room r: dep.getValue()){
                jo.addProperty("List of patients at room ",r.getRoomId());
                for(Patient p:r.getPatientsInRoom()){
                    jo.addProperty(p.getNames()," Doctor: "+p.getDoctor().getNames());
                }
            }
        }
        String text = gson.toJson(jo);

        try(PrintStream ps = new PrintStream(f)){
            ps.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
