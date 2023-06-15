package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.*;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.AppointmentRequestRepository;
import com.demo.hospitalmanagementtool.repository.DoctorRepository;
import com.demo.hospitalmanagementtool.repository.PatientRepository;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentRequestServiceImpl implements AppointmentRequestService {

    private final AppointmentRequestRepository appointmentRequestRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;


    public AppointmentRequestServiceImpl(AppointmentRequestRepository appointmentRequestRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.appointmentRequestRepository = appointmentRequestRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void createAppointmentRequest(AppointmentRequest appointmentRequest) {
        Patient patient = patientRepository.findById(appointmentRequest.getPatient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID: " + appointmentRequest.getPatient().getId()));

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + appointmentRequest.getDoctor().getId()));

        appointmentRequest.setPatient(patient);
        appointmentRequest.setDoctor(doctor);
        appointmentRequest.setAppointmentRequestApprovalStatus(AppointmentRequestApprovalStatus.PENDING);

        appointmentRequestRepository.save(appointmentRequest);
    }

    @Override
    public List<AppointmentRequest> getAppointmentRequestsForDoctor(Doctor doctor) {
        return appointmentRequestRepository.findByDoctor(doctor);
    }

    @Override
    public AppointmentRequest getAppointmentRequestById(Long id) {
        return appointmentRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appointment request ID: " + id));
    }

    @Override
    public List<AppointmentRequest> getAllApprovedAppointments() {
        return appointmentRequestRepository.findByAppointmentRequestApprovalStatus(AppointmentRequestApprovalStatus.APPROVED);
    }

    @Override
    public void updateRequestAppointment(Long id, AppointmentRequest appointmentRequest) {
        AppointmentRequest existingRequestAppointment = getAppointmentRequestById(id);
        existingRequestAppointment.setDateTime(appointmentRequest.getDateTime());
        existingRequestAppointment.setReason(appointmentRequest.getReason());
        existingRequestAppointment.setSymptoms(appointmentRequest.getSymptoms());
        existingRequestAppointment.setAdditionalNotes(appointmentRequest.getAdditionalNotes());
        appointmentRequestRepository.save(existingRequestAppointment);
    }

    @Override
    public void deleteRequestAppointment(Long id) {
        appointmentRequestRepository.deleteById(id);
    }

    @Override
    public List<AppointmentRequest> getAllAppointmentRequests() {
        return appointmentRequestRepository.findAll();
    }

    @Override
    public List<Appointment> getApprovedAppointmentsForDoctor(Doctor doctor) {
        return appointmentRequestRepository.findByDoctorAndAppointmentRequestApprovalStatus(doctor, AppointmentRequestApprovalStatus.APPROVED)
                .stream()
                .map(Appointment::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(Optional.ofNullable(doctor));
    }

    @Override
    public List<AppointmentRequest> getApprovedAppointmentRequests() {
        return appointmentRequestRepository.findByAppointmentRequestApprovalStatus(
                AppointmentRequestApprovalStatus.APPROVED);
    }
}
