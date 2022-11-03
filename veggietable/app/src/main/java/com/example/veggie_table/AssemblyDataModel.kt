package com.example.veggie_table

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "nwvrqwxyaytdsfvhu")
data class responseInfo(
    @Element
    val head: Head,
    @Element(name="row")
    val row: MutableList<myRow>
)

@Xml(name="head")
data class Head(
    @Element
    val RESULT: Result,
)

@Xml(name="RESULT")
data class Result(
    @PropertyElement
    val CODE: String,
    @PropertyElement
    val MESSAGE: String,
)

@Xml
data class myRow( // count: 17
    @PropertyElement
    val MONA_CD: String?,
    @PropertyElement
    val HG_NM: String?,
    @PropertyElement
    val HJ_NM: String?,
    @PropertyElement
    val ENG_NM: String?,
    @PropertyElement
    val BTH_DATE: String?,
    @PropertyElement
    val POLY_NM: String?,
    @PropertyElement
    val ORIG_NM: String?,
    @PropertyElement
    val ELECT_GBN_NM: String?,
    @PropertyElement
    val CMIT_NM: String?,
    @PropertyElement
    val CMITS: String?,
    @PropertyElement
    val REELE_GBN_NM: String?,
    @PropertyElement
    val UNITS: String?,
    @PropertyElement
    val SEX_GBN_NM: String?,
    @PropertyElement
    val TEL_NO: String?,
    @PropertyElement
    val E_MAIL: String?,
    @PropertyElement
    val MEM_TITLE: String?,
    @PropertyElement
    val ASSEM_ADDR: String?,
    @PropertyElement
    val HOMEPAGE: String?,
    ){
    constructor() : this( null, null,null, null, null, null, null, null,null,null,null,null,null,null,null,null,null,null)
}