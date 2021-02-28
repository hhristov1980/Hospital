package main.persons;

import main.hospital.Hospital;
import main.rooms.Room;
import main.treatment.Treatment;

import java.util.Random;

public class Patient extends Person{
    private Doctor doctor;
    private Treatment treatment;
    private String gender;
    private String diagnosis;
    private boolean hasRoom;
    private Room room;

    public Patient(String names, String telephoneNumber, Hospital hospital) {
        super(names, telephoneNumber, hospital);
        this.gender = new Random().nextBoolean()? "Male":"Female";
        this.hasRoom = false;
    }

    @Override
    protected PersonType validateType() {
        return PersonType.PATIENT;
    }

    @Override
    public void run() {
        while (!hasRoom){
            hospital.goToHospital(this);
        }
        try {
            Thread.sleep(treatment.getDuration().toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hospital.leaveHospital(this);


    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public String getGender() {
        return gender;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }

    public void receivePills(){
        treatment.decreasePills();
    }
}
