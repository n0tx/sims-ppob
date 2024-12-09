package com.riki.simsppob.service;

import com.riki.simsppob.model.request.TopupRequest;
import com.riki.simsppob.model.response.BalanceResponse;
import com.riki.simsppob.model.response.PaymentResponse;
import com.riki.simsppob.model.response.TransactionResponse;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    BalanceResponse getBalance(String email);
    BalanceResponse topup(String email, TopupRequest request);
    PaymentResponse payment(String email, String serviceCode);
    List<TransactionResponse> getAllTransactions(String email,Optional<Integer> page,Optional<Integer> limit);

}
