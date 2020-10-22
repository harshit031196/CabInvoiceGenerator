package com.invoiceservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exception.InvoiceServiceException;
import exception.RepositoryException;
import myrides.Ride;


public class InvoiceServiceTest {

	CabInvoiceService cabInvoiceService;

	@Before
	public void setUp() {
		cabInvoiceService = new CabInvoiceService();
	}

	@Test
	public void givenDistanceAndTime_ShouldReturnTheFare() {
		double distance = 5.0;
		int time = 10;
		double fare = cabInvoiceService.calculateFare(distance, time);
		assertEquals(60.0, fare, 0.0);
	}

	@Test
	public void givenDistanceAndTime_BelowMinimumFareThresholdCondition_ShouldReturnTheMinimumFare() {
		double distance = 0.3;
		int time = 1;
		double fare = cabInvoiceService.calculateFare(distance, time);
		assertEquals(5.0, fare, 0.0);
	}

	@Test
	public void givenMultipleRides_ShouldCalculateTotalFare() {
		Ride[] rides = { new Ride(5.0, 10), new Ride(0.3, 1), new Ride(10.0, 20) };
		List<Ride> myRides = Arrays.asList(rides);
		double fare = cabInvoiceService.calculateTotalFare(myRides);
		assertEquals(185.0, fare, 0.0);
	}

	@Test
	public void givenRides_ShouldReturnInvoiceSummary() {
		Ride[] rides = { new Ride(5.0, 10), new Ride(0.3, 1), new Ride(10.0, 20) };
		List<Ride> myRides = Arrays.asList(rides);
		InvoiceSummary summary;
		try {
			summary = cabInvoiceService.generateSummary(myRides);
			InvoiceSummary expectedSummary = new InvoiceSummary(3, 185.0);
			assertEquals(expectedSummary, summary);
		} catch (InvoiceServiceException e) {}
	}
	
	@Test
	public void givenUserId_ShouldRetunTheInvoiceSummary() {
		List<Ride> myRides = new ArrayList<Ride>();
		myRides.add(new Ride(5.0, 10));
		myRides.add(new Ride(0.3, 1));
		myRides.add(new Ride(10.0, 20));
		int id = 1;
		cabInvoiceService.getRepoService().addUserRides(id, myRides);
		InvoiceSummary summary;
		try {
			summary = cabInvoiceService.generateSummary(id);
			InvoiceSummary expectedSummary = new InvoiceSummary(3, 185.0);
			assertEquals(expectedSummary, summary);
		} catch (RepositoryException | InvoiceServiceException e) {}
	}
	
	@Test
	public void givenUserId_WhenNoRideAvailableForTheUser_ShouldThrowAnException() {
		List<Ride> myRides = new ArrayList<Ride>();
		myRides.add(new Ride(5.0, 10));
		myRides.add(new Ride(0.3, 1));
		myRides.add(new Ride(10.0, 20));
		int id = 1;
		cabInvoiceService.getRepoService().addUserRides(id, myRides);
		InvoiceSummary summary;
		try {
			summary = cabInvoiceService.generateSummary(2);
		} catch (RepositoryException e) {
			assertEquals(RepositoryException.ExceptionType.USER_NOT_FOUND, e.type);
		} catch (InvoiceServiceException e) {}
	}
}
