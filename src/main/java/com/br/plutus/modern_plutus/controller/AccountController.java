package com.br.plutus.modern_plutus.controller;

import com.br.plutus.modern_plutus.model.Account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.plutus.modern_plutus.model.PixRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RestController
public class AccountController {

    private Logger log = LoggerFactory.getLogger(getClass());
    private List<Account> accounts = new ArrayList<>();

    @GetMapping("/account")
    public List<Account> index() {
        return accounts;
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> getUniqueAccount(@PathVariable Long id) {
        var accountInfo = accounts.stream().filter(accountFiltred -> accountFiltred.getIdHolder().equals(id))
                .findFirst();
        log.info("Buscando conta " + id);
        if (accountInfo.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(accountInfo.get());
    }

    private Account getAccount(Long id) {
        return accounts.stream()
                .filter(accountFiltred -> accountFiltred.getIdHolder().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta " + id + " não encontrada"));
    }

    @JsonFormat(pattern = "yyyy/MM/dd")
    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {

        if (account.getNameHolder() == null || account.getNameHolder().isEmpty()) {
            return ResponseEntity.badRequest().body("Nome do titular é obrigatório");
        }

        if (account.getCpfHolder() <= 0) {
            return ResponseEntity.badRequest().body("CPF do titular é obrigatório! Por favor, o preencha");
        }

        if (account.getDateOpening() == null || account.getDateOpening().isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body("A data de abertura não pode ser no futuro");
        }

        if (account.getInitialCredit() < 0) {
            return ResponseEntity.badRequest().body("O saldo inicial não pode ser negativo");
        }

        if (account.getTypeAccount() == null || !account.getTypeAccount().matches("corrente|poupanca")) {
            return ResponseEntity.badRequest().body("Tipo de conta inválido. Deve ser 'corrente' ou      'poupanca'");
        }

        accounts.add(account);
        log.info("Cadastrando conta de: " + account.getNameHolder());
        return ResponseEntity.status(201).body(account);

    }

    private Optional<Account> findAccountById(Long id) {
        return accounts.stream()
                .filter(account -> account.getIdHolder().equals(id))
                .findFirst();
    }

    @PostMapping("/pix")
    public ResponseEntity<Account> makePix(@RequestBody PixRequest pixRequest) {
        var originAccount = findAccountById(pixRequest.getOriginAccountId());
        var destinationAccount = findAccountById(pixRequest.getDestinationAccountId());

        if (originAccount.isEmpty() || destinationAccount.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (pixRequest.getAmount() == null || pixRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        if (originAccount.get().getInitialCredit() < pixRequest.getAmount()) {
            return ResponseEntity.badRequest().body(null);
        }

        originAccount.get().setInitialCredit(originAccount.get().getInitialCredit() - pixRequest.getAmount());
        destinationAccount.get().setInitialCredit(destinationAccount.get().getInitialCredit() + pixRequest.getAmount());

        log.info("PIX realizado com sucesso! Valor: R$ " + pixRequest.getAmount());
        return ResponseEntity.ok(originAccount.get());
    }

    @DeleteMapping("account/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void exterminateAccount(@PathVariable Long id) {
        log.info("Apagando conta de ID: " + id);
        accounts.remove(getAccount(id));
    }

    @PutMapping("{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account account) {
        log.info("Atualizando conta de: " + id + " " + account);

        accounts.remove(getAccount(id));
        account.setIdHolder(id);
        accounts.add(account);

        return account;
    }

    @PutMapping("/account/desactivate/{id}")
    public ResponseEntity<String> desactivateAccount(@PathVariable Long id) {
        var account = findAccountById(id);

        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        Account existingAccount = account.get();

        if (!existingAccount.isActiveAccount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta já está inativa.");
        }

        existingAccount.setActiveAccount(false);
        log.info("Conta " + id + " foi desativada com sucesso.");

        return ResponseEntity.ok("Conta desativada com sucesso.");
    }

    @PutMapping("/account/{id}/adding-amount")
    public ResponseEntity<?> addingAmount(@PathVariable Long id, @RequestBody Account request) {
        Optional<Account> accountOpt = accounts.stream()
                .filter(acc -> acc.getIdHolder().equals(id))
                .findFirst();

        if (accountOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        Account account = accountOpt.get();

        double newAmount = account.getInitialCredit() + request.getAmount();
        account.setAmount(newAmount);

        return ResponseEntity.ok(account);
    }

    @PutMapping("/account/{id}/remove-amount")
    public ResponseEntity<?> removeAmount(@PathVariable Long id, @RequestBody Account request) {
        Optional<Account> accountOpt = accounts.stream()
                .filter(acc -> acc.getIdHolder().equals(id))
                .findFirst();

        if (accountOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada.");
        }

        Account account = accountOpt.get();

        double amountToWithdraw = request.getAmount();

        double newAmount = account.getAmount() - amountToWithdraw;
        account.setAmount(newAmount);

        log.info("Saque realizado com sucesso! Valor: R$ " + amountToWithdraw);
        return ResponseEntity.ok(account);
    }

}
