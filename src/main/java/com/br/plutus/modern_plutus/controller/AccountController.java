package com.br.plutus.modern_plutus.controller;


import java.util.ArrayList;
import java.util.List;


import com.br.plutus.modern_plutus.model.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {
    
        private List<Account> accounts = new ArrayList<>();


        @GetMapping("/account")
        public List<Account> index(){
            return accounts;
        }

        @PostMapping("/account")
        public ResponseEntity<Account> create(@RequestBody Account account){
            accounts.add(account);
            System.out.println("Cadastrando conta " + account.getName());
            return ResponseEntity.status(201).body(account);
        }

        @GetMapping("/account/{id}")
        public ResponseEntity<Account> getUniqueAccount(@PathVariable Long id){
            var accountInfo = accounts.stream().filter( accountFiltred -> accountFiltred.getId().equals(id))
                    .findFirst();
            System.out.println("Buscando conta " + id);
            if(accountInfo.isEmpty())
                return ResponseEntity.notFound().build(); 
            return ResponseEntity.ok(accountInfo.get());
        }

}
