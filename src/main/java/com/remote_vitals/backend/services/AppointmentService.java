package com.remote_vitals.backend.services;

import com.remote_vitals.entities.Appointment;
import com.remote_vitals.entities.Schedule;
import com.remote_vitals.repositories.AppointmentStatus;
import com.remote_vitals.repositories.AppointmentRepository;
import com.remote_vitals.repositories.ScheduleRepository;
import com.remote_vitals.entities.StaticDataClass;
import com.remote_vitals.entities.Doctor;
import com.remote_vitals.entities.Patient;
import com.remote_vitals.repositories.DoctorRepository;
import com.remote_vitals.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    // beans
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;

    // constructor
    public AppointmentService(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            ScheduleRepository scheduleRepository
    ) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    // methods
    @Transactional
    public String requestAppointment(Integer DoctorId,LocalDateTime time) {
        System.out.println("--> 1");
        if (DoctorId == null) return "DoctorId is Empty";
        if(time == null) return "Time is Empty";
        System.out.println("--> 2");
        Optional<Doctor> doctorOptional = doctorRepository.findById(DoctorId);
        if (!doctorOptional.isPresent()) {
            return "Doctor Not Found";
        }
        System.out.println("--> 3");
        doctorOptional.ifPresent(doctor -> {
            Patient patient = patientRepository.findById(StaticDataClass.currentUserId).get();
            Schedule schedule = new Schedule();
            schedule.setStartingTime(time);
            String dateTimeString = "2050-01-01T00:00:00";
            schedule.setEndingTime(LocalDateTime.parse(dateTimeString));
            Appointment appointment = new Appointment(
                    AppointmentStatus.REQUESTED, patient, doctor, schedule
            );
            System.out.println("--> 4");
            schedule.setAppointment(appointment);
            if(doctor.getAppointments() == null) doctor.setAppointments(Arrays.asList(appointment));
            else doctor.getAppointments().add(appointment);
            System.out.println("--> 5");
            if(patient.getAppointments() == null) patient.setAppointments(Arrays.asList(appointment));
            else patient.getAppointments().add(appointment);
            appointmentRepository.save(appointment);
            scheduleRepository.save(schedule);
            System.out.println("--> 6");
            appointment.getSchedule().getStartingTime();
        });


        return "Appointment Requested Successfully";
    }

    @Transactional
    public Optional<Appointment> changeAppointmentStatus(Integer AppointmentId, AppointmentStatus newStatus) {
        Optional<Appointment> wrappedAppointment = appointmentRepository.findById(AppointmentId);
        if (wrappedAppointment.isEmpty()) return null;
        wrappedAppointment.get().setStatus(newStatus);
        return wrappedAppointment;
    }




    // for doctor to get all the patients who have appointments with him/her
    public List<Appointment> getAllAppointments() {
        Optional<Doctor> wrappedDoctor = doctorRepository.findById(StaticDataClass.currentUserId);
        // ye function is project men null return normally nhi karega
        return wrappedDoctor.map(Doctor::getAppointments).orElse(null);
    }
    
    // for patient to get all their appointments
    public List<Appointment> getAllPatientAppointments() {
        Optional<Patient> wrappedPatient = patientRepository.findById(StaticDataClass.currentUserId);
        return wrappedPatient.map(Patient::getAppointments).orElse(null);
    }
    


    public Optional<Appointment> setAppointmentLink(Integer AppointmentId,String link){
        appointmentRepository.findById(AppointmentId).ifPresent(appointment -> {
            appointment.setLinkForRoom(link);
            appointmentRepository.save(appointment);
        });
        return appointmentRepository.findById(AppointmentId);
    }
}