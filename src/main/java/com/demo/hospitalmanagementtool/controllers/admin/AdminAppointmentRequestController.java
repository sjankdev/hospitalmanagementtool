package com.demo.hospitalmanagementtool.controllers.admin;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.Doctor;
import com.demo.hospitalmanagementtool.service.AppointmentRequestApprovalService;
import com.demo.hospitalmanagementtool.service.AppointmentRequestService;
import com.demo.hospitalmanagementtool.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth-appointment-request")
public class AdminAppointmentRequestController {

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentRequestApprovalService appointmentRequestApprovalService;


    @GetMapping("/doctor/{doctorId}/requests")
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


    @PostMapping("/doctor/{doctorId}/requests/{requestId}/approve")
    public String approveAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.approveAppointmentRequest(appointmentRequest);
            return "redirect:/appointment-request/doctor/" + doctorId + "/requests";
        } else {
            return "error";
        }
    }

    @PostMapping("/doctor/{doctorId}/requests/{requestId}/reject")
    public String rejectAppointmentRequest(@PathVariable Long doctorId, @PathVariable Long requestId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        AppointmentRequest appointmentRequest = appointmentRequestService.getAppointmentRequestById(requestId);

        if (appointmentRequest != null && appointmentRequest.getDoctor().equals(doctor)) {
            appointmentRequestApprovalService.rejectAppointmentRequest(appointmentRequest);
            return "redirect:/appointment-request/doctor/" + doctorId + "/requests";
        } else {
            return "error";
        }
    }
}


