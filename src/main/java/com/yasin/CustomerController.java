package com.yasin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerService.NewCustomerRequest req) {
        Customer customer = customerService.addCustomer(req);
        return ResponseEntity.created(URI.create("/customers/" + customer.getId())).build();
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customerId") Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") Integer id, @RequestBody Customer updatedCustomer) {
        customerService.updateCustomer(id, updatedCustomer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greet")
    public CustomerService.GreetResponse greet() {
        return customerService.greet();
    }
}
