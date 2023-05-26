function showMoreAppointments(link) {
    var table = link.nextElementSibling;
    table.classList.toggle("hidden");
    link.textContent = (table.classList.contains("hidden")) ? "See more" : "Hide";
}