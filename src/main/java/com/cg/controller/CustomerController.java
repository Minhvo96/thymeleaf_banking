package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ICC_ColorSpace;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private ICustomerService customerService = new CustomerServiceImpl();

    private CustomerServiceImpl customerService2 = new CustomerServiceImpl();

    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {

        if (customer.getFullName().length() == 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Created unsuccessful");
        } else {
            customerService.create(customer);

            model.addAttribute("success", true);
            model.addAttribute("message", "Created successfully");
        }

        return "customer/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(Model model, @PathVariable Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            model.addAttribute("message", "Can't find any customer with that ID");
        } else {
            model.addAttribute("customer", customer);
        }
        return "customer/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@ModelAttribute Customer customer, Model model, @PathVariable Long id) {
        customerService.update(id, customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Edit successfully");
        return "customer/edit";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable Long id) {
        customerService.removeById(id);
        return "customer/list";
    }

    @GetMapping("/deposit/{id}")
    public String showDepositPage(Model model, @PathVariable Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            model.addAttribute("message", "Can't find any customer with that ID");
        } else {
            model.addAttribute("customer", customer);
        }
        return "customer/deposit";
    }

    @PostMapping("/deposit/{id}")
    public String deposit(@ModelAttribute Customer customer, Model model, @PathVariable Long id, BigDecimal deposit) {

        if (deposit.compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("message", "Please input deposit bigger than 0");
            model.addAttribute("success", false);
            model.addAttribute("customer", customer);
        } else {
            Customer cusDeposit = customerService2.deposit(id, deposit);
            model.addAttribute("success", true);
            model.addAttribute("customer", cusDeposit);
            model.addAttribute("message", "Deposit completed");
        }
        return "customer/deposit";
    }

    @GetMapping("/withdraw/{id}")
    public String showWithdrawPage(Model model, @PathVariable Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            model.addAttribute("message", "Can't find any customer with that ID");
        } else {
            model.addAttribute("customer", customer);
        }
        return "customer/withdraw";
    }

    @PostMapping("/withdraw/{id}")
    public String withdraw(@ModelAttribute Customer customer, Model model, @PathVariable Long id, BigDecimal withdraw) {

        if (withdraw.compareTo(BigDecimal.ZERO) <= 0 || customer.getBalance().compareTo(withdraw) < 0 ) {
            model.addAttribute("message", "Please input withdraw bigger than 0");
            model.addAttribute("success", false);
            model.addAttribute("customer", customer);
        } else {
            Customer cusDeposit = customerService2.withdraw(id, withdraw);
            model.addAttribute("success", true);
            model.addAttribute("customer", cusDeposit);
            model.addAttribute("message", "Withdraw completed");
        }
        return "customer/withdraw";
    }

}


















