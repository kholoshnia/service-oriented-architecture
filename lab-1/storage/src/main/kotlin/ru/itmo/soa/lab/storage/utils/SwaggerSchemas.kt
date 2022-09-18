package ru.itmo.soa.lab.storage.utils

import ru.itmo.soa.lab.storage.model.product.dto.ManufactureCostGroupDto
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto

class ProductDtoPage(page: Int, size: Int, total: Int, data: List<ProductDto>) :
    PageDto<ProductDto>(page, size, total, data)

class ManufactureCostGroupDtoPage(page: Int, size: Int, total: Int, data: List<ManufactureCostGroupDto>) :
    PageDto<ManufactureCostGroupDto>(page, size, total, data)