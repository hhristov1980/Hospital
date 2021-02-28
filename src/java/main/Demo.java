package main;

import main.hospital.Hospital;
import main.hospital.Statistics;
import main.persons.Doctor;
import main.persons.Nurse;
import main.persons.Patient;
import main.util.Constants;
import main.util.Randomizer;

public class Demo {
    public static void main(String[] args) {
        Hospital hospital = new Hospital("Princeton–Plainsboro");
        Statistics statistics = new Statistics(hospital);
        statistics.setDaemon(true);
        for(int i = 0; i<10; i++){
            String name = Constants.DOCTOR_NAMES[Randomizer.getRandomInt(0,Constants.DOCTOR_NAMES.length-1)]+" "+(i+1);
            String telephoneNumber = "088"+ Randomizer.getRandomInt(0,9999999);
            String specialisation = Constants.SPECIALISATIONS[Randomizer.getRandomInt(0,Constants.SPECIALISATIONS.length-1)];
            Doctor doctor = new Doctor(name,telephoneNumber,hospital,specialisation);
            hospital.addDoctor(doctor);
            doctor.start();
        }
        for(int i = 0; i<3; i++){
            String name = "Nurse "+(i+1);
            String telephoneNumber = "087"+ Randomizer.getRandomInt(0,9999999);
            int age = Randomizer.getRandomInt(25,65);
            int experience = Randomizer.getRandomInt(0,40);
            String department = Constants.SPECIALISATIONS[i];
            Nurse nurse = new Nurse(name,telephoneNumber,hospital,age,experience,department);
            hospital.addNurse(nurse);
            nurse.start();
        }
        for(int i = 0; i<50; i++){
            String name = "Patient "+(i+1);
            String telephoneNumber = "089"+ Randomizer.getRandomInt(0,9999999);
            Patient patient = new Patient(name,telephoneNumber,hospital);
            patient.start();
        }
        statistics.start();
    }
}
