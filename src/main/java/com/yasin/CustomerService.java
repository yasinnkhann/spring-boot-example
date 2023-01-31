package com.yasin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age) {
    }

    public Customer addCustomer(NewCustomerRequest req) {
        Customer customer = new Customer();

        customer.setName(req.name());
        customer.setEmail(req.email());
        customer.setAge(req.age());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public void updateCustomer(Integer id, Customer updatedCustomer) {
        Customer customer = customerRepository.getById(id);

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAge(updatedCustomer.getAge());

        customerRepository.save(customer);
    }

    public GreetResponse greet() {
        GreetResponse res = new GreetResponse(
                "Hello", List.of("Java", "GoLang", "JavaScript"),
                new Person("Alex", 28, 30_000)
        );
        return res;
    }

    record Person(String name, int age, double savings) {
    }

    record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person) {
    }
}