/**
 * 
 */
package io.cpl.cdsp.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cpl.cdsp.model.Appointment;
import io.cpl.cdsp.model.Doctor;
import io.cpl.cdsp.model.LabRequest;
import io.cpl.cdsp.model.Patient;
import io.cpl.cdsp.model.Prescription;
import io.cpl.cdsp.repository.AppointmentRepository;
import io.cpl.cdsp.repository.DoctorRepository;
import io.cpl.cdsp.repository.LabRequestRepository;
import io.cpl.cdsp.repository.PatientRepository;
import io.cpl.cdsp.repository.PrescriptionRepository;

/**
 * @author Anurag Jain, developer advocate Thales Group
 *
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	PatientRepository patientRepo;
	
	@Autowired
	PrescriptionRepository prescriptionRepository;
	
	@Autowired
	LabRequestRepository labRequestRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@PostMapping("/patients")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		try {
			Patient _patient = patientRepo.save(patient);
			return new ResponseEntity<>(_patient, 
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/doctors")
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
		try {
			Doctor _doctor = doctorRepository.save(doctor);
			return new ResponseEntity<>(_doctor, 
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/appointments")
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		try {
			Appointment _appointment = appointmentRepository.save(appointment);
			return new ResponseEntity<>(_appointment, 
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/prescriptions")
	public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
		try {
			Prescription _prescription = prescriptionRepository.save(prescription);
			return new ResponseEntity<>(_prescription, 
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/lab-requests")
	public ResponseEntity<LabRequest> createLabRequest(@RequestBody LabRequest labRequest) {
		try {
			LabRequest _labRequest = labRequestRepository.save(labRequest);
			return new ResponseEntity<>(_labRequest, 
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/patients")
	public ResponseEntity<ArrayList<Patient>> listPatients(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
		try {
			ArrayList<Patient> patients = new ArrayList<Patient>();
			patientRepo.findAll().forEach(patients::add);
			return new ResponseEntity<ArrayList<Patient>>(patients, 
					HttpStatus.OK);
		} catch (Exception e) {	
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/doctors")
	public ResponseEntity<ArrayList<Doctor>> listDoctors(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
		try {
			ArrayList<Doctor> doctors = new ArrayList<Doctor>();
			doctorRepository.findAll().forEach(doctors::add);
			return new ResponseEntity<ArrayList<Doctor>>(doctors, 
					HttpStatus.OK);
		} catch (Exception e) {	
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/appointments")
	public ResponseEntity<ArrayList<Appointment>> listAppointments(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
			@RequestParam String doctorId,
			@RequestParam String patientId) {
		try {
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			
			if((doctorId != null && !doctorId.isEmpty()) && (patientId != null && !patientId.isEmpty())) {
				appointmentRepository.findByPatientAndDoctor(patientId, doctorId).forEach(appointments::add);
			} else if((doctorId != null && !doctorId.isEmpty()) && (patientId == null || patientId.isEmpty())) {
				appointmentRepository.findByDoctor(doctorId).forEach(appointments::add);
			} else if((patientId != null && !patientId.isEmpty()) && (doctorId == null || doctorId.isEmpty())) {
				appointmentRepository.findByPatient(patientId).forEach(appointments::add);
			} else {
				appointmentRepository.findAll().forEach(appointments::add);
			}
			return new ResponseEntity<ArrayList<Appointment>>(appointments, 
					HttpStatus.OK);
		} catch (Exception e) {	
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/prescriptions")
	public ResponseEntity<ArrayList<Prescription>> listPrescriptions(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
			@RequestParam String doctorId,
			@RequestParam String patientId) {
		try {
			ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();
			
			if((doctorId != null && !doctorId.isEmpty()) && (patientId != null && !patientId.isEmpty())) {
				prescriptionRepository.findByPatientAndDoctor(patientId, doctorId).forEach(prescriptions::add);
			} else if((doctorId != null && !doctorId.isEmpty()) && (patientId == null || patientId.isEmpty())) {
				prescriptionRepository.findByDoctor(doctorId).forEach(prescriptions::add);
			} else if((patientId != null && !patientId.isEmpty()) && (doctorId == null || doctorId.isEmpty())) {
				prescriptionRepository.findByPatient(patientId).forEach(prescriptions::add);
			} else {
				prescriptionRepository.findAll().forEach(prescriptions::add);
			}
			return new ResponseEntity<ArrayList<Prescription>>(prescriptions, 
					HttpStatus.OK);
		} catch (Exception e) {	
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/lab-requests")
	public ResponseEntity<ArrayList<LabRequest>> listLabRequests(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
			@RequestParam String doctorId,
			@RequestParam String patientId) {
		try {
			ArrayList<LabRequest> labRequests = new ArrayList<LabRequest>();
			
			if((doctorId != null && !doctorId.isEmpty()) && (patientId != null && !patientId.isEmpty())) {
				labRequestRepository.findByPatientAndDoctor(patientId, doctorId).forEach(labRequests::add);
			} else if((doctorId != null && !doctorId.isEmpty()) && (patientId == null || patientId.isEmpty())) {
				labRequestRepository.findByDoctor(doctorId).forEach(labRequests::add);
			} else if((patientId != null && !patientId.isEmpty()) && (doctorId == null || doctorId.isEmpty())) {
				labRequestRepository.findByPatient(patientId).forEach(labRequests::add);
			} else {
				labRequestRepository.findAll().forEach(labRequests::add);
			}
			return new ResponseEntity<ArrayList<LabRequest>>(labRequests, 
					HttpStatus.OK);
		} catch (Exception e) {	
			return new ResponseEntity<>(null, 
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
