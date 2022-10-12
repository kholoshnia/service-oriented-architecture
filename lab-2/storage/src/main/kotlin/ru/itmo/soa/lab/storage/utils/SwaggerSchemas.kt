package ru.itmo.soa.lab.storage.utils

import ru.itmo.soa.lab.storage.model.product.dto.ManufactureCostGroupDto
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto

class PageDtoProductDto(page: Int, size: Int, total: Int, data: List<ProductDto>) :
    PageDto<ProductDto>(page, size, total, data)

class PageDtoManufactureCostGroupDto(page: Int, size: Int, total: Int, data: List<ManufactureCostGroupDto>) :
    PageDto<ManufactureCostGroupDto>(page, size, total, data)