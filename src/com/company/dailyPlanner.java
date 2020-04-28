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
        int id;
        while (notes.containsKey(id = Math.abs(random.nextInt() % 1000))){
            if (notes.size() >= 1000) {
                System.out.println("Ежедневник переполнен");
                return;
            }
        };
        notes.put(id, note);
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
