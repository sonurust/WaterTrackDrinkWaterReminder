package com.watertrack.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.watertrack.R

class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val rectF = RectF()
    
    // Colors
    private val backgroundColor = ContextCompat.getColor(context, R.color.navigation_background)
    private val borderColor = ContextCompat.getColor(context, R.color.water_light)
    
    // Dimensions
    private val cornerRadius = 0f
    private val fabRadius = 80f
    private val cutoutDepth = 20f
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val width = width.toFloat()
        val height = height.toFloat()
        val centerX = width / 2
        
        // Create curved path for bottom navigation
        path.reset()
        
        // Start from left edge
        path.moveTo(0f, 0f)
        
        // Top edge to cutout start
        path.lineTo(centerX - fabRadius, 0f)
        
        // Curved cutout for FAB
        rectF.set(
            centerX - fabRadius,
            -cutoutDepth,
            centerX + fabRadius,
            cutoutDepth
        )
        path.arcTo(rectF, 180f, 180f, false)
        
        // Top edge from cutout end to right edge
        path.lineTo(width, 0f)
        
        // Right edge
        path.lineTo(width, height)
        
        // Bottom edge
        path.lineTo(0f, height)
        
        // Left edge back to start
        path.lineTo(0f, 0f)
        
        // Fill background
        paint.color = backgroundColor
        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)
        
        // Draw border
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        canvas.drawPath(path, paint)
        
        // Draw subtle shadow
        paint.color = Color.parseColor("#80B3E5FC")
        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, width, 2f, paint)
    }
}
