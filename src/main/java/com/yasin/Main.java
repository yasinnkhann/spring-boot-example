package com.yasin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age) {}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest req) {
        Customer customer = new Customer();

        customer.setName(req.name());
        customer.setEmail(req.email());
        customer.setAge(req.age());

        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") Integer id, @RequestBody Customer updatedCustomer) {
        Customer customer = customerRepository.getById(id);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAge(updatedCustomer.getAge());

        customerRepository.save(customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greet")
    public GreetResponse greet() {
        GreetResponse res = new GreetResponse(
                "Hello", List.of("Java", "GoLang", "JavaScript"),
                new Person("Alex", 28, 30_000 )
        );
        return res;
    }

    record Person(String name, int age, double savings) {}
    record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person) {}
}
