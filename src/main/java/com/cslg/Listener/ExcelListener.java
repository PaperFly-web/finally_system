package com.cslg.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
*@desc:excle的类
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/24 13:07
*/
@Slf4j
public class ExcelListener extends AnalysisEventListener {
    /**
     * 自定义用于暂时存储data。
     * 可以通过实例获取该值
     */
    public List<List<String>> data = new ArrayList<>();

    @Override
    public void invoke(Object object, AnalysisContext context) {
        List<String> stringList= (List<String>) object;
        log.info("当前sheet:"+context.getCurrentSheet().getSheetNo()+ " 当前:" + context.getCurrentRowNum()
                + " data:" + stringList.get(0));
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        data.add(stringList);
        //根据自己业务做处理
        doSomething(stringList);
    }
    private void doSomething(List<String> string) {
        //1、入库调用接口
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // datas.clear();//解析结束销毁不用的资源
    }
    public List<List<String>> getData() {
        return data;
    }
    public void setData(List<List<String>> data) {
        this.data = data;
    }
}