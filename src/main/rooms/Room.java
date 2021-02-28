package main.rooms;

import main.persons.Patient;
import java.util.concurrent.CopyOnWriteArrayList;


public class Room {
    private static int uniqueID = 1;
    private int roomId;
    private String genderOfPatients;
    private CopyOnWriteArrayList<Patient> patientsInRoom;

    public Room(){
        this.roomId = uniqueID++;
        patientsInRoom = new CopyOnWriteArrayList<>();
    }

    public int getRoomId() {
        return roomId;
    }

    public CopyOnWriteArrayList<Patient> getPatientsInRoom() {
        return patientsInRoom;
    }

    public String getGenderOfPatients() {
        return genderOfPatients;
    }

    public void setGenderOfPatients(String genderOfPatients) {
        this.genderOfPatients = genderOfPatients;
    }
}
