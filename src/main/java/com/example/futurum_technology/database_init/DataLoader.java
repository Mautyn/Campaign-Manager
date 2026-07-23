package com.example.futurum_technology.database_init;

import com.example.futurum_technology.model.EmeraldAccount;
import com.example.futurum_technology.repository.EmeraldAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(EmeraldAccountRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                EmeraldAccount account = new EmeraldAccount();
                account.setBalance(new BigDecimal("1000.00"));
                repository.save(account);
                System.out.println("Test Emerald Account created with ID: " + account.getId());
            }
        };
    }
}