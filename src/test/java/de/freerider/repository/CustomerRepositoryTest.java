package de.freerider.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.datamodel.Customer;


@SpringBootTest
public class CustomerRepositoryTest {
	//
	private final Customer c1;
	private final Customer c2;
	private final String c1_Id = "C020301";
	//
	private static int loglevel = 1;	// 0: silent; 1: @Test methods; 2: all methods

	@Autowired	// customerRepository is the unit-under-test
	private CrudRepository<Customer,String> customerRepository;


	// Constructor
	CustomerRepositoryTest() {
		log( "Constructor()", "CustomerRepositoryTest() called" );
		//
		this.c1 = new Customer( "Baerlinsky", "Max", "max3245@gmx.de" );
		this.c2 = new Customer( "Meyer", "Anne", "ma2958@gmx.de" );
		this.c1.setStatus( Customer.Status.InRegistration );
		this.c1.setId( c1_Id );
	}


	/**
	 * Set up test, runs before EVERY test execution
	 */
	@BeforeEach
	public void setUpEach() {
		log( "@BeforeEach", "setUpEach()" );
		//
		customerRepository.deleteAll();		// clear repository before each @Test method
	}


	/**
	 * -------------------------------
	 * @Test functions:
	 */

	@Test
	void firstTest() {
		//
		log( "firstTest()" );
		//
		assertNotNull( customerRepository );	// reference must be valid
		//
		customerRepository.save( c1 );		// save two Customer entities to initial repository
		customerRepository.save( c2 );
		//
		long count = customerRepository.count();
		//
		// should be 2 with actual CustomerRepository implementation and two saved entities
		assertEquals( count, 0 );
//		assertEquals( count, 2 );
	}


	/*
	 * private methods
	 */

	private void log( String meth ) {
		if( loglevel >= 1 ) {
			System.out.println( "@Test: " + this.getClass().getSimpleName() + "." + meth );
		}
	}

	private static void log( String label, String meth ) {
		if( loglevel >= 2 ) {
			if( label.equals( "@BeforeEach" ) ) {
				System.out.println();
			}
			java.io.PrintStream out_ = System.out;
			if( label.equals( "@BeforeAll" ) || label.equals( "@AfterAll" ) ) {
				System.out.println();
				out_ = System.err;	// print in red color
			}
			out_.println( label + ": " + CustomerRepositoryTest.class.getSimpleName() + "." + meth );
		}
	}
	
}
