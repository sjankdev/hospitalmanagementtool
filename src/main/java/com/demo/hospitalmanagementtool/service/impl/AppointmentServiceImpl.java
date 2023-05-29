package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.Appointment;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.exceptions.NotFoundException;
import com.demo.hospitalmanagementtool.repository.AppointmentRepository;
import com.demo.hospitalmanagementtool.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment with ID " + id + " not found."));
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(Optional.ofNullable(doctor));
    }

    @Override
    public Map<String, List<Appointment>> groupAppointmentsByDate(List<Appointment> appointments) {
        Map<String, List<Appointment>> groupedAppointments = new HashMap<>();

        for (Appointment appointment : appointments) {
            String formattedDate = appointment.getDateTime().toLocalDate().format(dateFormatter);
            List<Appointment> appointmentsByDate = groupedAppointments.getOrDefault(formattedDate, new ArrayList<>());
            appointmentsByDate.add(appointment);
            groupedAppointments.put(formattedDate, appointmentsByDate);
        }

        return groupedAppointments;
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
}

