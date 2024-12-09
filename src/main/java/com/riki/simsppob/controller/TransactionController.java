package com.riki.simsppob.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.riki.simsppob.model.request.PaymentRequest;
import com.riki.simsppob.model.request.TopupRequest;
import com.riki.simsppob.model.response.BalanceResponse;
import com.riki.simsppob.model.response.PaymentResponse;
import com.riki.simsppob.model.response.TransactionResponse;
import com.riki.simsppob.model.response.ApiResponse;
import com.riki.simsppob.service.TransactionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@Tag(
        name = "Transaction"
)
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/balance")
    public ApiResponse<BalanceResponse> getBalance(@AuthenticationPrincipal UserDetails userDetails) {

        // using email
        BalanceResponse response = transactionService.getBalance(userDetails.getUsername());

        return ApiResponse.<BalanceResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/topup")
    public ApiResponse<BalanceResponse> topup(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TopupRequest request) {

        // using email
        BalanceResponse response = transactionService.topup(
                userDetails.getUsername(),
                request);

        return ApiResponse.<BalanceResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/transaction")
    public ApiResponse<PaymentResponse> payment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PaymentRequest request) {

        // using email
        PaymentResponse response = transactionService.payment(
                userDetails.getUsername(),
                request.getServiceCode());

        return ApiResponse.<PaymentResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/transaction/history")
    public ApiResponse<List<TransactionResponse>> getAllTransactions(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {

        // using email
        List<TransactionResponse> response = transactionService.getAllTransactions(
                userDetails.getUsername(), page, limit);

        return ApiResponse.<List<TransactionResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }
}
