package org.gzunzu.adapter.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.dto.request.ProductAddRq;
import org.gzunzu.adapter.api.dto.request.ProductUpdateRq;
import org.gzunzu.adapter.api.dto.response.ProductRs;
import org.gzunzu.adapter.api.mapper.BasicEntityMapper;
import org.gzunzu.domain.model.Product;
import org.gzunzu.domain.ports.BasicEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
@Slf4j
public class ProductController extends BasicEntityController<Product, Long, ProductAddRq, ProductUpdateRq, ProductRs> {

    public ProductController(final BasicEntityService<Product, Long> service,
                             final BasicEntityMapper<Product, Long, ProductAddRq, ProductUpdateRq, ProductRs> mapper) {
        super(service, mapper);
    }
}
