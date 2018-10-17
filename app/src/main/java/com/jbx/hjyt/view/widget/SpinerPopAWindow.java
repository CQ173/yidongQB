package com.jbx.hjyt.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.jbx.hjyt.R;
import com.jbx.hjyt.view.adapter.SpinerAAdapter;

import java.util.List;


public class SpinerPopAWindow extends PopupWindow implements OnItemClickListener {
    private Context mContext;
    private ListView mListView;
    private SpinerAAdapter mAdapter;
    private SpinerAAdapter.IOnItemSelectListener mItemSelectListener;
    public SpinerPopAWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }
    public void setItemListener(SpinerAAdapter.IOnItemSelectListener listener) {
        mItemSelectListener = listener;
    }
    public void setAdatper(SpinerAAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }
    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_listview, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
    }
    public void refreshData(List<String> list, int selIndex) {
        if (list != null && selIndex != -1) {
            if (mAdapter != null) {
                mAdapter.refreshData(list, selIndex);
            }
        }
    }
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onItemAClick(pos);
        }
    }
}

