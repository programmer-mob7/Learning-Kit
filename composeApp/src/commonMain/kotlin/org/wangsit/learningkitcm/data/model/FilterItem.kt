package org.wangsit.learningkitcm.data.model

data class FilterItem(
    val name: String
){
    companion object{
        val StatusFilter = listOf(
            FilterItem("Active"),
            FilterItem("Inactive")
        )
        val CityFilter = listOf(
            FilterItem("Jakarta Utara"),
            FilterItem("Jakarta Selatan"),
            FilterItem("Jakarta Barat"),
            FilterItem("Jakarta Timur"),
            FilterItem("Bekasi Barat"),
            FilterItem("Bekasi Selatan"),
            FilterItem("Bekasi Utara"),
            FilterItem("Bekasi Timur"),
        )
        val ItemFilter = listOf(
            FilterItem("Laptop"),
            FilterItem("Printer"),
            FilterItem("Komputer"),
        )
        val SupplierFilter = listOf(
            FilterItem("PT. A Indonesia"),
            FilterItem("PT. B Indonesia"),
            FilterItem("PT. C Indonesia"),
            FilterItem("PT. D Indonesia"),
            FilterItem("PT. E Indonesia"),
            FilterItem("PT. F Indonesia"),
            FilterItem("PT. G Indonesia"),
            FilterItem("PT. H Indonesia"),
            FilterItem("PT. I Indonesia"),
            FilterItem("PT. J Indonesia"),
        )
        val ModifiedByFilter = listOf(
            FilterItem("John Doe"),
            FilterItem("Jane Doe"),
            FilterItem("Mark Lee"),
        )
    }
}