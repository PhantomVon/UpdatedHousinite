package com.example.housinite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class AgentsAdapter(var context: Context, var data:ArrayList<Agents>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtEmail:TextView
        var mTxtPhoneNumber:TextView
        var mBtnDelete:Button
        var mBtnUpdate:Button

        init {
            this.mTxtName = row?.findViewById(R.id.mTvName) as TextView
            this.mTxtEmail = row?.findViewById(R.id.mTvEmail) as TextView
            this.mTxtPhoneNumber = row?.findViewById(R.id.mTvPhoneNumber) as TextView
            this.mBtnDelete = row?.findViewById(R.id.mBtnDelete) as Button
            this.mBtnUpdate = row?.findViewById(R.id.mBtnUpdate) as Button
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.agents_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Agents = getItem(position) as Agents
        viewHolder.mTxtName.text = item.name
        viewHolder.mTxtEmail.text = item.email
        viewHolder.mTxtPhoneNumber.text = item.phoneNumber
        viewHolder.mBtnDelete.setOnClickListener {
            var ref = FirebaseDatabase.getInstance().getReference().child("users/"+item.id)
            ref.removeValue().addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(context, "User deleted successfully",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Deleting user failed",Toast.LENGTH_LONG).show()
                }
            }
        }
        viewHolder.mBtnUpdate.setOnClickListener {
            var intent = Intent(context,AgentsUpdateActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("email",item.email)
            intent.putExtra("phoneNumber",item.phoneNumber)
            intent.putExtra("password",item.password)
            intent.putExtra("confirm password",item.confirmPassword)
            intent.putExtra("id",item.id)
            context.startActivity(intent)
        }
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