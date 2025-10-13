package org.wangsit.learningkitcm.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ValkyrieIcons.filter_icon: ImageVector
    get() {
        if (_filter_icon != null) {
            return _filter_icon!!
        }
        _filter_icon = ImageVector.Builder(
            name = "filter_icon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(21f, 4f)
                verticalLineTo(6f)
                horizontalLineTo(20f)
                lineTo(15f, 13.5f)
                verticalLineTo(22f)
                horizontalLineTo(9f)
                verticalLineTo(13.5f)
                lineTo(4f, 6f)
                horizontalLineTo(3f)
                verticalLineTo(4f)
                horizontalLineTo(21f)
                close()
                moveTo(6.404f, 6f)
                lineTo(11f, 12.894f)
                verticalLineTo(20f)
                horizontalLineTo(13f)
                verticalLineTo(12.894f)
                lineTo(17.596f, 6f)
                horizontalLineTo(6.404f)
                close()
            }
        }.build()

        return _filter_icon!!
    }

@Suppress("ObjectPropertyName")
private var _filter_icon: ImageVector? = null
