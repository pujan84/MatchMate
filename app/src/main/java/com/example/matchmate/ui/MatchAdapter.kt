package com.example.matchmate.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchmate.data.local.MatchEntity
import com.example.matchmate.databinding.ItemMatchBinding

class MatchAdapter(
    private val onAccept: (String) -> Unit,
    private val onDecline: (String) -> Unit
) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    private val list = mutableListOf<MatchEntity>()

    fun submitList(data: List<MatchEntity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(val binding: ItemMatchBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {

        val binding = ItemMatchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MatchViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {

        val item = list[position]

        with(holder.binding) {

            txtName.text = "${item.name}, ${item.age}"
            txtLocation.text = "${item.city}, ${item.state}, ${item.country}"
            txtPhone.text = item.phone

            Glide.with(root.context)
                .load(item.image)
                .into(imgProfile)

            when (item.status) {

                "PENDING" -> {
                    txtStatus.visibility = android.view.View.GONE
                    buttonLayout.visibility = android.view.View.VISIBLE
                }

                "ACCEPTED" -> {
                    txtStatus.visibility = android.view.View.VISIBLE
                    txtStatus.text = "✓ Member Accepted"
                    txtStatus.setTextColor(androidx.core.content.ContextCompat.getColor(root.context, com.example.matchmate.R.color.accept))
                    buttonLayout.visibility = android.view.View.GONE
                }

                "DECLINED" -> {
                    txtStatus.visibility = android.view.View.VISIBLE
                    txtStatus.text = "✕ Member Declined"
                    txtStatus.setTextColor(androidx.core.content.ContextCompat.getColor(root.context, com.example.matchmate.R.color.decline))
                    buttonLayout.visibility = android.view.View.GONE
                }
            }

            btnAccept.setOnClickListener {
                onAccept(item.id)
            }

            btnDecline.setOnClickListener {
                onDecline(item.id)
            }
        }
    }
}