package org.wangsit.learningkitcm.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ValkyrieIcons.download_icon: ImageVector
    get() {
        if (_download_icon != null) {
            return _download_icon!!
        }
        _download_icon = ImageVector.Builder(
            name = "download_icon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(13f, 12f)
                horizontalLineTo(16f)
                lineTo(12f, 16f)
                lineTo(8f, 12f)
                horizontalLineTo(11f)
                verticalLineTo(8f)
                horizontalLineTo(13f)
                verticalLineTo(12f)
                close()
                moveTo(15f, 4f)
                horizontalLineTo(5f)
                verticalLineTo(20f)
                horizontalLineTo(19f)
                verticalLineTo(8f)
                horizontalLineTo(15f)
                verticalLineTo(4f)
                close()
                moveTo(3f, 2.992f)
                curveTo(3f, 2.444f, 3.447f, 2f, 3.999f, 2f)
                horizontalLineTo(16f)
                lineTo(21f, 7f)
                lineTo(21f, 20.993f)
                curveTo(21f, 21.549f, 20.555f, 22f, 20.007f, 22f)
                horizontalLineTo(3.993f)
                curveTo(3.445f, 22f, 3f, 21.545f, 3f, 21.008f)
                verticalLineTo(2.992f)
                close()
            }
        }.build()

        return _download_icon!!
    }

@Suppress("ObjectPropertyName")
private var _download_icon: ImageVector? = null
