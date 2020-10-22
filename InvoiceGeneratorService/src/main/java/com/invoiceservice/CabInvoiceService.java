package com.invoiceservice;

import java.util.List;

import exception.InvoiceServiceException;
import exception.InvoiceServiceException.ExceptionType;
import exception.RepositoryException;
import myrides.Ride;
import repositoryservice.RepositoryService;

public class CabInvoiceService {

	private RepositoryService repoService = new RepositoryService();

	public double calculateTotalFare(List<Ride> rides) {
		double totalFare = 0.0;
		for (Ride ride : rides) {
			totalFare += ride.getFare();
		}
		return totalFare;
	}

	public InvoiceSummary generateSummary(List<Ride> rides) throws InvoiceServiceException {
		if(rides.size() == 0) {
			throw new InvoiceServiceException(ExceptionType.NO_RIDE_TAKEN);
		}
		double totalFare = calculateTotalFare(rides);
		return new InvoiceSummary(rides.size(), totalFare);
	}
	
	public InvoiceSummary generateSummary(int id) throws InvoiceServiceException, RepositoryException {
		List<Ride> myRides = getRepoService().getUserRides(id);
		return generateSummary(myRides);
	}
	
	public RepositoryService getRepoService() {
		return repoService;
	}
}