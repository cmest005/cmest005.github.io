    SELECT
    @csale := CarSales,
    @facc := FatalAccidents,
    @ac := avg(CarSales),
    @af := avg(FatalAccidents),
    @sc := sum(CarSales),
    @sf := sum(FatalAccidents)
    
    FROM(
        SELECT
            count(*) AS CarSales,
            fatal_accidents_toyota.number_of_accidents AS FatalAccidents
        
            FROM
                cars            
                INNER JOIN
                    fatal_accidents_toyota
                ON
                    YEARWEEK(`Sale_date`) = YEARWEEK(`Date`) 
                
                WHERE `New_vehicle_make` = "Toyota" AND `State` = "FL"
                GROUP BY
                    YEARWEEK(`Sale_date`)       
            )AS blah;

     SELECT 
        @csac := CarSales-@ac,
        @faab := FatalAccidents-@af,
        @csacs := sum((CarSales-@ac)*(CarSales-@ac)),      
        @fsafs := sum((FatalAccidents-@af)*(FatalAccidents-@af)),
        @csactbsaf := sum(((CarSales-@ac)*(FatalAccidents-@af)))

       FROM(
        SELECT
            count(*) AS CarSales,
            fatal_accidents_toyota.number_of_accidents AS FatalAccidents
        
            FROM
                cars            
                INNER JOIN
                    fatal_accidents_toyota
                ON
                    YEARWEEK(`Sale_date`) = YEARWEEK(`Date`) 
                
                WHERE `New_vehicle_make` = "Toyota" AND `State` = "FL"
                GROUP BY
                    YEARWEEK(`Sale_date`)       
            )AS blah;

    SELECT
        TRUNCATE(@csactbsaf,0) AS "(X-Mx)(Y-My)",
        TRUNCATE(@fsafs,0) AS "SUM(X-Mx)^2",
        TRUNCATE(@csacs,0) AS "SUM(Y-My)^2",
        TRUNCATE(@csactbsaf/ SQRT(@fsafs * @csacs),4) AS "r";