package com.example.weblab3


import com.example.weblab3.DataBaseBean
import java.io.Serializable
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@ApplicationScoped

open class PersonalData : Serializable {
    @Inject
    private lateinit var dataBaseBean: DataBaseBean
    private var records: ArrayList<Result> = ArrayList()
    open fun getRecords() = records
    open fun addRecord(shoot: Result) {
        records.add(shoot)
    }
    @PostConstruct
    open fun connectToDb(){
        records.addAll(dataBaseBean.loadHits() as ArrayList<Result>)
    }
    open fun clearRecords() {
        records.clear()
    }
}