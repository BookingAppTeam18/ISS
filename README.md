# ISS
Backend of Booking App


##### Remove All Data

TRUNCATE accommodation_requests, accommodations, comments,notifications, report,  account, reservation

CASCADE;

##### Drop all tables

select 'drop table if exists "' || tablename || '" cascade;' 

  from pg_tables
  
 where schemaname = 'public';

(run the script that it makes)
