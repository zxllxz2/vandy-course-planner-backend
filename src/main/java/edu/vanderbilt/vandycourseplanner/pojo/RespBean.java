package edu.vanderbilt.vandycourseplanner.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Public return object
 *
 * @author Toby Zhu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object obj;

    /**
     * return successfully
     * @param message
     * @return code, message, object
     */
    public static RespBean success(String message) {
        return new RespBean(200, message, null);
    }

    /**
     * return successfully with object
     * @param message
     * @return code, message, object
     */
    public static RespBean success(String message, Object object) {
        return new RespBean(200, message, object);
    }

    /**
     * fail to return
     * @param message
     * @return code, message
     */
    public static RespBean error(String message) {
        return new RespBean(500, message, null);
    }

    /**
     * fail to return with object
     * @param message
     * @return code, message, object
     */
    public static RespBean error(String message, Object object) {
        return new RespBean(500, message, object);
    }

    /**
     * template response
     * @param code
     * @param message
     * @param obj
     * @return code, message, object
     */
    public static RespBean template(long code, String message, Object obj) {
        return new RespBean(code, message, obj);
    }

}
