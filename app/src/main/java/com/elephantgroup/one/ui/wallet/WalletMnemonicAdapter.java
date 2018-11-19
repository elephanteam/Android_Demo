package com.elephantgroup.one.ui.wallet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elephantgroup.one.R;
import com.elephantgroup.one.custom.flowtag.OnInitSelectedPosition;
import com.elephantgroup.one.ui.wallet.entity.MnemonicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * MnemonicAdapter
 */
public class WalletMnemonicAdapter extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<MnemonicBean> mDataList;

    public WalletMnemonicAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext,R.layout.item_mnemonic_layout, null);
            viewHolder.mnemonicTag = convertView.findViewById(R.id.mnemonicTag);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String mnemonic  = mDataList.get(position).getMnemonic();
        viewHolder.mnemonicTag.setText(mnemonic);
        return convertView;
    }

    static class ViewHolder{
        TextView mnemonicTag;
    }

    public void loadData(List<MnemonicBean> sourceList) {
        mDataList.addAll(sourceList);
        notifyDataSetChanged();
    }

    public void reloadAll(List<MnemonicBean> datas) {
        mDataList.clear();
        loadData(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return true;
    }
}
