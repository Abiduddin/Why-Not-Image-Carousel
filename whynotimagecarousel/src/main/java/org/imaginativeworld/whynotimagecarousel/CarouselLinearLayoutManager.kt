package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.min

class CarouselLinearLayoutManager(
    context: Context,
    orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {

    var scaleOnScroll = false
    var scalingFactor = 0f

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        scrollHorizontallyBy(0, recycler, state)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val scrolled = super.scrollHorizontallyBy(dx, recycler, state)

        return if (scaleOnScroll) {
            for (i in 0 until childCount) {
                getChildAt(i)?.let { child ->
                    val childWidth = child.right - child.left.toFloat()
                    val childWidthHalf = childWidth / 2f
                    val childCenter = child.left + childWidthHalf

                    val parentWidth = width.toFloat()
                    val parentWidthHalf = parentWidth / 2f

                    val d0 = 0f
                    val mShrinkDistance = .75f
                    val d1 = mShrinkDistance * parentWidthHalf

                    val s0 = 1f
                    val mShrinkAmount = scalingFactor
                    val s1 = 1f - mShrinkAmount

                    val d = min(d1, abs(parentWidthHalf - childCenter))

                    val scalingFactor = s0 + (s1 - s0) * (d - d0) / (d1 - d0)

                    child.scaleX = scalingFactor
                    child.scaleY = scalingFactor
                }
            }

            scrolled
        } else {
            scrolled
        }
    }

}