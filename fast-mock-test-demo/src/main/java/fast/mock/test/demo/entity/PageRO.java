package fast.mock.test.demo.entity;

import java.io.Serializable;

public class PageRO<T> implements Serializable {

    /** 默认为0*/
    public static final Integer SUCCESS = 0;
    /** 错误标识*/
    public static final Integer FAIL = 1;
    /** 编码*/
    private int code;
    /** 返回消息*/
    private String msg;
    /** 数据*/
    private T data;
    /** 总条数*/
    private int total;

    /**
     * 客户端判断请求是否成功
     */
    public boolean isSuccess() {
        return code == 0;
    }

    public PageRO() {
        this.code = SUCCESS;
    }

    public PageRO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PageRO(Integer code, String msg, Boolean isNeedTry) {
        this.code = code;
        this.msg = msg;
    }

    public PageRO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public PageRO(Integer code, String msg, T data, Boolean isNeedTry) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static PageRO BuildFailResponse() {
        return BuildFailResponse((String)null);
    }

    public static PageRO BuildFailResponse(String msg) {
        return BuildFailResponse(msg, (Object)null);
    }

    public static <T> PageRO BuildFailResponse(String msg, T data) {
        PageRO rtv = new PageRO();
        rtv.setCode(FAIL);
        rtv.setMsg(msg);
        rtv.setData(data);
        return rtv;
    }

    public static PageRO BuildSuccessResponse() {
        return BuildSuccessResponse((Object)null);
    }

    public static <T> PageRO BuildSuccessResponse(T data) {
        PageRO rtv = new PageRO();
        rtv.setCode(SUCCESS);
        rtv.setData(data);
        return rtv;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }
}
