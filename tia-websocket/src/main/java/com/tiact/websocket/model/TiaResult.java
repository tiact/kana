package com.tiact.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

/**
 *返回数据
 *
 * @author Tia_ct
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TiaResult<T> implements Serializable {

    /**
     * 响应码
     */
    @NonNull
    private Integer code;

    /**
     * 响应信息
     */
    @NonNull
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public TiaResult(Integer code, String msg){
        this(code,msg,null);
    }
}
