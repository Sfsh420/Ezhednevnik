package com.company;

import java.util.Date;
import java.util.Scanner;

public class Note{
    private Date creationDate;
    private String text;
    private Date modificatedDate;
    private String name;

    private boolean isImportant;

    static int count = 0;

    Note(Scanner scanner){
        creationDate = new Date();
        modificatedDate = new Date();
        System.out.println("Введите название заметки:");
        this.name = scanner.nextLine();
        System.out.println("Введите текст заметки:");
        this.text = scanner.nextLine();
        System.out.println("Важна ли заметка?");
        String s;
        while (true){
            System.out.println("Введите (Y/N)");
            s = scanner.nextLine().trim().toUpperCase();
            if (s.equals("Y") || s.equals("N")){
                isImportant = s.equals("Y");
                break;
            }
        }
    }

    Note(Date creationDate, Date modificatedDate, String name, String text, boolean isImportant){
        this.creationDate = creationDate;
        this.modificatedDate = modificatedDate;
        this.name = name;
        this.text = text;
        this.isImportant = isImportant;
    }


    public Date getModificatedDate() {
        return modificatedDate;
    }

    public void setModificatedDate(Date modificatedDate) {
        this.modificatedDate = modificatedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        modificatedDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Имя: " + getName() + '\n');
        s.append("Текст записи: " + getText() + '\n');
        s.append("Время создания: " + getCreationDate().toString() + '\n');
        s.append("Время изменения: " + getModificatedDate().toString() + '\n');
        s.append("Важность: " + (isImportant ? "Да" : "Нет"));
        s.append("\n----------------------------------------------------------------\n");
        return s.toString();
    }
}
