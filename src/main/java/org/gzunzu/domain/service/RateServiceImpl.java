package org.gzunzu.domain.service;

import org.gzunzu.domain.model.Rate;
import org.gzunzu.domain.ports.RateService;
import org.gzunzu.domain.repositories.BasicEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImpl extends BasicEntityServiceImpl<Rate, Long> implements RateService {

    public RateServiceImpl(final BasicEntityRepository<Rate, Long> repository) {
        super(repository);
    }

    @Override
    protected String getEntityClassName() {
        return Rate.class.getSimpleName();
    }
}
