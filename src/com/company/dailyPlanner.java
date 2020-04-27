package com.company;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;

public class dailyPlanner {


    private long seed;
    private Random random;

    private Map<Integer, Note> notes;
    dailyPlanner(){
        this.notes = new HashMap<>();
    }
    dailyPlanner(long seed){
        this.seed = seed;
        this.random = new Random(seed);
        this.notes = new HashMap<>();
    }

    public void addNote(Note note){
        notes.put(Math.abs(random.nextInt()%100), note);
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }


    public Map<Integer, Note> getNotes() {
        return notes;
    }

    public void setNotes(Map<Integer, Note> notes) {
        this.notes = notes;
    }

}
