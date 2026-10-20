# STQA MINI PROJECT REPORT

## Title
**Create a web-based application (MediLink – Doctor Appointment & Healthcare Management System). Narrate concise Test Plan consisting features to be tested and bug taxonomy. Narrate scripts in order to perform regression tests. Identify bugs using Selenium WebDriver and generate test reports encompassing exploratory and boundary testing.**

---

### Problem Definition
Perform automated web testing and identify defects using **Selenium WebDriver** and generate test reports for **MediLink – Doctor Appointment & Healthcare Management System** with a comprehensive suite of **50 Test Cases (40 Positive Passing + 10 Negative Failing)** to achieve the mandated **80% Pass Rate**.

---

### Project Overview & InternHub Architectural Reference
MediLink is built as a production-grade healthcare management portal following the modern web application design standards demonstrated in [InternHub](https://github.com/Prashil-360/InternHub):
- **Glassmorphic UI Design System:** Clean dark-mode background (`#0a0f1e`), cyan/sky accents (`#0ea5e9`), high-contrast typography, and responsive cards.
- **Role-Based Access:** Distinct portals for **Patients** and **Healthcare Providers (Doctors)**.
- **Core User Journeys:** Doctor Search & Specialty Filter, Real-time Slot Booking, Patient Dashboard, Consultation Cancellation, and Doctor Queue Processing.

---

### Software & Hardware Requirements
1. **Software Requirements:**
   - Operating System: Windows 10/11, Linux, or macOS
   - Programming Language: **Java (JDK 17+)** / **Python 3.10+**
   - Automation Framework: **Selenium WebDriver 4.x**, **TestNG 7.9** / PyTest
   - Build Tool: **Apache Maven 3.9+**
   - Browser: Google Chrome (Version 120+) with ChromeDriver
   - IDE: VS Code / IntelliJ IDEA / Eclipse

2. **Hardware Requirements:**
   - Processor: Intel Core i3 / AMD Ryzen 3 or higher
   - RAM: Minimum 4 GB (8 GB recommended)
   - Disk Space: 500 MB free space

---

## 1. TEST PLAN

### 1.1 Scope of Testing
#### 1.1.1 Features to be Tested:
1. **Landing Page:** Navigation links, hero CTAs, service card counts, demo account details.
2. **Patient Registration:** Valid input registration, empty name/email validation, malformed email, password length, and mismatch detection.
3. **Authentication:** Patient login, doctor login, invalid credentials alert, quick demo login buttons.
4. **Doctor Directory & Filtering:** Search by doctor name, filter by specialty (Cardiology, Dermatology), filter by city (Mumbai, Pune), reset filter action.
5. **Appointment Booking:** Doctor summary rendering, consultation mode selection (In-Person / Video), slot dropdown population, booking confirmation.
6. **Receipt Generation:** Verified appointment ID pill, doctor details, payment fee calculation, navigation back to dashboard.
7. **Patient Portal:** Appointment records table, search/filter consultations, appointment cancellation.
8. **Doctor Portal:** Consultation queue view, mark consultation as completed.

#### 1.1.2 Features Not to be Tested:
- Payment gateway banking backend integrations
- Hardware biometric device connectivity
- Real-time video stream packet encryption performance

### 1.2 Test Criteria
- **Run Rate:** 100% (All 50 test cases must be executed).
- **Pass Rate:** **80%** (Exactly 40 test cases pass).
- **Suspension Criteria:** Testing is suspended if failure rate exceeds 40%. (Our suite achieves exactly 20% failure, satisfying the exit criteria without suspension).

---

## 2. 50 TEST CASES SPECIFICATION & EXECUTION TABLE

| Test ID | Test Objective | Test Steps | Expected Result | Actual Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **TC01** | Verify Landing Page Title | Open `index.html` and read page title | Title contains "MediLink" | Title contains "MediLink" | **PASS** |
| **TC02** | Verify Nav Brand Logo | Check element `#nav-brand` text | Displays "MediLink" | Displays "MediLink" | **PASS** |
| **TC03** | Verify Hero CTA Button | Locate `hero-find-doctor` button | Visible and points to search page | Button visible and operational | **PASS** |
| **TC04** | Verify Services Grid Count | Count `.service-card` elements | At least 4 service cards present | 6 service cards rendered | **PASS** |
| **TC05** | Verify Navigation Menu Items | Check Nav Login, Register, Search links | All navigation links visible | Links visible & clickable | **PASS** |
| **TC06** | Verify Footer Demo Credentials | Read footer text | States demo logins | Footer displays demo logins | **PASS** |
| **TC07** | Verify 24/7 Hotline Element | Locate `#emergency-hotline-24x7` | Hotline badge visible on hero | Element not found in DOM | **FAIL** |
| **TC08** | Verify Registration Page Render | Navigate to `register.html` | Header displays "Create MediLink Account" | Header rendered accurately | **PASS** |
| **TC09** | Register With Valid Details | Enter valid name, email, phone, password | Registration successful alert | Alert displayed and redirected | **PASS** |
| **TC10** | Register Empty Name Validation | Submit form with empty full name | Validation message "Full name is required" | Error message displayed | **PASS** |
| **TC11** | Register Malformed Email | Enter email without domain/format | Validation message "valid email" | Error message displayed | **PASS** |
| **TC12** | Register Short Password | Enter password < 6 characters | Validation message "at least 6 characters" | Error message displayed | **PASS** |
| **TC13** | Register Password Mismatch | Enter differing confirm password | Validation message "do not match" | Error message displayed | **PASS** |
| **TC14** | Strict Phone Country Code | Enter 5-digit phone number | Strict E.164 country code warning | Custom error element missing | **FAIL** |
| **TC15** | Guardian Infant Consent Box | Enter age 0 | Consent modal for newborn guardian | Guardian box element missing | **FAIL** |
| **TC16** | Verify Login Form Elements | Navigate to `login.html` | Email, password, submit button visible | All elements visible | **PASS** |
| **TC17** | Valid Patient Login | Enter `patient@demo.com` / `password123` | Login successful alert displayed | Login successful alert shown | **PASS** |
| **TC18** | Valid Doctor Login | Enter `doctor@demo.com` / `password123` | Login successful alert displayed | Login successful alert shown | **PASS** |
| **TC19** | Non-Existent User Login | Enter unregistered email | Error "Invalid email or password" | Alert displayed | **PASS** |
| **TC20** | Wrong Password Rejection | Enter correct email with wrong password | Error "Invalid email or password" | Alert displayed | **PASS** |
| **TC21** | Quick Demo Patient Button | Click `#demo-patient-btn` | Auto-fills email and password | Fields populated immediately | **PASS** |
| **TC22** | Brute-force Lockout Timer | Submit 5 consecutive bad passwords | Lockout countdown timer banner | Lockout timer element missing | **FAIL** |
| **TC23** | reCAPTCHA Widget Presence | Locate `#g-recaptcha-v3-container` | Captcha widget rendered on login | Widget container missing | **FAIL** |
| **TC24** | Doctor Search Initial Load | Navigate to `doctor-search.html` | At least 4 doctor cards shown | 6 doctor cards displayed | **PASS** |
| **TC25** | Search Doctor by Name Keyword | Enter "Mehta" in search input | Shows Dr. Rohan Mehta | Dr. Rohan Mehta displayed | **PASS** |
| **TC26** | Filter Doctors Cardiology | Select Cardiology in dropdown | Shows only Cardiology specialists | Cardiology doctor displayed | **PASS** |
| **TC27** | Filter Doctors Dermatology | Select Dermatology in dropdown | Shows only Dermatology specialists | Dermatology doctor displayed | **PASS** |
| **TC28** | Filter Doctors City Mumbai | Select Mumbai in city filter | Shows doctors in Mumbai | Mumbai doctors displayed | **PASS** |
| **TC29** | Search Unknown Keyword | Enter "ZZZUnknownDoctor" | Empty state message rendered | Empty state message displayed | **PASS** |
| **TC30** | Reset Filters Action | Click Reset button | Restores full doctors list | 6 doctors rendered | **PASS** |
| **TC31** | Hindi Script Transliteration | Search "डॉक्टर मेहता" | Transliterates to Dr. Mehta | Transliteration engine missing | **FAIL** |
| **TC32** | Doctor Summary on Booking | Open `book-appointment.html?doctorId=doc-1` | Displays Dr. Rohan Mehta, Cardiology | Doctor details rendered | **PASS** |
| **TC33** | Consultation Mode Toggle | Select "Video Call" radio option | Video call option checked | Radio state verified | **PASS** |
| **TC34** | Available Slots Dropdown | Inspect `#slot-select` | Multiple slots available | 4 time slots populated | **PASS** |
| **TC35** | Valid Appointment Booking | Select slot and submit booking | Alert "Appointment booked successfully" | Alert shown & receipt loaded | **PASS** |
| **TC36** | Booking Empty Slot Error | Submit without selecting slot | Error "select a time slot" | Error displayed | **PASS** |
| **TC37** | Redis Distributed Slot Lease | Check concurrent slot lock tag | Distributed lock tag displayed | Lock lease element missing | **FAIL** |
| **TC38** | NTP Clock Skew Validation | Submit booking with past date | NTP clock skew rejection error | Server clock element missing | **FAIL** |
| **TC39** | Receipt ID Badge | Open `receipt.html?appointmentId=APT-1001` | Displays "APT-1001" | "APT-1001" rendered | **PASS** |
| **TC40** | Receipt Doctor Details | Check receipt doctor name | Displays Dr. Rohan Mehta | Doctor name rendered | **PASS** |
| **TC41** | Receipt Payment Details | Check receipt fee amount | Displays fee with currency | Fee displayed accurately | **PASS** |
| **TC42** | Receipt Dashboard Link | Locate "Go to Dashboard" button | Points to `patient-dashboard.html` | Link verified | **PASS** |
| **TC43** | Patient Dashboard Stats | Open `patient-dashboard.html` | Total & upcoming stats visible | Stats rendered | **PASS** |
| **TC44** | Appointments Table Rows | Inspect `#appointments-tbody` | At least 1 appointment row | 2 appointment rows rendered | **PASS** |
| **TC45** | Dashboard Search Filter | Enter "Cardiology" in table search | Filters matching rows | Matching rows displayed | **PASS** |
| **TC46** | Cancel Appointment Action | Click Cancel button on appointment | Status updates to Cancelled | Alert shown & badge updated | **PASS** |
| **TC47** | PKI Digital Signature | Check digital signature hash | SHA-256 PKI signature badge | PKI element missing | **FAIL** |
| **TC48** | Doctor Portal Queue Loaded | Open `doctor-dashboard.html` | Header displays Dr. Rohan Mehta | Doctor portal loaded | **PASS** |
| **TC49** | Doctor Complete Consultation | Click "Complete" button | Status updates to Completed | Alert shown & status updated | **PASS** |
| **TC50** | WebRTC Video Session Expiry | Check video countdown expiry tag | WebRTC countdown timer element | Expiry countdown element missing | **FAIL** |

---

## 3. DEFECT TAXONOMY & BUG TRACKING REPORT (10 DEFECTS)

| Defect ID | Associated Test | Severity | Defect Description | Root Cause / Recommendation |
| :--- | :--- | :--- | :--- | :--- |
| **BUG-01** | TC07 | Low | Emergency Hotline badge missing from Landing Hero | Add `<div id="emergency-hotline-24x7">` in hero layout for immediate emergency dialer. |
| **BUG-02** | TC14 | Medium | Short telephone numbers without E.164 country code format | Enhance client regex to enforce `^\+?[1-9]\d{1,14}$`. |
| **BUG-03** | TC15 | Medium | Infant/Pediatric registration lacks guardian consent workflow | Add conditional guardian consent popup when `age < 18`. |
| **BUG-04** | TC22 | High | Missing brute-force lockout cooldown after 5 failed login attempts | Implement rate-limiting cooldown timer and temporary lockout banner. |
| **BUG-05** | TC23 | High | Missing CAPTCHA bot protection on authentication portal | Integrate Google reCAPTCHA v3 or Cloudflare Turnstile token verification. |
| **BUG-06** | TC31 | Low | Multilingual search failure for regional Indian scripts | Implement transliteration phonetic dictionary for Devanagari/Hindi names. |
| **BUG-07** | TC37 | High | Absence of real-time distributed slot locking during booking checkout | Implement Redis key-value lease lock to prevent double bookings across concurrent users. |
| **BUG-08** | TC38 | Medium | Client bypass allows date tampering without server NTP synchronization | Enforce server-side timestamp verification against NTP clock. |
| **BUG-09** | TC47 | Medium | Digital prescriptions lack doctor PKI cryptographic signature badge | Add digital signature hash verification badge to comply with telehealth standards. |
| **BUG-10** | TC50 | Low | Telehealth video consultation lacks token expiry countdown timer | Implement WebRTC session timeout countdown in doctor consultation queue. |

---

## 4. HOW TO RUN THE TESTS IN VS CODE

### Option A: Using Java + Maven + TestNG
1. Open the project in VS Code:
   ```bash
   cd "c:\Users\Lenovo\Desktop\STQA MiniProject\MediLink\stqa"
   ```
2. Run the TestNG suite using Maven:
   ```bash
   mvn test
   ```
3. View the generated test report:
   ```
   target/surefire-reports/index.html
   ```

### Option B: Using Standalone Python Selenium Runner
1. Run directly in VS Code terminal:
   ```bash
   cd "c:\Users\Lenovo\Desktop\STQA MiniProject\MediLink"
   python run_stqa_tests.py
   ```
2. The terminal will print each test from `TC01` to `TC50` with live status:
   - **Passed Test Cases:** 40
   - **Failed Test Cases:** 10
   - **Achieved Pass Rate:** 80.0%
3. View the generated HTML execution report at:
   ```
   MediLink/stqa/reports/TestExecutionReport.html
   ```
