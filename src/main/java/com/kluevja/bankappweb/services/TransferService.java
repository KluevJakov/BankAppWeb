package com.kluevja.bankappweb.services;

import com.kluevja.bankappweb.models.Account;
import com.kluevja.bankappweb.models.Client;
import com.kluevja.bankappweb.models.Transaction;
import com.kluevja.bankappweb.models.TransferDTO;
import com.kluevja.bankappweb.repositories.AccountRepository;
import com.kluevja.bankappweb.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    public void execute(TransferDTO transferDTO, Client currentClient) {

        Account debitor = null;
        Account creditor = null;

        if (!accountRepository.findById(transferDTO.getTransferDebtorAccountId()).isPresent()) {
            throw new IllegalArgumentException("Счет списания не найден");
        } else {
            debitor = accountRepository.findById(transferDTO.getTransferDebtorAccountId()).get();
        }

        if (!accountRepository.findById(transferDTO.getTransferCreditorAccountId()).isPresent()) {
            throw new IllegalArgumentException("Счет начисления не найден");
        } else {
            creditor = accountRepository.findById(transferDTO.getTransferCreditorAccountId()).get();
        }

        if (transferDTO.getTransferAmount() <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }

        if (debitor.getId().equals(creditor.getId())) {
            throw new IllegalArgumentException("Счета должны быть разными");
        }

        if (!currentClient.getAccounts().contains(debitor)) {
            throw new IllegalArgumentException("Счет списания не пренадлежит текущему пользователю");
        }

        if (debitor.getBalance() - transferDTO.getTransferAmount() < 0) {
            throw new IllegalArgumentException("На счету недостаточно средств");
        }

        debitor.setBalance(debitor.getBalance() - transferDTO.getTransferAmount());
        creditor.setBalance(creditor.getBalance() + transferDTO.getTransferAmount());

        accountRepository.save(debitor); // нарушаем атомарность
        accountRepository.save(creditor); //позже реализовать через транзакцию

        Transaction transaction = new Transaction();
        transaction.setSender(debitor);
        transaction.setGetter(creditor);
        transaction.setValueOfPayment(transferDTO.getTransferAmount());
        transaction.setDateOfPayment(new Date());

        transactionRepository.save(transaction);
    }

    public List<Transaction> getCurrentUserTransactions (Client currentClient) {//потом исправить, прописав специфичный запрос к БД

        List<Account> currentUserAccounts = currentClient.getAccounts();

        return transactionRepository.findAll().stream()
                .filter(e -> currentUserAccounts.contains(e.getGetter()) || currentUserAccounts.contains(e.getSender()))
                .sorted(Comparator.comparing(Transaction::getDateOfPayment, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
