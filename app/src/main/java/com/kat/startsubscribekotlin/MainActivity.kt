package com.kat.startsubscribekotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.TypedValue
import android.view.View
import com.kat.startsubscribekotlin.adapter.SubscribeAdapter
import com.kat.startsubscribekotlin.model.Subscribe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_order_content.*
import kotlinx.android.synthetic.main.main_content.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var adapter: SubscribeAdapter? = null

    var daySubscribe: Int = 31
    var boxSelect: Int = 1
    var priceSelect: Int = 1
    var daySelect: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSteps()
        setUpRecyclerView()
        setUpCelender()
        detailOrder()


        btnMin.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
    }


    private fun setUpSteps() {
        stepview.setProgress(1, 3, resources.getStringArray(R.array.steps_array), null)

    }

    private fun setUpRecyclerView() {
        val list = ArrayList<Subscribe>()
        settings(list)

        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(2), true))
        recyclerView.layoutManager = layoutManager

        adapter = SubscribeAdapter(this, list, object : SubscribeAdapter.OnClickListener {
            override fun OnClicked(subscribe: Subscribe) {

                val parts = subscribe.title.split(" ")
                val part1 = parts[0]
                if (part1.equals("Pilih"))
                    daySubscribe = 2
                else daySubscribe = part1.toInt()

                priceSelect = subscribe.price

                setUpCelender()
                detailOrder()

            }

        })
        recyclerView.adapter = adapter

    }

    private fun setUpCelender() {

        val min = Calendar.getInstance()
        min.add(Calendar.DAY_OF_MONTH, -1)

        val max = Calendar.getInstance()
        max.add(Calendar.DAY_OF_MONTH, daySubscribe - 1)

        calendarView.setDate(Calendar.getInstance())
        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)

        calendarView.setOnDayClickListener {

            if (calendarView.selectedDates.size >= daySubscribe) {

                return@setOnDayClickListener
            }

            if (calendarView.selectedDates.size != -0) {
                long_subs_desc.text = "Mulai $daySelect"
                formatDay()
                detailOrder()
            }

        }

    }

    fun detailOrder() {
        price.text = "Rp $priceSelect"
        box.text = "$boxSelect Box"
        total_box.text = "$boxSelect Box"
        subs.text = "$daySubscribe Hari"
        total_pay.text = "Rp ${priceSelect * daySubscribe} "

    }

    fun formatDay() {
        val parseFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")
        val date = Date(calendarView.selectedDates[0].time.toString())
        daySelect = parseFormat.format(date)

    }

    private fun settings(settings: ArrayList<Subscribe>) {
        settings.add(Subscribe("20 Hari", 22500))
        settings.add(Subscribe("10 Hari", 24500))
        settings.add(Subscribe("5 Hari", 25000))
        settings.add(Subscribe("Pilih Sendiri", 0))
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMin -> {
                boxSelect = boxSelect - 1
                box.text = "$boxSelect Box"
                detailOrder()
            }
            R.id.btnPlus -> {
                boxSelect = boxSelect + 1
                box.text = "$boxSelect Box"
                detailOrder()
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

}
