package com.example.housinite


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ApartmentsAdapter(var context: Context, var data:ArrayList<UploadApartments>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtLocation:TextView
        var mTxtBedrooms:TextView
        var mTxtFloor:TextView
        var mTxtRent:TextView
        var mImage:ImageView
        init {
            this.mTxtLocation = row?.findViewById(R.id.mTvLocation) as TextView
            this.mTxtBedrooms = row?.findViewById(R.id.mTvBedrooms) as TextView
            this.mTxtFloor = row?.findViewById(R.id.mTvFloor) as TextView
            this.mTxtRent = row?.findViewById(R.id.mTvRent) as TextView
            this.mImage = row?.findViewById(R.id.mImgPic) as ImageView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.apartment_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:UploadApartments = getItem(position) as UploadApartments
        viewHolder.mTxtLocation.text = item.location
        viewHolder.mTxtBedrooms.text = item.bedrooms
        viewHolder.mTxtFloor.text = item.floor
        viewHolder.mTxtRent.text = item.rent
        Glide.with(context).load(item.image).into(viewHolder.mImage)
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}