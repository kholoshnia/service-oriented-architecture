package ru.itmo.soa.lab.storage.utils

interface DtoConverter<Entity, Dto> {
    fun toEntity(dto: Dto): Entity {
        throw NotImplementedError()
    }

    fun toDto(entity: Entity): Dto {
        throw NotImplementedError()
    }
}
