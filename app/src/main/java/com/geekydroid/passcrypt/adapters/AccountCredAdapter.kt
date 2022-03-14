package com.geekydroid.passcrypt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.entities.CredWrapper
import com.geekydroid.passcrypt.listeners.CredOnClickListener
import com.google.android.material.card.MaterialCardView

class AccountCredAdapter(private val credOnClickListener: CredOnClickListener) :
    ListAdapter<CredWrapper, AccountCredAdapter.ViewHolder>(AccountCredCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountCredAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.account_cred_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: AccountCredAdapter.ViewHolder, position: Int) {
        val currentCred = currentList[position]
        holder.tvSiteName.text = currentCred.credName
        holder.tvComments.text = currentCred.credComments
        holder.tvCreatedOn.text =
            holder.itemView.context.getString(R.string.created_on, currentCred.createdOnFormatted)

        holder.credCard.setOnClickListener {
            credOnClickListener.onCredClick(currentCred)
        }
    }


    override fun getItemCount(): Int {
        return currentList.size
    }


    class AccountCredCallBack : DiffUtil.ItemCallback<CredWrapper>() {


        override fun areItemsTheSame(oldItem: CredWrapper, newItem: CredWrapper): Boolean {
            return oldItem.credType == newItem.credType && oldItem.credId == newItem.credId
        }


        override fun areContentsTheSame(oldItem: CredWrapper, newItem: CredWrapper): Boolean {
            return oldItem.credType == newItem.credType && oldItem == newItem
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSiteName: TextView = itemView.findViewById(R.id.tv_site_name)
        val tvComments: TextView = itemView.findViewById(R.id.tv_comments)
        val tvCreatedOn: TextView = itemView.findViewById(R.id.tv_created_on)
        val credCard: MaterialCardView = itemView.findViewById(R.id.cred_card)
    }

}