package ru.itmo.soa.lab.storage.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.itmo.soa.lab.storage.model.product.dto.NewProductDto
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.services.product.ProductService
import ru.itmo.soa.lab.storage.utils.LoggerDelegate
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Tag(name = "Products", description = "Actions with the products")
@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    companion object {
        val log by LoggerDelegate()
    }

    @Operation(summary = "Add product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun addProduct(@RequestBody @Valid newProductDto: NewProductDto) =
        productService.addProduct(newProductDto)

    @Operation(summary = "Get product by ID")
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable @Min(1) productId: ProductId) =
        productService.getProductById(productId)

    @Operation(summary = "Update product")
    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable @Min(1) productId: ProductId,
        @RequestBody @Valid newProductDto: NewProductDto
    ) = productService.updateProduct(productId, newProductDto)

    @Operation(summary = "Delete product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable @Min(1) productId: ProductId) =
        productService.deleteProduct(productId)

    // TODO implement
    @Operation(summary = "Get products page with filters")
    @GetMapping
    fun getProductsPage(
        @RequestParam(required = false) filterBy: Map<String, String>,
        @ParameterObject pageable: Pageable,
    ): ResponseEntity<Void> {
        log.info("pageable $pageable")
        log.info("filterBy $filterBy")
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Get product with max price")
    @GetMapping("/max-price")
    fun getMaxPriceProduct() =
        productService.getMaxPriceProduct()

    @Operation(summary = "Group products by manufacture cost")
    @GetMapping("/manufacture-cost-groups")
    fun getManufactureCostGroups(@ParameterObject pageable: Pageable) =
        productService.getManufactureCostGroups(pageable)

    @Operation(summary = "Get products which has greater part number than specified")
    @GetMapping("/greater-part-number")
    fun getGreaterPartNumber(
        @RequestParam @Size(min = 25, max = 48) partNumber: String,
        @ParameterObject pageable: Pageable
    ) = productService.getGreaterPartNumber(partNumber, pageable)
}