package com.example.weblab3

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import java.io.Serializable
import java.time.ZonedDateTime
import javax.inject.Named

@ApplicationScoped
@Named
open class Point : Serializable {

    @Inject
    private lateinit var personalData: PersonalData

    @Inject
    private lateinit var dataBaseBean: DataBaseBean

    private var x: String? = "-3"
    open fun getX() = x
    open fun setX(value: String) {
        x = value
    }


    private var y: String? = null
    open fun getY() = y
    open fun setY(value: String) {
        y = value
    }


    private var r: String? = "1"
    open fun getR() = r
    open fun setR(value: String) {
        r = value
    }

    fun submit() {
        val start = System.nanoTime()
        if (y.isNullOrEmpty() || r.isNullOrEmpty() || x.isNullOrEmpty()) return
        if (y.isNullOrEmpty() || r.isNullOrEmpty() || x.isNullOrEmpty()) return
        val xValue: Int
        val yValue: Float
        val rValue = try {
            xValue = x!!.toInt()
            yValue = y!!.toFloat()
            r!!.toFloat()
        } catch (e: NumberFormatException) {
            return
        };
        if (!checkRanges(xValue, yValue, rValue)) return
        val check = checkTheShoot(xValue, yValue, rValue)
        pushTheResult(xValue, yValue, rValue, start, check);
    }

    open fun pushTheResult(x: Int, y: Float, r: Float, stamp: Long, result: Boolean) {
        val shoot: Result = Result().apply {
            setCordX(x); setCordY(y); setCordR(r)
            setTime(ZonedDateTime.now())
            setExecution(System.nanoTime() - stamp)
            setResult(result.toString())
        }
        personalData.addRecord(shoot)
        dataBaseBean.saveTheShoot(shoot)
    }


    private fun checkRanges(x: Int, y: Float, r: Float): Boolean {
        if (x < -3 || x > 5) return false
        return if (y < -3 || y > 3) false else r in 1.0..4.0
    }

    open fun clearRecords() {
        personalData.clearRecords()
        dataBaseBean.drop()
    }

    private fun checkTheShoot(x: Int, y: Float, r: Float): Boolean {
        return if (x >= 0 && y >= 0 && x + y <= r) {
            true
        } else if (x >= 0 && y <= 0 && x * x + y * y <= r * r) {
            true
        } else x <= 0 && y <= 0 && kotlin.math.abs(y) <= r
    }
}