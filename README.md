# ISS
Backend of Booking App


#### Remove All Data

select 'truncate "' || tablename || '" cascade;' 
  from pg_tables
 where schemaname = 'public';


(run the script that it makes)


#### Drop all tables

select 'drop table if exists "' || tablename || '" cascade;' 
  from pg_tables
 where schemaname = 'public';

(run the script that it makes)
