package org.gzunzu.adapter.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.dto.request.BrandAddRq;
import org.gzunzu.adapter.api.dto.request.BrandUpdateRq;
import org.gzunzu.adapter.api.dto.response.BrandRs;
import org.gzunzu.adapter.api.mapper.BasicEntityMapper;
import org.gzunzu.domain.model.Brand;
import org.gzunzu.domain.ports.BasicEntityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/brand")
@Validated
@Slf4j
public class BrandController extends BasicEntityController<Brand, Integer, BrandAddRq, BrandUpdateRq, BrandRs> {

    public BrandController(final BasicEntityService<Brand, Integer> service,
                           final BasicEntityMapper<Brand, Integer, BrandAddRq, BrandUpdateRq, BrandRs> mapper) {
        super(service, mapper);
    }
}
