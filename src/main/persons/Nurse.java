package main.persons;

import main.hospital.Hospital;

public class Nurse extends Person{
    private int workExperience;
    private int age;
    private String department;
    public Nurse(String names, String telephoneNumber, Hospital hospital, int workExperience, int age, String department) {
        super(names, telephoneNumber, hospital);

        if(age>=22&&age<=65){
            this.age = age;
        }
        if((age-workExperience)>=20){
            this.workExperience = workExperience;
        }
        else {
            this.workExperience = 2;
        }
        this.department = department;
    }

    @Override
    protected PersonType validateType() {
        return PersonType.NURSE;
    }

    @Override
    public void run() {
        while (true){
            hospital.givePills(this);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDepartment() {
        return department;
    }
}
