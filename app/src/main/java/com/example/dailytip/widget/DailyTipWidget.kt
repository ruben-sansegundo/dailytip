package com.example.dailytip.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.dailytip.DailyTipApplication
import com.example.dailytip.data.local.TipEntity

class DailyTipWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val app = context.applicationContext as DailyTipApplication
        val tip = app.dailyTipManager.getTodayTip()

        provideContent {
            GlanceTheme {
                DailyTipWidgetContent(tip = tip)
            }
        }
    }

    @Composable
    private fun DailyTipWidgetContent(tip: TipEntity?) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.surface)
                .padding(16.dp)
                .cornerRadius(12.dp),
            contentAlignment = Alignment.Center
        ) {
            if (tip == null) {
                Text(
                    text = "Abre la app para añadir tu primer consejo.",
                    style = TextStyle(
                        color = GlanceTheme.colors.onSurface,
                        fontSize = 14.sp
                    )
                )
            } else {
                Column(
                    modifier = GlanceModifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Consejo de hoy",
                        style = TextStyle(
                            color = GlanceTheme.colors.primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(6.dp))
                    Text(
                        text = tip.text,
                        style = TextStyle(
                            color = GlanceTheme.colors.onSurface,
                            fontSize = 13.sp
                        ),
                        maxLines = 5
                    )
                    if (!tip.source.isNullOrBlank()) {
                        Spacer(modifier = GlanceModifier.height(4.dp))
                        Text(
                            text = "— ${tip.source}",
                            style = TextStyle(
                                color = GlanceTheme.colors.onSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
