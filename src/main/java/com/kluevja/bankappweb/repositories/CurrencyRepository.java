package com.kluevja.bankappweb.repositories;

import com.kluevja.bankappweb.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
