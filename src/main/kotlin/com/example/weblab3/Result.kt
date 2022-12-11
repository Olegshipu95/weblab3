package com.example.weblab3

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity(name = "results")
@Table(name="results")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class Result: Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private var id: Long? = null
    fun getId() = id

    @Column(name = "cordX")
    private var cordX: Int? = null
    fun setCordX(value: Int) {cordX = value}
    fun getCordX() = cordX

    @Column(name = "cordY")
    private var cordY: Float? = null
    fun setCordY(value: Float) {cordY = value}
    fun getCordY() = cordY

    @Column(name = "cordR")
    private var cordR: Float? = null
    fun setCordR(value: Float) {cordR = value}
    fun getCordR() = cordR
    @Column(name = "time")
    private var time: ZonedDateTime? = null
    fun setTime(time: ZonedDateTime) { this.time = time }
    fun getTime() = time

    @Column(name = "exec")
    private var execution: Long? = null
    fun setExecution(time: Long) { execution = time }
    fun getExecution() = execution

    @Column(name = "result")
    private var result: String? = null
    fun setResult(value: String) { result = value }
    fun getResult() = result



    fun cordsToString() = "$cordX, $cordY, $cordR"
    fun timeFormatted(): String =
        time!!.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    fun execFormatted() = "$execution ms"
    override fun toString() = "$cordX, $cordY, $cordR"
}