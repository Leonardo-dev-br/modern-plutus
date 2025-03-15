package com.br.plutus.modern_plutus.controller;

import com.br.plutus.modern_plutus.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AccountController {

        private Logger log = LoggerFactory.getLogger(getClass());
        private List<Account> accounts = new ArrayList<>();


        @GetMapping("/account")
        public List<Account> index(){
            return accounts;
        }

        @PostMapping("/account")
        public ResponseEntity<Account> createAccount(@RequestBody Account account){
            accounts.add(account);
            System.out.println("Cadastrando conta " + account.getNameHolder());
            return ResponseEntity.status(201).body(account);
        }

        @GetMapping("/account/{id}")
        public ResponseEntity<Account> getUniqueAccount(@PathVariable Long id){
            var accountInfo = accounts.stream().filter( accountFiltred -> accountFiltred.getIdHolder().equals(id))
                    .findFirst();
            System.out.println("Buscando conta " + id);
            if(accountInfo.isEmpty())
                return ResponseEntity.notFound().build(); 
            return ResponseEntity.ok(accountInfo.get());
        }

        @DeleteMapping("{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void eliminate(@PathVariable Long id) {
            log.info("Apagando conta " + id);
            accounts.remove(getAccount(id));
        }

        @PutMapping("{id}")
        public Account updateAccount(@PathVariable Long id, @RequestBody Account account) {
            log.info("Atualizando conta " + id + " " + account);

            accounts.remove(getAccount(id));
            account.setIdHolder(id);
            accounts.add(account);

            return account;
        }

        private Account getAccount(Long id) {
            return accounts.stream()
                    .filter(accountFiltred -> accountFiltred.getIdHolder().equals(id))
                    .findFirst()
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta " + id + " não encontrada")
                    );
        }

}
