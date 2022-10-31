package ru.itmo.soa.lab.storage.model.organization.entity

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Embeddable
class Coordinates(
    @field:Min(0)
    @field:Max(5000)
    @field:NotNull
    @field:Column(nullable = false)
    var x: Double,

    @field:Min(0)
    @field:Max(5000)
    @field:NotNull
    @field:Column(nullable = false)
    var y: Double,
)