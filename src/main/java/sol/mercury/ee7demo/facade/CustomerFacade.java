/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.mercury.ee7demo.facade;

import java.util.List;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import sol.mercury.ee7demo.domain.Address;
import sol.mercury.ee7demo.domain.Customer;

/**
 *
 * @author debiandev
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CustomerFacade {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Customer find(long id) {
        this.customer = this.em.find(Customer.class, id);
        return this.customer;
    }

    public void create(Customer customer) {
        this.em.persist(customer);
        this.customer = customer;
    }
    
    public void remove(long id){
        Customer ref = this.em.getReference(Customer.class, id);
        this.em.remove(ref);
    }
    
     public void removeAddress(long id){
        Address ref = this.em.getReference(Address.class, id);
        this.em.remove(ref);
    }
    

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save() {
        //nothing to do
    }

    public List<Customer> findAll() {
        return this.em.createQuery("select c from Customer c").getResultList();
    }

    @Remove
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveAndClose() {
        System.out.println("saveAndClose()");
    }

    public void abort() {

    }

    public void pseudoUndo() {
        this.em.refresh(this.customer);
    }
    
    
    public void showStats(){
    }
}
