package main.treatment;

import main.util.Randomizer;

import java.time.Duration;

public class Treatment {
    private Duration duration;
    private int numberOfPills;

    public Treatment(){
        int number = Randomizer.getRandomInt(3,5);
        this.duration = Duration.ofSeconds(number*5);
        this.numberOfPills = number;
    }

    public Duration getDuration() {
        return duration;
    }

    public void decreasePills(){
        if(numberOfPills>=1){
            numberOfPills--;
        }

    }
}
