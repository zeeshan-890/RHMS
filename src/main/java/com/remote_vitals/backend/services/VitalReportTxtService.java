package com.remote_vitals.backend.services;

import com.remote_vitals.entities.Patient;
import com.remote_vitals.repositories.UserRepository;
import com.remote_vitals.repositories.VitalStatus;
import com.remote_vitals.entities.VitalRecord;
import com.remote_vitals.frontend.controllers.VitalReport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VitalReportTxtService {

    private final UserService userService;
    private final UserRepository userRepository;
    @Value("${path.download}")
    String downloadPath;

    public String getDownloadPath() {
        return downloadPath;
    }
    // Static counter to generate unique file names
    private static int reportCounter = 1;

    public VitalReportTxtService(UserService userService
    , UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public String downloadVitalReportTxt(Integer patientId) {
        String fileName = "vitalReport" + reportCounter + ".txt";
        File file = new File(downloadPath, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Patient patient =((Patient) userRepository.findById(patientId).get());
            String fullName = patient.getFirstName() + " " + patient.getLastName();
            writer.write("Patient Name: " + fullName);
            writer.write("\n--------------------------------------------------");
            writer.newLine();

            for(VitalReport vitalReport :patient.getVitalReports()){
                // Format and write submission time
                String formattedTime = vitalReport.getReportWhenMade()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
                writer.write("Time Of Submission : " + formattedTime);
                writer.newLine();
                // Write abnormal vital records
                List<VitalRecord> records = vitalReport.getVitalRecords();
                for(VitalRecord record : records){
                    writer.write(
                            "\t" + record.getClass().getSimpleName() + " : " + record.getValue()
                                    + (
                                    record.getStatus() == VitalStatus.ABNORMAL ? " (Abnormal)" : ""
                            )
                    );
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            return "Error";
        }
        return "Success";
    }
    public String getVitalReportData(Integer patientId) {
        StringBuilder reportData = new StringBuilder();
        try {
            Patient patient = (Patient) userRepository.findById(patientId).get();
            String fullName = patient.getFirstName() + " " + patient.getLastName();
            reportData.append("Patient Name: ").append(fullName).append("\n");
            reportData.append("************************************************************\n");

            for (VitalReport vitalReport : patient.getVitalReports()) {
                // Format and append submission time
                String formattedTime = vitalReport.getReportWhenMade()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
                reportData.append("************************************************************\n");
                reportData.append("Time Of Submission : ").append(formattedTime).append("\n");

                // Append each vital record
                List<VitalRecord> records = vitalReport.getVitalRecords();
                for (VitalRecord record : records) {
                    reportData.append("\t")
                            .append(record.getClass().getSimpleName())
                            .append(" : ")
                            .append(record.getValue());
                    if (record.getStatus() == VitalStatus.ABNORMAL) {
                        reportData.append(" (Abnormal)");
                    }
                    reportData.append("\n");
                }
            }
        } catch (Exception e) {
            return "Error retrieving report";
        }
        return reportData.toString();
    }
}
