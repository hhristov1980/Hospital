package main.persons;

import main.hospital.Hospital;
import main.treatment.Treatment;

import java.util.concurrent.CopyOnWriteArraySet;

public class Doctor extends Person{
    private String specialisation;
    private CopyOnWriteArraySet<Patient> patients;

    public Doctor(String names, String telephoneNumber, Hospital hospital, String specialisation) {
        super(names, telephoneNumber,hospital);
        this.specialisation = specialisation;
        patients = new CopyOnWriteArraySet<>();
    }


    @Override
    public void run() {
        while (true){
            hospital.makeVisitations(this);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected PersonType validateType() {
        return PersonType.DOCTOR;
    }

    public void examination(Patient patient){
        patient.setDiagnosis(specialisation);
        patient.setTreatment(new Treatment());

    }

    public String getSpecialisation() {
        return specialisation;
    }

    public CopyOnWriteArraySet<Patient> getPatients() {
        return patients;
    }
}
