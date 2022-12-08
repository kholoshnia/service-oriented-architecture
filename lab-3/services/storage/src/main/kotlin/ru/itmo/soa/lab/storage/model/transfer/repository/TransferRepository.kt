package ru.itmo.soa.lab.storage.model.transfer.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.itmo.soa.lab.storage.model.transfer.entity.Transfer
import ru.itmo.soa.lab.storage.model.transfer.entity.TransferId

interface TransferRepository : JpaRepository<Transfer, TransferId> {
    fun findAllByFinishedAtIsNull(pageable: Pageable): Page<Transfer>;
}
