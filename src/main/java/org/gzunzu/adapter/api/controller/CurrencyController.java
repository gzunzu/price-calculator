package org.gzunzu.adapter.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.dto.request.CurrencyAddRq;
import org.gzunzu.adapter.api.dto.request.CurrencyUpdateRq;
import org.gzunzu.adapter.api.dto.response.CurrencyRs;
import org.gzunzu.adapter.api.mapper.BasicEntityMapper;
import org.gzunzu.domain.model.Currency;
import org.gzunzu.domain.ports.BasicEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/currency")
@Slf4j
public class CurrencyController extends BasicEntityController<Currency, Integer, CurrencyAddRq, CurrencyUpdateRq, CurrencyRs> {

    public CurrencyController(final BasicEntityService<Currency, Integer> service,
                              final BasicEntityMapper<Currency, Integer, CurrencyAddRq, CurrencyUpdateRq, CurrencyRs> mapper) {
        super(service, mapper);
    }
}
