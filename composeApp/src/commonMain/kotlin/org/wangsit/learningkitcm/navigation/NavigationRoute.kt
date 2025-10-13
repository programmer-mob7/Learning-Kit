package org.wangsit.learningkitcm.navigation

sealed class Screen(val route: String) {
    object SupplierMain : Screen("supplier_main")
    object SupplierCreate : Screen("supplier_create")
    object SupplierEdit : Screen("supplier_edit/{supplierId}")
    object SupplierLog : Screen("supplier_log")
//    {
//        fun createRoute(supplierId: Int) = "supplier_edit/$supplierId"
//    }
}