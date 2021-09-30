package fast.mock.test.demo.entity;

import java.io.Serializable;

/**
 * @author wanwenhao
 */
public class DataRO<T> implements Serializable {
    private int code = 0;
    private String msg = "";
    private T data = null;

    public final static String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    /**
     * 构造默认调用成功无返回值BO
     * @return 调用结果BO
     */
    public static <T> DataRO<T> buildSuccess() {
        return buildSuccess(null);
    }

    /**
     * 构造默认调用成功无返回值BO
     * @return 调用结果BO
     */
    public static <T> DataRO<T> faild(String msg) {
        return buildCommon(9999999, msg);
    }

    /**
     * 构造调用成功带返回值T的BO
     *
     * @param data 返回值
     * @param <T>  返回值类型
     * @return 调用结果BO
     */
    public static <T> DataRO<T> buildSuccess(T data) {
        return buildSuccess(DEFAULT_SUCCESS_MESSAGE, data);
    }

    /**
     * 转换返回值
     * @param data
     * @param <T>
     * @return
     */
    public static <T> DataRO <T> translate(DataRO data) {
        return buildCommon(data.code,data.msg);
    }

    /**
     * 构造调用成功带提示信息+返回值T的BO
     *
     * @param msg  提示信息
     * @param data 返回值
     * @param <T>  返回值类型
     * @return 调用结果BO
     */
    public static <T> DataRO<T> buildSuccess(String msg, T data) {
        return buildCommon(0, msg, data);
    }


    /**
     * 用返回码+提示信息+返回值构造BO，用于指定通用码
     *
     * @param code 返回码
     * @param msg  提示信息
     * @param data 返回值
     * @param <T>  返回值类型
     * @return 调用结果BO
     */
    public static <T> DataRO<T> buildCommon(int code, String msg, T data) {
        return new DataRO<T>()
                .setCode(code)
                .setMsg(msg)
                .setData(data);
    }

    /**
     * 返回码+提示信息构造BO，用于指定通用码
     *
     * @param code 返回码
     * @param msg  提示信息
     * @param <T>  返回值类型
     * @return 调用结果BO
     */
    public static <T> DataRO<T> buildCommon(int code, String msg) {
        return buildCommon(code, msg, null);
    }

    /**
     * 客户端判断请求是否成功
     */
    public boolean isSuccess() {
        return code == 0;
    }


    public static boolean judgeSuccess(DataRO dataRO){
        if(null == dataRO || !dataRO.isSuccess() || null == dataRO.getData()){
            return false;
        }
        return true;
    }

    public static boolean judgeNoDataSuccess(DataRO dataRO){
        if(null == dataRO || !dataRO.isSuccess()){
            return false;
        }
        return true;
    }

    public int getCode() {
        return code;
    }

    public DataRO setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public DataRO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public DataRO<T> setData(T data) {
        this.data = data;
        return this;
    }


}