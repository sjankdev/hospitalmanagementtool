package com.demo.hospitalmanagementtool.controllers.doctor;

import com.demo.hospitalmanagementtool.entities.*;
import com.demo.hospitalmanagementtool.repository.AppointmentRequestRepository;
import com.demo.hospitalmanagementtool.service.AppointmentRequestApprovalService;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.DoctorAppointmentCalendarService;
import com.demo.hospitalmanagementtool.service.DoctorService;
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
    AppointmentRequestRepository appointmentRequestRepository;

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
            return "error";
        }
    }


    @GetMapping("/{doctorId}/requests")
    public String viewAppointmentRequests(@PathVariable Long doctorId, Model model, Principal principal) {
        String username = principal.getName();

        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor != null && doctor.getUsername().equals(username)) {
            model.addAttribute("doctor", doctor);
            model.addAttribute("appointmentRequests", appointmentRequestService.getAppointmentRequestsForDoctor(doctor));
            return "doctor/appointment-requests";
        } else {
            return "error";
        }
    }


    @PostMapping("/{doctorId}/requests/{requestId}/approve")
    public String approveAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.approveAppointmentRequest(appointmentRequest);
            return "redirect:/doctor/" + doctorId + "/requests";
        } else {
            return "error";
        }
    }

    @PostMapping("/{doctorId}/requests/{requestId}/reject")
    public String rejectAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.rejectAppointmentRequest(appointmentRequest);
            return "redirect:/doctor/" + doctorId + "/requests";
        } else {
            return "error";
        }
    }

    @GetMapping("/{doctorId}/appointments")
    public String getDoctorAppointments(@PathVariable Long doctorId,
                                        @RequestParam(value = "year", required = false, defaultValue = "2023") int year,
                                        @RequestParam(value = "month", required = false, defaultValue = "1") int month,
                                        Model model, Principal principal) {

        String username = principal.getName();

        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor != null && doctor.getUsername().equals(username)) {
            List<Appointment> appointments = calendarService.getAppointmentsByDoctor(doctor);
            LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
            LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
            List<Appointment> appointmentsForMonth = calendarService.getAppointmentsForMonth(appointments, firstDayOfMonth, lastDayOfMonth);
            Map<String, List<Appointment>> appointmentsByDate = calendarService.groupAppointmentsByDate(appointmentsForMonth);

            // Get approved appointments
            List<Appointment> approvedAppointments = appointmentRequestRepository.findByDoctorAndAppointmentRequestApprovalStatus(doctor, AppointmentRequestApprovalStatus.APPROVED)
                    .stream()
                    .map(Appointment::new)
                    .collect(Collectors.toList());

            List<Appointment> approvedAppointmentsForMonth = approvedAppointments.stream()
                    .filter(appointment -> {
                        LocalDate appointmentDate = appointment.getDateTime().toLocalDate();
                        return appointmentDate.getYear() == year && appointmentDate.getMonthValue() == month;
                    })
                    .collect(Collectors.toList());

            model.addAttribute("approvedAppointmentsForMonth", approvedAppointmentsForMonth);

            calendarService.setModelAttributesDoctor(model, doctor, appointmentsByDate, year, month);
            model.addAttribute("approvedAppointments", approvedAppointments);
        }
        return "doctor-appointment-calendar";
    }


}
