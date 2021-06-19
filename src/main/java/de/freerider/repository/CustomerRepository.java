package de.freerider.repository;

import org.springframework.stereotype.Component;


/**
 * Customer-Repository implementation of the CrudRepository<Customer, String>
 * interface using an internal HashMap<String, Customer>.
 * 
 * @Component with @Qualifier to differentiate from other components of the
 * CrudRepository<Customer, String> interface.
 * 
 */
@Component
class CustomerRepository { //implements CrudRepository<Customer, String> {
	//
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );


	
}
