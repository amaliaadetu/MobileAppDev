package es.upm.btb.helloworldkt

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class BarAdapter(private val context: Activity) :
    ArrayAdapter<Bar>(context, R.layout.list_item, BarManager.barList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item, null)

        val imageView: ImageView = view.findViewById(R.id.barImage)
        val barName: TextView = view.findViewById(R.id.barName)
        val barRating: TextView = view.findViewById(R.id.barRating)
        val barPrice: TextView = view.findViewById(R.id.barPrice)
        val isChecked : CheckBox = view.findViewById(R.id.checkbox)

        try {
            val inputStream = context.assets
                .open("${BarManager.barList[position].id}.jpg")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        barName.text = BarManager.barList[position].name
        barRating.text = BarManager.barList[position].rating.toString()
        barPrice.text = BarManager.barList[position].price
        isChecked.isChecked = BarManager.barList[position].isChecked
        return view
    }
}