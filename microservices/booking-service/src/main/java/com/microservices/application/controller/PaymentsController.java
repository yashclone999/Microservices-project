package com.microservices.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.application.models.Booking;
import com.microservices.application.models.PaymentsResponse;
import com.microservices.application.services.BookingService;
import com.microservices.application.services.ServiceBusMessageService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/payments")
public class PaymentsController{

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private ServiceBusMessageService SBservice;
	
	
	@PostMapping("/success")
	public Booking paymentsSuccess(@RequestBody PaymentsResponse data) throws Exception{
		Booking currBooking =  bookingService.confirmBooking(data.getBookingID(), data.getUserID());
		SBservice.sendMessage(currBooking);
		return currBooking;
	}
	
	@PostMapping("/fail")
	public String paymentsFail(@RequestBody PaymentsResponse data) throws Exception{
		bookingService.cancelBooking(data.getBookingID(), data.getUserID());
		return "Payment Failed. Booking Cancelled.";
	} 
	
}



