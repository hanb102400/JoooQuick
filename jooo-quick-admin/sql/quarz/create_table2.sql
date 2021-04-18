
DROP TABLE IF EXISTS tbl_quartz_task_config;
CREATE TABLE tbl_quartz_task_config (
  task_id           int(11) not null  AUTO_INCREMENT comment '任务编号',
  task_job_name     varchar(50)  comment '任务名称' ,
  task_trigger_name varchar(50) comment '任务名称',
  task_cron         varchar(50) comment '任务cron表达式',
  task_object       varchar(200),
  task_method       varchar(200),
  task_start_time   bigint(13),
  task_end_time     bigint(13),
  status            tinyint(4)            default 0                   comment   '状态：0正常，1停用 -1删除',
  remark            varchar(500)      default null               comment   '备注',
  create_user       varchar(50)      default null ,
  create_time 	    bigint(13)         default null ,
  update_user       varchar(50)      default null ,
  update_time       bigint(13)         default null,
  primary key (task_id)
 ) ENGINE=InnoDB;

DROP TABLE IF EXISTS tbl_quartz_task_log;
CREATE TABLE tbl_quartz_task_log (
  log_id            int(11) not null  AUTO_INCREMENT comment '日志编号',
  task_id           int(11) not null  comment '任务编号' ,
  task_run_time     bigint(13)   comment '任务运行时间' ,
  task_run_state    varchar(13)  comment '任务运行状态' ,
  task_fail_msg     varchar(500)  comment  '错误日志',
  primary key (log_id)
) ENGINE=InnoDB;
