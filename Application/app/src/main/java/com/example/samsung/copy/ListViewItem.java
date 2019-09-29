package com.example.samsung.copy;

/**
 * Created by SAMSUNG on 2017-11-08.
 */

public class ListViewItem {
    private  String imgDrawable;
    private  String descStr;

    public void setImg(String img){
        imgDrawable=img;
    }
    public void setDesc(String desc){
        descStr=desc;
    }

    public String getImg(){
        return this.imgDrawable;
    }
    public String getDesc(){
        return this.descStr;
    }
}
