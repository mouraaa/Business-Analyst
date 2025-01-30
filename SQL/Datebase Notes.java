/* DATABASES:

SQL (structured query language is the database language used by all RDB (relational databases)
postgreSQL / mySQL / SQL Server / Oracle / are different database engines 
some are free and opensource while others are not

We are using pgAdmin4, an application to manage postgre databases

There's no specific limit to how many tables / rows / attriibutes 
The only real boundary is how much space you have on your hard disk

If the database exists: “pqsl name_of_database”
If not, “create db name_of_database”
------------------------------------------------------------------------------------------------
NOTES;

-table = entity 
-Dont use camelCase, use snake_case (customer_id)
-When creating tables, make them plural (customers, orders, items etc..)

-Attributes are the coloumns, also in snake_case 
-There are required attributes and optional attributes that can be left empty (EX: middle name)
-Domain: a set of possible values for a given attributes EX: GPA -> [0,4]
-Tuple = 1 Row
-Row = a record

-Driven elements are anything that the computer can compute for you
EXAMPLE: balance / order total is not an attribute, its driven by other attributes such as (item price)
item price is an attribute but order total is not 
------------------------------------------------------------------------------------------------
Creating Tables:

When creating tables, try to abstract everything to make it simple
For example: we are creating a store database 
Tables would include : Customers, Stores, Suppliers, Orders, Items, etc...
Tables have attrbutes
EXAMPLE: a customer has attributes id, name, address, email, phone
attributes can be repeated in different tables
EXAMPLE: customer has a name and supplier has a name but they dont correlate


Always create entities first then create the relationships between the entities  
Each entity is a table of attributes


The relatiionship between entities should always be one to many and many to one
Example: one customer can have many orders but each one of those orders belongs to one customer

You cannot have many to many relationships. ALWYAS break it down to two one to many relations
When you break it down, there should be three tables (the two originals and the middleman table)
Take the primary key from each table and use both as a composite primary key for the middleman table
You will either start from the very right or very left, go to the middleman table then return to the oppsoite side.


EXAMPLE 1: 
One car can have multiple colors but each color can belong to many cars (one table called cars, other table called colors)
Introduce the middle man table, called car_color that uses car_id and color_id as attributes, each are foreign keys and together are the primary key for that table

For SQL: Theres an outer query and an inner query. Evaluate the inner query to use for the outer query. (The subquery is the middleman)
-TO SELECT ALL CARS THAT ARE YELLOW (ex: yellow's id = 7): 
select * from cars where car_id in (select car_id from car_color where color_id = 7;

-TO SELECT ALL COLORS THAT COME IN A SPECIFIC CAR (ex: niassan rouge's id is NISSAN176): 
select * from colors where color_id in(select color_id from car_colors where car_id = NISSAN176);



EXAMPLE 2:
One student can enroll in many classes and each class can have multiple students
introduce a third relationship, you can call it enroll
one student can enroll many times but each enrollment is by one student (1:M)
and each enrollment is to one course but manyeach course has many enrollments (1:M)



EXAMPLE 3: 
One supplier ships to multiple stores and each store has multiple suppliers 
introduce a third entity to act as a middleman; lets call it shipment 
Each supplier has multiple shipments but each shipment belongs to one supplier
and store has multiple shipments but each shipment belongs to one store 

------------------------------------------------------------------------------------------------
When building tables for one to many relationships:
The table that holds the 'many' side of the relation will need to reference the id of the 'one' side of the relationship

EXAMPLE:
Each customer has multiple orders and each order belongs to one customer so when creating the table:
the orders table (which is a table of all the orders by any customer) has to have the customer id to which that order belongs to
so if the customer is being referenced by id, the order table has to have an attribute coloumn for customer_id (the foreign key)
DO NOT place order id in the customers table

OTHER EXAMPLE: 
one supplier has many items and each item has one supplier so when creating the table
reference supplier_id inside the items table as a foreign key 


EXAMPLES OF POORT DATABASE DESIGN:
-empty fields
-data redundancy
-different names for the same thing (attributes named m_name and middle_name)
-attributes combined into the same coloumn (EX: full name or an entire address rather than first name, last name, address, state, city, zip code)

------------------------------------------------------------------------------------------------
CONSTRAINTS: 

-constraints are part of relations

Domain: 
a set of possible values for a given attributes EX: GPA -> [0,4]

Cardinality: 
expresses the min and max of an entity's occurences (EX: a professor teaches 4 classes, so a professors primary key shouldnt appear more than 4 times)

Not Null: 
the attribute cannot be left empty 

Super Key / Composite Keys:
an attribute or a combination of attributes that represents an entire entity/row/tuple
EX: student_id + first_name + last_name or first_name + last_name

Canidate keys are a subset of super keys that can be unique so the first one would work since it cant be replicated but the second one wouldnt

Primary Key:
Each table must have a primary key attribute which cannot be null, changed and must be unique across the specific table
You need a primary key to identify each entity

Secondary Key: 
a key other than primary key that can be used to retrieve data
EX: not every customer can remember their customer id so they would use first and last name as well as address and phone number to verify themselves

Foreign Key:
an identifier for an entity for which a specific instance belongs (can be repeated or null)
required to create relationships between tables
a foreign key is a foreign key in one tablr but the primary key in the other table
Example:
Order number 14 belongs to customer 207
order number 15 belongs to customer 222
14 and 15 would be primary keys 
207 ane 222 are called a foreign keys which will reference someone in the customer table

Cascades:
(A PART OF FOPREIGN KEYS)
On Cascades: delete - deletes the entire row 
On Cascades: nullify - doesn't delete the row, but deletes the reference (the foreign key)

EXAMPLE: You have a facebook account and want to delete it 
The account table will check if 'on cascades' is set to delete and if it is, the entire row will be deleted
Then, it will check if that account id is referenced in any other table and if it is, it will check that tables 'on cascade' constraint
If it's also set to delete, it will delete that row and check if any other table was using the foreign key of the most recently deleted row and so on so forth

If any of the tables on cascade is set to nullify, the row wont be deleted, but the reference will be deleted.
EXAMPLE:
You delete you account, but the billing table is set to nullify, the database will no loner know who placed this order but 
it will have all the other attrobutes of the order (price/paymentetc..)

------------------------------------------------------------------------------------------------
COMMANDS:

create database    	  -->   EX:  create database lab1

create table    	  -->   EX1: create table t1(id int, name varchar(10)) #string isnt a thing in database, must use varchar()
					 		EX2: create table customer(customer_id INT NOT NULL, customer_name varchar(255) NOT NULL, agent_id INT NOT NULL, total_price REAL NOT NULL, PRIMARY KEY(customer_id)) #REAL = DOUBLE IN SQL

insert into           -->   EX:  insert into table_name(id,name) values (3124, 'Adam') #single quotes because it is chars
select ... from  	  -->   EX:  Select name from t1 where id = 3 

delete from           -->   EX:  delete from t1 where id = 3
					  		EX2: delete from t1 (clears all rows in the table)


drop table                      -->   drops the entire table											
select * from table_name        -->   shows everything in the table

distinct						-->   shows only distinct names, no replicas
									  EX: select distinct name from t1

unique 							-->   only acceps unique entrys for that specific variable
									  EX: if you try to add the same variable, itll give you an error			

primary key 					-->   makes a variable into primary key 
									  EX: create table vendor(v_code integer not null unique, v_name varchar(35) not null, primary key(v_code));																																		

foreign key 					-->   makes a variable into foreign key 		
									  EX: create table product(p_code integer not null unique, p_name varchar(35) not null, foreign key(p_code));																																																			


check 							-->   a constraint to check if the value entered into the table can be entered based on the condition 
									  EX: create table discounts(discount float not null CHECK(discount >= 0 and discount <= 100))
									  checks if discount is within 0 to 100, if not, an error rises


count 							-->   shows how many rows in a table
									  EX1: select count(*) from agent (shows the amount of rows from agent)
								      EX2: select count(agent_name) from agent where agent_id between 10 and 20;
									  EX3: select count(distinct agent_name) from agent;
									  EX4: select agent_name, count(agent_name) from agent GROUP BY agent_name;
									  (shows the count of each agent_name) 


or                              -->   select * from agent where agent_id = 11 or agent_name = 'Bob';

between 						-->   INCLUSIVE
									  EX: select * from agent where agent_id between 11 and 22;

where not                       -->   returns anything that is not (must have parenthesis if multiple conditions)
									  EX: select * from agent where not (agent_id = 17 or agent_name = 'Bob')

like 							-->   look for a pattern
									  EX1: select * from agent where agent_name LIKE '_an_'; (returns any agent whose name has 4 characters and has 'a' as the second char and 'n' as the third char)
									  EX2: select * from agent where agent_name LIKE 'J%'; (returns any agent whose name starts with 'J')
									  EX3: select * from agent where agent_name LIKE '_a%'; (returns any agent whose name you dont know what char starts with, but has 'a' as the second char and if any characters afterwards)

having 							-->   use this term instead of where for conditions when using GROUP BY
									  EX: select customer_id, count(price) from invoice GROUP BY customer_id HAVING customer_id > 10;			
									  basically show the customer_id and how many products that id bought but only if customer_id > 10																


in / not in 					-->   check if something is in/not in a list
									  EX: select * from invoice where item_desc in (select item_desc from invoice where customer_id = 10);
									  Basically select everything bought by customer 10 and anyone who bought the same item(s) as 10															

ALL 							-->   select * from invoice where price > ALL (select price from invoice where customer_id = 10);
									  basically select all from invoice where the price is greater than all the prices of customer_id = 10


ANY 							-->  select * from invoice where price > ANY (select price from invoice where customer_id = 15);
									 basically select all from invoice where the price is greater than any the prices of customer_id = 10


%                               -->   any number of characters 
									  EX: select * from agent where agent_name LIKE 'e%'; 

UPPER() 						-->   EX: select upper(agent_name) from agent; (returns the uppercase value)
LOWER() 						-->   EX: select lower(agent_name) from agent; (returns the lowercase value)

MIN() 							-->   EX: select min(agent_id) from agent; (returns the minimum value)
MAX() 							-->   EX: select max(agent_id) from agent; (returns the maximum value) 
AVG() 							-->   EX: select avg(agent_id) from agent; (returns the average value)
SUM()        					-->   EX: Select sum(agent_id) from agent; (basically use sum to sum up all the coloumns and return one answer)

::int                           --> Converts into an int
									EX: select sum(price*qty)::int  from revenue;

------------------------------------
DATE/TIME: (you can use these instead of manually creating date/time attributes)

select CURRENT_DATE				-->   shows the current date
select CURRENT_TIME        	    -->   shows the current date
select NOW()					-->   shows the current date

values(CURRENT_DATE) 			-->   shows the current date
values(CURRENT_TIME)			-->   shows the current time
values(NOW()) 					-->   shows the current date and time

create table date(date date) #the first date is attribute name and second date is keyword date)
insert into t1(date) values (current_date)


------------------------------------
MORE COMMANDS: (THESE ARE IN THE POSTGRE DATABASE)

1: select id,id,id from t1        -->   shows id three times

2: #select the id and modify it and add it in a new attribute (so multiplying whatever is in id by 2 in a new attribute called times2, etc.)
select id, id*2 as times2, id*3 as times3 from t1 

3: #just adds a new attribute in the result table, but not in the original table so if you do select * from t1; it will return to id, name
select id^2 as sqr from t1


4: #just changes the name of the attribute in the query, but not in the actual table so if you do select * from t1; it will return to id, name
select id as student_id, name as name_of_student from t1 


5: #shows all the attributes are in a table 
select column_name from information_schema.columns where table_name = ‘class’  

6: #Another way of searching for multiple rows (Jerry would not appear in the results since hes not in the table)
select * from customer where customer_name IN ('Bob','Mike','Kelsey','Jerry');

7: select * from agent where not agent_id = 12 or agent_id = 17;

8: select * from agent where agent_name is NULL; #CHECK IF NULL/NOT NULL

9: #shows if a customer is also an agent
#E and M are nicknames because you are joining the same table twice, so to remove ambiguity, rename each table
select E.customer_name, M.customer_id from customer E JOIN customer M ON E.customer_id = M.agent_id;

------------------------------------

\l                 --> means list all the databases in your server
\c db_name         --> basically means change to db_name (change from database to database)
\dt      		   --> shows all the tables in a database

<>                 --> means does not equal
or not             --> means <> (does not equal)
ON 				   --> means if (ONLY USE ON WHEN JOINING TABLES)


------------------------------------------------------------------------------------------------
JOINING TABLES: (THESE ARE IN THE LECTURE DB)

#JOIN means cartesian product. JOIN needs an on ON statement
select * from customer JOIN agent ON customer_name = agent_name (shows you from both tables where customer_name = agent_name)
select * from customer JOIN agent ON customer_name <> agent_name 

CORSS JOIN = cartesian product (no ON statement needed)
select * from customer CROSS JOIN agent;


#LEFT/RIGHT joins need an ON statement (use the foreign key from 1st table = primary key of 2nd table)
#shows you the entire customer table and any agent that is linked to each customer
select * from customer LEFT JOIN agent ON customer.agent_id = agent.agent_id;

#shows you the entire agent table and any customer linked to each agent (if an agent helped multiple customers, that agent will have multiple rows for each customer helped)
select * from customer RIGHT JOIN agent ON customer.agent_id = agent.agent_id;

#uses natural join based on a specific attribute
select * from customer JOIN agent USING(agent_id);

#UNION (shows everything that is in t1 and t2)
select * from t1 union select * from t2;

#INTERSECT (shows everything that is in t1 and t2)
select * from t1 intersect select * from t2;

#EXCEPT (shows everything in t1 that isnt in t2)
select * from T1 except select * from T2;
------------------------------------
ORDERING:

select * from customer ORDER BY agent_id;          
select * from customer ORDER BY customer_name;
select * from customer ORDER BY agent_id DESC;     
select * from customer ORDER BY customer_name DESC;

#if 2 agents have the same name, then resort those people by their agent_id in descending order, but if they have the same agent_id, sort them by ascending phone #
select * from agent ORDER BY agent_name  DESC, agent_id DESC, phone;


------------------------------------
GROUP BY: removes duplicates and groups by a certain attribute and can only be used with aggregate functions

EX: SELECT customer_id, avg(price) from invoice GROUP BY customer_id;
Basically group each of the customer_ids together and use the aggrate function avg to the avg price each person paid


------------------------------------
-CREATING A TABLE FROM ANOTHER TABLE:

create table priceList as select p_code, p_descript, p_price from product
creates a table called pricelist using the coloumns p_code, p_descript and p_price from the product table

you can also change the name of one of the coloums, also using as (the first as is different from second as)
create table priceList as select p_code, p_descript as p_description, p_price from product


-INSERTING VARIABLES FROM ONE TABLE TO ANOTHER ALREADY CREATED TABLE
EX: INSERT INTO plist SELECT p_code, p_price from priceList WHERE p_price < 50;

------------------------------------
-SHOW METADATA ABOUT THE TABLE : (name of data table HAS to be lowercase or else itll show nothing)
EX: select column_name, data_type, character_maximum_length FROM information_schema.columns where table_name = 'pricelist';

ALTER THE METADATA (examples: you want to add an extra column, drop a column, change varchar from 30 to 40, or you forgot to create one of the variables as primary key)
EX1: ALTER TABLE pricelist ALTER p_descript TYPE  VARCHAR(40);   
EX2: ALTER TABLE pricelist ADD p_qty int;
EX3: ALTER TABLE priceList DROP column p_qty
EX4: ALTER TABLE pricelist ADD PRIMARY KEY(p_code);



UPDATING ROW INFO
UPDATE pricelist SET p_descript = 'graphic tshirt', p_price = 45 where p_code = 100;


ALTER VS UPDATE:
ALTER CHANGES METADATA (column_name, data_type, character_maximum_length, primary key, etc)
UPDATE: changes actual data inside the table

------------------------------------
CREATE VIEWS: lets say you use the same query everyday and the query is long, just create a view of that query and select * from view_name

EX: CREATE VIEW stats AS SELECT * FROM priceList WHERE p_price < 100;

(Basically we have a table with the query (SELECT * FROM priceList WHERE p_price < 100) )
now just 'SELECT' * FROM stats' is much easier to write than the query (usually the query is much longer so you would use a view)

------------------------------------
LEARN TRANSACTIONS, PARALLEL PROCESSESING, BIG DATA
learn noSQL
*/



