package com.demo.hospitalmanagementtool.service.impl;

import com.demo.hospitalmanagementtool.entities.AppointmentRequest;
import com.demo.hospitalmanagementtool.entities.AppointmentRequestApprovalStatus;
import com.demo.hospitalmanagementtool.service.AppointmentRequestApprovalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentRequestApprovalServiceImpl implements AppointmentRequestApprovalService {

    public AppointmentRequestApprovalStatus getApprovalStatus(AppointmentRequest appointmentRequest) {
        return appointmentRequest.getAppointmentRequestApprovalStatus();
    }

    @Transactional
    @Override
    public void approveAppointmentRequest(AppointmentRequest appointmentRequest) {
        appointmentRequest.setAppointmentRequestApprovalStatus(AppointmentRequestApprovalStatus.APPROVED);

    }

    @Transactional
    @Override
    public void rejectAppointmentRequest(AppointmentRequest appointmentRequest) {
        appointmentRequest.setAppointmentRequestApprovalStatus(AppointmentRequestApprovalStatus.REJECTED);

    }
}
