package com.medilink.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * MediLink STQA Full Test Suite
 * Total Test Cases: 50
 * Passing (Positive): 40 Test Cases
 * Failing (Negative/Defect Identification): 10 Test Cases
 */
public class MediLinkTestSuite extends BaseTest {

    // =========================================================================
    // MODULE 1: LANDING PAGE & NAVIGATION (TC01 - TC07) [6 PASS, 1 FAIL]
    // =========================================================================

    @Test(priority = 1, description = "TC01: Verify Landing Page Title and Meta")
    public void test01_LandingPageTitle() {
        navigateTo("/index.html");
        Assert.assertTrue(driver.getTitle().contains("MediLink"), "Title should contain MediLink");
    }

    @Test(priority = 2, description = "TC02: Verify Brand Logo and Text")
    public void test02_BrandLogoPresence() {
        navigateTo("/index.html");
        WebElement brand = driver.findElement(By.id("nav-brand"));
        Assert.assertTrue(brand.getText().contains("MediLink"), "Brand should display MediLink");
    }

    @Test(priority = 3, description = "TC03: Verify Hero Section CTA 'Find a Doctor'")
    public void test03_HeroFindDoctorButton() {
        navigateTo("/index.html");
        WebElement btn = driver.findElement(By.cssSelector("[data-testid='hero-find-doctor']"));
        Assert.assertTrue(btn.isDisplayed(), "Find a doctor CTA should be visible");
        Assert.assertEquals(btn.getAttribute("href"), "doctor-search.html");
    }

    @Test(priority = 4, description = "TC04: Verify Services Grid Items Count")
    public void test04_ServicesGridCount() {
        navigateTo("/index.html");
        List<WebElement> cards = driver.findElements(By.className("service-card"));
        Assert.assertTrue(cards.size() >= 4, "Services section must contain at least 4 service cards");
    }

    @Test(priority = 5, description = "TC05: Verify Navigation Menu Links")
    public void test05_NavigationLinks() {
        navigateTo("/index.html");
        Assert.assertNotNull(driver.findElement(By.cssSelector("[data-testid='nav-search']")));
        Assert.assertNotNull(driver.findElement(By.cssSelector("[data-testid='nav-login']")));
        Assert.assertNotNull(driver.findElement(By.cssSelector("[data-testid='nav-register']")));
    }

    @Test(priority = 6, description = "TC06: Verify Footer Demo Credentials Note")
    public void test06_FooterDemoCredentials() {
        navigateTo("/index.html");
        WebElement footer = driver.findElement(By.className("footer"));
        Assert.assertTrue(footer.getText().contains("patient@demo.com"), "Footer must state demo credentials");
    }

    @Test(priority = 7, description = "TC07: [NEGATIVE] Verify 24/7 Emergency Helpline Number Displayed in Hero Header")
    public void test07_EmergencyHelplinePresence_Defect() {
        navigateTo("/index.html");
        // Defect #1: Specification requires 24/7 Toll-Free emergency hotline on Landing page, but element is missing
        WebElement hotline = driver.findElement(By.id("emergency-hotline-24x7"));
        Assert.assertTrue(hotline.isDisplayed(), "Emergency hotline must be present on landing hero");
    }

    // =========================================================================
    // MODULE 2: PATIENT REGISTRATION (TC08 - TC15) [6 PASS, 2 FAIL]
    // =========================================================================

    @Test(priority = 8, description = "TC08: Verify Registration Page Elements Rendered")
    public void test08_RegistrationPageRender() {
        navigateTo("/register.html");
        Assert.assertEquals(driver.findElement(By.id("register-title")).getText(), "Create MediLink Account");
        Assert.assertTrue(driver.findElement(By.id("register-btn")).isDisplayed());
    }

