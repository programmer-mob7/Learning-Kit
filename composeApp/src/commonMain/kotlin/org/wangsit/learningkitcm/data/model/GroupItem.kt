package org.wangsit.learningkitcm.data.model

data class GroupItem(
    val name: String,
    val children: List<GroupItem> = emptyList()
) {
    companion object {
        val Groups = listOf(
            GroupItem("All"),
            GroupItem(
                "Head Office A", children = listOf(
                    GroupItem("1st Floor"),
                    GroupItem(
                        "2nd Floor", children = listOf(
                            GroupItem("Room 202")
                        )
                    )
                )
            ),
            GroupItem(
                "Head Office B", children = listOf(
                    GroupItem("1st Floor"),
                    GroupItem(
                        "2nd Floor", children = listOf(
                            GroupItem("Room 202")
                        )
                    )
                )
            )
        )
    }
}

