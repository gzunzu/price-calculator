package org.gzunzu.domain.service;

import org.gzunzu.domain.model.Brand;
import org.gzunzu.domain.ports.BrandService;
import org.gzunzu.domain.repositories.BasicEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends BasicEntityServiceImpl<Brand, Integer> implements BrandService {

    public BrandServiceImpl(final BasicEntityRepository<Brand, Integer> repository) {
        super(repository);
    }

    @Override
    protected String getEntityClassName() {
        return Brand.class.getSimpleName();
    }
}
