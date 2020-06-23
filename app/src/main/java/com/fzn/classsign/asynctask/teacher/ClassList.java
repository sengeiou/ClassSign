package com.fzn.classsign.asynctask.teacher;

import com.fzn.classsign.asynctask.base.CustomAsyncTask;

import java.util.List;
import java.util.Map;

/**
 * 令牌刷新接口
 *
 * @param <T>
 */
public class ClassList<T> extends CustomAsyncTask<T> {
    public ClassList(Map headers, Map body, Map params, String url) {
        super(headers, body, params, url);

    }

    @Override
    protected void onPostExecute(String s) {
        ResponseResultJson<List<Map<String, String>>> temp = (ResponseResultJson<List<Map<String, String>>>) getResponse(s);

        List<Map<String, String>> data=temp.getData();

        super.onPostExecute(s);
    }
}