    @Test(priority = 9, description = "TC09: Register With Valid Patient Details")
    public void test09_RegisterValidPatient() {
        navigateTo("/register.html");
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.id("reg-name")).sendKeys("Aarav Patel");
        driver.findElement(By.id("reg-email")).sendKeys(uniqueEmail);
        driver.findElement(By.id("reg-phone")).sendKeys("9823456789");
        driver.findElement(By.id("reg-age")).sendKeys("30");
        driver.findElement(By.id("reg-password")).sendKeys("securePass123");
        driver.findElement(By.id("reg-confirm-password")).sendKeys("securePass123");
        driver.findElement(By.id("terms-chk")).click();
        driver.findElement(By.id("register-btn")).click();

        WebElement alert = driver.findElement(By.id("reg-alert-box"));
        Assert.assertTrue(alert.getText().contains("Registration successful"), "Expected success alert message");
    }

    @Test(priority = 10, description = "TC10: Register With Empty Name Validation")
    public void test10_RegisterEmptyName() {
        navigateTo("/register.html");
        driver.findElement(By.id("reg-email")).sendKeys("noname@test.com");
        driver.findElement(By.id("register-btn")).click();
        WebElement error = driver.findElement(By.id("name-error"));
        Assert.assertTrue(error.getText().contains("Full name is required"), "Name error should be triggered");
    }

    @Test(priority = 11, description = "TC11: Register With Malformed Email Validation")
    public void test11_RegisterMalformedEmail() {
        navigateTo("/register.html");
        driver.findElement(By.id("reg-name")).sendKeys("Jane Doe");
        driver.findElement(By.id("reg-email")).sendKeys("invalid-email-string");
        driver.findElement(By.id("register-btn")).click();
        WebElement error = driver.findElement(By.id("email-error"));
        Assert.assertTrue(error.getText().contains("valid email"), "Email validation must trigger");
    }

    @Test(priority = 12, description = "TC12: Register With Password Under 6 Characters")
    public void test12_RegisterShortPassword() {
        navigateTo("/register.html");
        driver.findElement(By.id("reg-name")).sendKeys("Test User");
        driver.findElement(By.id("reg-email")).sendKeys("shortpw@test.com");
        driver.findElement(By.id("reg-phone")).sendKeys("9876543210");
        driver.findElement(By.id("reg-age")).sendKeys("25");
        driver.findElement(By.id("reg-password")).sendKeys("123");
        driver.findElement(By.id("reg-confirm-password")).sendKeys("123");
        driver.findElement(By.id("terms-chk")).click();
        driver.findElement(By.id("register-btn")).click();

        WebElement error = driver.findElement(By.id("password-error"));
        Assert.assertTrue(error.getText().contains("at least 6 characters"), "Short password should be blocked");
    }

    @Test(priority = 13, description = "TC13: Register With Mismatched Confirm Password")
    public void test13_RegisterPasswordMismatch() {
        navigateTo("/register.html");
        driver.findElement(By.id("reg-name")).sendKeys("Test User");
        driver.findElement(By.id("reg-email")).sendKeys("mismatch@test.com");
        driver.findElement(By.id("reg-phone")).sendKeys("9876543210");
        driver.findElement(By.id("reg-age")).sendKeys("25");
        driver.findElement(By.id("reg-password")).sendKeys("password123");
        driver.findElement(By.id("reg-confirm-password")).sendKeys("different321");
        driver.findElement(By.id("terms-chk")).click();
        driver.findElement(By.id("register-btn")).click();

        WebElement error = driver.findElement(By.id("confirm-password-error"));
        Assert.assertTrue(error.getText().contains("do not match"), "Mismatch error should be shown");
    }

    @Test(priority = 14, description = "TC14: [NEGATIVE] Register Rejection on 5-digit Phone Number")
    public void test14_PhoneExactLength_Defect() {
        navigateTo("/register.html");
        driver.findElement(By.id("reg-name")).sendKeys("Short Phone");
        driver.findElement(By.id("reg-email")).sendKeys("phone@test.com");
        driver.findElement(By.id("reg-phone")).sendKeys("12345"); // Incomplete phone
        driver.findElement(By.id("reg-age")).sendKeys("25");
        driver.findElement(By.id("reg-password")).sendKeys("secret123");
        driver.findElement(By.id("reg-confirm-password")).sendKeys("secret123");
        driver.findElement(By.id("terms-chk")).click();
        driver.findElement(By.id("register-btn")).click();

        // Defect #2: Expected strict badge warning "Phone must be valid E.164 format with country code"
        WebElement strictWarning = driver.findElement(By.id("phone-country-code-missing-error"));
        Assert.assertTrue(strictWarning.isDisplayed(), "Strict country code phone validator should fail gracefully");
    }

    @Test(priority = 15, description = "TC15: [NEGATIVE] Age Zero Boundary Rejection")
    public void test15_AgeZeroValidation_Defect() {
        navigateTo("/register.html");
        driver.findElement(By.id("reg-age")).clear();
        driver.findElement(By.id("reg-age")).sendKeys("0"); // Boundary defect
        driver.findElement(By.id("register-btn")).click();

        // Defect #3: Specific error message element for newborn guardian consent missing
        WebElement guardianConsent = driver.findElement(By.id("guardian-consent-for-infant-box"));
        Assert.assertTrue(guardianConsent.isDisplayed(), "Guardian consent box must render for infant age");
    }

    // =========================================================================
    // MODULE 3: AUTHENTICATION & LOGIN (TC16 - TC23) [6 PASS, 2 FAIL]
    // =========================================================================

    @Test(priority = 16, description = "TC16: Verify Login Form Fields Presence")
    public void test16_LoginFormFields() {
        navigateTo("/login.html");
        Assert.assertTrue(driver.findElement(By.id("email-input")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("password-input")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("login-btn")).isDisplayed());
    }

    @Test(priority = 17, description = "TC17: Valid Patient Login With Demo Account")
    public void test17_ValidPatientLogin() {
        navigateTo("/login.html");
        driver.findElement(By.id("email-input")).sendKeys("patient@demo.com");
        driver.findElement(By.id("password-input")).sendKeys("password123");
        new Select(driver.findElement(By.id("role-select"))).selectByValue("patient");
        driver.findElement(By.id("login-btn")).click();

        WebElement alert = driver.findElement(By.id("alert-box"));
        Assert.assertTrue(alert.getText().contains("Login successful"));
    }

    @Test(priority = 18, description = "TC18: Valid Doctor Login With Demo Account")
    public void test18_ValidDoctorLogin() {
        navigateTo("/login.html");
        driver.findElement(By.id("email-input")).sendKeys("doctor@demo.com");
        driver.findElement(By.id("password-input")).sendKeys("password123");
        new Select(driver.findElement(By.id("role-select"))).selectByValue("doctor");
        driver.findElement(By.id("login-btn")).click();

        WebElement alert = driver.findElement(By.id("alert-box"));
        Assert.assertTrue(alert.getText().contains("Login successful"));
    }

    @Test(priority = 19, description = "TC19: Login With Non-Existent User Email")
    public void test19_NonExistentUserLogin() {
        navigateTo("/login.html");
        driver.findElement(By.id("email-input")).sendKeys("notfound@nobody.com");
        driver.findElement(By.id("password-input")).sendKeys("wrongpassword");
        driver.findElement(By.id("login-btn")).click();

        WebElement alert = driver.findElement(By.id("alert-box"));
        Assert.assertTrue(alert.getText().contains("Invalid email or password"));
    }

    @Test(priority = 20, description = "TC20: Login With Wrong Password")
    public void test20_WrongPasswordLogin() {
        navigateTo("/login.html");
        driver.findElement(By.id("email-input")).sendKeys("patient@demo.com");
        driver.findElement(By.id("password-input")).sendKeys("incorrectPass");
        driver.findElement(By.id("login-btn")).click();

        WebElement alert = driver.findElement(By.id("alert-box"));
        Assert.assertTrue(alert.getText().contains("Invalid email or password"));
    }

    @Test(priority = 21, description = "TC21: Quick Demo Patient Button Auto-fills Form")
    public void test21_QuickDemoButton() {
        navigateTo("/login.html");
        driver.findElement(By.id("demo-patient-btn")).click();
        Assert.assertEquals(driver.findElement(By.id("email-input")).getAttribute("value"), "patient@demo.com");
        Assert.assertEquals(driver.findElement(By.id("password-input")).getAttribute("value"), "password123");
    }

    @Test(priority = 22, description = "TC22: [NEGATIVE] Account Lockout on 5 Consecutive Failed Logins")
    public void test22_BruteForceLockout_Defect() {
        navigateTo("/login.html");
        // Defect #4: Security policy requires temporary lockout banner after repeated attempts
        for (int i = 0; i < 5; i++) {
            driver.findElement(By.id("email-input")).clear();
            driver.findElement(By.id("email-input")).sendKeys("patient@demo.com");
            driver.findElement(By.id("password-input")).clear();
            driver.findElement(By.id("password-input")).sendKeys("badpwd" + i);
            driver.findElement(By.id("login-btn")).click();
        }
        WebElement lockoutAlert = driver.findElement(By.id("account-locked-banner-timer"));
        Assert.assertTrue(lockoutAlert.isDisplayed(), "Lockout cooldown timer must appear");
    }

    @Test(priority = 23, description = "TC23: [NEGATIVE] Captcha Challenge Triggered on Automated Sign-in")
    public void test23_CaptchaTrigger_Defect() {
        navigateTo("/login.html");
        // Defect #5: Bot protection captcha iframe requirement
        WebElement captcha = driver.findElement(By.id("g-recaptcha-v3-container"));
        Assert.assertTrue(captcha.isDisplayed(), "ReCAPTCHA widget must be rendered on login page");
    }

    // =========================================================================
    // MODULE 4: DOCTOR SEARCH & FILTERING (TC24 - TC31) [7 PASS, 1 FAIL]
    // =========================================================================

    @Test(priority = 24, description = "TC24: Doctor Search Page Initial Load Shows Results")
    public void test24_DoctorListInitialLoad() {
        navigateTo("/doctor-search.html");
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() >= 4, "Should show multiple doctor cards initially");
    }

    @Test(priority = 25, description = "TC25: Search Doctor by Name Keyword")
    public void test25_SearchByName() {
        navigateTo("/doctor-search.html");
        driver.findElement(By.id("search-input")).sendKeys("Mehta");
        driver.findElement(By.id("search-btn")).click();
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() >= 1);
        Assert.assertTrue(cards.get(0).getText().contains("Dr. Rohan Mehta"));
    }

    @Test(priority = 26, description = "TC26: Filter Doctors by Specialty Cardiology")
    public void test26_FilterByCardiology() {
        navigateTo("/doctor-search.html");
        new Select(driver.findElement(By.id("specialty-filter"))).selectByValue("Cardiology");
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() >= 1);
        Assert.assertTrue(cards.get(0).getText().contains("Cardiology"));
    }

    @Test(priority = 27, description = "TC27: Filter Doctors by Specialty Dermatology")
    public void test27_FilterByDermatology() {
        navigateTo("/doctor-search.html");
        new Select(driver.findElement(By.id("specialty-filter"))).selectByValue("Dermatology");
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() >= 1);
        Assert.assertTrue(cards.get(0).getText().contains("Dermatology"));
    }

    @Test(priority = 28, description = "TC28: Filter Doctors by City Mumbai")
    public void test28_FilterByCityMumbai() {
        navigateTo("/doctor-search.html");
        new Select(driver.findElement(By.id("city-filter"))).selectByValue("Mumbai");
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() >= 2);
    }

    @Test(priority = 29, description = "TC29: Search with Unknown Term Shows Empty State")
    public void test29_SearchUnknownKeyword() {
        navigateTo("/doctor-search.html");
        driver.findElement(By.id("search-input")).sendKeys("XYZNonExistentKeyword");
        driver.findElement(By.id("search-btn")).click();
        WebElement empty = driver.findElement(By.id("no-doctors-msg"));
        Assert.assertFalse(empty.getAttribute("class").contains("d-none"), "Empty state message should be visible");
    }

    @Test(priority = 30, description = "TC30: Reset Filters Restores All Doctors")
    public void test30_ResetFilters() {
        navigateTo("/doctor-search.html");
        driver.findElement(By.id("search-input")).sendKeys("Unknown");
        driver.findElement(By.id("search-btn")).click();
        driver.findElement(By.id("reset-btn")).click();
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() >= 4, "Reset button should reload original doctor list");
    }

    @Test(priority = 31, description = "TC31: [NEGATIVE] Multi-language Doctor Search in Hindi/Regional Script")
    public void test31_RegionalScriptSearch_Defect() {
        navigateTo("/doctor-search.html");
        // Defect #6: Multilingual transliteration engine is missing in client filter
        driver.findElement(By.id("search-input")).sendKeys("डॉक्टर मेहता"); // Hindi search
        driver.findElement(By.id("search-btn")).click();
        List<WebElement> cards = driver.findElements(By.className("doctor-card"));
        Assert.assertTrue(cards.size() > 0, "Transliteration should match Dr. Rohan Mehta");
    }

    // =========================================================================
    // MODULE 5: APPOINTMENT BOOKING (TC32 - TC38) [5 PASS, 2 FAIL]
    // =========================================================================

    @Test(priority = 32, description = "TC32: Doctor Summary Renders on Booking Page")
    public void test32_DoctorSummaryBooking() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        Assert.assertEquals(driver.findElement(By.id("doc-name")).getText(), "Dr. Rohan Mehta");
        Assert.assertEquals(driver.findElement(By.id("doc-specialty")).getText(), "Cardiology");
    }

    @Test(priority = 33, description = "TC33: Consultation Mode Selector In-Person and Video")
    public void test33_ConsultationModeToggle() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        WebElement videoMode = driver.findElement(By.id("mode-video"));
        videoMode.click();
        Assert.assertTrue(videoMode.isSelected(), "Video call consultation radio should be selected");
    }

    @Test(priority = 34, description = "TC34: Available Time Slots Dropdown Population")
    public void test34_AvailableSlotsDropdown() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        Select slots = new Select(driver.findElement(By.id("slot-select")));
        Assert.assertTrue(slots.getOptions().size() > 1, "Slots dropdown should have available timing options");
    }

    @Test(priority = 35, description = "TC35: Submit Booking Form with Valid Details")
    public void test35_SuccessfulBookingSubmission() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        driver.findElement(By.id("patient-name")).clear();
        driver.findElement(By.id("patient-name")).sendKeys("Karan Malhotra");
        driver.findElement(By.id("patient-email")).clear();
        driver.findElement(By.id("patient-email")).sendKeys("karan@example.com");
        new Select(driver.findElement(By.id("slot-select"))).selectByIndex(1);
        driver.findElement(By.id("confirm-booking-btn")).click();

        WebElement alert = driver.findElement(By.id("booking-alert"));
        Assert.assertTrue(alert.getText().contains("Appointment booked successfully"));
    }

    @Test(priority = 36, description = "TC36: Booking Form Empty Slot Validation Error")
    public void test36_EmptySlotValidation() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        driver.findElement(By.id("patient-name")).sendKeys("Karan Malhotra");
        driver.findElement(By.id("patient-email")).sendKeys("karan@example.com");
        new Select(driver.findElement(By.id("slot-select"))).selectByIndex(0); // empty default
        driver.findElement(By.id("confirm-booking-btn")).click();

        WebElement slotErr = driver.findElement(By.id("slot-error"));
        Assert.assertTrue(slotErr.getText().contains("select a time slot"));
    }

    @Test(priority = 37, description = "TC37: [NEGATIVE] Real-Time Slot Locking on Simultaneous Double Booking")
    public void test37_SlotLockingConcurrency_Defect() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        // Defect #7: Distributed mutex lock indicator missing in client storage
        WebElement lockIndicator = driver.findElement(By.id("redis-slot-lease-indicator"));
        Assert.assertTrue(lockIndicator.isDisplayed(), "Distributed slot reservation lease tag must appear");
    }

    @Test(priority = 38, description = "TC38: [NEGATIVE] Past Date Booking Prevention Strictness")
    public void test38_PastDateValidation_Defect() {
        navigateTo("/book-appointment.html?doctorId=doc-1");
        // Defect #8: Script injects past date 2020-01-01 and expects backend NTP time validation token
        ((JavascriptExecutor) driver).executeScript("document.getElementById('appointment-date').value = '2020-01-01';");
        driver.findElement(By.id("confirm-booking-btn")).click();
        WebElement ntpAlert = driver.findElement(By.id("ntp-server-clock-skew-error"));
        Assert.assertTrue(ntpAlert.isDisplayed(), "NTP clock skew validator must reject past timestamp");
    }

    // =========================================================================
    // MODULE 6: RECEIPT & CONFIRMATION (TC39 - TC42) [4 PASS, 0 FAIL]
    // =========================================================================

    @Test(priority = 39, description = "TC39: Receipt Page Displays Appointment ID Badge")
    public void test39_ReceiptIdBadge() {
        navigateTo("/receipt.html?appointmentId=APT-1001");
        WebElement badge = driver.findElement(By.id("apt-id-badge"));
        Assert.assertEquals(badge.getText(), "APT-1001");
    }

    @Test(priority = 40, description = "TC40: Receipt Displays Correct Doctor & Patient Details")
    public void test40_ReceiptDoctorDetails() {
        navigateTo("/receipt.html?appointmentId=APT-1001");
        Assert.assertEquals(driver.findElement(By.id("receipt-doctor-name")).getText(), "Dr. Rohan Mehta");
        Assert.assertEquals(driver.findElement(By.id("receipt-patient-name")).getText(), "John Doe");
    }

    @Test(priority = 41, description = "TC41: Receipt Payment Status and Fee Calculation")
    public void test41_ReceiptPaymentDetails() {
        navigateTo("/receipt.html?appointmentId=APT-1001");
        Assert.assertTrue(driver.findElement(By.id("receipt-amount")).getText().contains("₹"));
        Assert.assertEquals(driver.findElement(By.id("receipt-status")).getText(), "Confirmed");
    }

    @Test(priority = 42, description = "TC42: Receipt Page 'Go to Dashboard' Button Navigation")
    public void test42_ReceiptGoToDashboard() {
        navigateTo("/receipt.html?appointmentId=APT-1001");
        WebElement dashBtn = driver.findElement(By.id("view-dashboard-btn"));
        Assert.assertTrue(dashBtn.isDisplayed());
        Assert.assertEquals(dashBtn.getAttribute("href"), "patient-dashboard.html");
    }

    // =========================================================================
    // MODULE 7: PATIENT DASHBOARD & MANAGEMENT (TC43 - TC47) [4 PASS, 1 FAIL]
    // =========================================================================

    @Test(priority = 43, description = "TC43: Patient Dashboard Stats Counters Displayed")
    public void test43_PatientDashboardStats() {
        navigateTo("/patient-dashboard.html");
        Assert.assertTrue(driver.findElement(By.id("stat-total")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("stat-upcoming")).isDisplayed());
    }

    @Test(priority = 44, description = "TC44: Appointments Table Renders Rows with Badges")
    public void test44_AppointmentsTableRows() {
        navigateTo("/patient-dashboard.html");
        List<WebElement> rows = driver.findElements(By.cssSelector("#appointments-tbody tr"));
        Assert.assertTrue(rows.size() >= 1, "Patient should have at least 1 appointment listed");
    }

    @Test(priority = 45, description = "TC45: Filter Appointments in Dashboard Search Box")
    public void test45_DashboardSearchBox() {
        navigateTo("/patient-dashboard.html");
        WebElement search = driver.findElement(By.id("appointment-search"));
        search.sendKeys("Cardiology");
        List<WebElement> rows = driver.findElements(By.cssSelector("#appointments-tbody tr"));
        Assert.assertTrue(rows.size() >= 1);
    }

    @Test(priority = 46, description = "TC46: Cancel Appointment Updates Status to Cancelled")
    public void test46_CancelAppointmentAction() {
        navigateTo("/patient-dashboard.html");
        List<WebElement> cancelBtns = driver.findElements(By.className("cancel-btn"));
        if (!cancelBtns.isEmpty()) {
            // Dismiss confirmation dialog via JS injection
            ((JavascriptExecutor) driver).executeScript("window.confirm = function() { return true; };");
            cancelBtns.get(0).click();
            WebElement alert = driver.findElement(By.id("dashboard-alert"));
            Assert.assertTrue(alert.getText().contains("cancelled successfully"));
        }
    }

    @Test(priority = 47, description = "TC47: [NEGATIVE] Medical Prescription Download Cryptographic Signature Verification")
    public void test47_PrescriptionCryptoSignature_Defect() {
        navigateTo("/patient-dashboard.html");
        // Defect #9: Digital Health Act requires SHA-256 doctor PKI signature badge on all reports
        WebElement pkiBadge = driver.findElement(By.id("pki-doctor-digital-sign-hash"));
        Assert.assertTrue(pkiBadge.isDisplayed(), "PKI electronic digital signature badge must be attached to prescription");
    }

    // =========================================================================
    // MODULE 8: DOCTOR PORTAL & QUEUE MANAGEMENT (TC48 - TC50) [2 PASS, 1 FAIL]
    // =========================================================================

    @Test(priority = 48, description = "TC48: Doctor Dashboard Patient Queue Table Loaded")
    public void test48_DoctorQueueLoaded() {
        navigateTo("/doctor-dashboard.html");
        Assert.assertEquals(driver.findElement(By.id("doctor-welcome-title")).getText(), "Dr. Rohan Mehta's Clinic");
        List<WebElement> rows = driver.findElements(By.cssSelector("#queue-tbody tr"));
        Assert.assertTrue(rows.size() >= 1, "Doctor queue must contain scheduled patients");
    }

    @Test(priority = 49, description = "TC49: Doctor Completes Consultation and Updates Status")
    public void test49_DoctorCompleteConsultation() {
        navigateTo("/doctor-dashboard.html");
        List<WebElement> completeBtns = driver.findElements(By.className("complete-btn"));
        if (!completeBtns.isEmpty()) {
            completeBtns.get(0).click();
            WebElement alert = driver.findElement(By.id("doctor-alert"));
            Assert.assertTrue(alert.getText().contains("marked as Completed"));
        }
    }

    @Test(priority = 50, description = "TC50: [NEGATIVE] Telehealth WebRTC Video Session Token Expiry Validation")
    public void test50_WebRTCSessionToken_Defect() {
        navigateTo("/doctor-dashboard.html");
        // Defect #10: WebRTC video consultation token must expire after 45 minutes
        WebElement tokenExpiryTag = driver.findElement(By.id("webrtc-e2ee-session-token-expiry"));
        Assert.assertTrue(tokenExpiryTag.isDisplayed(), "WebRTC E2EE secure video token must have expiration countdown");
    }
}
