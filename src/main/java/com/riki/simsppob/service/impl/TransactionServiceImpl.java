package com.riki.simsppob.service.impl;

import com.riki.simsppob.exception.BadRequestException;
import com.riki.simsppob.exception.ResourceNotFoundException;
import com.riki.simsppob.model.Balance;
import com.riki.simsppob.model.Transaction;
import com.riki.simsppob.model.User;
import com.riki.simsppob.model.request.TopupRequest;
import com.riki.simsppob.model.response.BalanceResponse;
import com.riki.simsppob.model.response.PaymentResponse;
import com.riki.simsppob.model.response.TransactionResponse;
import com.riki.simsppob.repository.BalanceRepository;
import com.riki.simsppob.repository.ServiceRepository;
import com.riki.simsppob.repository.TransactionRepository;
import com.riki.simsppob.repository.UserRepository;
import com.riki.simsppob.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public BalanceResponse getBalance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        Balance balance = balanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("balance"));

        return BalanceResponse
                .builder()
                .balance(balance.getBalanceAmount())
                .build();
    }

    @Override
    @Transactional
    public BalanceResponse topup(String email, TopupRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        Balance balance = balanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("balance"));

        Long newBalance = balance.getBalanceAmount() + request.getTopUpAmount();
        balance.setBalanceAmount(newBalance);

        balanceRepository.save(balance);

        String desc = "Top Up Balance";

        if (request.getDescription() != null) {
            desc = request.getDescription();
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionType("TOPUP");
        newTransaction.setDescription(desc);
        newTransaction.setTotalAmount(request.getTopUpAmount());
        newTransaction.setCreatedOn(new Date());
        newTransaction.setUserId(user.getId());

        transactionRepository.save(newTransaction);

        return BalanceResponse
                .builder()
                .balance(balance.getBalanceAmount())
                .build();
    }

    @Override
    @Transactional
    public PaymentResponse payment(String email, String serviceCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        Balance balance = balanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("balance"));

        com.riki.simsppob.model.Service service = serviceRepository.findById(serviceCode)
                .orElseThrow(() -> new ResourceNotFoundException("balance"));

        if (balance.getBalanceAmount() < service.getServiceTariff()) {
            throw new BadRequestException("Balance Kurang");
        }

        Long newBalance = balance.getBalanceAmount() - service.getServiceTariff();
        balance.setBalanceAmount(newBalance);

        balanceRepository.save(balance);

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionType("PAYMENT");
        newTransaction.setDescription("Pembayaran " + service.getServiceName());
        newTransaction.setTotalAmount(service.getServiceTariff());
        newTransaction.setCreatedOn(new Date());
        newTransaction.setServiceCode(service.getServiceCode());
        newTransaction.setUserId(user.getId());

        transactionRepository.save(newTransaction);

        return PaymentResponse
                .builder()
                .invoiceNumber(newTransaction.getInvoiceNumber())
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .transactionType(newTransaction.getTransactionType())
                .totalAmount(newTransaction.getTotalAmount())
                .createdOn(newTransaction.getCreatedOn())
                .build();

    }

    @Override
    public List<TransactionResponse> getAllTransactions(
            String email,
            Optional<Integer> page,
            Optional<Integer> limit) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        int pageSize = limit.orElse(0);
        int pageNumber = page.orElse(0);

        List<TransactionResponse> responses = new ArrayList<>();

        if (pageSize == 0) {
            Iterable<Transaction> transactions = this.transactionRepository.findByUserId(user.getId());

            transactions.forEach(
                    tx -> responses.add(new TransactionResponse(
                            tx.getInvoiceNumber(),
                            tx.getTransactionType(),
                            tx.getDescription(),
                            tx.getTotalAmount(),
                            tx.getCreatedOn())));

            return responses;
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Transaction> transactions = this.transactionRepository.findByUserId(user.getId(), pageable);

        transactions.forEach(
                tx -> responses.add(new TransactionResponse(
                        tx.getInvoiceNumber(),
                        tx.getTransactionType(),
                        tx.getDescription(),
                        tx.getTotalAmount(),
                        tx.getCreatedOn())));

        return responses;
    }

}
