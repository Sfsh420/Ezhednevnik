package com.company;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    static Random rand;
    static Scanner scanner;



    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);
        dailyPlanner dp = null;
        String filepath = "";
        System.out.println("Студент: Кульба Н.А.");
        System.out.println("Группа: БАСО-02-18");
        System.out.println("\n");
        System.out.println("Выберите пункт меню:");
        System.out.println("1. Новый ежедневник");
        System.out.println("2. Импортирование ежедневника");
        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Введите название файла:");
                filepath = scanner.nextLine();
                dp = new dailyPlanner(new Date().getTime());
                break;
            case "2":
                System.out.println("Введите название файла:");
                filepath = scanner.nextLine();
                dp = creatingJavaFromJson(filepath);
                break;
        }

        String state = "";

        while (!state.equals("exit")) {
            System.out.println("Выберите пункт меню:");
            System.out.println("1. Вывести записи");
            System.out.println("2. Добавить запись");
            System.out.println("3. Удалить запись");
            System.out.println("4. Редактировать запись");
            System.out.println("5. Вывести записи отсортированные по дате создания");
            System.out.println("6. Вывести записи отсортированные по дате изменения");
            System.out.println("7. Вывести записи отсортированные по имени");
            System.out.println("8. Добавить записи из другого файла");
            System.out.println("0. Выйти");
            int id = -1;
            List<Map.Entry<Integer, Note>> entries;
            switch (scanner.nextLine()) {
                case "1":
                    for (var e : dp.getNotes().entrySet()) {
                        System.out.println("ID: " + e.getKey());
                        System.out.println(e.getValue());
                    }
                    break;
                case "2":
                    dp.addNote(new Note(scanner));
                    break;
                case "3":
                    for (var e : dp.getNotes().entrySet()) {
                        System.out.println("ID: " + e.getKey());
                        System.out.println(e.getValue());
                    }
                    System.out.println("Введите ID удаляемой заметки: ");
                    id = Integer.parseInt(scanner.nextLine());
                    if (dp.getNotes().containsKey(id)) {
                        dp.getNotes().remove(id);
                    } else {
                        System.out.println("Нет такого ID");
                    }
                    break;
                case "4":
                    for (var e : dp.getNotes().entrySet()) {
                        System.out.println("ID: " + e.getKey());
                        System.out.println(e.getValue());
                    }
                    System.out.println("Введите ID редактируемой заметки: ");
                    id = Integer.parseInt(scanner.nextLine());
                    if (dp.getNotes().containsKey(id)) {
                        Note redactingNote = dp.getNotes().get(id);
                        changeNote(redactingNote);
                    } else {
                        System.out.println("Нет такого ID");
                    }
                    break;
                case "5":
                    entries = new ArrayList(dp.getNotes().entrySet());
                    Collections.sort(entries, Comparator.comparing(o -> o.getValue().getCreationDate()));
                    for (var e:entries
                         ) {
                        System.out.println("ID: " + e.getKey());
                        System.out.println(e.getValue());
                    }

                    break;
                case "6":
                    entries = new ArrayList(dp.getNotes().entrySet());
                    Collections.sort(entries, Comparator.comparing(o -> o.getValue().getModificatedDate()));
                    for (var e:entries
                    ) {
                        System.out.println("ID: " + e.getKey());
                        System.out.println(e.getValue());
                    }
                    break;
                case "7":
                    entries = new ArrayList(dp.getNotes().entrySet());
                    Collections.sort(entries, Comparator.comparing(o -> o.getValue().getName()));
                    for (var e:entries
                    ) {
                        System.out.println("ID: " + e.getKey());
                        System.out.println(e.getValue());
                    }
                    break;
                case "8":
                    System.out.println("Введите путь добавляемого файла:");
                    String addingFilepath = scanner.nextLine();
                    addJavaFromJsonTo(addingFilepath, dp);
                    break;
                case "0":
                    state = "exit";
                    break;
            }
        }
        creatingJsonFrom(dp, filepath);

    }

    static void changeNote(Note note){


        while (true) {
            System.out.println(note);
            System.out.println("Выберите пункт меню:");
            System.out.println("1. Изменить название записи");
            System.out.println("2. Изменить текст записи");
            System.out.println("3. Сменить важность записи");
            System.out.println("0. Вернуться в меню");
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("Введите новое название:");
                    String name = scanner.nextLine().trim();
                    note.setName(name);
                    break;
                case "2":
                    System.out.println("Введите новый текст:");
                    note.setText(scanner.nextLine().trim());
                    break;
                case "3":
                    boolean isImportant = note.isImportant();
                    System.out.println("Важность установдена на: " + (!isImportant ? "Важно" : "Не важно"));
                    note.setImportant(!isImportant);
                    break;
                case "0":
                    return;
            }
        }

    }

    public static void creatingJsonFrom(dailyPlanner dp, String filepath) throws IOException {
        JsonFactory jsonF = new JsonFactory();
        JsonGenerator jg = jsonF.createGenerator(new File(filepath), JsonEncoding.UTF8);
        jg.useDefaultPrettyPrinter();
        jg.writeStartObject();
        jg.writeNumberField("seed", dp.getSeed());
        ObjectMapper mapper = new ObjectMapper();
        for (var e : dp.getNotes().entrySet()
        ) {
            jg.writeObjectFieldStart(e.getKey().toString());
            Note note = e.getValue();
            jg.writeNumberField("creationDate", note.getCreationDate().getTime());
            jg.writeNumberField("modificatedDate", note.getModificatedDate().getTime());
            jg.writeStringField("Name", note.getName());
            jg.writeStringField("Text", note.getText());
            jg.writeBooleanField("isImportant", note.isImportant());
            jg.writeEndObject();
        }
        jg.writeEndObject();
        jg.close();
    }

    public static void addJavaFromJsonTo(String filePath, dailyPlanner existingDP) throws IOException {
        JsonFactory jsonF = new JsonFactory();
        JsonParser jp = jsonF.createJsonParser(new File(filePath));
        dailyPlanner dp = new dailyPlanner();

        if (jp.nextToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected data to start with an Object");
        }

        while (jp.nextToken() != JsonToken.END_OBJECT){
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            if (fieldName.equals("seed")) dp.setSeed(jp.getLongValue());
            else {
                Date creationDate = null;
                Date modificatedDate = null;
                String Name = "";
                String Text = "";
                boolean isImportant = false;
                while (jp.nextToken() != JsonToken.END_OBJECT){
                    String noteFieldName = jp.getCurrentName();
                    jp.nextToken();
                    if (noteFieldName.equals("creationDate")) creationDate = new Date(jp.getLongValue());
                    else if (noteFieldName.equals("modificatedDate")) modificatedDate = new Date(jp.getLongValue());
                    else if (noteFieldName.equals("Name")) Name = jp.getText();
                    else if (noteFieldName.equals("Text")) Text = jp.getText();
                    else if (noteFieldName.equals("isImportant")) isImportant = jp.getBooleanValue();
                }

                existingDP.addNote(new Note(creationDate, modificatedDate, Name, Text, isImportant));
            }
        }
        jp.close();

    }

    public static dailyPlanner creatingJavaFromJson(String filePath) throws IOException {
        JsonFactory jsonF = new JsonFactory();
        JsonParser jp = jsonF.createJsonParser(new File(filePath));
        dailyPlanner dp = new dailyPlanner();

        if (jp.nextToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected data to start with an Object");
        }

        while (jp.nextToken() != JsonToken.END_OBJECT){
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            if (fieldName.equals("seed")) dp.setSeed(jp.getLongValue());
            else {
                Date creationDate = null;
                Date modificatedDate = null;
                String Name = "";
                String Text = "";
                boolean isImportant = false;
                while (jp.nextToken() != JsonToken.END_OBJECT){
                    String noteFieldName = jp.getCurrentName();
                    jp.nextToken();
                    if (noteFieldName.equals("creationDate")) creationDate = new Date(jp.getLongValue());
                    else if (noteFieldName.equals("modificatedDate")) modificatedDate = new Date(jp.getLongValue());
                    else if (noteFieldName.equals("Name")) Name = jp.getText();
                    else if (noteFieldName.equals("Text")) Text = jp.getText();
                    else if (noteFieldName.equals("isImportant")) isImportant = jp.getBooleanValue();
                }

                dp.addNote(new Note(creationDate, modificatedDate, Name, Text, isImportant));
            }
    }
        jp.close();
        return dp;
    }
}
