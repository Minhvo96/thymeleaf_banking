package com.cg.service.customer;
import com.cg.model.Customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements ICustomerService {

    private static final List<Customer> customers = new ArrayList<>();
    private static long id = 1L;

    static {
        customers.add(new Customer(id++, "NVA", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.ZERO, false));
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public Customer findById(Long id) {
        List<Customer> customersList = findAll();
        for(int i = 0; i < customersList.size(); i++) {
            if (id == customersList.get(i).getId()) {
                return customersList.get(i);
            }
        }
        return null;
    }

    @Override
    public void create(Customer customer) {
        customer.setId(id++);
        customer.setBalance(BigDecimal.ZERO);
        customers.add(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        int index = customers.indexOf(findById(id));
        customers.set(index, customer);
    }

    @Override
    public void removeById(Long id) {
        customers.remove(findById(id));
    }

    public Customer deposit (Long id, BigDecimal deposit) {
        Customer customerDeposit = findById(id);
        BigDecimal currentBalance = customerDeposit.getBalance();
        BigDecimal newDeposit = currentBalance.add(deposit);

        customerDeposit.setBalance(newDeposit);

        return customerDeposit;
    }

    public Customer withdraw (Long id, BigDecimal withdraw) {
        Customer customerWithdraw = findById(id);
        BigDecimal currentBalance = customerWithdraw.getBalance();
        BigDecimal newWithdraw = currentBalance.subtract(withdraw);

        customerWithdraw.setBalance(newWithdraw);

        return customerWithdraw;
    }

}
