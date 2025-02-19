package org.gzunzu.adapter.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.dto.request.RateAddRq;
import org.gzunzu.adapter.api.dto.request.RateUpdateRq;
import org.gzunzu.adapter.api.dto.response.RateRs;
import org.gzunzu.adapter.api.mapper.BasicEntityMapper;
import org.gzunzu.domain.model.Rate;
import org.gzunzu.domain.ports.BasicEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rate")
@Slf4j
public class RateController extends BasicEntityController<Rate, Long, RateAddRq, RateUpdateRq, RateRs> {

    public RateController(final BasicEntityService<Rate, Long> service,
                          final BasicEntityMapper<Rate, Long, RateAddRq, RateUpdateRq, RateRs> mapper) {
        super(service, mapper);
    }
}
