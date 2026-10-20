/**
 * MediLink - Healthcare Management System
 * Core Application Engine & Data Layer (app.js)
 */

(function () {
  // Initialize Mock Data if not present
  const defaultDoctors = [
    {
      id: "doc-1",
      name: "Dr. Rohan Mehta",
      specialty: "Cardiology",
      hospital: "City Heart Center",
      city: "Mumbai",
      experience: "12 years",
      fee: 800,
      rating: 4.9,
      slots: ["09:00 AM", "10:30 AM", "02:00 PM", "04:30 PM"],
      avatar: "👨‍⚕️"
    },
    {
      id: "doc-2",
      name: "Dr. Ananya Sharma",
      specialty: "Dermatology",
      hospital: "Skin & Glow Clinic",
      city: "Pune",
      experience: "8 years",
      fee: 600,
      rating: 4.8,
      slots: ["10:00 AM", "11:30 AM", "03:00 PM", "05:00 PM"],
      avatar: "👩‍⚕️"
    },
    {
      id: "doc-3",
      name: "Dr. Vikram Patil",
      specialty: "Orthopedics",
      hospital: "Care Ortho Hospital",
      city: "Mumbai",
      experience: "15 years",
      fee: 900,
      rating: 4.7,
      slots: ["09:30 AM", "11:00 AM", "01:30 PM", "04:00 PM"],
      avatar: "👨‍⚕️"
    },
    {
      id: "doc-4",
      name: "Dr. Priya Kulkarni",
      specialty: "Pediatrics",
      hospital: "Little Angels Care",
      city: "Bangalore",
      experience: "10 years",
      fee: 700,
      rating: 4.9,
      slots: ["10:00 AM", "12:00 PM", "03:30 PM", "06:00 PM"],
      avatar: "👩‍⚕️"
    },
    {
      id: "doc-5",
      name: "Dr. Rajesh Iyer",
      specialty: "Neurology",
      hospital: "Apollo Neuro Institute",
      city: "Delhi",
      experience: "18 years",
      fee: 1200,
      rating: 4.9,
      slots: ["11:00 AM", "01:00 PM", "04:00 PM", "05:30 PM"],
      avatar: "👨‍⚕️"
    },
    {
      id: "doc-6",
      name: "Dr. Sneha Verma",
      specialty: "General Medicine",
      hospital: "LifeCare Hospital",
      city: "Pune",
      experience: "6 years",
      fee: 500,
      rating: 4.6,
      slots: ["09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM"],
      avatar: "👩‍⚕️"
    }
  ];

  const defaultUsers = [
    {
      name: "John Doe",
      email: "patient@demo.com",
      password: "password123",
      role: "patient",
      phone: "9876543210",
      age: 28,
      gender: "Male"
    },
    {
      name: "Dr. Rohan Mehta",
      email: "doctor@demo.com",
      password: "password123",
      role: "doctor",
      specialty: "Cardiology",
      hospital: "City Heart Center"
    }
  ];

  const defaultAppointments = [
    {
      id: "APT-1001",
      patientEmail: "patient@demo.com",
      patientName: "John Doe",
      doctorId: "doc-1",
      doctorName: "Dr. Rohan Mehta",
      specialty: "Cardiology",
      hospital: "City Heart Center",
      date: "2026-10-25",
      time: "10:30 AM",
      type: "In-Person",
      fee: 800,
      status: "Confirmed",
      createdDate: "2026-10-18"
    },
    {
      id: "APT-1002",
      patientEmail: "patient@demo.com",
      patientName: "John Doe",
      doctorId: "doc-2",
      doctorName: "Dr. Ananya Sharma",
      specialty: "Dermatology",
      hospital: "Skin & Glow Clinic",
      date: "2026-11-02",
      time: "03:00 PM",
      type: "Video Call",
      fee: 600,
      status: "Confirmed",
      createdDate: "2026-10-19"
    }
  ];

  if (!localStorage.getItem("medilink_doctors")) {
    localStorage.setItem("medilink_doctors", JSON.stringify(defaultDoctors));
  }
  if (!localStorage.getItem("medilink_users")) {
    localStorage.setItem("medilink_users", JSON.stringify(defaultUsers));
  }
  if (!localStorage.getItem("medilink_appointments")) {
    localStorage.setItem("medilink_appointments", JSON.stringify(defaultAppointments));
  }

  // MediLink Global API
  window.MediLink = {
    getDoctors: function () {
      return JSON.parse(localStorage.getItem("medilink_doctors") || "[]");
    },
    getDoctorById: function (id) {
      const docs = this.getDoctors();
      return docs.find(d => d.id === id);
    },
    getUsers: function () {
      return JSON.parse(localStorage.getItem("medilink_users") || "[]");
    },
    getCurrentUser: function () {
      const u = localStorage.getItem("medilink_active_user");
      return u ? JSON.parse(u) : null;
    },
    setCurrentUser: function (user) {
      localStorage.setItem("medilink_active_user", JSON.stringify(user));
    },
    logout: function () {
      localStorage.removeItem("medilink_active_user");
      window.location.href = "login.html";
    },
    registerUser: function (userData) {
      const users = this.getUsers();
      if (users.find(u => u.email.toLowerCase() === userData.email.toLowerCase())) {
        return { success: false, message: "User with this email already exists." };
      }
      users.push(userData);
      localStorage.setItem("medilink_users", JSON.stringify(users));
      return { success: true, message: "Registration successful! Please login." };
    },
    loginUser: function (email, password, role) {
      const users = this.getUsers();
      const user = users.find(u => u.email.toLowerCase() === email.toLowerCase().trim() && u.password === password);
      if (!user) {
        return { success: false, message: "Invalid email or password." };
      }
      if (role && user.role !== role) {
        return { success: false, message: "Selected role does not match account type." };
      }
      this.setCurrentUser(user);
      return { success: true, user: user };
    },
    getAppointments: function () {
      return JSON.parse(localStorage.getItem("medilink_appointments") || "[]");
    },
    getPatientAppointments: function (email) {
      const all = this.getAppointments();
      return all.filter(a => a.patientEmail.toLowerCase() === email.toLowerCase());
    },
    bookAppointment: function (bookingData) {
      const appointments = this.getAppointments();
      const newApt = {
        id: "APT-" + Math.floor(1000 + Math.random() * 9000),
        status: "Confirmed",
        createdDate: new Date().toISOString().split("T")[0],
        ...bookingData
      };
      appointments.push(newApt);
      localStorage.setItem("medilink_appointments", JSON.stringify(appointments));
      return newApt;
    },
    cancelAppointment: function (appointmentId) {
      const appointments = this.getAppointments();
      const idx = appointments.findIndex(a => a.id === appointmentId);
      if (idx !== -1) {
        appointments[idx].status = "Cancelled";
        localStorage.setItem("medilink_appointments", JSON.stringify(appointments));
        return { success: true, message: "Appointment cancelled successfully." };
      }
      return { success: false, message: "Appointment ID not found." };
    }
  };
})();
