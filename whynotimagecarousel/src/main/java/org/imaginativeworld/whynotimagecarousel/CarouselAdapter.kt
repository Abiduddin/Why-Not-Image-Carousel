package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CarouselAdapter(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val carouselType: CarouselType,
    private val autoWidthFixing: Boolean,
    @LayoutRes private val itemLayout: Int,
    @IdRes private val imageViewId: Int,
    var listener: OnItemClickListener? = null,
    private val imageScaleType: ImageView.ScaleType,
    private val imagePlaceholder: Drawable?
) : RecyclerView.Adapter<CarouselAdapter.MyViewHolder>() {

    private val TAG = "CarouselAdapter"

    class MyViewHolder(itemView: View, imageViewId: Int) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(imageViewId)
    }

    private val dataList: MutableList<CarouselItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)

        return MyViewHolder(view, imageViewId)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]

        /*
         * Bug#1
         * ----------------------------------------------------
         * If the sum of the two item of recyclerView not greater then the screen width,
         * then scrollToPosition() function not worked as expected.
         * So we will check the width of the element and increase the minimum width to fix the
         * problem.
         */
        if (autoWidthFixing && carouselType == CarouselType.SHOWCASE) {
            Log.d(TAG, "recyclerView_width: ${recyclerView.width}")
            Log.d(
                TAG,
                "holder_itemView_width * 2: ${holder.itemView.layoutParams.width * 2}"
            )

            val containerWidth = recyclerView.width
            if (holder.itemView.layoutParams.width >= 0 && holder.itemView.layoutParams.width * 2 <= containerWidth) {
                Log.d(TAG, "Fixing Bug#1")
                holder.itemView.layoutParams.width = (containerWidth / 2) + 1
            }
        }


        // Init views
        holder.img.scaleType = imageScaleType

        Glide.with(context)
            .load(item.imageUrl)
            .placeholder(imagePlaceholder)
            .into(holder.img)


        // Init start and end offsets
        if (carouselType == CarouselType.SHOWCASE) {
            holder.itemView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {

                    override fun onGlobalLayout() {
                        if (recyclerView.itemDecorationCount > 0) {
                            recyclerView.removeItemDecorationAt(0)
                        }

                        recyclerView.addItemDecoration(
                            CarouselItemDecoration(
                                holder.itemView.width,
                                0
                            ), 0
                        )

                        holder.itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }

                })
        }

        // Init listeners
        listener?.apply {

            holder.itemView.setOnClickListener {
                this.onClick(position, item)
            }

            holder.itemView.setOnLongClickListener {
                this.onLongClick(position, item)

                true
            }

        }
    }

    fun getItem(position: Int): CarouselItem? {
        return if (position < dataList.size) {
            dataList[position]
        } else {
            null
        }
    }

    fun addAll(dataList: List<CarouselItem>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun add(item: CarouselItem) {
        this.dataList.add(item)
        notifyItemInserted(dataList.size - 1)
    }

}