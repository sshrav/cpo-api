delete from CPO_QUERY_PARAMETER  where query_id in (select distinct query_id from CPO_QUERY where group_id in (select distinct group_id from CPO_QUERY_GROUP where class_id=(select class_id from CPO_CLASS where name='org.synchronoss.cpo.meta.cpo.CpoFunctionBean')));
delete from CPO_QUERY where group_id in (select distinct group_id from CPO_QUERY_GROUP where class_id=(select class_id from CPO_CLASS where name='org.synchronoss.cpo.meta.cpo.CpoFunctionBean'));
delete from cpo_query_text where text_id = '0d29129e-0a0e-1e64-0c4c-7e10b61bfba1';
delete from cpo_query_text where text_id = '64cf37c6-0a0b-1454-1204-6136eab9b421';
delete from cpo_query_text where text_id = '0cb585c4-0a0e-1e64-0c4c-7e10866cacb6';
delete from cpo_query_text where text_id = '0cb53cca-0a0e-1e64-0c4c-7e105b23e19d';
delete from cpo_query_text where text_id = '0d258874-0a0e-1e64-0c4c-7e105c21d818';
delete from cpo_query_text where text_id = '0cb5effa-0a0e-1e64-0c4c-7e10d7d1b35c';
delete from cpo_query_text where text_id = '0cb5b46c-0a0e-1e64-0c4c-7e10f12518a4';
delete from CPO_QUERY_GROUP where class_id=(select class_id from CPO_CLASS where name='org.synchronoss.cpo.meta.cpo.CpoFunctionBean');
delete from CPO_ATTRIBUTE_MAP where class_id=(select class_id from CPO_CLASS where name='org.synchronoss.cpo.meta.cpo.CpoFunctionBean');
delete from CPO_CLASS where name='org.synchronoss.cpo.meta.cpo.CpoFunctionBean';
insert into cpo_query_text (text_id, sql_text, description, userid) values ('0d29129e-0a0e-1e64-0c4c-7e10b61bfba1','delete from __CPO_TABLE_PREFIX__cpo_query where group_id=?','CpoQueryBean - deleteQueriesForGroup','dberry');
insert into cpo_query_text (text_id, sql_text, description, userid) values ('64cf37c6-0a0b-1454-1204-6136eab9b421','delete from __CPO_TABLE_PREFIX__cpo_query_parameter where query_id = ?','CpoQueryBean - deleteQueryParameters','dberry');
insert into cpo_query_text (text_id, sql_text, description, userid) values ('0cb585c4-0a0e-1e64-0c4c-7e10866cacb6','delete from __CPO_TABLE_PREFIX__cpo_query where query_id=?','CpoQueryBean - deleteQuery','dberry');
insert into cpo_query_text (text_id, sql_text, description, userid) values ('0cb53cca-0a0e-1e64-0c4c-7e105b23e19d','insert into __CPO_TABLE_PREFIX__cpo_query (CREATEDATE, GROUP_ID, QUERY_ID, SEQ_NO, STORED_PROC, TEXT_ID, USERID) values (?,?,?,?,?,?,?)','CpoQueryBean - insertQuery','dberry');
insert into cpo_query_text (text_id, sql_text, description, userid) values ('0d258874-0a0e-1e64-0c4c-7e105c21d818','select * from __CPO_TABLE_PREFIX__cpo_query where group_id = ? order by seq_no','CpoQueryBean - retrieveQueriesForGroup','dberry');
insert into cpo_query_text (text_id, sql_text, description, userid) values ('0cb5effa-0a0e-1e64-0c4c-7e10d7d1b35c','select * from __CPO_TABLE_PREFIX__cpo_query where query_id = ?','CpoQueryBean - retrieveQuery','dberry');
insert into cpo_query_text (text_id, sql_text, description, userid) values ('0cb5b46c-0a0e-1e64-0c4c-7e10f12518a4','update __CPO_TABLE_PREFIX__cpo_query set SEQ_NO = ?, STORED_PROC = ?, TEXT_ID = ? where QUERY_ID = ?','CpoQueryBean - updateQuery','dberry');
insert into cpo_class (class_id, name, userid) values ('0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','org.synchronoss.cpo.meta.cpo.CpoFunctionBean','dberry');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e105c7fc137','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','CREATEDATE','createdate','DATE',null,null,'org.synchronoss.cpo.transform.jdbc.TransformTimestampToCalendar','dberry');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e103d3f115b','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','TEXT_ID','expressionId','VARCHAR',null,null,null,'dberry');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e104eb94edd','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','QUERY_ID','functionId','VARCHAR',null,null,null,'dberry');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e101c3115c0','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','GROUP_ID','groupId','VARCHAR',null,null,null,'dberry');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10f9a8e890','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','SEQ_NO','seqNo','INTEGER',null,null,null,'michael');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10eda69ce7','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','STORED_PROC','storedProc','VARCHAR',null,null,null,'dberry');
insert into cpo_attribute_map (attribute_id, class_id, column_name, attribute, column_type, db_column, db_table,transform_class, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10f878db5d','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','USERID','userid','VARCHAR',null,null,null,'dberry');
insert into cpo_query_group (group_id, class_id, group_type, name, userid) values ('0d2953e8-0a0e-1e64-0c4c-7e10f4b16346','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','DELETE','deleteQueriesForGroup','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('0d2953e9-0a0e-1e64-0c4c-7e10ecc9092e','0d2953e8-0a0e-1e64-0c4c-7e10f4b16346','0d29129e-0a0e-1e64-0c4c-7e10b61bfba1','0','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e101c3115c0','0d2953e9-0a0e-1e64-0c4c-7e10ecc9092e','1','IN','dberry');
insert into cpo_query_group (group_id, class_id, group_type, name, userid) values ('0d22e4f4-0a0e-1e64-0c4c-7e10fbb81d98','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','DELETE','deleteQuery','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('0d22e4f5-0a0e-1e64-0c4c-7e101bb00545','0d22e4f4-0a0e-1e64-0c4c-7e10fbb81d98','64cf37c6-0a0b-1454-1204-6136eab9b421','0','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e104eb94edd','0d22e4f5-0a0e-1e64-0c4c-7e101bb00545','1','IN','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('64cf6e6d-0a0b-1454-1204-6136112adca7','0d22e4f4-0a0e-1e64-0c4c-7e10fbb81d98','0cb585c4-0a0e-1e64-0c4c-7e10866cacb6','1','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e104eb94edd','64cf6e6d-0a0b-1454-1204-6136112adca7','1','IN','dberry');
insert into cpo_query_group (group_id, class_id, group_type, name, userid) values ('0d22a58d-0a0e-1e64-0c4c-7e10a2fbb383','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','CREATE','insertQuery','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('0d22a58e-0a0e-1e64-0c4c-7e10800f2695','0d22a58d-0a0e-1e64-0c4c-7e10a2fbb383','0cb53cca-0a0e-1e64-0c4c-7e105b23e19d','0','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e105c7fc137','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','1','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e101c3115c0','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','2','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e104eb94edd','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','3','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10f9a8e890','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','4','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10eda69ce7','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','5','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e103d3f115b','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','6','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10f878db5d','0d22a58e-0a0e-1e64-0c4c-7e10800f2695','7','IN','dberry');
insert into cpo_query_group (group_id, class_id, group_type, name, userid) values ('0d2456dc-0a0e-1e64-0c4c-7e10d44594e4','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','LIST','retrieveQueriesForGroup','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('0d2456dd-0a0e-1e64-0c4c-7e100bb5c08d','0d2456dc-0a0e-1e64-0c4c-7e10d44594e4','0d258874-0a0e-1e64-0c4c-7e105c21d818','0','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e101c3115c0','0d2456dd-0a0e-1e64-0c4c-7e100bb5c08d','1','IN','dberry');
insert into cpo_query_group (group_id, class_id, group_type, name, userid) values ('0d23758a-0a0e-1e64-0c4c-7e1079366ecf','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','RETRIEVE','retrieveQuery','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('0d23758b-0a0e-1e64-0c4c-7e10c2b63a60','0d23758a-0a0e-1e64-0c4c-7e1079366ecf','0cb5effa-0a0e-1e64-0c4c-7e10d7d1b35c','0','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e104eb94edd','0d23758b-0a0e-1e64-0c4c-7e10c2b63a60','1','IN','dberry');
insert into cpo_query_group (group_id, class_id, group_type, name, userid) values ('0d23264e-0a0e-1e64-0c4c-7e109ebaf923','0c97c7ec-0a0e-1e64-0c4c-7e106c41528b','UPDATE','updateQuery','dberry');
insert into cpo_query (query_id, group_id, text_id, seq_no, userid) values ('0d23264f-0a0e-1e64-0c4c-7e104e77f64d','0d23264e-0a0e-1e64-0c4c-7e109ebaf923','0cb5b46c-0a0e-1e64-0c4c-7e10f12518a4','0','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10f9a8e890','0d23264f-0a0e-1e64-0c4c-7e104e77f64d','1','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ee-0a0e-1e64-0c4c-7e10eda69ce7','0d23264f-0a0e-1e64-0c4c-7e104e77f64d','2','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e103d3f115b','0d23264f-0a0e-1e64-0c4c-7e104e77f64d','3','IN','dberry');
insert into cpo_query_parameter (attribute_id, query_id, seq_no, param_type, userid) values ('0c97c7ed-0a0e-1e64-0c4c-7e104eb94edd','0d23264f-0a0e-1e64-0c4c-7e104e77f64d','4','IN','dberry');
