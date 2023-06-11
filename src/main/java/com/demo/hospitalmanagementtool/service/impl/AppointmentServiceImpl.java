package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.AppointmentRequestApprovalStatus;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.repository.AppointmentRequestRepository;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentRequestRepository appointmentRequestRepository;


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

}

