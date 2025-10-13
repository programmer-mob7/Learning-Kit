package org.wangsit.learningkitcm.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ValkyrieIcons.list_icon: ImageVector
    get() {
        if (_list_icon != null) {
            return _list_icon!!
        }
        _list_icon = ImageVector.Builder(
            name = "list_icon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20f, 22f)
                horizontalLineTo(4f)
                curveTo(3.448f, 22f, 3f, 21.552f, 3f, 21f)
                verticalLineTo(3f)
                curveTo(3f, 2.448f, 3.448f, 2f, 4f, 2f)
                horizontalLineTo(20f)
                curveTo(20.552f, 2f, 21f, 2.448f, 21f, 3f)
                verticalLineTo(21f)
                curveTo(21f, 21.552f, 20.552f, 22f, 20f, 22f)
                close()
                moveTo(19f, 20f)
                verticalLineTo(4f)
                horizontalLineTo(5f)
                verticalLineTo(20f)
                horizontalLineTo(19f)
                close()
                moveTo(8f, 7f)
                horizontalLineTo(16f)
                verticalLineTo(9f)
                horizontalLineTo(8f)
                verticalLineTo(7f)
                close()
                moveTo(8f, 11f)
                horizontalLineTo(16f)
                verticalLineTo(13f)
                horizontalLineTo(8f)
                verticalLineTo(11f)
                close()
                moveTo(8f, 15f)
                horizontalLineTo(16f)
                verticalLineTo(17f)
                horizontalLineTo(8f)
                verticalLineTo(15f)
                close()
            }
        }.build()

        return _list_icon!!
    }

@Suppress("ObjectPropertyName")
private var _list_icon: ImageVector? = null
