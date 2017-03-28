package com.xingzhaohui.xingtest.mainmodule.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingzhaohui.xingtest.mainmodule.data.CustomerRecord;
import com.xingzhaohui.xingtest.mainmodule.view.CustomerPage.OnListFragmentInteractionListener;

import java.util.List;

import com.xingzhaohui.xingtest.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CustomePageItemRecyclerViewAdapter extends RecyclerView.Adapter<CustomePageItemRecyclerViewAdapter.ViewHolder> {

    private final List<CustomerRecord> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CustomePageItemRecyclerViewAdapter(List<CustomerRecord> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custompage_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.m_Item = mValues.get(position);
        holder.m_NameText.setText(mValues.get(position).m_CustomerName);
        holder.m_AddressText.setText(mValues.get(position).m_CustomerAddress);
        holder.m_PhoneText.setText(mValues.get(position).m_CustomerPhone);
        holder.m_EmailText.setText(mValues.get(position).m_CustomerEmail);

        holder.m_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                    mListener.onCustomerPageInteraction(holder.m_Item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View m_View;
        public final TextView m_NameText;
        public final TextView m_AddressText;
        public final TextView m_PhoneText;
        public final TextView m_EmailText;
        public CustomerRecord m_Item;

        public ViewHolder(View view) {
            super(view);
            m_View = view;
            m_NameText = (TextView) view.findViewById(R.id.name);
            m_AddressText = (TextView) view.findViewById(R.id.address);
            m_PhoneText = (TextView) view.findViewById(R.id.phone);
            m_EmailText = (TextView) view.findViewById(R.id.email);
        }

        //@Override
        //public String toString() {
        //    return super.toString() + " '" + m_AddressText.getText() + "'";
        //}
    }
}
