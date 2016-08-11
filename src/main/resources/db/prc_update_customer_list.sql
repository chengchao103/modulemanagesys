DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_customer_list $$
CREATE PROCEDURE prc_update_customer_list()
  BEGIN
    /*
    更新客户列表
    */
    DECLARE p_id INT;
    DECLARE p_customer_name VARCHAR(30);
    DECLARE p_num INT;
    DECLARE p_module_count INT;
    DECLARE p_type_num INT;
    DECLARE p_separate_num INT;
    DECLARE p_sim_num INT DEFAULT 0;
    DECLARE p_comm_sys VARCHAR(20);
    DECLARE p_carrier VARCHAR(20);


    DECLARE stopFlag INT;
    DECLARE customers CURSOR
    FOR SELECT customer_name
        FROM tm_main_customers;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;

    OPEN customers;
    REPEAT
      FETCH customers
      INTO p_customer_name;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM tm_main_customer_list
        WHERE customer_name = p_customer_name;
        IF p_num = 0
        THEN INSERT INTO tm_main_customer_list (customer_name
        )
        VALUES (p_customer_name
        );
        END IF;


        /*模组数量*/

        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1 AND separate = -1 AND
              customer_value = -1 AND customer_count = -1 AND customer_add_count = -1 AND province = -1 AND
              percentage = -1 AND customer_name = p_customer_name;
        IF p_num != 0
        THEN
          SELECT module_count
          INTO p_module_count
          FROM ts_sys_statistics_analysis
          WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1 AND separate = -1 AND
                customer_value = -1 AND customer_count = -1 AND customer_add_count = -1 AND province = -1 AND
                percentage = -1 AND customer_name = p_customer_name;

          UPDATE tm_main_customer_list
          SET module_num = p_module_count
          WHERE customer_name = p_customer_name;
        END IF;

        /*机卡分离模组数量*/


        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1
              AND separate = 1 AND customer_value = -1 AND customer_count = -1
              AND customer_add_count = -1 AND province = -1 AND percentage = -1
              AND customer_name = p_customer_name;
        IF p_num != 0
        THEN
          SELECT module_count
          INTO p_separate_num
          FROM ts_sys_statistics_analysis
          WHERE industry = -1 AND module_type = -1 AND carrier = -1 AND comm_sys = -1
                AND separate = 1 AND customer_value = -1 AND customer_count = -1
                AND customer_add_count = -1 AND province = -1 AND percentage = -1
                AND customer_name = p_customer_name;

          UPDATE tm_main_customer_list
          SET separate_num = p_separate_num
          WHERE customer_name = p_customer_name;
        END IF;


        /*型号种类数量*/


        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        WHERE module_type !=-1 AND
              module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
              industry = -1 AND comm_sys = -1 AND customer_value = -1 AND customer_name = p_customer_name;
        IF p_num != 0
        THEN
          SELECT count(DISTINCT module_type)
          INTO p_type_num
          FROM ts_sys_statistics_analysis
          WHERE module_type !=-1  AND
                module_add_count = -1 AND customer_count = -1 AND customer_add_count = -1 AND percentage = -1 AND
                industry = -1 AND comm_sys = -1 AND customer_value = -1 AND customer_name = p_customer_name;

          UPDATE tm_main_customer_list
          SET type_num = p_type_num
          WHERE customer_name = p_customer_name;
        END IF;


        /*所有通讯制式*/


        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        where comm_sys!=-1 and province=-1 and customer_name=p_customer_name;
        IF p_num != 0
        THEN
          SELECT GROUP_CONCAT(DISTINCT comm_sys)
          INTO p_comm_sys
          FROM ts_sys_statistics_analysis
          where comm_sys!=-1 and province=-1 and customer_name=p_customer_name;

          UPDATE tm_main_customer_list
          SET comm_sys = p_comm_sys
          WHERE customer_name = p_customer_name;
        END IF;


        /*所有运营商*/


        SELECT count(*)
        INTO p_num
        FROM ts_sys_statistics_analysis
        where  carrier !=-1 AND province = -1 AND customer_name = p_customer_name;
        IF p_num != 0
        THEN
          SELECT GROUP_CONCAT(DISTINCT carrier)
          INTO p_carrier
          FROM ts_sys_statistics_analysis
          where  carrier !=-1 AND province = -1 AND customer_name = p_customer_name;

          UPDATE tm_main_customer_list
          SET carrier = p_carrier
          WHERE customer_name = p_customer_name;
        END IF;




        /*sim卡数量*/
        SELECT count(iccid)
        INTO p_sim_num
        FROM tm_main_sims
        WHERE  customer_name = p_customer_name;

        UPDATE tm_main_customer_list
        SET sim_num = p_sim_num
        WHERE customer_name = p_customer_name;





      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE customers;

  END$$
DELIMITER ;