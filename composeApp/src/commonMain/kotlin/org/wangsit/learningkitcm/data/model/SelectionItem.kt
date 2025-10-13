package org.wangsit.learningkitcm.data.model

data class SelectionItem(
    val name: String
)

object CountryDataSource {
    val countries = listOf(
        SelectionItem( "Indonesia"),
        SelectionItem( "Singapore"),
        SelectionItem( "Malaysia"),
        SelectionItem( "Thailand")
    )
}

object StateDataSource {
    val states = listOf(
        SelectionItem("Jawa Barat"),
        SelectionItem("Jawa Tengah"),
        SelectionItem("DKI Jakarta"),
        SelectionItem("Banten")
    )
}

object CityDataSource {
    val cities = listOf(
        SelectionItem("Bandung"),
        SelectionItem("Jakarta"),
        SelectionItem("Bekasi"),
        SelectionItem("Depok")
    )
}