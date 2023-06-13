package com.demo.hospitalmanagementtool.controllers.doctor;

import com.demo.hospitalmanagementtool.entities.*;
import com.demo.hospitalmanagementtool.repository.AppointmentRequestRepository;
import com.demo.hospitalmanagementtool.security.token.services.DoctorSecurityService;
import com.demo.hospitalmanagementtool.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    private AppointmentRequestApprovalService appointmentRequestApprovalService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    private DoctorAppointmentCalendarService calendarService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorSecurityService doctorSecurityService;

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Doctor> optionalDoctor = doctorService.getDoctorByUsername(username);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            model.addAttribute("doctor", doctor);
            model.addAttribute("doctorId", doctor.getId());
            return "doctor/index";
        } else {
            return "error/unauthorized-access";
        }
    }

    @GetMapping("/{doctorId}/requests")
    public String viewAppointmentRequests(@PathVariable Long doctorId, Model model, Principal principal) {
        Doctor doctor = doctorSecurityService.validateDoctor(doctorId, principal);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointmentRequests", appointmentRequestService.getAppointmentRequestsForDoctor(doctor));
            return "doctor/appointment-requests";
        } else {
            return "error/unauthorized-access";
        }
    }

    @PostMapping("/{doctorId}/requests/{requestId}/approve")
    public String approveAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId, Principal principal) {
        Doctor doctor = doctorSecurityService.validateDoctor(doctorId, principal);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.approveAppointmentRequest(appointmentRequest);
            return "redirect:/doctor/" + doctorId + "/requests";
        } else {
            return "error/unauthorized-access";
        }
    }

    @PostMapping("/{doctorId}/requests/{requestId}/reject")
    public String rejectAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId, Principal principal) {
        Doctor doctor = doctorSecurityService.validateDoctor(doctorId, principal);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.rejectAppointmentRequest(appointmentRequest);
            return "redirect:/doctor/" + doctorId + "/requests";
        } else {
            return "error/unauthorized-access";
        }
    }

    @GetMapping("/{doctorId}/appointments")
    public String getDoctorAppointments(@PathVariable Long doctorId, @RequestParam(value = "year", required = false, defaultValue = "2023") int year, @RequestParam(value = "month", required = false, defaultValue = "1") int month, Model model, Principal principal) {
        Doctor doctor = doctorSecurityService.validateDoctor(doctorId, principal);

        if (doctor != null) {
            List<Appointment> appointments = calendarService.getAppointmentsByDoctor(doctor);
            LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
            LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
            List<Appointment> appointmentsForMonth = calendarService.getAppointmentsForMonth(appointments, firstDayOfMonth, lastDayOfMonth);
            Map<String, List<Appointment>> appointmentsByDate = calendarService.groupAppointmentsByDate(appointmentsForMonth);

            List<Appointment> approvedAppointments = appointmentRequestService.getApprovedAppointmentsForDoctor(doctor);
            List<Appointment> approvedAppointmentsForMonth = appointmentService.filterAppointmentsByMonth(approvedAppointments, year, month);

            model.addAttribute("approvedAppointmentsForMonth", approvedAppointmentsForMonth);

            calendarService.setModelAttributesDoctor(model, doctor, appointmentsByDate, year, month);
            model.addAttribute("approvedAppointments", approvedAppointments);

            return "doctor-appointment-calendar";
        } else {
            return "error/unauthorized-access";
        }
    }
}