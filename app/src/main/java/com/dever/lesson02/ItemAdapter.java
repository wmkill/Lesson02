package com.dever.lesson02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2015/12/29.
 */
public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> list;

    public ItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        Item item = list.get(position);
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (item.getUserName()!=null){
            holder.name.setText(item.getUserName());
            Picasso.with(context).load(getIconId(item.getId(),item.getUserIcon())).transform(new CircleTansForm()).into(holder.icon);
        }else{
            holder.name.setText("匿名用戶");
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }

        holder.content.setText(item.getContent());
        if(item.getImage()==null){
            holder.image.setVisibility(View.GONE);
        }else{
            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(getImageURL(item.getImage()))
                    //.fit()//永远正好的，listView中不是很好用
                    .placeholder(R.mipmap.ic_launcher)//下载中，下载失败的占位图片
                    .error(R.mipmap.ic_launcher)
                    .resize(parent.getWidth(), 0)//控制大小，两者不能同时为0，或者小于0
                    .into(holder.image);
        }
        return convertView;
    }
    public static String getImageURL(String image){
        String url = "http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s";
        Pattern pattern = Pattern.compile("(\\d+)\\d{4}");
        Matcher matcher = pattern.matcher(image);
        matcher.find();
        // TODO: 2015/12/29 监测网络 确定是small或者medium
        return String.format(url,matcher.group(1),matcher.group(),"medium",image);
    }
    public static String getIconId(long id,String icon){
        String url = "http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";
        return String.format(url,id/10000,id,icon);
    }
    public void addAll(Collection<? extends Item> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }
    private  static  class ViewHolder{
        private ImageView icon,image;
        private TextView name,content;

        public ViewHolder(View itemView){
            icon = (ImageView) itemView.findViewById(R.id.user_icon);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.user_name);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
