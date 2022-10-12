package ru.itmo.soa.lab.storage.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
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
import ru.itmo.soa.lab.storage.model.product.dto.ProductDto
import ru.itmo.soa.lab.storage.model.product.entity.ProductId
import ru.itmo.soa.lab.storage.services.product.ProductService
import ru.itmo.soa.lab.storage.utils.PageDtoManufactureCostGroupDto
import ru.itmo.soa.lab.storage.utils.PageDtoProductDto
import ru.itmo.soa.lab.storage.utils.ProductFilters
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Tag(name = "Products", description = "Actions with the products")
@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    @Operation(summary = "Add product", responses = [
        ApiResponse(responseCode = "201", description = "Product added successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = ProductDto::class)))]),
        ApiResponse(responseCode = "400", description = "Invalid product provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun addProduct(@RequestBody @Valid newProductDto: NewProductDto) =
        productService.addProduct(newProductDto)

    @Operation(summary = "Get product by ID", responses = [
        ApiResponse(responseCode = "200", description = "Product found successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = ProductDto::class)))]),
        ApiResponse(responseCode = "400", description = "Invalid id provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))]),
        ApiResponse(responseCode = "404", description = "Product with specified ID not found",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable @Min(1) productId: ProductId) =
        productService.getProductById(productId)

    @Operation(summary = "Update product", responses = [
        ApiResponse(responseCode = "200", description = "Product updated successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = ProductDto::class)))]),
        ApiResponse(responseCode = "400", description = "Invalid id or product provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))]),
        ApiResponse(responseCode = "404", description = "Product with specified ID not found",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @PutMapping("/{productId}")
    fun updateProduct(
        @PathVariable @Min(1) productId: ProductId,
        @RequestBody @Valid newProductDto: NewProductDto
    ) = productService.updateProduct(productId, newProductDto)

    @Operation(summary = "Delete product", responses = [
        ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        ApiResponse(responseCode = "400", description = "Invalid id provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))]),
        ApiResponse(responseCode = "404", description = "Product with specified ID not found",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable @Min(1) productId: ProductId) =
        productService.deleteProduct(productId)

    @Operation(summary = "Get products page with filters", responses = [
        ApiResponse(responseCode = "200", description = "Products filtered successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = PageDtoProductDto::class)))]),
        ApiResponse(responseCode = "400", description = "Invalid pagination or filters provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @GetMapping
    fun getProductsPage(
        @ParameterObject pageable: Pageable,
        @ParameterObject productFilters: ProductFilters
    ) = productService.getProductsPage(productFilters, pageable)

    @Operation(summary = "Get product with max price", responses = [
        ApiResponse(responseCode = "200", description = "Product found successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = ProductDto::class)))]),
        ApiResponse(responseCode = "404", description = "Products storage is empty",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @GetMapping("/max-price")
    fun getMaxPriceProduct() = productService.getMaxPriceProduct()

    @Operation(summary = "Group products by manufacture cost", responses = [
        ApiResponse(responseCode = "200", description = "Products grouped successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = PageDtoManufactureCostGroupDto::class)))]),
        ApiResponse(responseCode = "400", description = "Invalid pagination provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @GetMapping("/manufacture-cost-groups")
    fun getManufactureCostGroups(@ParameterObject pageable: Pageable) =
        productService.getManufactureCostGroups(pageable)

    @Operation(summary = "Get products which has greater part number than specified", responses = [
        ApiResponse(responseCode = "200", description = "Products found successfully",
            content = [(Content(mediaType = "application/xml", schema = Schema(implementation = PageDtoProductDto::class)))]),
        ApiResponse(responseCode = "400", description = "Invalid pagination or part number provided",
            content = [(Content(mediaType = "text/plain", schema = Schema(implementation = String::class)))])])
    @GetMapping("/greater-part-number")
    fun getGreaterPartNumber(
        @RequestParam @Size(min = 25, max = 48) partNumber: String,
        @ParameterObject pageable: Pageable
    ) = productService.getGreaterPartNumber(partNumber, pageable)
}
