document.addEventListener("DOMContentLoaded", function() {
        toggleAppointmentRequests();
    });

    function toggleAppointmentRequests() {
        var checkbox = document.getElementById("show-appointment-requests");
        var appointmentRequests = document.getElementsByClassName("appointment-request-row");
        for (var i = 0; i < appointmentRequests.length; i++) {
            appointmentRequests[i].style.display = checkbox.checked ? "table-row" : "none";
        }
    }