package ru.itmo.soa.lab.storage.utils

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import ru.itmo.soa.lab.shared.dto.product.ManufactureCostGroupDto
import ru.itmo.soa.lab.shared.dto.product.ProductDto
import ru.itmo.soa.lab.shared.dto.utils.PageDto

class ProductDtoData(val data: ProductDto)

class PageDtoProductDto(
    page: Int,
    size: Int,
    total: Long,
    @ArraySchema(schema = Schema(implementation = ProductDtoData::class))
    data: List<ProductDto>
) : PageDto<ProductDto>(page, size, total, data)

class ManufactureCostGroupDtoData(val data: ManufactureCostGroupDto)

class PageDtoManufactureCostGroupDto(
    page: Int,
    size: Int,
    total: Long,
    @ArraySchema(schema = Schema(implementation = ManufactureCostGroupDtoData::class))
    data: List<ManufactureCostGroupDto>
) : PageDto<ManufactureCostGroupDto>(page, size, total, data)