
# Hospital Management Tool

The Hospital Management Tool is a software application designed to streamline and automate various administrative tasks in a hospital or healthcare facility. It provides an intuitive user interface for managing patient records, appointments, medical staff, inventory, and other essential aspects of hospital management.

## Features

- **Admin Dashboard**: The admin has access to multiple modules and can perform CRUD (Create, Read, Update, Delete) operations on the following features:
  - **Appointments**: Create, update, and manage appointments between doctors and patients.
  - **Doctors**: Manage doctor profiles, including their qualifications, schedules, and availability.
  - **Patients**: Maintain patient records, including demographics, medical history, and treatment details.
  - **Medical Records**: Access and update patient medical records securely.
  - **Billing**: Generate bills and manage invoicing for patient services.
  - **Staff**: Maintain records of hospital staff members, including doctors, nurses, and other healthcare professionals.
  - **Inventory**: Manage medical supplies, equipment, and medications inventory.

- **Authentication and Authorization**: The application implements a JWT-based authentication system with role-based access control. It includes separate login and registration pages for admin, patients, and doctors. Each user receives a unique token upon login, and the application verifies the token for secure API access. Only the admin role has access to all modules, while doctors and patients have limited access based on their roles.

- **Doctor Appointments**: Doctors can view appointment requests sent by patients and approve or reject them. They also have access to a calendar view that displays all appointments, including those created by admin or patients.

- **Patient Appointments**: When a patient logs in for the first time, they are prompted to choose their doctor. They can select their preferred doctor and then create appointments with that doctor. Patients can also view their appointment history and manage their requests for appointments.


## Technologies Used

- Frontend: HTML, CSS, JavaScript, Thymeleaf
- Backend: Java, Spring
- Database: MySQL


## Contact

For any inquiries or suggestions, please contact us at [stefanjanko.dev@gmail.com](mailto:stefanjanko.dev@gmail.com).

---

