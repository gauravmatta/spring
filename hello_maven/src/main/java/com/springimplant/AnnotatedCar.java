package com.springimplant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AnnotatedCar implements Vehicle{

    @Autowired
    @Qualifier("tyreSetter")
    private TyreSetter tyre;

    @Autowired
    private AnnotatedTyre stepney;

    public void setStepne(AnnotatedTyre stepney) {
        this.stepney = stepney;
    }

    public AnnotatedTyre getStepney() {
        return stepney;
    }

    public TyreSetter getTyre() {
        return tyre;
    }

    public void setTyre(TyreSetter tyre) {
        this.tyre = tyre;
    }

    public void drive(){
        System.out.println("Car with Tyre "+tyre+" is running and has stepney "+stepney);
    }
}
