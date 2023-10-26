package com.cg.service.customer;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements ICustomerService {

    private static final List<Customer> customers = new ArrayList<>();
    private static long id = 1L;
    private static final List<Transfer> histories = new ArrayList<>();

    static {
        customers.add(new Customer(id++, "NVA", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.valueOf(10000), false));
        customers.add(new Customer(id++, "NVB", "nvb@co.cc", "6789", "27 Nguyễn Tri Phương", BigDecimal.valueOf(20000), false));
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

    public List<Customer> findAllWithoutId(Long id) {
        return customers.stream().filter(customer -> !Objects.equals(customer.getId(), id)).collect(Collectors.toList());
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
        Customer customer1 = findById(id);
        customer.setBalance(customer1.getBalance());
        customers.set(index, customer);
    }
    @Override
    public void removeById(Long id) {
        customers.remove(findById(id));
    }

    public Customer deposit (Deposit deposit) {
        Customer customerDeposit = findById(deposit.getCustomer().getId());
        BigDecimal currentBalance = customerDeposit.getBalance();
        BigDecimal newDeposit = currentBalance.add(deposit.getTransactionAmount());

        customerDeposit.setBalance(newDeposit);

        deposit.setCustomer(customerDeposit);

        return customerDeposit;
    }

    public Customer withdraw (Withdraw withdraw) {
        Customer customerWithdraw = findById(withdraw.getCustomer().getId());
        BigDecimal currentBalance = customerWithdraw.getBalance();
        BigDecimal newWithdraw = currentBalance.subtract(withdraw.getTransactionAmount());

        customerWithdraw.setBalance(newWithdraw);

        withdraw.setCustomer(customerWithdraw);

        return customerWithdraw;
    }

    public void transfer (Transfer transfer) {
        Customer sender = findById(transfer.getSender().getId());
        Customer recipient = findById(transfer.getRecipient().getId());

        BigDecimal senderBalance = sender.getBalance();
        BigDecimal recipientBalance = recipient.getBalance();

        BigDecimal totalBalance = transfer.getTransferAmount().multiply(transfer.getFee()).divide(BigDecimal.valueOf(100)).add(transfer.getTransferAmount());

        sender.setBalance(senderBalance.subtract(totalBalance));
        recipient.setBalance(recipientBalance.add(transfer.getTransferAmount()));

        // set lại giá trị mới cho Sender và Recipient
        transfer.setSender(sender);
        transfer.setRecipient(recipient);

        createHistories(transfer);

    }

    public List<Transfer> createHistories (Transfer transfer) {
        transfer.setId(id++);
        histories.add(transfer);

        return histories;
    }

    public List<Transfer> showHistories () {
        return histories;
    }
}
