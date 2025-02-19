package org.gzunzu.adapter.api.mapper;

import org.gzunzu.adapter.api.dto.request.ProductAddRq;
import org.gzunzu.adapter.api.dto.request.ProductUpdateRq;
import org.gzunzu.adapter.api.dto.response.ProductRs;
import org.gzunzu.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BasicEntityMapper<Product, Long, ProductAddRq, ProductUpdateRq, ProductRs> {
}
