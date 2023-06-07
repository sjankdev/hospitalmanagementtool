package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.repository.AppointmentRequestRepository;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import com.demo.hospitalmanagementtool.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AppointmentRequestServiceImpl implements AppointmentRequestService {

    private final AppointmentRequestRepository appointmentRequestRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentRequestServiceImpl(AppointmentRequestRepository appointmentRequestRepository,
                                         PatientService patientService,
                                         DoctorService doctorService) {
        this.appointmentRequestRepository = appointmentRequestRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @Override
    public AppointmentRequest createAppointmentRequest(Long patientId, Long doctorId, LocalDate date, LocalTime time) {
        Patient patient = patientService.getPatientById(patientId);
        Doctor doctor = doctorService.getDoctorById(doctorId);

        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setPatient(patient);
        appointmentRequest.setDoctor(doctor);
        appointmentRequest.setDate(date);
        appointmentRequest.setTime(time);
        appointmentRequest.setApproved(false);

        return appointmentRequestRepository.save(appointmentRequest);
    }
}
