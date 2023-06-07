package com.demo.hospitalmanagementtool.service;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;

public interface AppointmentRequestApprovalService {

    void approveAppointmentRequest(AppointmentRequest appointmentRequest);

    void rejectAppointmentRequest(AppointmentRequest appointmentRequest);

}
