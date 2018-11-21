package com.chris.base;

/**
 * Created by lisen on 2018/11/22.
 */

import com.chris.base.common.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author lisen
 * @Description //TODO
 * @Date 2018/11/22
 * @Param
 * @return
 * Created by lisen on 2018/11/22.
 * 测试JDBCUtils工具类
 **/
public class JDBCUtilsTest {
    public static void main(String[] args) {
        show();
    }

    private static void show() {
        /**
         * 获取数据库连接对象Connection
         */
        Connection conn = JDBCUtils.getConnection();
        Statement stat = null;
        ResultSet rs = null;
        try {
            /**
             * 获取执行sql语句的执行者对象
             */
            stat = conn.createStatement();
            /**
             * 执行sql语句
             */
            /*String sql = "INSERT INTO `sp_car_info` ( `car_no`, `name`, `phone`, `reservation_id`, `ext1`, `ext2`, `ext3`, `create_time`, `create_user_id`, `update_time`, `update_user_id`, `remark`) \n" +
                    "VALUES ( 'IGNP', '驾驶员', '66666666666', '3', NULL, NULL, NULL, '2018-11-23 03:04:17', NULL, NULL, NULL, NULL);";*/
            String sql = "INSERT INTO NDr2_AuthorSet1 ([CardID], [DoorID], [PassWord], [DueDate], [AuthorType], [AuthorStatus], [UserTimeGrp], [DownLoaded], [FirstDownLoaded], [PreventCard], [StartTime]) VALUES ('9992', '229', '0000', '2099-12-31 10:24:06.000', '1', '0', '0', '0', '0', '0', '2018-11-19 10:25:04.000')";
            Boolean flag = stat.execute(sql);
            /**
             * 处理结果集,遍历Set集合
             */
            if (!flag) {
                System.out.println("记录已成功插入");
            } else {
                System.out.println("数据插入异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * 使用JDBCUtilsConfig工具类中的方法close释放资源
             */
            JDBCUtils.close(rs, stat, conn);
        }
    }

}
