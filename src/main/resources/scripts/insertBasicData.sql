insert into Plan(id,name) values (1,'Basic');

insert into Role(id,rolename) values (1,'ROLE_BASIC');

insert into User(id,username,password,is_active,plan_id,email)
values(1,'prashant','{noop}prashant',TRUE,1,'prashant.6388@gmail.com');
//{noop} is used because we have not implemented password encoder required by spring security

insert into User_Role(id,role_id,user_id) values (1,1,1);