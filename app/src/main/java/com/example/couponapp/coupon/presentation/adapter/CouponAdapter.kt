package com.example.couponapp.coupon.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.couponapp.R
import com.example.couponapp.coupon.domain.Coupon
import kotlinx.android.synthetic.main.item_coupon_card.view.*

class CouponAdapter(
    private val onClicked: (Coupon) -> Unit,
    private val onActivateClicked: (Coupon) -> Unit
) :
    ListAdapter<Coupon, CouponAdapter.ItemViewHolder>(CouponDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_coupon_card, parent, false)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(item: Coupon) {
            itemView.setOnClickListener { onClicked(item) }
            itemView.productNameTv.text = item.product.name
            itemView.activateBtn.setOnClickListener { onActivateClicked(item) }
        }
    }
}

class CouponDiffUtil : DiffUtil.ItemCallback<Coupon>() {
    override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
        oldItem == newItem
}