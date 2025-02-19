package org.gzunzu.domain.service;

import org.gzunzu.domain.model.Product;
import org.gzunzu.domain.ports.ProductService;
import org.gzunzu.domain.repositories.BasicEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BasicEntityServiceImpl<Product, Long> implements ProductService {

    public ProductServiceImpl(final BasicEntityRepository<Product, Long> repository) {
        super(repository);
    }

    @Override
    protected String getEntityClassName() {
        return Product.class.getSimpleName();
    }
}
