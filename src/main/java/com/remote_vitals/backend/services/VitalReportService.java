package com.remote_vitals.backend.services;

import com.remote_vitals.entities.*;
import com.remote_vitals.repositories.UserRepository;
import com.remote_vitals.frontend.controllers.VitalReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class VitalReportService {
    // Attributes and Beans
    @Value("${path.csv-files-directory}")
    private String csvFilesDirectory;

    private final UserRepository userRepository;
    private final CsvService csvService;
    private final DateAndTimeService dateAndTimeService;

    // Constructor
    public VitalReportService(
            UserRepository userRepository,
            CsvService csvService,
            DateAndTimeService dateAndTimeService
    ) {
        this.userRepository = userRepository;
        this.csvService = csvService;
        this.dateAndTimeService = dateAndTimeService;
    }
    // Methods
    @Transactional
    public String readCsvForCurrentUser(){
        if(StaticDataClass.currentUserId == null)
            throw new RuntimeException("Current User Id Is Not Present");
        try {
            List<Map<String,String>> reports =
                    csvService.parseCsv(
                            csvFilesDirectory + "/" + StaticDataClass.currentUserId.toString() + ".csv"
                    );
            System.out.println("- - - - - -- - - - - - - - - - - -  -");
            List<VitalReport> vitalReports = new ArrayList<>();
            System.out.println("- - - - - -- - - - - - - - - - - -  -");
            reports.forEach(report -> {
                VitalReport vitalReportCreated = createVitalReport(report);
                if(vitalReportCreated == null)
                    throw new RuntimeException("Invalid File Format");
                vitalReports.add(vitalReportCreated);
            });
            System.out.println("- - - - - -- - - - - - - - - - - -  -");
            userRepository.findById(StaticDataClass.currentUserId).ifPresent(
                    user -> {
                        if(user instanceof Patient){
                            ((Patient) user).getVitalReports().clear();
                            vitalReports.forEach(vitalReport ->{
                                vitalReport.setPatient((Patient) user);
                                ((Patient) user).getVitalReports().add(vitalReport);
                            });
                        }
                    }
            );
            System.out.println("- - - - - -- - - - - - - - - - - -  -");
            return "Csv Parsed Successfully";
        }catch(IOException ex){
            return "File Not Found Or Invalid Format";
        } catch (RuntimeException ex) {
            return "File Format Invalid";
        }
    }

    public VitalReport createVitalReport(Map<String,String> vitalRecordList){
        if(vitalRecordList == null || vitalRecordList.isEmpty()) return null;
        VitalReport vitalReport = new VitalReport();
        vitalReport.setVitalRecords(new ArrayList<VitalRecord>());
        // finds the report time of the report list
        for(Map.Entry<String,String> entry : vitalRecordList.entrySet()){
            if(entry.getKey().equals("date-time")) {
                if(dateAndTimeService.matchDateformat(entry.getValue(), "yyyy-MM-dd'T'HH:mm:ss")){
                    LocalDateTime timeWhenMade = dateAndTimeService.analyzeDateTimeFormat(entry.getValue(),"yyyy-MM-dd'T'HH:mm:ss");
                    if(timeWhenMade == null)
                        throw new IllegalArgumentException("Date Format Is not Valid");
                    vitalReport.setReportWhenMade(timeWhenMade);
                    break;
                }else{
                    throw new IllegalArgumentException("Date Format Is not Valid");
                }
            }
        }
        // removing the date-time key value
        vitalRecordList.remove("date-time");
        // make the vital record objects for the vitalReport
        try {
            vitalRecordList.forEach((k,v)->{
                VitalRecord vitalRecord = null;
                switch (k) {
                    case "body-temperature" ->
                            vitalReport.getVitalRecords()
                                    .add(vitalRecord = new BodyTemperature(Float.parseFloat(v)));
                    case "heart-rate" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new HeartRate(Float.parseFloat(v)));
                    }
                    case "rbc" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new RBC(Float.parseFloat(v)));
                    }
                    case "wbc" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new WBC(Float.parseFloat(v)));
                    }
                    case "platelet-count" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new PlateletCount(Float.parseFloat(v)));
                    }
                    case "respiratory-rate" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new RespiratoryRate(Float.parseFloat(v)));
                    }
                    case "blood-pressure-systolic" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new BloodPressureSystolic(Float.parseFloat(v)));
                    }
                    case "blood-pressure-diastolic" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new BloodPressureDiastolic(Float.parseFloat(v)));
                    }
                    case "blood-volume" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new BloodVolume(Float.parseFloat(v)));
                    }
                    case "haemoglobin" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new Haemoglobin(Float.parseFloat(v)));
                    }
                    case "height" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new Height(Float.parseFloat(v)));
                    }
                    case "weight" -> {
                        vitalReport.getVitalRecords()
                                .add(vitalRecord = new Weight(Float.parseFloat(v)));
                    }
                    default -> {
                        System.out.println("Invalid Key In The Report Map");
                        throw new IllegalArgumentException("Invalid Key In The Report Map");
                    }
                }
                vitalRecord.setVitalReport(vitalReport);
            });


            return vitalReport;
        } catch (Exception ex) {
            return null;
        }
    }
    public int appendToCsvForCurrentUser(String data) {
        if (StaticDataClass.currentUserId == null) {
            return -1;
        }

        String fileName = StaticDataClass.currentUserId + ".csv";
        File file = new File(csvFilesDirectory, fileName);

        try {
            // Ensure the directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                return -1;
            }

            // Open file in append mode
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(data);
                writer.newLine(); // Appends a newline after the input
            }

            return 0; // Success
        } catch (IOException e) {
            return -1; // Failure
        }
    }

    public int appendFileToCsv(String sourceFilePath) {
        if (StaticDataClass.currentUserId == null) {
            return -1;
        }

        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            return -1;
        }

        String targetFileName = StaticDataClass.currentUserId + ".csv";
        File targetFile = new File(csvFilesDirectory, targetFileName);

        try {
            // Ensure the target directory exists
            File parentDir = targetFile.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                return -1;
            }

            // Read from source and append to target
            try (
                    BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false));
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(sourceFile))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            return 0; // Success
        } catch (IOException e) {
            return -1; // Failure
        }
    }

}
