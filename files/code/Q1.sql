SELECT 
	@cs := cs,
	@ampg := avg(nvcm) - avg(tim),
	@appg := avg(ppg),
	@nvcm := avg(nvcm),
	@tim := avg(tim)
	
	FROM(
		SELECT
			cars.State AS cs,
			cars.New_vehicle_car_mileage AS nvcm,
			cars.Trade_in_mileage AS tim,
			fuelp.PPG as ppg
		FROM 
			cars 
			INNER JOIN 
				fuelp 
			ON 
				MONTH(`Sale_date`) = MONTH(`Date`)	
			WHERE 
			cars.State = "FL" AND MONTH(`DATE`) in (7,8,9,10))AS blah;


SELECT
	@cs AS "State",
	TRUNCATE(@tim,2) AS "Old AVG MPG",
	TRUNCATE(@nvcm,2) AS "New AVG MPG",
	TRUNCATE(@ampg,2) AS "Savings in MPG",
	CONCAT('$',TRUNCATE(@appg,2)) AS "AVG PPG",
	CONCAT('$',TRUNCATE(@appg * 25 / @tim,2)) AS "Old: Cost 25 miles",
	CONCAT('$',TRUNCATE(@appg * 25 / @nvcm,2)) AS "New: Cost 25 miles",
	CONCAT('$',TRUNCATE((@appg * 25 / @tim) - (@appg * 25 / @nvcm),2)) AS "Savings per 25 miles",
	CONCAT('$',TRUNCATE((@appg * 15000 / @tim) - (@appg * 15000 / @nvcm),2)) AS "Avg Annual Savings: 15k miles";
