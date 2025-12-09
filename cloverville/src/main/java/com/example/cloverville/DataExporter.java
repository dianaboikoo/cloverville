package com.example.cloverville;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataExporter {

    public static void exportAllData() {
        exportResidents();
        exportTasks();
        exportLogs();
        System.out.println("Export completed.");
    }

    // ----------------- RESIDENTS -----------------
    private static void exportResidents() {
        ArrayList<Object> residents = new ArrayList<>();

        for (Resident r : ListController.residents) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", r.getName());
            map.put("personalPoints", r.getPersonalPoints());
            map.put("greenPoints", r.getGreenPoints());
            residents.add(map);
        }

        writeJsonToFile("residents.json", residents);
    }

    // ----------------- TASKS -----------------
    private static void exportTasks() {
        ArrayList<Object> tasks = new ArrayList<>();

        for (CommunalTask t : TaskListController.tasks) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", t.getName());
            map.put("description", t.getDescription());
            map.put("greenPoints", t.getPersonalPoints());
            tasks.add(map);
        }

        writeJsonToFile("tasks.json", tasks);
    }

    // ----------------- LOG ENTRIES -----------------
    private static void exportLogs() {
        ArrayList<Object> logs = new ArrayList<>();

        for (TaskLogEntry entry : TaskLog.log) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("resident", entry.getResident().getName());
            map.put("task", entry.getTask().getName());
            map.put("points", entry.getTask().getPersonalPoints());
            logs.add(map);
        }

        writeJsonToFile("task_log.json", logs);
    }

    // ----------------- JSON WRITER -----------------
    private static void writeJsonToFile(String filename, ArrayList<Object> list) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(toJson(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------------- SIMPLE JSON BUILDER -----------------
    private static String toJson(Object obj) {
        if (obj instanceof ArrayList<?>) {
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");

            ArrayList<?> list = (ArrayList<?>) obj;
            for (int i = 0; i < list.size(); i++) {
                sb.append("  ").append(toJson(list.get(i)));
                if (i < list.size() - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("]");
            return sb.toString();
        }

        if (obj instanceof HashMap<?, ?> map) {
            StringBuilder sb = new StringBuilder();
            sb.append("{");

            var entries = map.entrySet().toArray();
            for (int i = 0; i < entries.length; i++) {
                var e = (java.util.Map.Entry<?, ?>) entries[i];
                sb.append("\"").append(e.getKey()).append("\":");
                sb.append(formatValue(e.getValue()));

                if (i < entries.length - 1)
                    sb.append(",");
            }

            sb.append("}");
            return sb.toString();
        }

        return "\"UNKNOWN\"";
    }

    private static String formatValue(Object v) {
        if (v instanceof Number) return v.toString();
        return "\"" + v.toString().replace("\"", "\\\"") + "\"";
    }
}
