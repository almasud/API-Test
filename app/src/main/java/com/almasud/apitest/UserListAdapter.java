package com.almasud.apitest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.almasud.apitest.databinding.LayoutCustomerBinding;
import com.almasud.apitest.databinding.LayoutVendorBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CUSTOMER_LAYOUT = 1;
    private static final int VENDOR_LAYOUT = 2;
    private List<User> mUsers;

    public UserListAdapter(List<User> mUsers) {
        this.mUsers = mUsers;
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return (mUsers.get(position).getRating() != null) ? VENDOR_LAYOUT: CUSTOMER_LAYOUT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCustomerBinding customerBinding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.layout_customer, parent, false);
        LayoutVendorBinding vendorBinding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.layout_vendor, parent, false);

        return (viewType == VENDOR_LAYOUT)? new VendorViewHolder(vendorBinding) : new CustomerViewHolder(customerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final User user = mUsers.get(position);
        String photoUrl = MainActivity.BASE_URL.concat(user.getPhoto());

        if (holder instanceof VendorViewHolder) {
            VendorViewHolder viewHolder = (VendorViewHolder) holder;
            // Set the user (vendor) into UI
            viewHolder.setVendor(user);
            // Download the user (vendor) photo and set it into UI or set a default
            Picasso.get().load(photoUrl).placeholder(R.drawable.profile_image)
                    .into(viewHolder.vendorBinding.userProfileImage);

        } else {
            CustomerViewHolder viewHolder = (CustomerViewHolder) holder;
            // Set the user (customer) into UI
            viewHolder.setCustomer(user);
            // Download the user (customer) photo and set it into UI or set a default
            Picasso.get().load(photoUrl).placeholder(R.drawable.profile_image)
                    .into(viewHolder.customerBinding.userProfileImage);
        }
        // Event for click on each user
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), "Name: " + user.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    private class CustomerViewHolder extends RecyclerView.ViewHolder {
        private LayoutCustomerBinding customerBinding;

        CustomerViewHolder(@NonNull LayoutCustomerBinding customerBinding) {
            super(customerBinding.getRoot());
            this.customerBinding = customerBinding;
        }

        void setCustomer(User user) {
            customerBinding.setUser(user);
        }
    }

    private class VendorViewHolder extends RecyclerView.ViewHolder {
        private LayoutVendorBinding vendorBinding;

        VendorViewHolder(@NonNull LayoutVendorBinding vendorBinding) {
            super(vendorBinding.getRoot());
            this.vendorBinding = vendorBinding;
        }

        void setVendor(User user) {
            vendorBinding.setUser(user);
        }
    }

    public void updateUsers(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }
}
