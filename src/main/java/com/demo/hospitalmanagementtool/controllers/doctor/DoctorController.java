package com.demo.hospitalmanagementtool.controllers.doctor;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.entities.Patient;
import com.demo.hospitalmanagementtool.service.AppointmentRequestApprovalService;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    private AppointmentRequestApprovalService appointmentRequestApprovalService;

    @Autowired
    DoctorService doctorService;

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
    public String viewAppointmentRequests(@PathVariable Long doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor != null) {
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
}
