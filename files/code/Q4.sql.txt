select 
	toyotaDate1 as "Date",
	toyotas as "Toyotas sold",
	fords as "Fords sold", 
	FORMAT((toyotas/fords),2) as "Toyotas To Fords Sold",
	TRUNCATE(fc,2) as "Ford stock",
	TRUNCATE(tc,2) as "Toyota stock",
    FORMAT((tc/fc),2) as "Toyota To Ford Stock" 
    
from(
    select 
    	YEARWEEK(c.Sale_Date) as toyotaDate1,
    	count(c.Invoice_id) as toyotas
    	
    	from
    		cars as c
		where 
        	c.New_vehicle_make = 'Toyota'
		group by 
        	YEARWEEK(c.Sale_Date)) q1
	
			inner join(
				select 
					YEARWEEK(c.Sale_Date) as fordDate1,
        	 		count(c.Invoice_id) as fords
        	 		from
        	 			cars as c
					where 
        				c.New_vehicle_make = 'Ford'
					group by 
        				YEARWEEK(c.Sale_Date)) q2
					on 
    					toyotaDate1=fordDate1
	
			inner join(
				select
					YEARWEEK(fs.Date) as fordDate2,
         			fs.close as fc 
         			from 
         				Ford_stock as fs) q3
					on 
    					fordDate1=fordDate2
	
			inner join(
				select
					YEARWEEK(ts.Date) as toyotaDate2,
     				ts.close tc 
     				from 
     					Toyota_stock as ts) q4
					on
        				toyotaDate1=toyotaDate2
;