package org.imaginativeworld.whynotimagecarousel.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --------------------------------
        // Sample One
        // --------------------------------
        val list = mutableListOf<CarouselItem>()

        val max = 10

        for (i in 1..max) {
            if (i % 2 == 0) {
                list.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1581357825340-32259110788a?w=1080",
                        caption = "Image $i of $max"
                    )
                )
            } else {
                list.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1581441117193-63e8f6547081?w=1080",
                        caption = "Image $i of $max"
                    )
                )
            }
        }

        carousel1.addData(list)

        // --------------------------------
        // Sample Two
        // --------------------------------
        list.clear()

        for (i in 1..max) {
            if (i % 2 == 0) {
                list.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1569398034126-476b0d96e2d1?w=1080"
                    )
                )
            } else {
                list.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1507667522877-ad03f0c7b0e0?w=1080"
                    )
                )
            }
        }

        carousel2.addData(list)

        carousel2.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {

                Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_SHORT).show()

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {

                Toast.makeText(this@MainActivity, "Long Clicked!", Toast.LENGTH_SHORT).show()

            }

        }

        // --------------------------------
        // Sample Three
        // --------------------------------
        list.clear()

        for (i in 1..max) {
            if (i % 2 == 0) {
                list.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1569398034126-476b0d96e2d1?w=1080"
                    )
                )
            } else {
                list.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1507667522877-ad03f0c7b0e0?w=1080"
                    )
                )
            }
        }

        carousel3.addData(list)

    }
}
