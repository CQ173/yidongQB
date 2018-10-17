package com.jbx.hjyt.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jbx.hjyt.R;
import com.jbx.hjyt.model.entity.res.WholeInfoRes;
import com.jbx.hjyt.util.FrescoUtil;
import com.jbx.hjyt.util.network.UrlUtil;
import com.jbx.hjyt.view.adapter.base.RecyclerBaseAdapter;
import com.jbx.hjyt.view.adapter.base.ViewHolder;

import java.util.List;

public class LoanHomepageAdapter extends RecyclerBaseAdapter<WholeInfoRes> {



    public LoanHomepageAdapter(@NonNull Context context, @NonNull List<WholeInfoRes> wholeInfoRes ) {
        super(context, wholeInfoRes);
    }

    @Override
    protected void bindDataForView(ViewHolder holder, WholeInfoRes wholeInfoRes, int position) {
        Log.i("aaaaaaaa" , wholeInfoRes.getTitle()+"");
        TextView tv_Title = holder.getView(R.id.tv_Title);
        tv_Title.setText(wholeInfoRes.getTitle());
        SimpleDraweeView image_log = holder.getView(R.id.image_log);
//        Uri uri = Uri.parse(wholeInfoRes.getIcon());
////        image_log.setImageURI(uri);
        FrescoUtil.getInstance().loadNetImage(image_log , UrlUtil.IMAGE_URL + wholeInfoRes.getIcon());
        Log.e("imageview" , "---"+UrlUtil.IMAGE_URL + wholeInfoRes.getIcon());
        TextView tv_max = holder.getView(R.id.tv_max);
        tv_max.setText("可贷款："+wholeInfoRes.getQuota_min() + "-" + wholeInfoRes.getQuota_max() + "元");
        TextView tv_rate = holder.getView(R.id.tv_rate);
        tv_rate.setText("日利率：" + wholeInfoRes.getRate() + "%");
        TextView tv_success_rate = holder.getView(R.id.tv_success_rate);
        tv_success_rate.setText(wholeInfoRes.getSuccess_rate() + "%");
        ImageView iv_Label = holder.getView(R.id.iv_Label);
        if (wholeInfoRes.getRecommend() == 1){
            iv_Label.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.icon_hot));
        }else if (wholeInfoRes.getRecommend() == 0) {
            iv_Label.setVisibility(View.GONE);
        }else if (wholeInfoRes.getNw() == 1) {
            iv_Label.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.icon_new_mouth));
        }else if (wholeInfoRes.getNw() == 0) {
            iv_Label.setVisibility(View.GONE);
        }
        TextView tv_Checks = holder.getView(R.id.tv_Checks);
        tv_Checks.setText(wholeInfoRes.getChecks());
        RelativeLayout rl_fukj = holder.getView(R.id.rl_fukj);
        rl_fukj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick( position ,wholeInfoRes.getUrl());
            }
        });
        TextView tv_traits = holder.getView(R.id.tv_traits);
        tv_traits.setText(wholeInfoRes.getTraits());
    }

    //跳转
    public interface OnItemClickListener{
        void onItemClick(int position, String url);
    }

    private OnItemClickListener listener;

    public void setClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(getContext(), R.layout.item_home_page, null);
        return new ViewHolder(view);
    }
}
