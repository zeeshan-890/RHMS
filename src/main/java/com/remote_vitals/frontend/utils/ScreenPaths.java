package com.remote_vitals.frontend.utils;

/**
 * Constants class that stores the paths to all FXML screens in the application.
 * Centralizing screen paths makes navigation more maintainable.
 */
public class ScreenPaths {
    // Base path for all screens
    private static final String SCREENS_BASE_PATH = "/screens/";
    
    // Main screens
    public static final String MAIN_VIEW = "/main-view.fxml";
    
    // Authentication screens
    public static final String LOGIN = SCREENS_BASE_PATH + "login-page.fxml";
    public static final String LOGIN_PAGE = SCREENS_BASE_PATH + "login-page.fxml";
    public static final String SIGNUP_PAGE = SCREENS_BASE_PATH + "signup-page.fxml";
    
    // Dashboard screens
    public static final String ADMIN_DASHBOARD = SCREENS_BASE_PATH + "admin-dashboard.fxml";
    public static final String PATIENT_DASHBOARD = SCREENS_BASE_PATH + "patient-dashboard.fxml";
    public static final String DOCTOR_DASHBOARD = SCREENS_BASE_PATH + "doctor-dashboard.fxml";
    
    // Feature screens
    public static final String SCHEDULE_APPOINTMENT = SCREENS_BASE_PATH + "schedule-appointment.fxml";
    public static final String PATIENT_APPOINTMENTS = SCREENS_BASE_PATH + "patient-appointments.fxml";
    public static final String DOCTOR_APPOINTMENTS = SCREENS_BASE_PATH + "doctor-appointments.fxml";
    public static final String UPLOAD_VITALS = SCREENS_BASE_PATH + "upload-vitals-data.fxml";
    public static final String UPLOAD_VITALS_DATA = SCREENS_BASE_PATH + "upload-vitals-data.fxml";
    public static final String VIEW_VITALS = SCREENS_BASE_PATH + "view-vitals.fxml";
    public static final String PATIENT_MEDICAL_HISTORY = SCREENS_BASE_PATH + "patient-medical-history.fxml";
    
    // Profile screens
    public static final String PATIENT_PROFILE = SCREENS_BASE_PATH + "patient-profile.fxml";
    public static final String DOCTOR_PROFILE = SCREENS_BASE_PATH + "doctor-profile.fxml";
    public static final String ADMIN_PROFILE = SCREENS_BASE_PATH + "admin-profile.fxml";
    public static final String PATIENT_CHECKUP = SCREENS_BASE_PATH + "c.fxml";
    // Vitals visualization screens
    public static final String VITALS_GRAPH = SCREENS_BASE_PATH + "vitals-graph.fxml";
    
    // Direct dashboard access (for testing)
    public static final String DASHBOARD_SELECTOR = SCREENS_BASE_PATH + "dashboard-selector.fxml";
    
    // Screen titles
    public static final String TITLE_MAIN = "Remote Vitals Monitoring System";
    public static final String TITLE_LOGIN = "Login - Remote Vitals";
    public static final String TITLE_SIGNUP = "Sign Up - Remote Vitals";
    public static final String TITLE_ADMIN_DASHBOARD = "Admin Dashboard - Remote Vitals";
    public static final String TITLE_PATIENT_DASHBOARD = "Patient Dashboard - Remote Vitals";
    public static final String TITLE_DOCTOR_DASHBOARD = "Doctor Dashboard - Remote Vitals";
    public static final String TITLE_SCHEDULE_APPOINTMENT = "Schedule Appointment - Remote Vitals";
    public static final String TITLE_DOCTOR_APPOINTMENTS = "Manage Appointments - Remote Vitals";
    public static final String TITLE_UPLOAD_VITALS = "Upload Vitals Data - Remote Vitals";
    public static final String TITLE_VIEW_VITALS = "View Vitals - Remote Vitals";
    public static final String TITLE_PATIENT_PROFILE = "Patient Profile - Remote Vitals";
    public static final String TITLE_DOCTOR_PROFILE = "Doctor Profile - Remote Vitals";
    public static final String TITLE_ADMIN_PROFILE = "Admin Profile - Remote Vitals";
    public static final String TITLE_VITALS_GRAPH = "Vitals Visualization - Remote Vitals";
    public static final String TITLE_DASHBOARD_SELECTOR = "Dashboard Selection - Remote Vitals";
    public static final String TITLE_PATIENT_MEDICAL_HISTORY = "Medical History - Remote Vitals";
    public static final String TITLE_PATIENT_APPOINTMENTS = "Patient Appointments - Remote Vitals";
    
    public static final String DOCTOR_PATIENTS = "/screens/doctor-patients.fxml";
    public static final String TITLE_DOCTOR_PATIENTS = "My Patients";
    
    public static final String DOCTOR_CHECKUP = "/screens/doctor-checkup.fxml";
    public static final String TITLE_DOCTOR_CHECKUP = "Patient Checkup";

} 