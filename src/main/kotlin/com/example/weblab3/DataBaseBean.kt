package com.example.weblab3


import java.io.Serializable
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.persistence.*
import javax.transaction.Transaction
import javax.transaction.TransactionManager
import javax.transaction.Transactional

@Named
@ApplicationScoped
@Transactional
open class DataBaseBean: Serializable {
    private val persistenceUnit = "db"
    private var entityManagerFactory: EntityManagerFactory? = null
    private var entityManager: EntityManager? = null
    private var transaction: EntityTransaction? = null

    @PostConstruct
    private fun settleConnection() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit)
        entityManager = entityManagerFactory!!.createEntityManager()

    }

    open fun loadHits(): ArrayList<*>? {
        try {
            transaction = entityManager!!.transaction
            transaction!!.begin()
            val query: Query = entityManager!!.createQuery("SELECT e FROM results e")
            transaction!!.commit()
            return query.resultList as ArrayList<*>
        } catch (exception: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            println("Failed to synchronize lists")
        }
        return null
    }
    open fun saveTheShoot(shoot:Result){
        try {
            transaction = entityManager!!.transaction
            transaction!!.begin()
            entityManager!!.persist(shoot)
            entityManager!!.transaction.commit()
        }catch (exception: RuntimeException) {
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            println("Failed to synchronize lists")
        }
    }
    open fun drop(){
        try {
            transaction = entityManager!!.transaction
            transaction!!.begin()
            val query: Query = entityManager!!.createQuery("delete FROM results")
            entityManager!!.transaction.commit()
        }catch (exception: RuntimeException){
            if (transaction!!.isActive) {
                transaction!!.rollback()
            }
            println("Failed to synchronize lists")
        }
    }
}