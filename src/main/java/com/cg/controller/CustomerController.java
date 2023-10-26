package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.CustomerServiceImpl;
import com.cg.service.customer.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ICC_ColorSpace;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private ICustomerService customerService = new CustomerServiceImpl();

    private CustomerServiceImpl customerServiceImp = new CustomerServiceImpl();

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
    public String delete(@PathVariable Long id, Model model) {
        customerService.removeById(id);
        model.addAttribute("success", true);
        model.addAttribute("message", "Delete successfully");
        return "customer/list";
    }

    @GetMapping("/deposit/{id}")
    public String showDepositPage(Model model, @PathVariable Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            model.addAttribute("message", "Can't find any customer with that ID");
        } else {
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);
            model.addAttribute("deposit", deposit);
        }
        return "customer/deposit";
    }

    @PostMapping("/deposit/{id}")
    public String deposit(@ModelAttribute Deposit deposit, Model model) {

            if (deposit.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
                model.addAttribute("message", "Please input deposit bigger than 0");
                model.addAttribute("success", false);
                model.addAttribute("deposit", deposit);
            } else {
                customerServiceImp.deposit(deposit);
                deposit.setTransactionAmount(null);
                model.addAttribute("success", true);
                model.addAttribute("message", "Deposit completed");
            }

        return "customer/deposit";
    }
    @GetMapping("/withdraw/{id}")
    public String showWithdrawPage(Model model, @PathVariable Long id) {

        Customer customer = customerService.findById(id);

        if (customer == null) {
            model.addAttribute("messageID", "Can't find any customer with that ID");
            model.addAttribute("successWithdraw", false);
            model.addAttribute("customer", new Customer());
        } else {
            Withdraw withdraw = new Withdraw();
            withdraw.setCustomer(customer);
            model.addAttribute("withdraw", withdraw);
        }
        return "customer/withdraw";
    }
    @PostMapping("/withdraw/{id}")
    public String withdraw(@ModelAttribute Withdraw withdraw, Model model) {

        if (withdraw.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0 || withdraw.getCustomer().getBalance().compareTo(withdraw.getTransactionAmount()) < 0) {
            model.addAttribute("message", "Please input withdraw bigger than 0 and not lower than current balance");
            model.addAttribute("success", false);
            model.addAttribute("withdraw", withdraw);
        } else {
            customerServiceImp.withdraw(withdraw);
            withdraw.setTransactionAmount(null);
            model.addAttribute("withdraw", withdraw);
            model.addAttribute("success", true);
            model.addAttribute("message", "Withdraw completed");
        }
        return "customer/withdraw";
    }
    @GetMapping("/transfer/{id}")
    public String transfer(Model model, @PathVariable Long id) {
        Customer customer = customerService.findById(id);
        List<Customer> customerAll = customerService.findAll();

        // tạo một mảng mới để copy các Customer, khi remove sẽ không bị ảnh hưởng tới mảng gốc
        List<Customer> customerList = new ArrayList<>(customerAll);
        customerList.remove(customer);

        customer.setTransfer(new Transfer(BigDecimal.valueOf(10L)));

        model.addAttribute("customer", customer);
        model.addAttribute("customerList", customerList);

        return "/customer/transfer";
    }
    @PostMapping("/transfer/{id}")
    public String transfer(@ModelAttribute Customer customer, Model model, @PathVariable Long id) {
        Customer customerTransfer = customerService.findById(id);
        List<Customer> customerAll = customerService.findAll();

        // tạo một mảng mới để copy các Customer, khi remove sẽ không bị ảnh hưởng tới mảng gốc
        List<Customer> customerList = new ArrayList<>(customerAll);
        customerList.remove(customerTransfer);
        model.addAttribute("customerList", customerList);

        customerServiceImp.transfer(id, new BigDecimal(customer.getTransfer().getTransferAmount()), customer);
        model.addAttribute("success", true);
        model.addAttribute("message", "Transfer completed");
        model.addAttribute("customer", customerTransfer);

        return "/customer/transfer";
    }
    @GetMapping("/histories")
    public String transferHistories(Model model) {



        return "customer/histories";
    }
}


















