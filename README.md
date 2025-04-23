# Hospital Management System

## Overview
This state-of-the-art Hospital Management System streamlines healthcare operations with an intelligent notification infrastructure. The system offers an intuitive web interface for hospital staff while providing powerful backend services for patient care coordination.

## 🌟 Key Features

### Advanced Notification System
- **Email Alerts**: Instantly send critical information to patients and medical staff using Gmail's SMTP service
- **SMS Notifications**: Deliver urgent alerts through text messages
- **Appointment Reminders**: Automated notifications to reduce no-shows and improve scheduling
- **Medication Reminders**: Timely alerts for medication adherence

### Emergency Alert System
- **Vital Sign Monitoring**: Continuous monitoring of patient vital signs
- **Abnormal Vitals Detection**: Instant alerts when vitals exceed safe thresholds
- **Emergency Response**: Rapid notification of medical staff during critical situations

### User Management
- **Role-based Access**: Specialized interfaces for Doctors, Patients, and Administrators
- **Doctor Portal**: Manage appointments, record vital signs, and provide patient feedback
- **Patient Portal**: View appointments, medications, and personal health records
- **Administrative Controls**: System-wide management and user oversight

## 💻 Technical Implementation

- **Modern Java Architecture**: Built with contemporary Java practices for reliability
- **Secure Authentication**: Protect sensitive medical information
- **Flexible Notification Framework**: Easily extendable to accommodate various communication channels
- **Robust Data Management**: Structured approach to handling patient records and medical data

## 🚀 Getting Started

1. Clone the repository
2. Configure your email credentials in `src/EmailNotification.java`
3. Build the project using your preferred Java IDE or build tool
4. Run the application using `Main.java`

## 📧 Email Configuration

The system uses Gmail SMTP for sending notifications:
- Update the sender email and app password in `EmailNotification.java`
- Generate an app password through your Google account security settings
- The system supports both plain text and HTML formatted emails

## 🔒 Security

- All sensitive patient data is handled with strict privacy controls
- Email credentials are secured and can be easily updated
- System employs proper authentication mechanisms to protect user information

## 📱 Web Interface

The front-end provides an intuitive experience for all users with:
- Responsive design that works across devices
- Real-time notifications and alerts
- User-friendly appointment scheduling
- Interactive dashboards for monitoring patient health
- 
## 📋 Prerequisites

1. **Java Development Kit (JDK):** Ensure you have JDK 8 or later installed on your system.
2. **Gmail App Password:** Generate an app password through your Google account for secure email sending.
3. **Java Libraries:** The project uses the following libraries:
   - `jakarta.mail` for email handling.
   - `jakarta.activation` for mail attachments.
   - `itextpdf` for PDF generation (optional).

## 🚀 Getting Started

### 1. Set Up the Project
- Clone the repository to your local machine:
  ```sh
  git clone https://github.com/muneebahmed-nust/Mail-Sender.git
  ```
- Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

### 2. Configure Email Credentials
- Locate the `EmailNotification` class (you may find it in the `src` directory).
- Update the sender email and Gmail app password in the configuration section:
  ```java
  String senderEmail = "your-email@gmail.com";
  String appPassword = "your-app-password";
  ```

### 3. Build and Run
- Build the project using your IDE or a build tool like Maven or Gradle.
- Run the `TestEmailSender.java` file to test the application:
  ```sh
  java -cp bin src.TestEmailSender
  ```
---

This Hospital Management System represents the perfect blend of technology and healthcare, putting patient care and communication at the forefront while maintaining the highest standards of medical practice.
