package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.AppointmentRequestApprovalStatus;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.AppointmentRequestRepository;
import com.demo.hospitalmanagementtool.repository.PatientRepository;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentRequestRepository appointmentRequestRepository;

    @Autowired
    PatientRepository patientRepository;


    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment with ID " + id + " not found."));
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        Long patientId = appointment.getPatient().getId();

        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }

        Patient patient = optionalPatient.get();
        appointment.setPatient(patient);

        String title = patient.getEmail() + " - " + appointment.getDateTime();
        appointment.setTitle(title);

        appointmentRepository.save(appointment);
    }

    @Override
    public void updateAppointment(Long id, Appointment appointment) {
        Appointment existingAppointment = getAppointmentById(id);
        existingAppointment.setPatient(appointment.getPatient());
        existingAppointment.setDoctor(appointment.getDoctor());
        existingAppointment.setReasonForVisit(appointment.getReasonForVisit());
        existingAppointment.setNotes(appointment.getNotes());
        existingAppointment.setStatus(appointment.getStatus());
        existingAppointment.setMedicalRecord(appointment.getMedicalRecord());
        existingAppointment.setDateTime(appointment.getDateTime());
        appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getApprovedAppointments(int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        List<AppointmentRequest> approvedRequests = appointmentRequestRepository.findByAppointmentRequestApprovalStatusAndDateTimeBetween(AppointmentRequestApprovalStatus.APPROVED, firstDayOfMonth.atStartOfDay(), lastDayOfMonth.atTime(LocalTime.MAX));

        return approvedRequests.stream().map(approvedRequest -> new Appointment(approvedRequest.getDateTime(), approvedRequest.getPatient(), approvedRequest.getDoctor())).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> filterAppointmentsByMonth(List<Appointment> appointments, int year, int month) {
        return appointments.stream().filter(appointment -> appointment.getDateTime().toLocalDate().getYear() == year && appointment.getDateTime().toLocalDate().getMonthValue() == month).collect(Collectors.toList());
    }

}

