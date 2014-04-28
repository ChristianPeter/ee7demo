/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sol.mercury.ee7demo.presentation;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sol.mercury.ee7demo.domain.Address;
import sol.mercury.ee7demo.domain.Customer;
import sol.mercury.ee7demo.facade.CustomerFacade;

/**
 *
 * @author debiandev
 */
@Named
//@SessionScoped
@ConversationScoped
public class CustomerView implements Serializable {

    @Inject
    CustomerFacade facade;

    @Inject
    Conversation conversation;

    private String demo = "Hello World";

    public String getDemo() {
        return demo;
    }

    public Customer getCustomer() {
        return facade.getCustomer();
    }

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String createCustomer() {
        startConversation();
        Customer c = new Customer();
        facade.create(c);
        //facade.save();
        return "detail?faces-redirect=true";
    }

    public String editCustomer(long id) {
        startConversation();
        facade.find(id);
        return "detail?faces-redirect=true";
    }

    public String save() {
        facade.save();
        conversation.end();
        return "list?faces-redirect=true";
    }
    
    public String cancel(){
        conversation.end();
        return "list?faces-redirect=true";
    }
    
    public void removeCustomer(long id){
        facade.remove(id);
        facade.save();
    }
    
    public void removeAddress(Address addr){
        facade.getCustomer().getAddresses().remove(addr);
        if (addr.getId() != null){
            facade.removeAddress(addr.getId());
        }
    }
    
    
    public void createAddress(){
        Address a = new Address();
        a.setStreet("my street");
        a.setCustomer(facade.getCustomer());
        facade.getCustomer().getAddresses().add(a);
        
    }

    public List<Customer> getAllCustomers() {
        return facade.findAll();
    }
}
