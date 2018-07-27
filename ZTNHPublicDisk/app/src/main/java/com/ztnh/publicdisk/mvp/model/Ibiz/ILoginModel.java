package com.ztnh.publicdisk.mvp.model.Ibiz;



import com.ztnh.publicdisk.mvp.view.TestV;

import java.util.Map;

/**
 * @date 2018/3/2 15:19
 * @author lingwancai
 * @desc ILoginModel.java{TOOO}
 */
public interface ILoginModel {

   void OnTest(Map<String, Object> map, TestV view);

   void cancleReq();
}
