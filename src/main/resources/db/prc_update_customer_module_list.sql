DELIMITER $$
DROP PROCEDURE IF EXISTS prc_update_customer_module_list $$
CREATE PROCEDURE prc_update_customer_module_list()
  BEGIN
    /*
    更新客户模组列表
    */
    DECLARE p_customer_name VARCHAR(30);
    DECLARE p_num INT;
    DECLARE p_module_type VARCHAR(20);
    DECLARE p_imei VARCHAR(40);
    DECLARE p_sn VARCHAR(40);
    DECLARE p_comm_sys VARCHAR(10);
    DECLARE p_app_ver VARCHAR(40);
    DECLARE p_iccid VARCHAR(40);
    DECLARE p_carrier VARCHAR(10);
    DECLARE p_combo_name VARCHAR(30);
    DECLARE p_combo_remain FLOAT;
    DECLARE p_separate INT;


    DECLARE stopFlag INT;
    DECLARE customer_modules CURSOR
    FOR SELECT
          a.customer_name,
          a.module_type,
          a.imei,
          a.sn,
          a.comm_sys,
          a.app_ver,
          a.iccid,
          b.carrier,
          b.combo_name,
          b.combo_remain,
          a.separate
        FROM tm_main_modules a , tm_main_sims b where
                                                          a.iccid = b.iccid
        ORDER BY customer_name;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stopFlag = 1;

    OPEN customer_modules;
    REPEAT
      FETCH customer_modules
      INTO p_customer_name, p_module_type,
        p_imei,
        p_sn,
        p_comm_sys,
        p_app_ver,
        p_iccid,
        p_carrier,
        p_combo_name,
        p_combo_remain,
        p_separate;
      BEGIN
        SELECT count(*)
        INTO p_num
        FROM tm_main_customer_module_list
        WHERE customer_name = p_customer_name and sn=p_sn;
        IF p_num = 0
        THEN INSERT INTO tm_main_customer_module_list (customer_name,sn
        )
        VALUES (p_customer_name,p_sn
        );
        END IF;
        UPDATE tm_main_customer_module_list
        SET module_type = p_module_type, imei = p_imei, sn = p_sn, comm_sys = p_comm_sys, app_ver = p_app_ver,
          iccid         = p_iccid, carrier = p_carrier, combo_name = p_combo_name, combo_remain = p_combo_remain,
          separate      = p_separate
        WHERE customer_name = p_customer_name and sn=p_sn;


      END;
    UNTIL stopFlag = 1
    END REPEAT;
    CLOSE customer_modules;

  END$$
DELIMITER ;