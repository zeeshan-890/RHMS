## Remote Vitals Monitoring System
### Table of Contents
- [What is it?](#what-is-it)
- [How it Works](#how-it-works)
- [Project Architecture](#project-architecture)
    - [Backend](#backend)
    - [Frontend](#frontend)

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installing and Running the Project](#installing-and-running-the-project)

DEMO VIDEO:

- GOOGLE DRIVE Link :https://drive.google.com/file/d/1_LwV-QvoZtN-33BFwwXDd1KQW6JqiFhM/view?usp=sharing
  

### What is it?
This project is a **Remote Vitals Monitoring System** designed to enable hospitals, clinics, and caregivers to monitor patients' vital signs and medical history remotely. The system consists of:
- A **backend system** to manage users, patients, doctors, appointments, and vital data.
- A **frontend application** for administrators, doctors, and patients to interact with the system.

The goal is to improve healthcare efficiency by allowing remote consultation and monitoring of patients' health status.
### How it Works
1. **Admin**:
    - Admins can manage patients, doctors, and appointments.
    - Can view and supervise the overall tracking of vitals.

2. **Doctors**:
    - Doctors can view their assigned patients.
    - They can see patients' details, medical history, and vital records.

3. **Patients**:
    - Patients can view their medical history and vital records.
    - They can schedule appointments with doctors.

4. **Vitals Management**:
    - Doctors or caregivers can update patient vital data (heart rate, blood pressure, etc.).
    - Patients and doctors can view these vital records in a graphical or tabular format.

### Project Architecture
#### Backend
The backend is built using **Java**, using the following frameworks and libraries:
- **Jakarta EE**: For managing APIs and services.
- **Spring Data JPA**: For interacting with the database.
- **H2/MySQL**: Used as the database for storing data (can be switched depending on the environment).

##### Key Backend Components
1. **Entities**:
    - : Represents patients undergoing monitoring. `Patient`
    - : Holds doctor profiles and specializations. `Doctor`
    - : Handles authentication and roles (e.g., admin, doctor, patient). `User`
    - `Appointment`: Manages the scheduling of patient-doctor interactions.
    - `Vital`: Tracks patients' vital signs such as blood pressure, heart rate, and more.

2. **Repositories**:
    - Spring Data JPA repositories handle CRUD operations for entities like , , and `Vital`. `Patient``Doctor`

3. **Services**:
    - Business logic is managed in the , `PatientService`, and components. `UserService``AppointmentService`

4. **Database**:
    - The relational database structure includes tables for `Patients`, `Doctors`, `Users`, `Appointments`, and their relationships.

#### Frontend
The frontend application is designed using **JavaFX**, offering a simple yet intuitive user interface.
##### Key Frontend Components
1. **Controllers**:
    - : Displays the list of patients to administrators. `ViewPatientsController`
    - : Allows doctors and patients to view vital records. `ViewVitalsController`
    - : Manages common routines like scene navigation and alert dialogs. `BaseController`

2. **UI Screens**:
    - Use `.fxml` files for defining scenes:
        - : For user authentication. `login.fxml`
        - : Admin dashboard. `admin.fxml`
        - : Displays patient information. `view_patients.fxml`
        - : Shows individual patient vitals. `view_vitals.fxml`

3. **Models**:
    - Frontend models such as mirror backend entities and are used to bind data to UI tables. `Patient`

### Features
1. **User Roles**:
    - Admin, Doctor, and Patient roles for separate functionality.

2. **Vitals Monitoring**:
    - Manage and view vital records for patients:
        - Track blood pressure, oxygen levels, heart rate, and more.

3. **Appointments**:
    - Patients can schedule appointments with doctors.
    - Admins can manage all appointments.

4. **Medical History**:
    - Patients have access to their medical history.
    - Doctors can review and monitor patient histories before consultations.

5. **Frontend Views**:
    - User-friendly JavaFX UI with tables, buttons, and easy navigation.

### Prerequisites
To run this project, ensure the following tools are installed:
1. Java Development Kit (JDK) version 24+
2. Apache Maven
3. H2/MySQL Database (Use H2 for testing or MySQL for production)
4. JavaFX libraries (should be included in your project dependencies)
5. An IDE (e.g., IntelliJ IDEA) for development purposes

### Installing and Running the Project
#### Backend Setup
1. **Clone the Repository**: Clone the repository to your local machine:
``` sh
   git clone <repository-url>
   cd remote-vitals-system
```
1. **Update Database Configuration**:
    - Update the file under `src/main/resources` to configure the database connection: `application.properties`
``` properties
     spring.datasource.url=jdbc:mysql://localhost:3306/remote_vitals
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     spring.jpa.hibernate.ddl-auto=update
```
1. **Compile and Run the Backend**:
    - Use Maven to build the backend:
``` sh
     mvn clean install
     mvn spring-boot:run
```
1. **Database Migration**:
    - If you’re using MySQL, create the database manually or use built-in tools to initialize schema and data.

#### Frontend Setup
1. **Configure JavaFX Runtime**: Ensure that JavaFX is properly configured in your IDE. Add the required JavaFX dependencies in your : `pom.xml`
``` xml
   <dependency>
       <groupId>org.openjfx</groupId>
       <artifactId>javafx-controls</artifactId>
       <version>21</version> <!-- Replace with your compatible JavaFX version -->
   </dependency>
```
1. **Run the Frontend Application**:
    - Open the project in IntelliJ IDEA (or your IDE).
    - Run the or another main JavaFX scene by navigating to `src/main/java`. `ViewPatientsController`

### Key Files/Pages
1. **Frontend Files**:
    - `src/main/resources/fxml/`: Contains all `.fxml` scenes like and files. `login.fxml``admin.fxml`
    - `src/main/java/com/remote_vitals/frontend/controllers`: Includes all `JavaFX` controllers.

2. **Backend Files**:
    - Entities: `src/main/java/com/remote_vitals/backend/user/entities/`
    - Services: `src/main/java/com/remote_vitals/backend/services/`

### Contributing
Feel free to contribute to this project by:
- Reporting issues.
- Suggesting features.
- Submitting pull requests.

### License
This project is licensed under the MIT License. See the LICENSE file for more information.
Let me know if you'd like any part of the README updated or tailored further
