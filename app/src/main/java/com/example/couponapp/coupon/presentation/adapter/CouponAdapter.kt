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
    private val onActivateClicked: (Coupon) -> Unit,
    private val onClicked: (Coupon) -> Unit
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
            bindListeners(item)
            handleCardButtonState(item.isActive)
            setTitleAndSubtitle(item)
        }

        private fun setTitleAndSubtitle(item: Coupon) {
            itemView.productNameTv.text = item.product.name
            itemView.expiredTv.text =
                itemView.context.getString(R.string.coupon_expire_date, item.expiredAt)
        }

        private fun bindListeners(item: Coupon) {
            itemView.cardCtaBtn.setOnClickListener { onActivateClicked(item) }
            itemView.setOnClickListener { onClicked(item) }
        }

        private fun handleCardButtonState(isActive: Boolean) {
            itemView.cardCtaBtn.isActivated = isActive
            itemView.cardCtaBtn.text = itemView.context.getString(
                if (isActive) R.string.coupon_active_cta else R.string.coupon_cta
            )
        }
    }
}

class CouponDiffUtil : DiffUtil.ItemCallback<Coupon>() {
    override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean =
        oldItem == newItem
}