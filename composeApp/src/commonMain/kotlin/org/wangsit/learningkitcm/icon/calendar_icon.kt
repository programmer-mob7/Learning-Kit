package org.wangsit.learningkitcm.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ValkyrieIcons.calendar_icon: ImageVector
    get() {
        if (_calendar_icon != null) {
            return _calendar_icon!!
        }
        _calendar_icon = ImageVector.Builder(
            name = "calendar_icon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(17f, 3f)
                horizontalLineTo(21f)
                curveTo(21.552f, 3f, 22f, 3.448f, 22f, 4f)
                verticalLineTo(20f)
                curveTo(22f, 20.552f, 21.552f, 21f, 21f, 21f)
                horizontalLineTo(3f)
                curveTo(2.448f, 21f, 2f, 20.552f, 2f, 20f)
                verticalLineTo(4f)
                curveTo(2f, 3.448f, 2.448f, 3f, 3f, 3f)
                horizontalLineTo(7f)
                verticalLineTo(1f)
                horizontalLineTo(9f)
                verticalLineTo(3f)
                horizontalLineTo(15f)
                verticalLineTo(1f)
                horizontalLineTo(17f)
                verticalLineTo(3f)
                close()
                moveTo(4f, 9f)
                verticalLineTo(19f)
                horizontalLineTo(20f)
                verticalLineTo(9f)
                horizontalLineTo(4f)
                close()
                moveTo(6f, 13f)
                horizontalLineTo(11f)
                verticalLineTo(17f)
                horizontalLineTo(6f)
                verticalLineTo(13f)
                close()
            }
        }.build()

        return _calendar_icon!!
    }

@Suppress("ObjectPropertyName")
private var _calendar_icon: ImageVector? = null
