package com.java.demomp.sys.entity;

    import com.baomidou.mybatisplus.annotation.TableName;
    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author lost丶wind
* @since 2019-06-10
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("t_person")
    public class Person extends Model<Person> {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
