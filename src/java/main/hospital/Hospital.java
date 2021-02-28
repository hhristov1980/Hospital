package main.hospital;

import main.persons.Doctor;
import main.persons.Nurse;
import main.persons.Patient;
import main.rooms.Room;
import main.util.Constants;
import main.util.Randomizer;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hospital {
    private String name;
    private CopyOnWriteArrayList<Doctor> doctors;
    private CopyOnWriteArrayList<Nurse> nurses;
    private ConcurrentSkipListMap<String, CopyOnWriteArrayList<Room>> departments;

    public Hospital(String name){
        if(name.length()>0){
            this.name = name;
        }
        doctors = new CopyOnWriteArrayList<>();
        nurses = new CopyOnWriteArrayList<>();
        departments = new ConcurrentSkipListMap<>();
        addRoomsToHospital(Constants.SPECIALISATIONS[0],10 );
        addRoomsToHospital(Constants.SPECIALISATIONS[1],10 );
        addRoomsToHospital(Constants.SPECIALISATIONS[2],10 );
    }

    public synchronized void goToHospital(Patient patient){
        if(patient.getDiagnosis()==null){
            Doctor d = askForDoctor();
            d.examination(patient);
            patient.setDoctor(d);
        }
        boolean hasRoom = false;
        for(Map.Entry<String, CopyOnWriteArrayList<Room>> dep: departments.entrySet()){
            if(dep.getKey().equals(patient.getDiagnosis())){
                for(Room r: dep.getValue()){
                    if(r.getPatientsInRoom().isEmpty()){
                        r.getPatientsInRoom().add(patient);
                        r.setGenderOfPatients(patient.getGender());
                        System.out.println(patient.getNames()+" was sent to room "+r.getRoomId()+" in Department of "+dep.getKey()+" at "+this.name
                                +" with diagnosis "+patient.getDiagnosis()+". His doctor is "+patient.getDoctor().getNames());
                        hasRoom = true;
                        patient.setHasRoom(true);
                        patient.setRoom(r);
                        patient.getDoctor().getPatients().add(patient);
                        notifyAll();
                        break;
                    }
                    else {
                        if(r.getPatientsInRoom().size()<3){
                            if(r.getGenderOfPatients().equals(patient.getGender())){
                                r.getPatientsInRoom().add(patient);
                                System.out.println(patient.getNames()+" was sent to room "+r.getRoomId()+" in Department of "+dep.getKey()+" at "+this.name
                                        +" with diagnosis "+patient.getDiagnosis()+". His doctor is "+patient.getDoctor().getNames());
                                hasRoom = true;
                                patient.setHasRoom(true);
                                patient.setRoom(r);
                                patient.getDoctor().getPatients().add(patient);
                                notifyAll();
                                break;
                            }
                        }
                    }

                }

            }
            if(hasRoom){
                break;
            }
        }
        if(!hasRoom){
            System.out.println("No room for "+patient.getNames()+" so wait!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public synchronized void leaveHospital (Patient patient){
        for(Map.Entry<String, CopyOnWriteArrayList<Room>> dep: departments.entrySet()){
            if(dep.getKey().equals(patient.getDiagnosis())){
                for(Room r: dep.getValue()){
                    if(r.getRoomId()==patient.getRoom().getRoomId()){
                        System.out.println(patient.getNames()+" is leaving room "+r.getRoomId()+" department of "+ dep.getKey()+" at Hospital of "+this.name);
                        r.getPatientsInRoom().remove(patient);
                        patient.getDoctor().getPatients().remove(patient);
                        if(r.getPatientsInRoom().isEmpty()){
                            r.setGenderOfPatients(null);
                        }
                    }

                }
            }
        }
    }
    public synchronized void makeVisitations(Doctor doctor){
        boolean noPatients = true;
        for(Map.Entry<String, CopyOnWriteArrayList<Room>> dep: departments.entrySet()){
            if(dep.getKey().equals(doctor.getSpecialisation())){
                for(Room r: dep.getValue()){
                    for(Patient p: r.getPatientsInRoom()){
                        if(doctor.getPatients().contains(p)){
                            noPatients = false;
                            System.out.println(doctor.getNames()+" visited "+p.getNames()+" in room "+r.getRoomId()+" at department of "+dep.getKey());
                        }
                    }
                }
            }
        }
        if(noPatients){
            System.out.println(doctor.getNames()+" has no patients and is waiting!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void givePills(Nurse nurse){
        boolean noPatients = true;
        for(Map.Entry<String, CopyOnWriteArrayList<Room>> dep: departments.entrySet()){
            if(dep.getKey().equals(nurse.getDepartment())){
                for(Room r: dep.getValue()){
                    for(Patient p: r.getPatientsInRoom()){
                        p.receivePills();
                        noPatients = false;
                        System.out.println(nurse.getNames()+" gave pills to "+p.getNames()+" who is in room "+r.getRoomId()+" at Department of "+dep.getKey());
                    }
                }
            }
        }
        if(noPatients){
            System.out.println(nurse.getNames()+" has no patients and is waiting!");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }










    private void addRoomsToHospital(String specialisation, int numberOfRooms){
        for(int i = 0; i<numberOfRooms; i++){
            Room r = new Room();
            if(!departments.containsKey(specialisation)){
                departments.put(specialisation, new CopyOnWriteArrayList<>());
            }
            departments.get(specialisation).add(r);
        }
    }
    public void addDoctor(Doctor d){
        doctors.add(d);
    }
    public void addNurse(Nurse n){
        nurses.add(n);
    }

    private Doctor askForDoctor(){
        return doctors.get(Randomizer.getRandomInt(0,doctors.size()-1));
    }

    public ConcurrentSkipListMap<String, CopyOnWriteArrayList<Room>> getDepartments() {
        return departments;
    }
}
