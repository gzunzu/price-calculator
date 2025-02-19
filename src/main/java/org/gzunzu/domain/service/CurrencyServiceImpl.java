package org.gzunzu.domain.service;

import org.gzunzu.domain.model.Currency;
import org.gzunzu.domain.ports.CurrencyService;
import org.gzunzu.domain.repositories.BasicEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl extends BasicEntityServiceImpl<Currency, Integer> implements CurrencyService {

    public CurrencyServiceImpl(final BasicEntityRepository<Currency, Integer> repository) {
        super(repository);
    }

    @Override
    protected String getEntityClassName() {
        return Currency.class.getSimpleName();
    }
}
